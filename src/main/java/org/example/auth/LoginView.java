package org.example.auth;

import com.google.gson.Gson;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.*;

import java.io.IOException;
import java.net.URL;
import jakarta.inject.Inject;

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
@Route
public class LoginView extends VVerticalLayout implements RequestHandler {

    private Anchor googleLoginButton;

    OAuthService service;

    @Inject
    UserSession userSession;
    String redirectUrl;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        if(userSession.isLoggedIn()) {
            UI.getCurrent().navigate("");
            return;
        }


        UI.getCurrent().getPage().fetchCurrentURL(currentUrl -> {

            redirectUrl = currentUrl.toString();
            service = createService(currentUrl);
            String url = service.getAuthorizationUrl(null);

            googleLoginButton = new Anchor(url, "Login with Google");

            VaadinSession.getCurrent().addRequestHandler(this);

            add(googleLoginButton);
            alignAll(FlexComponent.Alignment.CENTER).withFullHeight();

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
        sb.apiKey(googleAppkey);
        sb.apiSecret(googleAppSecret);
        sb.scope("email");
        String callBackUrl = currentUrl.toString();
        // strip away all views / parameters
        callBackUrl = callBackUrl.substring(0, callBackUrl.lastIndexOf("/")) + "/";
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
                    "https://www.googleapis.com/oauth2/v3/userinfo");
            service.signRequest(t, r);
            Response resp = r.send();

            String body = resp.getBody();
            //System.out.println(body);
            GoogleUserinfo answer = new Gson().fromJson(body, GoogleUserinfo.class);

            userSession.login(answer.email, answer.picture);

            getUI().ifPresent(ui ->
                ui.access(() -> {
                    VaadinSession.getCurrent().removeRequestHandler(this);
                })
            );

            ((VaadinServletResponse) response).getHttpServletResponse().
                    sendRedirect(redirectUrl);
            return true;
        }

        return false;
    }

    private final String googleAppkey = System.getenv("JPA_INVOICER_GAPPKEY");

    private final String googleAppSecret = System.getenv("JPA_INVOICER_GAPPSECRET");

}
