package org.example;

import com.vaadin.cdi.annotation.RouteScopeOwner;
import com.vaadin.cdi.annotation.RouteScoped;
import com.vaadin.flow.component.select.Select;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.example.backend.Invoicer;
import org.example.backend.UserSession;

import java.util.List;

/**
 *
 * @author Matti Tahvonen
 */
@RouteScoped
public class InvoicerSelect extends Select<Invoicer> {
    
    @Inject
    UserSession session;
    
    public InvoicerSelect() {
        setSizeUndefined();
    }

    @PostConstruct
    void init() {
        List<Invoicer> invoicers = session.getInvoicers();
        setItems(invoicers);
        setValue(invoicers.get(0));
    }

}
