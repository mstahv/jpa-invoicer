package org.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import org.example.backend.Product;
import org.example.backend.service.ProductFacade;
import org.vaadin.firitin.appframework.MenuItem;
import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.orderedlayout.VHorizontalLayout;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

/**
 *
 * @author Mortoza Khan
 */
@Route(layout = MainLayout.class)
@MenuItem(title = "Products", order = MenuItem.END)
public class ProductView extends VVerticalLayout {

    @Inject
    ProductFacade facade;
    @Inject
    ProductForm form;

    @Inject
    InvoicerSelect invoicerSelect;

    // TODO PrimaryButon
    Button newButton = new Button("New", e -> {
        Product p = new Product();
        p.setInvoicer(invoicerSelect.getValue());
        form.setEntity(p);
        form.openInModalPopup();
    });

    VGrid<Product> table = new VGrid<>(Product.class)
            .withProperties("name", "price", "unit", "productState");

    @PostConstruct
    public void initComponent() {

        invoicerSelect.addValueChangeListener(e -> listEntities());

        form.setResetHandler(this::reset);
        form.setSavedHandler(this::save);

        table.setWidth("400px");
        //table.setColumnCollapsingAllowed(true);
        
        table.asSingleSelect().addValueChangeListener(e -> {
            form.setEntity(e.getValue());
            form.openInModalPopup();
        });

        listEntities();

        add(new H1("Product listing"),
                new VHorizontalLayout(invoicerSelect, newButton)
                .alignAll(Alignment.CENTER),
                table
        );
    }

    private void listEntities() {
        table.setItems(facade.findByInvoicer(invoicerSelect.getValue()));
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

}
