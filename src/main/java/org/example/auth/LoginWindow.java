package org.example.auth;

import com.google.gson.Gson;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.Router;
import com.vaadin.flow.server.*;

import java.io.IOException;
import java.net.URL;
import javax.inject.Inject;

import com.vaadin.flow.theme.lumo.Lumo;
import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.example.backend.UserSession;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

/**
 *
 * @author Matti Tahvonen
 */
public class LoginWindow extends Dialog implements RequestHandler {

    private Anchor gplusLoginButton;

    OAuthService service;

    @Inject
    UserSession userSession;
    String redirectUrl;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        UI.getCurrent().getPage().fetchCurrentURL(currentUrl -> {
            redirectUrl = currentUrl.toString();
            service = createService(currentUrl);
            String url = service.getAuthorizationUrl(null);

            gplusLoginButton = new Anchor(url, "Login with Google");
            //TODO gplusLoginButton.addStyleName(ValoTheme.LINK_LARGE);

            VaadinSession.getCurrent().addRequestHandler(this);

            add(new VVerticalLayout(gplusLoginButton)
                    .alignAll(FlexComponent.Alignment.CENTER).withFullHeight());
            setModal(true);
            setWidth("300px");
            setHeight("200px");
        });

    }

    public void authDenied(String reason) {
        Notification.show("authDenied:" + reason
                /* TODO,
                Notification.Type.ERROR_MESSAGE*/);
    }

    private OAuthService createService(URL currentUrl) {
        ServiceBuilder sb = new ServiceBuilder();
        sb.provider(Google2Api.class);
        sb.apiKey(gpluskey);
        sb.apiSecret(gplussecret);
        sb.scope("email");
        String callBackUrl = currentUrl.toString();
        if(callBackUrl.contains("#")) {
            callBackUrl = callBackUrl.substring(0, callBackUrl.indexOf("#"));
        }
        sb.callback(callBackUrl);
        return sb.build();
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
