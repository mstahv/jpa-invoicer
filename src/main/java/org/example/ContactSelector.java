package org.example;

import com.vaadin.cdi.annotation.RouteScoped;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import org.example.backend.Contact;
import org.example.backend.service.ContactFacade;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.orderedlayout.VHorizontalLayout;

/**
 * An entity selector component that allows to dynamically add entities.
 *
 * @author Matti Tahvonen
 */
@RouteScoped
public class ContactSelector extends Composite<HorizontalLayout> implements HasValue<HasValue.ValueChangeEvent<Contact>, Contact> {

    ComboBox<Contact> comboBox = new ComboBox<>();

    @Inject
    private ContactFacade contactFacade;

    @Inject
    InvoicerSelect invoicerSelect;

    ContactForm form = new ContactForm();

    VButton edit = new VButton(VaadinIcon.EDIT.create(), e -> {
        editSelected();
    });

    @PostConstruct
    void init() {
        updatelist();
        edit.setEnabled(false);
        comboBox.addValueChangeListener(e -> edit.setEnabled(
                getValue() != null));

        invoicerSelect.addValueChangeListener(e->updatelist());

        comboBox.setLabel("Customer");

        comboBox.addCustomValueSetListener(e -> addNewItem(e.getDetail()));
        comboBox.setPlaceholder("Type new or choose existing");

    }

    void updatelist() {

        comboBox.setItems(contactFacade.findAll(invoicerSelect.getValue()));
        /* Can't use lazy loading :-( https://github.com/vaadin/flow-components/issues/2524
        comboBox.setItems(lc -> contactFacade.findPaged(
                invoicerSelect.getValue(),
                lc.getFilter().get(),
                lc.getOffset(),
                lc.getLimit()
        ).stream());
         */
    }

    public void addNewItem(String newItemCaption) {
        // make contact with detail
        Contact contact = new Contact();
        contact.setName(newItemCaption);
        contact.setInvoicer(invoicerSelect.getValue());
        form.setEntity(contact);
        form.setSavedHandler(entity -> {
            entity = contactFacade.save(entity);
            MainLayout.get().closeSubView(form);
            updatelist();
            setValue(entity);
        });
        form.setResetHandler(e -> {
            MainLayout.get().closeSubView(form);
        });
        MainLayout.get().openSubView(form, "Edit new customer");
        form.getSaveButton().setEnabled(true); // new item
    }

    private void editSelected() {
        form.setEntity(contactFacade.refresh(getValue()));
        form.setSavedHandler(entity -> {
            contactFacade.save(entity);
            MainLayout.get().closeSubView(form);
            updatelist();
            setValue(entity);
        });
        form.setResetHandler(e -> {
            MainLayout.get().closeSubView(form);
        });
        MainLayout.get().openSubView(form, "Edit existing customer");
    }

    @Override
    protected HorizontalLayout initContent() {
        return new VHorizontalLayout(comboBox, edit).alignAll(FlexComponent.Alignment.BASELINE);
    }

    @Override
    public void setValue(Contact value) {
        comboBox.setValue(value);
    }

    @Override
    public Contact getValue() {
        return comboBox.getValue();
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super ValueChangeEvent<Contact>> listener) {
        return comboBox.addValueChangeListener(listener);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        comboBox.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() {
        return comboBox.isReadOnly();
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        comboBox.setRequiredIndicatorVisible(requiredIndicatorVisible);
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return comboBox.isRequiredIndicatorVisible();
    }
}
