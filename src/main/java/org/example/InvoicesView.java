package org.example;

import com.vaadin.cdi.annotation.CdiComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import org.example.backend.Invoice;
import org.example.backend.UserSession;
import org.example.backend.service.InvoiceFacade;
import org.example.backend.service.InvoicerFacade;
import org.vaadin.firitin.appframework.MenuItem;
import org.vaadin.firitin.components.DynamicFileDownloader;
import org.vaadin.firitin.components.button.ConfirmButton;
import org.vaadin.firitin.components.button.DeleteButton;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.orderedlayout.VHorizontalLayout;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import java.io.IOException;

@Route(layout = MainLayout.class)
@MenuItem(order = MenuItem.END)
@CdiComponent
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

    DynamicFileDownloader backup = new DynamicFileDownloader("Download backup", "backup.xml",
            out -> invoicerFacade.writeAsXml(sender.getValue(), out)
    );

    @PostConstruct
    public void initComponent() {
        table.addComponentColumn(this::getInvoiceActions).setHeader("actions");
        table.getColumns().forEach(c -> c.setResizable(true).setAutoWidth(true));

        if (session.getUser().getAdministrates().isEmpty()) {
            Notification.show("Add invoicer first!");
            getUI().get().navigate(MyAccount.class);
            return;
        }
        sender.addValueChangeListener(e -> listEntities());

        form.setResetHandler(this::reset);
        form.setSavedHandler(this::save);

        table.asSingleSelect().addValueChangeListener(e -> {
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
                new VHorizontalLayout(addButton, sender).space().withComponents(backup)
                        .alignAll(Alignment.CENTER)
        );
        addAndExpand(table);
    }

    public static final int DEFAULT_DUE_DATE_DURATION = 14;

    private void listEntities() {
        table.setItems(facade.findAll(sender.getValue()));
    }

    public VHorizontalLayout getInvoiceActions(Invoice invoice) {
        final VHorizontalLayout actions = new VHorizontalLayout();
        if (invoice.getInvoicer() != null) {
            final DynamicFileDownloader odtDownload = new DynamicFileDownloader(" odt", "invoice_" + invoice.getInvoiceNumber() + ".odt",
                    out -> facade.writeAsOdt(invoice, out)) // .withIcon(VaadinIcon.FILE_TEXT_O.create())
                    //.withStyleName(ValoTheme.BUTTON_ICON_ONLY)
                    ;
            odtDownload.addComponentAsFirst(VaadinIcon.DOWNLOAD.create());

            final DynamicFileDownloader pdfDownload = new DynamicFileDownloader(" pdf", "invoice_" + invoice.getInvoiceNumber() + ".pdf",
                    out -> facade.writeAsPdf(invoice, out));
            pdfDownload.addComponentAsFirst(VaadinIcon.DOWNLOAD_ALT.create());

            final VButton sendInvoice = new VButton()
                    .withIcon(VaadinIcon.ENVELOPE_O.create())
                    //.withStyleName(ValoTheme.BUTTON_ICON_ONLY)
                    .withClickListener(e -> sendInvoiceClicked(invoice))
                    .withThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);
            actions.add(odtDownload, pdfDownload, sendInvoice);
        }

        final ConfirmButton deleteButton = new DeleteButton()
                .withConfirmHandler(() -> {
                    facade.remove(invoice);
                    listEntities();
                })
                .withConfirmationPrompt("Are you sure you want to delete this invoice");

        deleteButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);

        actions.add(deleteButton);
        actions.alignAll(Alignment.CENTER);
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
        facade.sendInvoice(invoice);
        Notification.show("Invoice sent");
    }

}
