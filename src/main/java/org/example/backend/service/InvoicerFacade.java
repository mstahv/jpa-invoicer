package org.example.backend.service;

import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.example.backend.AbstractFacade;
import org.example.backend.Contact;
import org.example.backend.Invoice;
import org.example.backend.InvoiceRow;
import org.example.backend.Invoicer;
import org.example.backend.User;

/**
 *
 * @author Matti Tahvonen
 */
@Stateless
public class InvoicerFacade extends AbstractFacade<Invoicer> {

    @PersistenceContext(unitName = "invoicerdb")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvoicerFacade() {
        super(Invoicer.class);
    }

    @Override
    public void create(Invoicer entity) {
        for (int i = 0; i < entity.getAdministrators().size(); i++) {
            User usr = entity.getAdministrators().get(i);
            User existing = userFacade.findByEmail(usr.getEmail());
            if (existing != null) {
                entity.getAdministrators().remove(usr);
                entity.getAdministrators().add(i, em.find(User.class, existing.
                        getId()));
            } else {
                em.persist(usr);
            }
        }
        super.create(entity);
    }

    @Inject
    UserFacade userFacade;

    @Override
    public void edit(Invoicer entity) {
        for (int i = 0; i < entity.getAdministrators().size(); i++) {
            User usr = entity.getAdministrators().get(i);
            User existing = userFacade.findByEmail(usr.getEmail());
            if (existing != null) {
                entity.getAdministrators().remove(usr);
                entity.getAdministrators().add(i, em.find(User.class, existing.
                        getId()));
            } else {
                em.persist(usr);
            }
        }
        super.edit(entity);
    }
    private JAXBContext jaxbContext;

    @PostConstruct
    void init() {
        try {
            jaxbContext = JAXBContext.newInstance(Invoice.class, Invoicer.class,
                    InvoiceRow.class, User.class, Contact.class);
        } catch (JAXBException ex) {
            Logger.getLogger(InvoiceFacade.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public void writeAsXml(Invoicer invoicer, OutputStream out) {
        invoicer = em.find(Invoicer.class, invoicer.getId());
        invoicer.getInvoices().size();
        try {
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
            jaxbMarshaller.marshal(invoicer, out);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public Invoicer findJoined(Integer id) {
        Invoicer in = em.find(Invoicer.class, id);
        in.getAdministrators().size(); // init lazy relation
        return in;
    }

}
