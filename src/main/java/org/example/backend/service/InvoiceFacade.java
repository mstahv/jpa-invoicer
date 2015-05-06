package org.example.backend.service;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.ejb.Stateless;
import javax.inject.Inject;
import static org.example.InvoicesView.DEFAULT_DUE_DATE_DURATION;
import org.example.backend.Invoice;
import org.example.backend.InvoiceRow;
import org.example.backend.Invoicer;
import org.example.backend.UserSession;

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

    public List<Invoice> findAll(Invoicer value) {
        return repo.findAll();
    }

    public Invoice createFor(Invoicer invoicer) {
        invoicer = invoicerRepo.findBy(invoicer.getId());
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoicer.getAndIcrementNextInvoiceNumber());
        invoice.setInvoicer(invoicer);
        invoice.setInvoiceDate(new Date());
        invoice.setDueDate(new Date(
                System.currentTimeMillis() + DEFAULT_DUE_DATE_DURATION));
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

}
