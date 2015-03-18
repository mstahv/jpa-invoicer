package org.example.auth;

import com.google.gson.Gson;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Page;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinServletResponse;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import java.io.IOException;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.example.backend.UserSession;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.label.Header;
import org.vaadin.viritin.layouts.MVerticalLayout;

/**
 *
 * @author Matti Tahvonen
 */
public class LoginWindow extends Window implements RequestHandler {

    private final Header loginHeader = new Header("Login with Google+");

    private final Button gplusLoginButton = new MButton("Login");

    OAuthService service;

    @Inject
    UserSession userSession;
    final String redirectUrl;

    @Override
    public void attach() {
        super.attach();

        setContent(new MVerticalLayout(loginHeader, gplusLoginButton));

        service = createService();
        String url = service.getAuthorizationUrl(null);

        BrowserWindowOpener opener = new BrowserWindowOpener(url);
        opener.setWindowName("_top");
        opener.extend(gplusLoginButton);

        VaadinSession.getCurrent().addRequestHandler(this);

        setModal(true);

    }

    public void authDenied(String reason) {
        Notification.show("authDenied:" + reason,
                Notification.Type.ERROR_MESSAGE);
    }

    private OAuthService createService() {
        ServiceBuilder sb = new ServiceBuilder();
        sb.provider(Google2Api.class);
        sb.apiKey(gpluskey);
        sb.apiSecret(gplussecret);
        sb.scope("email");
        String callBackUrl = Page.getCurrent().getLocation().toString();
        if(callBackUrl.contains("#")) {
            callBackUrl = callBackUrl.substring(0, callBackUrl.indexOf("#"));
        }
        sb.callback(callBackUrl);
        return sb.build();
    }

    public LoginWindow() {
        super("Login");
        redirectUrl = Page.getCurrent().getLocation().toString();

    }

    @Override
    public boolean handleRequest(VaadinSession session, VaadinRequest request,
            VaadinResponse response) throws IOException {
        if (request.getParameter("code") != null) {
            String code = request.getParameter("code");
            Verifier v = new Verifier(code);
            Token t = service.getAccessToken(null, v);

            OAuthRequest r = new OAuthRequest(Verb.GET,
                    "https://www.googleapis.com/plus/v1/people/me");
            service.signRequest(t, r);
            Response resp = r.send();

            GooglePlusAnswer answer = new Gson().fromJson(resp.getBody(),
                    GooglePlusAnswer.class);

            userSession.login(answer.emails[0].value, answer.displayName);

            close();
            VaadinSession.getCurrent().removeRequestHandler(this);

            ((VaadinServletResponse) response).getHttpServletResponse().
                    sendRedirect(redirectUrl);
            return true;
        }

        return false;
    }

    @Inject
    @ConfigProperty(name = "jpa-invoicer.gpluskey")
    private String gpluskey;

    @Inject
    @ConfigProperty(name = "jpa-invoicer.gplussecret")
    private String gplussecret;

}
