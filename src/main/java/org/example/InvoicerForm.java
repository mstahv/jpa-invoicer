package org.example;

import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import org.example.backend.Invoicer;
import org.example.backend.User;
import org.vaadin.easyuploads.UploadField;
import org.vaadin.viritin.fields.ElementCollectionField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;

@ViewScoped
public class InvoicerForm extends AbstractForm<Invoicer> {

    TextField name = new MTextField("name");

    TextField address = new MTextField("address");

    TextField email = new MTextField("email");

    TextField phone = new MTextField("phone");

    TextField bankAccount = new MTextField("bankAccount");

    TextField nextInvoiceNumber = new MTextField("next invoice number");

    UploadField template = new TemplateField();

    public static class UserRow {

        MTextField email = new MTextField();
    }

    ElementCollectionField<User> administrators
            = new ElementCollectionField<>(User.class, UserRow.class)
            .expand("email")
            .withCaption("Users")
            .withFullWidth();

    @Override
    protected Component createContent() {

        return new MVerticalLayout(
                getToolbar(),
                new FormLayout(
                        name,
                        address,
                        phone,
                        email,
                        bankAccount,
                        nextInvoiceNumber,
                        template,
                        administrators
                )
        );
    }

    @Override
    public Window openInModalPopup() {
        final Window window = super.openInModalPopup();
        window.setHeight("80%");
        window.setWidth("50%");
        return window;
    }

}
