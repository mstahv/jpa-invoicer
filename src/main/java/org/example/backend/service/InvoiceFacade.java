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

//import org.apache.deltaspike.core.api.config.ConfigProperty;
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
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.annotation.Resource;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;

/**
 *
 * @author Matti Tahvonen
 */
@Stateless
public class InvoiceFacade {

    @PersistenceContext
    EntityManager em;

    public List<Invoice> findAll(Invoicer invoicer) {
        return em.createQuery("SELECT iv from Invoice iv WHERE iv.invoicer = :invoicer", Invoice.class)
                .setParameter("invoicer", invoicer)
                .getResultList();
    }

    public Invoice createFor(Invoicer invoicer) {
        invoicer = em.find(Invoicer.class, invoicer.getId());
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoicer.getAndIcrementNextInvoiceNumber());
        invoice.setInvoicer(invoicer);
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setDueDate(LocalDate.now().plusDays(DEFAULT_DUE_DATE_DURATION));
        invoice.setLastEditor(session.getUser());
        em.merge(invoicer);
        return em.merge(invoice);
    }

    @Inject
    UserSession session;

    public Invoice save(Invoice entity) {
        entity.setLastEditor(session.getUser());
        return em.merge(entity);
    }

    public void writeAsOdt(Invoice invoice, OutputStream out) {
        Locale.setDefault(new Locale("fi"));
        invoice = em.find(Invoice.class, invoice.getId());
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
                    put("sender", em.find(Invoice.class, invoice.getInvoicer().
                            getId()));

            // 4) Generate report by merging Java model with the ODT
            report.process(ctx, out);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected static InputStream getTemplate(Invoicer invoicer) {
        if (invoicer.getTemplate() == null) {
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
        invoice = em.find(Invoice.class, invoice.getId());

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
        em.remove(em.find(Invoice.class, invoice.getId()));
    }

    public void sendInvoice(final Invoice invoice) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            writeAsPdf(invoice, out);
            DataSource dataSource
                    = new ByteArrayDataSource(out.toByteArray(), "application/pdf");
            
            InitialContext initialContext= new InitialContext ();
            // See https://rieckpil.de/howto-send-emails-with-java-ee-using-payara/ how
            // configure Java mail to this JNDI address, not injecting with Resource to
            // make testing/demoing possible without smtp server
            Session mailSession = (Session) initialContext.lookup ("mail/localsmtp");
            
            MimeMessage mimeMessage = new MimeMessage(mailSession);

            mimeMessage.setSubject("Hello World from Java EE!");
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(invoice.getInvoicer().getEmail()));

            MimeMultipart mailContent = new MimeMultipart();

            MimeBodyPart mailMessage = new MimeBodyPart();
            mailMessage.setContent("<p>Take a look at the attached invoice PDF file</p>", "text/html; charset=utf-8");
            mailContent.addBodyPart(mailMessage);

            MimeBodyPart mailAttachment = new MimeBodyPart();
            mailAttachment.setDataHandler(new DataHandler(dataSource));
            mailAttachment.setFileName("invoice_" + invoice.getInvoiceNumber() + ".pdf");

            mailContent.addBodyPart(mailAttachment);
            mimeMessage.setContent(mailContent);

            Transport.send(mimeMessage);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
