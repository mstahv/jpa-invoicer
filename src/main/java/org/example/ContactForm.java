package org.example;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.example.backend.Contact;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.components.textfield.VTextField;
import org.vaadin.firitin.form.AbstractForm;

public class ContactForm extends AbstractForm<Contact> {

    TextField name = new VTextField("name");

    TextField address1 = new VTextField("address1");
    TextField address2 = new VTextField("address2");
    TextField address3 = new VTextField("address3");
    TextField phone = new VTextField("phone");

    TextField email = new VTextField("email");

    public ContactForm() {
        super(Contact.class);
    }

    @Override
    protected Component createContent() {
        return new VVerticalLayout(
                new FormLayout(
                        name,
                        address1,
                        address2,
                        address3,
                        phone,
                        email
                ),
                getToolbar()
        );
    }

}
