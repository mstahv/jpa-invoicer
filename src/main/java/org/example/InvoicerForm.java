package org.example;

import com.vaadin.cdi.annotation.RouteScoped;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.example.backend.Invoicer;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.components.textfield.VTextField;
import org.vaadin.firitin.form.AbstractForm;

@RouteScoped
public class InvoicerForm extends AbstractForm<Invoicer> {

    TextField name = new VTextField("name");

    TextField address = new VTextField("address");

    TextField email = new VTextField("email");

    TextField phone = new VTextField("phone");

    TextField bankAccount = new VTextField("bankAccount");

    TextField nextInvoiceNumber = new VTextField("next invoice number");

    public InvoicerForm() {
        super(Invoicer.class);
    }

// TODO    UploadField template = new TemplateField();

    public static class UserRow {

        VTextField email = new VTextField();
    }
/* TODO
    ElementCollectionField<User> administrators
            = new ElementCollectionField<>(User.class, UserRow.class)
            .expand("email")
            .withCaption("Users")
            .withFullWidth();
*/
    @Override
    protected Component createContent() {

        return new VVerticalLayout(
                getToolbar(),
                new FormLayout(
                        name,
                        address,
                        phone,
                        email,
                        bankAccount,
                        nextInvoiceNumber
//                        template,
//                        administrators
                )
        );
    }
}
