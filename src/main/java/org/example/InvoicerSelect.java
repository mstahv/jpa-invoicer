package org.example;

import com.vaadin.cdi.annotation.RouteScoped;
import com.vaadin.flow.component.select.Select;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.example.backend.Invoicer;
import org.example.backend.UserSession;

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
        setItems(session.getUser().getAdministrates());
        setValue(session.getUser().getAdministrates().get(0));
    }

}
