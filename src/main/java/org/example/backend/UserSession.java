package org.example.backend;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.example.backend.service.InvoicerFacade;
import org.example.backend.service.UserFacade;

/**
 *
 * @author Matti Tahvonen
 */
@SessionScoped
public class UserSession implements Serializable {

    @Inject
    UserFacade userFacade;

    @Inject
    InvoicerFacade invoicerFacade;

    private User user;

    @PostConstruct
    public void init() {
        final String propertyValue = ConfigResolver.getPropertyValue(
                "jpa-invoicer.gpluskey");
        // If no Google OAuth API key available, use fake login
        if (StringUtils.isEmpty(propertyValue)) {
            demoLogin();
        }
    }

    protected void demoLogin() {
        final String email = "matti.meikalainen@gmail.com";
        this.user = userFacade.findByEmail(email);
        if (this.user == null) {
            final User user = new User(email);
            userFacade.create(user);
            this.user = userFacade.findByEmail(email);

            Invoicer invoicer = new Invoicer();
            invoicer.setName("Matin pummpu ja imu");
            invoicer.setAddress("Ruukinkatu 4, 20100 Turku");
            invoicer.setBankAccount("FI1234567890");
            invoicer.setEmail("matti@pumppu.fi");
            invoicer.setPhone("+34567890");
            invoicer.getAdministrators().add(this.user);
            this.user.getAdministrates().add(invoicer);
            invoicerFacade.save(invoicer);
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
