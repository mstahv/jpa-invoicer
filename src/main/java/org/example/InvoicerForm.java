package org.example;

import com.vaadin.cdi.annotation.RouteScoped;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import org.example.backend.Invoicer;
import org.example.backend.User;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.components.textfield.VIntegerField;
import org.vaadin.firitin.components.textfield.VTextField;
import org.vaadin.firitin.fields.ByteArrayUploadField;
import org.vaadin.firitin.fields.ElementCollectionField;
import org.vaadin.firitin.form.AbstractForm;
import org.vaadin.stefan.table.TableRow;

@RouteScoped
public class InvoicerForm extends AbstractForm<Invoicer> {

    TextField name = new VTextField("name");

    TextField address = new VTextField("address");

    TextField email = new VTextField("email");

    TextField phone = new VTextField("phone");

    TextField bankAccount = new VTextField("bankAccount");

    VIntegerField nextInvoiceNumber = new VIntegerField("next invoice number");

    public InvoicerForm() {
        super(Invoicer.class);
    }

    ByteArrayUploadField template = new ByteArrayUploadField();

    public static class UserEditor extends AbstractForm<User> {

        VTextField email = new VTextField();

        public UserEditor() {
            super(User.class);
        }

        @Override
        protected Component createContent() {
            return new TableRow(email);
        }
    }

    ElementCollectionField<User> administrators = new ElementCollectionField<>(User.class, UserEditor.class);
/* TODO
    ElementCollectionField<User> administrators
            = new ElementCollectionField<>(User.class, UserRow.class)
            .expand("email")
            .withCaption("Users")
            .withFullWidth();
*/
    @Override
    protected Component createContent() {

        // TODO add API to configure template field

        template.setUploadCaption("Upload new template");
        template.setFileDownloadText("Download current template ( %s )");
        template.setDownloadFileName("template.odt");

        return new VVerticalLayout(
                getToolbar(),
                new FormLayout(
                        name,
                        address,
                        phone,
                        email,
                        bankAccount,
                        nextInvoiceNumber,
                        administrators,
                        template,
                        administrators
                )
        );
    }
}
