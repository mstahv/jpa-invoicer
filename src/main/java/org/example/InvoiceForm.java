package org.example;

import com.vaadin.cdi.annotation.RouteScoped;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H3;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import org.example.backend.Invoice;
import org.example.backend.InvoiceRow;
import org.example.backend.Product;
import org.example.backend.service.ProductFacade;
import org.vaadin.firitin.components.combobox.VComboBox;
import org.vaadin.firitin.components.orderedlayout.VHorizontalLayout;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.components.textfield.VIntegerField;
import org.vaadin.firitin.components.textfield.VNumberField;
import org.vaadin.firitin.components.textfield.VTextField;
import org.vaadin.firitin.fields.ElementCollectionField;
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

    public static final class RowEditor {
        VComboBox<Product> product = new VComboBox<Product>()
                .withWidth("150px")
                .withPlaceholder("Pick a product");
        VTextField description = new VTextField().withFullWidth();
        VNumberField quantity = new VNumberField().withWidth("3em");
        VTextField unit = new VTextField().withWidth("3em");
        VNumberField price = new VNumberField().withWidth("3em");
    }

    ElementCollectionField<InvoiceRow> invoiceRows = new ElementCollectionField<>(
            InvoiceRow.class, RowEditor.class)/*.expand("description")*/
            .withEditorInstantiator(() -> {
                RowEditor r = new RowEditor();
                r.product.setItems(
                        productFacade.findActiveByInvoicer(getEntity().getInvoicer()));
                r.product.addValueChangeListener(event -> {
                    if(event.getValue() != null) {
                        // Copy "default values" from Product to row
                        r.price.setValue(event.getValue().getPrice());
                        r.unit.setValue(event.getValue().getUnit());
                        r.description.focus();
                    }
                });
                return r;
    });

    @PostConstruct
    void init() {
        // Allowing null in DB, but not when modified by the UI
        /* TODO looks to be impossible these days...
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
         */
        invoiceRows.addValueChangeListener(e -> updateTotal());
    }

    @Override
    public void setEntity(Invoice entity) {
        super.setEntity(entity);
        updateTotal();
    }

    @Override
    protected Component createContent() {

        return new VVerticalLayout(
                new VHorizontalLayout(getToolbar())
                        .withComponent(total)
                        .withFullWidth()
                        .alignAll(FlexComponent.Alignment.CENTER),
                new VVerticalLayout(
                        to,
                        new VHorizontalLayout(invoiceDate, dueDate),
                        invoiceRows
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
