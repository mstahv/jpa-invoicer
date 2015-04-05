package org.example;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import com.vaadin.ui.Button;
import org.example.backend.Product;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.label.Header;
import org.vaadin.viritin.layouts.MVerticalLayout;
import org.example.backend.service.ProductFacade;
import org.vaadin.viritin.button.PrimaryButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;

/**
 *
 * @author Mortoza Khan
 */
@CDIView
public class ProductView extends MVerticalLayout implements View {

    @Inject
    ProductFacade facade;
    @Inject
    ProductForm form;

    @Inject
    InvoicerSelect invoicerSelect;

    Button newButton = new PrimaryButton("New", e -> {
        Product p = new Product();
        p.setInvoicer(invoicerSelect.getValue());
        form.setEntity(p);
        form.openInModalPopup();
    });

    MTable<Product> table = new MTable<>(Product.class)
            .withProperties("name", "price", "unit", "productState");

    @PostConstruct
    public void initComponent() {

        invoicerSelect.addMValueChangeListener(e -> listEntities());

        form.setResetHandler(this::reset);
        form.setSavedHandler(this::save);

        table.setWidth("400px");
        //table.setHeight("400px");
        table.setColumnCollapsingAllowed(true);
        table.addMValueChangeListener(e -> {
            form.setEntity(e.getValue());
            form.openInModalPopup();
        });

        listEntities();

        addComponents(new Header("Product listing"),
                new MHorizontalLayout(invoicerSelect, newButton)
                .alignAll(Alignment.MIDDLE_LEFT),
                table
        );
    }

    private void listEntities() {
        table.setBeans(facade.findByInvoicer(invoicerSelect.getValue()));
    }

    public void save(Product entity) {
        facade.save(entity);
        form.getPopup().close();
        listEntities();
        Notification.show("Saved!");
    }

    public void reset(Product entity) {
        // just hide the form
        form.setEntity(null);
        form.getPopup().close();
        listEntities();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
