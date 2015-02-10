package org.example;

import com.vaadin.cdi.ViewScoped;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.example.backend.Invoicer;
import org.example.backend.UserSession;
import org.vaadin.viritin.fields.TypedSelect;

/**
 *
 * @author Matti Tahvonen
 */
@ViewScoped
public class InvoicerSelect extends TypedSelect<Invoicer> {
    
    @Inject
    UserSession session;
    
    public InvoicerSelect() {
        super(Invoicer.class);
        setSizeUndefined();
        setNullSelectionAllowed(false);
    }

    @PostConstruct
    void init() {
        setBeans(session.getUser().getAdministrates());
        selectFirst();
    }

}
