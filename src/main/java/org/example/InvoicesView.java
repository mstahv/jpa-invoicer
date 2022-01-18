package org.example;

import java.io.IOException;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.mail.EmailException;
import org.example.backend.Invoice;
import org.example.backend.UserSession;
import org.example.backend.service.InvoiceFacade;
import org.example.backend.service.InvoicerFacade;
import org.vaadin.firitin.appframework.MenuItem;
import org.vaadin.firitin.components.DynamicFileDownloader;
import org.vaadin.firitin.components.button.DeleteButton;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.orderedlayout.VHorizontalLayout;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

@Route(layout = MainLayout.class)
@MenuItem(order = MenuItem.END)
public class InvoicesView extends VVerticalLayout {

    @Inject
    InvoiceFacade facade;

    @Inject
    InvoicerFacade invoicerFacade;

    @Inject
    UserSession session;

    @Inject
    InvoiceForm form;

    @Inject
    InvoicerSelect sender;

    VGrid<Invoice> table = new VGrid<>(Invoice.class)
            .withFullWidth()
            .withProperties("invoiceNumber", "to", "invoiceDate", "lastEdited",
                    "lastEditor");
            //.withColumnHeaders("Nr", "To", "Date", "Last edited", "Edited by")

    DynamicFileDownloader backup = new DynamicFileDownloader("Download backup","backup.xml",
            out -> invoicerFacade.writeAsXml(sender.getValue(), out)
    );

    @PostConstruct
    public void initComponent() {
        table.addComponentColumn(this::getInvoiceActions).setHeader("actions");
        table.getColumnByKey("invoiceDate").setWidth("110px").setFlexGrow(0);
        table.getColumnByKey("lastEdited").setWidth("110px").setFlexGrow(0);
        table.getColumnByKey("lastEditor").setWidth("110px").setFlexGrow(0);

        if (session.getUser().getAdministrates().isEmpty()) {
            Notification.show("Add invoicer first!");
            getUI().get().navigate(MyAccount.class);
            return;
        }
        sender.addValueChangeListener(e -> listEntities());

        form.setResetHandler(this::reset);
        form.setSavedHandler(this::save);

        table.asSingleSelect().addValueChangeListener(e->{
            if (e.getValue() != null && e.isFromClient()) {
                form.setEntity(e.getValue());
                MainLayout.get().openSubView(form, "Edit invoice");
            }

        });

        listEntities();

        Button addButton = new Button("Add", e -> {
            final Invoice invoice = facade.createFor(sender.getValue());
            form.setEntity(invoice);
            MainLayout.get().openSubView(form, "Create invoice");
        });

        add(
                new VHorizontalLayout(addButton, sender)/*.space()*/.withComponents(backup)
                .alignAll(Alignment.CENTER)
        );
        addAndExpand(table);
        expand(table);
    }

    public static final int DEFAULT_DUE_DATE_DURATION = 14;

    private void listEntities() {
        table.setItems(facade.findAll(sender.getValue()));
    }

    public VHorizontalLayout getInvoiceActions(Invoice invoice) {
        final VHorizontalLayout actions = new VHorizontalLayout();
        if (invoice.getInvoicer() != null) {
            final DynamicFileDownloader odtDownload = new DynamicFileDownloader("odt", "invoice_" + invoice.getInvoiceNumber() + ".odt",
                    out -> facade.writeAsOdt(invoice, out))
                    // .withIcon(VaadinIcon.FILE_TEXT_O.create())
                    //.withStyleName(ValoTheme.BUTTON_ICON_ONLY)
                    ;

            final DynamicFileDownloader pdfDownload = new DynamicFileDownloader("pdf","invoice_" + invoice.getInvoiceNumber() + ".pdf",
                    out -> facade.writeAsPdf(invoice, out))
                    //.withIcon(VaadinIcon.FILE_TEXT_O.create())
                    //.withStyleName(ValoTheme.BUTTON_ICON_ONLY)
                    ;

            final VButton sendInvoice = new VButton()
                    .withIcon(VaadinIcon.ENVELOPE_O.create())
                    //.withStyleName(ValoTheme.BUTTON_ICON_ONLY)
                    .withClickListener(e -> sendInvoiceClicked(invoice));
            actions.add(odtDownload, pdfDownload, sendInvoice);
        }

        final DeleteButton deleteButton = new DeleteButton()
                .withIcon(VaadinIcon.TRASH.create())
                .withPromptText("Are you sure you want to delete this invoice")
                .withConfirmHandler( () -> {
                    facade.remove(invoice);
                    listEntities();
                }
        );
        actions.add(deleteButton);
        return actions;
    }

    public void save(Invoice entity) {
        facade.save(entity);
        Notification.show("Saved!");
        MainLayout.get().closeSubView(form);
        listEntities();
    }

    public void reset(Invoice entity) {
        // just hide the form
        MainLayout.get().closeSubView(form);
        listEntities();
    }

    private void sendInvoiceClicked(final Invoice invoice) {
        try {
            facade.sendInvoice(invoice);
            Notification.show("Invoice sent");
        } catch (EmailException | IOException e) {
            e.printStackTrace();
            Notification.show("Error sending the invoice"
                    // TODO , Notification.Type.ERROR_MESSAGE
            );
        }
    }

}
