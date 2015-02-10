package org.example;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.example.backend.Invoice;
import org.example.backend.UserSession;
import org.example.backend.service.InvoiceFacade;
import org.example.backend.service.InvoicerFacade;
import org.vaadin.cdiviewmenu.ViewMenuUI;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.DownloadButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

@CDIView("invoice")
public class InvoicesView extends MVerticalLayout implements View {

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

    MTable<Invoice> table = new MTable<>(Invoice.class)
            .withFullWidth()
            .withProperties("invoiceNumber", "to", "invoiceDate", "lastEdited",
                    "lastEditor")
            .withColumnHeaders("Nr", "To", "Date", "Last edited", "Edited by")
            .withGeneratedColumn("actions", this::getInvoiceActions);

    Button backup = new DownloadButton(
            out -> invoicerFacade.writeAsXml(sender.getValue(), out)
    )
            .withIcon(FontAwesome.DOWNLOAD)
            .setFileName("backup.xml")
            .withDescription("Download backup");

    @PostConstruct
    public void initComponent() {
        table.setColumnCollapsingAllowed(true);
        table.setColumnWidth("invoiceDate", 105);
        table.setColumnWidth("lastEdited", 105);
        table.setColumnWidth("lastEditor", 110);
        if (session.getUser().getAdministrates().isEmpty()) {
            Notification.show("Add invoicer first!");
            ViewMenuUI.getMenu().navigateTo(InvoicerGroupsView.class);
            return;
        }
        sender.addMValueChangeListener(e -> listEntities());

        form.setResetHandler(this::reset);
        form.setSavedHandler(this::save);

        table.addMValueChangeListener(e -> {
            if (e.getValue() != null) {
                form.setEntity(e.getValue());
                form.openInModalPopup();
            }
        });

        listEntities();

        Button addButton = new Button("Add", e -> {
            final Invoice invoice = facade.createFor(sender.getValue());
            form.setEntity(invoice);
            form.openInModalPopup();
        });

        add(
                new MHorizontalLayout(addButton, sender).space().add(backup)
                .alignAll(Alignment.MIDDLE_LEFT)
        );
        expand(table);
    }

    public static final int DEFAULT_DUE_DATE_DURATION = 14 * 24 * 60 * 60 * 1000;

    private void listEntities() {
        table.setBeans(facade.findAll(sender.getValue()));
    }

    public MHorizontalLayout getInvoiceActions(Invoice invoice) {
        final MHorizontalLayout actions = new MHorizontalLayout();
        if (invoice.getInvoicer() != null) {
            final MButton odtDownload = new DownloadButton(
                    out -> facade.writeAsOdt(invoice, out))
                    .setFileName(
                            "invoice_" + invoice.getInvoiceNumber() + ".odt")
                    .withIcon(FontAwesome.FILE_WORD_O)
                    .withStyleName(ValoTheme.BUTTON_ICON_ONLY);

            final MButton pdfDownload = new DownloadButton(
                    out -> facade.writeAsPdf(invoice, out))
                    .setFileName(
                            "invoice_" + invoice.getInvoiceNumber() + ".pdf")
                    .withIcon(FontAwesome.FILE_PDF_O)
                    .withStyleName(ValoTheme.BUTTON_ICON_ONLY);
            actions.add(odtDownload, pdfDownload);
        }

        final MButton deleteButton = new ConfirmButton(
                FontAwesome.TRASH_O,
                "Are you sure you want to delete this invoice",
                e -> {
                    facade.remove(invoice);
                    listEntities();
                }
        ).withStyleName(ValoTheme.BUTTON_ICON_ONLY);
        actions.add(deleteButton);
        return actions;
    }

    public void save(Invoice entity) {
        facade.edit(entity);
        Notification.show("Saved!");
        form.getPopup().close();
        listEntities();
    }

    public void reset(Invoice entity) {
        // just hide the form
        form.getPopup().close();
        listEntities();
    }

    @Override
    public
            void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
