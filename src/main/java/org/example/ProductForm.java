package org.example;

import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import javax.inject.Inject;
import org.example.backend.Product;
import org.example.backend.service.UserFacade;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;

@ViewScoped
public class ProductForm extends AbstractForm<Product> {

    TextField name = new MTextField("name");

    @Inject
    UserFacade userFacade;

    @Override
    protected Component createContent() {
        return new MVerticalLayout(
                getToolbar(),
                new FormLayout(
                        name
                )
        );
    }

}
