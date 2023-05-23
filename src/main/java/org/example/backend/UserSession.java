package org.example.backend;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import static org.example.InvoicesView.DEFAULT_DUE_DATE_DURATION;
import org.example.backend.service.InvoiceFacade;
import org.example.backend.service.InvoicerFacade;
import org.example.backend.service.ProductFacade;
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

    @Inject
    ProductFacade productFacade;

    @Inject
    InvoiceFacade invoiceFacade;
    
    private User user;
    private String image;

    @PostConstruct
    public void init() {
        final String propertyValue = System.getenv("JPA_INVOICER_GAPPKEY");
        // If no Google OAuth API key available, use fake login
        if (StringUtils.isEmpty(propertyValue)) {
            demoLogin();
        }
    }

    protected void demoLogin() {
        final String email = "info@vacuumandsuck.com";
        this.user = userFacade.findByEmail(email);
        if (user == null) {
            this.user = userFacade.save(new User(email));

            Invoicer invoicer = new Invoicer();
            invoicer.setName("Vacuum & Suck Ltd");
            invoicer.setAddress("Ruukinkatu 4, 20100 Turku");
            invoicer.setBankAccount("FI1234567890");
            invoicer.setEmail("matti@pumppu.fi");
            invoicer.setPhone("+34567890");
            invoicer.getAdministrators().add(this.user);
            try {
                invoicer.setTemplate(Invoice.class.getResourceAsStream("/tmpl.odt").readAllBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.user.getAdministrates().add(invoicer);
            Integer nextInvoiceNumber = invoicer.getAndIcrementNextInvoiceNumber();
            
            invoicer = invoicerFacade.save(invoicer);
            Product product = new Product();
            product.setName("Pump");
            product.setPrice(30.0);
            product.setInvoicer(invoicer);
            productFacade.save(product);
            product = new Product();
            product.setName("Hoover");
            product.setPrice(60.0);
            product.setInvoicer(invoicer);
            product = productFacade.save(product);
            
            Invoice invoice = new Invoice();
            invoice.setInvoiceNumber(nextInvoiceNumber);
            invoice.setInvoicer(invoicer);
            invoice.setInvoiceDate(LocalDate.now());
            invoice.setDueDate(LocalDate.now().plusDays(DEFAULT_DUE_DATE_DURATION));
            invoice.setLastEditor(user);
        
            invoice.setDueDate(LocalDate.now().plusDays(14));
            Contact c = new Contact("carl@customer.com", "Carl Customer");
            c.setInvoicer(invoicer);
            invoice.setTo(c);
            InvoiceRow invoiceRow = new InvoiceRow();
            invoiceRow.setDescription("New model!");
            invoiceRow.setProduct(product);
            invoiceRow.setPrice(product.getPrice());
            invoiceRow.setQuantity(1.0);
            invoiceRow.setUnit("pcs");
            invoice.getInvoiceRows().add(invoiceRow);
            invoiceFacade.save(invoice);
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

    public void login(String email, String image) {
        try {
            user = userFacade.findByEmail(email);
            this.image = image;
        } catch (Exception e) {
        }
        if (user == null) {
            userFacade.save(new User(email));
            user = userFacade.findByEmail(email);
        }
    }

    public List<Invoicer> getInvoicers() {
        return invoicerFacade.findFor(user);
    }

    public String getImage() {
        return image;
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation("https://google.com/");
        VaadinSession.getCurrent().close();
        VaadinSession.getCurrent().getSession().invalidate();
    }
}
