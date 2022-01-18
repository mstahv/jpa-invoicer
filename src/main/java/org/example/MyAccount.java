package org.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.example.backend.Invoicer;
import org.example.backend.UserSession;
import org.example.backend.service.InvoicerFacade;
import org.vaadin.firitin.appframework.MenuItem;
import org.vaadin.firitin.components.RichText;
import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

@Route(layout = MainLayout.class)
@MenuItem(icon = VaadinIcon.LIFEBUOY, order = MenuItem.END)
public class MyAccount extends VVerticalLayout {

    @Inject
    InvoicerFacade cf;

    @Inject
    UserSession session;

    @Inject
    InvoicerForm form;

    VGrid<Invoicer> table = new VGrid<>(Invoicer.class)
            .withProperties("id", "name", "email");

    @PostConstruct
    void init() {

        add(new RichText().withMarkDown("# Your account: " + session.getUser().
                getEmail()));
        
        form.setResetHandler(this::reset);
        form.setSavedHandler(this::save);

        table.asSingleSelect().addValueChangeListener(e -> editEntity(e.getValue()));

        Button addButton = new Button("Add");
        addButton.addClickListener(e -> {
            Invoicer invoicer = new Invoicer();
            invoicer.getAdministrators().add(session.getUser());
            editEntity(invoicer);
        });

        add(
                new H3("Invoicers you administrate"),
                addButton
        );
        addAndExpand(table);
        listEntities();
    }

    private void listEntities() {
        table.setItems(session.getUser().getAdministrates());
    }

    private void editEntity(Invoicer invoicer) {
        if (invoicer != null) {
            if (invoicer.getId() != null) {
                // ensure a fresh entity
                invoicer = cf.findJoined(invoicer.getId());
            }
            form.setEntity(invoicer);
            form.openInModalPopup();
        }
    }

    public void save(Invoicer entity) {
        try {
            if (entity.getId() == null) {
                cf.save(entity);
                session.getUser().getAdministrates().add(entity);
            } else {
                cf.save(entity);
            }
            Notification.show("Saved!");
        } catch (Exception e) {
            Notification.show("Saving failed!"
                    //,"Most probably concurrently edited",
                    //Notification.Type.WARNING_MESSAGE
            );
        }
        listEntities();
        form.getPopup().close();
    }

    public void reset(Invoicer entity) {
        // just hide the form
        form.getPopup().close();
    }

}
