package org.example;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import org.example.backend.Contact;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;

public class ContactForm extends AbstractForm<Contact> {

    TextField name = new MTextField("name");

    TextField address1 = new MTextField("address1");
    TextField address2 = new MTextField("address2");
    TextField address3 = new MTextField("address3");
    TextField phone = new MTextField("phone");

    TextField email = new MTextField("email");

    @Override
    protected Component createContent() {
        return new MVerticalLayout(
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
