package org.example.backend.service;

import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
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
public class InvoicerFacade {

    @PersistenceContext
    EntityManager em;

    public Invoicer save(Invoicer entity) {
        for (int i = 0; i < entity.getAdministrators().size(); i++) {
            User usr = entity.getAdministrators().get(i);
            User existing = userFacade.findByEmail(usr.getEmail());
            if (existing != null) {
                entity.getAdministrators().remove(usr);
                entity.getAdministrators().add(i, em.find(User.class, existing.
                        getId()));
            } else {
                em.merge(usr);
            }
        }
        return em.merge(entity);
    }

    @Inject
    UserFacade userFacade;

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

    public Invoicer findJoined(Long id) {
        Invoicer in = em.find(Invoicer.class, id);
        in.getAdministrators().size(); // init lazy relation
        in.getProducts().size();
        return in;
    }

    public List<Invoicer> findFor(User user) {
        return em.createQuery("SELECT iv FROM Invoicer iv JOIN FETCH iv.administrators a WHERE a = ?1", Invoicer.class)
                .setParameter(1, user)
                .getResultList();
    }
}
