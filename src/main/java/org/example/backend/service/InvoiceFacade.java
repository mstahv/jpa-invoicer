package org.example.backend.service;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import static org.example.InvoicesView.DEFAULT_DUE_DATE_DURATION;
import org.example.backend.AbstractFacade;
import org.example.backend.Invoice;
import org.example.backend.InvoiceRow;
import org.example.backend.Invoicer;
import org.example.backend.UserSession;

/**
 *
 * @author Matti Tahvonen
 */
@Stateless
public class InvoiceFacade extends AbstractFacade<Invoice> {

    @PersistenceContext(unitName = "invoicerdb")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvoiceFacade() {
        super(Invoice.class);
    }

    public List<Invoice> findAll(Invoicer value) {
        final CriteriaBuilder cb = getEntityManager().
                getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root r = cq.from(Invoice.class);
        CriteriaQuery<Invoice> q = cq.select(r).where(cb.
                equal(r.get("invoicer"), value));
        return em.createQuery(q).getResultList();
    }

    public Invoice createFor(Invoicer invoicer) {
        invoicer = em.find(Invoicer.class, invoicer.getId());
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoicer.getAndIcrementNextInvoiceNumber());
        invoice.setInvoicer(invoicer);
        invoice.setInvoiceDate(new Date());
        invoice.setDueDate(new Date(
                System.currentTimeMillis() + DEFAULT_DUE_DATE_DURATION));
        invoice.setLastEditor(session.getUser());
        em.merge(invoicer);
        em.persist(invoice);
        return invoice;
    }

    @Inject
    UserSession session;

    @Override
    public void edit(Invoice entity) {
        entity.setLastEditor(session.getUser());
        super.edit(entity);
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
            InputStream in = Invoice.class.getResourceAsStream(
                    "/tmpl.odt");
            IXDocReport report = XDocReportRegistry.getRegistry().
                    loadReport(in, TemplateEngineKind.Freemarker);

            FieldsMetadata metadata = report.createFieldsMetadata();
            metadata.load("r", InvoiceRow.class, true);

            IContext ctx = report.createContext();
            ctx.put("invoice", invoice);
            ctx.put("to", invoice.getTo());
            ctx.put("r", invoice.getInvoiceRows());
            ctx.put("sender", em.find(Invoicer.class, invoice.getInvoicer().getId()));

            // 4) Generate report by merging Java model with the ODT
            report.process(ctx, out);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void writeAsPdf(Invoice invoice, OutputStream out) {
        Locale.setDefault(new Locale("fi"));
        invoice = em.find(Invoice.class, invoice.getId());
        List<InvoiceRow> invoiceRows = new ArrayList<>(invoice.getInvoiceRows());
        int size = invoice.getInvoiceRows().size();
        invoice.getInvoicer().getBankAccount();
        invoice.getInvoicer().getEmail();
        invoice.getInvoicer().getAddress();
        invoice.getInvoicer().getPhone();
        try {
            // 1) Load ODT file by filling Velocity template engine and cache
            // it to the registry
            InputStream in = Invoice.class.getResourceAsStream(
                    "/tmpl.odt");
            IXDocReport report = XDocReportRegistry.getRegistry().
                    loadReport(in, TemplateEngineKind.Freemarker);

            FieldsMetadata metadata = report.createFieldsMetadata();
            metadata.load("r", InvoiceRow.class, true);

            IContext ctx = report.createContext();
            ctx.put("invoice", invoice);
            ctx.put("to", invoice.getTo());
            ctx.put("r", invoiceRows);
            ctx.put("sender", em.find(Invoicer.class, invoice.getInvoicer().getId()));

            // 4) Generate report by merging Java model with the ODT
            Options options = Options.getTo(ConverterTypeTo.PDF).via(
                    ConverterTypeVia.ODFDOM);
            report.convert(ctx, options, out);
            out.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }        
    }

}
