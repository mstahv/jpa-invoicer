package org.example;

import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import org.example.backend.Product;
import org.vaadin.viritin.fields.EnumSelect;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;

@ViewScoped
public class ProductForm extends AbstractForm<Product> {

    TextField name = new MTextField("Name");
    TextField unit = new MTextField("Unit");
    TextField price = new MTextField("Price");
    EnumSelect productState = new EnumSelect("State");

    @Override
    protected Component createContent() {
        return new MVerticalLayout(
                getToolbar(),
                new FormLayout(
                        name,
                        price,
                        unit,
                        productState
                )
        );
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }
    
    

}
