package org.example;

import com.vaadin.cdi.annotation.RouteScoped;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H3;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.example.backend.Invoice;
import org.example.backend.Product;
import org.example.backend.service.ProductFacade;
import org.vaadin.firitin.components.combobox.VComboBox;
import org.vaadin.firitin.components.orderedlayout.VHorizontalLayout;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.components.textfield.VTextField;
import org.vaadin.firitin.form.AbstractForm;

@RouteScoped
public class InvoiceForm extends AbstractForm<Invoice> {
    
    @Inject
    ContactSelector to;
    
    @Inject
    ProductFacade productFacade;
    
    DatePicker invoiceDate = new DatePicker("Date");
    
    DatePicker dueDate = new DatePicker("Due Date");
    
    H3 total = new H3("");

    public InvoiceForm() {
        super(Invoice.class);
    }

    public static final class RowEditorModel {
        
        VComboBox<Product> product = new VComboBox<Product>()
                .withWidth("150px")
                .withPlaceholder("Pick a product");
        VTextField description = new VTextField().withFullWidth();
        VTextField quantity = new VTextField().withWidth("3em");
        VTextField unit = new VTextField().withWidth("3em");
        VTextField price = new VTextField().withWidth("3em");
        
    }

/* TODO    
    ElementCollectionField<InvoiceRow> invoiceRows = new ElementCollectionField<>(
            InvoiceRow.class, RowEditorModel.class).expand("description")
            .withEditorInstantiator(() -> {
                RowEditorModel r = new RowEditorModel();
                r.product.setOptions(
                        productFacade.findActiveByInvoicer(getEntity().getInvoicer()));
                r.product.addMValueChangeListener(event -> {
                    if(event.getValue() != null) {
                        // Copy "default values" from Product to row
                        r.price.setValue(event.getValue().getPrice().toString());
                        r.unit.setValue(event.getValue().getUnit());

                        r.description.focus();
                    }
                });
                return r;
    });
*/    
    @PostConstruct
    void init() {
        // Allowing null in DB, but not when modified by the UI
        /* TODO
        to.setRequired(true);
        to.addValidator(new AbstractValidator("Receiver must be set") {
            
            @Override
            protected boolean isValidValue(Object value) {
                return value != null;
            }
            
            @Override
            public Class getType() {
                return Contact.class;
            }
        });
        invoiceRows.addValueChangeListener(e -> updateTotal());
*/
    }
    
    @Override
    protected Component createContent() {
        
        return new VVerticalLayout(
                new VHorizontalLayout(getToolbar()).withComponent(total).withFullWidth(),
                new VVerticalLayout(
                        to,
                        new VHorizontalLayout(invoiceDate, dueDate)
                        // TODO,
                        //invoiceRows
                )
        );
    }
    
    void updateTotal() {
        try {
            total.setText(getEntity().getTotal() + " €");
        } catch (Exception e) {
            total.setText("--");
        }
    }
        
}
