package org.example;

import com.vaadin.cdi.annotation.RouteScoped;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.example.backend.Product;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.components.textfield.VTextField;
import org.vaadin.firitin.form.AbstractForm;

@RouteScoped
public class ProductForm extends AbstractForm<Product> {

    TextField name = new VTextField("Name");
    TextField unit = new VTextField("Unit");
    TextField price = new VTextField("Price");

    public ProductForm() {
        super(Product.class);
    }
    // TODO EnumSelect productState = new EnumSelect("State");

    @Override
    protected Component createContent() {
        return new VVerticalLayout(
                getToolbar(),
                new FormLayout(
                        name,
                        price,
                        unit
                        //,
                 //       productState
                )
        );
    }


}
