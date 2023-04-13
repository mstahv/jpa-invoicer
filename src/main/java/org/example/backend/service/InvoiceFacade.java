package org.example.backend.service;

import static org.example.InvoicesView.DEFAULT_DUE_DATE_DURATION;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.mail.util.ByteArrayDataSource;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.example.backend.Invoice;
import org.example.backend.InvoiceRow;
import org.example.backend.Invoicer;
import org.example.backend.UserSession;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

/**
 *
 * @author Matti Tahvonen
 */
@Stateless
public class InvoiceFacade {

    @Inject
    InvoiceRepository repo;

    @Inject
    InvoicerRepository invoicerRepo;

    @Inject
    @ConfigProperty(name = "jpa-invoicer.smtp.username")
    private String smtpUsername;
    @Inject
    @ConfigProperty(name = "jpa-invoicer.smtp.password")
    private String smtpPassword;

    @Inject
    @ConfigProperty(name = "jpa-invoicer.smtp.hostname")
    private String smtpHostname;

    @Inject
    @ConfigProperty(name = "jpa-invoicer.smtp.port")
    private Integer smtpPort;

    @Inject
    @ConfigProperty(name = "jpa-invoicer.smtp.from")
    private String smtpFrom;

    @Inject
    @ConfigProperty(name = "jpa-invoicer.smtp.subject")
    private String smtpSubject;

    @Inject
    @ConfigProperty(name = "jpa-invoicer.smtp.message")
    private String smtpMessage;

    public List<Invoice> findAll(Invoicer value) {
        return repo.findAll();
    }

    public Invoice createFor(Invoicer invoicer) {
        invoicer = invoicerRepo.findBy(invoicer.getId());
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoicer.getAndIcrementNextInvoiceNumber());
        invoice.setInvoicer(invoicer);
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setDueDate(LocalDate.now().plusDays(DEFAULT_DUE_DATE_DURATION));
        invoice.setLastEditor(session.getUser());
        invoicerRepo.save(invoicer);
        repo.save(invoice);
        return invoice;
    }

    @Inject
    UserSession session;

    public Invoice save(Invoice entity) {
        entity.setLastEditor(session.getUser());
        return repo.save(entity);
    }

    public void writeAsOdt(Invoice invoice, OutputStream out) {
        Locale.setDefault(new Locale("fi"));
        invoice = repo.findBy(invoice.getId());
        invoice.getInvoiceRows().size();
        invoice.getInvoicer().getBankAccount();
        invoice.getInvoicer().getBankAccount();
        invoice.getInvoicer().getEmail();
        invoice.getInvoicer().getAddress();
        invoice.getInvoicer().getPhone();

        try {
            // 1) Load ODT file by filling Velocity template engine and cache
            // it to the registry
            InputStream in = getTemplate(invoice.getInvoicer());
            IXDocReport report = XDocReportRegistry.getRegistry().
                    loadReport(in, TemplateEngineKind.Freemarker);

            FieldsMetadata metadata = report.createFieldsMetadata();
            metadata.load("r", InvoiceRow.class, true);

            IContext ctx = report.createContext();
            ctx.put("invoice", invoice);
            ctx.put("to", invoice.getTo());
            ctx.put("r", invoice.getInvoiceRows());
            ctx.
                    put("sender", invoicerRepo.findBy(invoice.getInvoicer().
                                    getId()));

            // 4) Generate report by merging Java model with the ODT
            report.process(ctx, out);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected static InputStream getTemplate(Invoicer invoicer) {
        if(invoicer.getTemplate() == null) {
            return getDefaultTemplate();
        } else {
            return new ByteArrayInputStream(invoicer.getTemplate());
        }
    }

    public static InputStream getDefaultTemplate() {
        return Invoice.class.getResourceAsStream("/tmpl.odt");
    }

    public void writeAsPdf(Invoice invoice, OutputStream out) {
        Locale.setDefault(new Locale("fi"));
        invoice = repo.findBy(invoice.getId());
        
        try {
            
            // Get template stream (either the default or overridden by the user)
            InputStream in = getTemplate(invoice.getInvoicer());

            // Prepare the IXDocReport instance based on the template, using
            // Freemarker template engine
            IXDocReport report = XDocReportRegistry.getRegistry().
                    loadReport(in, TemplateEngineKind.Freemarker);
            
            // Define what we want to do (PDF file from ODF template)
            Options options = Options.getTo(ConverterTypeTo.PDF).via(
                    ConverterTypeVia.ODFDOM);

            // Add properties to the context
            IContext ctx = report.createContext();
            ctx.put("invoice", invoice);
            ctx.put("to", invoice.getTo());
            ctx.put("sender", invoice.getInvoicer());
            // instruct XDocReport to inspect InvoiceRow entity as well
            // which is given as list and iterated in a table
            FieldsMetadata metadata = report.createFieldsMetadata();
            metadata.load("r", InvoiceRow.class, true);
            ctx.put("r", invoice.getInvoiceRows());
            
            // Write the PDF file to output stream
            report.convert(ctx, options, out);
            out.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void remove(Invoice invoice) {
        repo.remove(repo.findBy(invoice.getId()));
    }

    public void sendInvoice(final Invoice invoice) throws EmailException, IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            writeAsPdf(invoice, out);
            ByteArrayDataSource dataSource =
                    new ByteArrayDataSource(out.toByteArray(), "application/pdf");
            String fileName = "invoice_" + invoice.getInvoiceNumber() + ".pdf";

            MultiPartEmail email = new MultiPartEmail();
            email.setAuthentication(smtpUsername, smtpPassword);
            email.setHostName(smtpHostname);
            email.setSmtpPort(smtpPort);
            email.setFrom(smtpFrom);
            email.addTo(invoice.getInvoicer().getEmail());
            email.setSubject(smtpSubject);
            email.setMsg(smtpMessage);
            // TODO FIGURE OUT WHAT IS WRONG HERE
//            email.attach(dataSource, fileName, "Invoice");
            email.send();
        }
    }
}
