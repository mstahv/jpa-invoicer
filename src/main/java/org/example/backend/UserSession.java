package org.example.backend;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
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
        final String propertyValue = ConfigResolver.getPropertyValue("jpa-invoicer.gpluskey");
        // If no Google OAuth API key available, use fake login
        if(StringUtils.isEmpty(propertyValue)) {
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
