package org.example;

import com.vaadin.cdi.annotation.RouteScoped;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
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
public class ContactSelector extends ComboBox<Contact> {
    protected static final int PAGE_SIZE = 15;

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
        /* TODO
        initList(Contact.class,
                (int firstRow, String filter) -> contactFacade.findPaged(
                        invoicerSelect.getValue(), 
                        filter,
                        firstRow, 
                        PAGE_SIZE),
                (String filter) -> contactFacade.countContacts(
                        invoicerSelect.getValue(), 
                        filter),
                PAGE_SIZE);
*/
        updatelist();
        edit.setEnabled(false);
        addValueChangeListener(e -> edit.setEnabled(
                getValue() != null));

//        invoicerSelect.addMValueChangeListener(e -> updatelist());

        setLabel("Customer");

        /* TODO
        getSelect().setNewItemHandler(this);
        getSelect().setNewItemsAllowed(true);
        */
        setWidth("300px");
        setPlaceholder("Type new or choose existing");

    }

    void updatelist() {
        // TODO
        //refresh();
    }

//    @Override
    public void addNewItem(String newItemCaption) {
        // make contact with detail
        Contact contact = new Contact();
        contact.setName(newItemCaption);
//        contact.setInvoicer(invoicerSelect.getValue());
        form.setEntity(contact);
        form.setSavedHandler(entity -> {
            contactFacade.save(entity);
            form.getPopup().close();
            updatelist();
            setValue(entity);
        });
        form.setResetHandler(e -> {
            form.getPopup().close();
        });
  //      form.openInModalPopup().setCaption("Add new customer");
        form.getSaveButton().setEnabled(true); // new item
    }

    private void editSelected() {
        form.setEntity(contactFacade.refresh(getValue()));
        form.setSavedHandler(entity -> {
            contactFacade.save(entity);
            form.getPopup().close();
            updatelist();
            setValue(entity);
        });
        form.setResetHandler(e -> {
            form.getPopup().close();
        });
    //    form.openInModalPopup().setCaption("Edit customer");

    }

//    @Override
//    protected Component initContent() {
//        final Component compositionRoot = super.initContent();
//        return new VHorizontalLayout(compositionRoot, edit);
//    }
    
}
