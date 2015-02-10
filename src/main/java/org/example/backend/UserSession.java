package org.example.backend;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.example.backend.service.UserFacade;

/**
 *
 * @author Matti Tahvonen
 */
@SessionScoped
public class UserSession implements Serializable {
    
    @Inject
    UserFacade userFacade;

    private User user;
    
    @PostConstruct
    public void init() {
        // If no Google OAuth API key available, use fake login
        if(ConfigResolver.getPropertyValue("jpa-invoicer.gpluskey") == null) {
            login("matti.meikalainen@gmail.com", "Matti Meikäläinen");
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void login(String email, String displayName) {
        try {
            user = userFacade.findByEmail(email);
        } catch (Exception e) {
        }
        if (user == null) {
            userFacade.create(new User(email));
            user = userFacade.findByEmail(email);
        }
    }

}
