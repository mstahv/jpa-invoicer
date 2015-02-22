package org.example.backend.service;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.example.backend.Contact;
import org.example.backend.Contact_;
import org.example.backend.Invoicer;
import org.example.backend.QContact;

/**
 *
 * @author Matti Tahvonen
 */
@Stateless
public class ContactFacade {

    @PersistenceContext(unitName = "invoicerdb")
    public EntityManager em;

    @Inject
    ContactRepository repository;

    public List<Contact> findAll(Invoicer invoicer) {
        return repository.findByInvoicer(invoicer);
    }

    protected static BooleanExpression filterByNamePredicate(String filter,
            Invoicer invoicer) {
        return QContact.contact.name.startsWithIgnoreCase(filter)
                .and(QContact.contact.invoicer.eq(invoicer));
    }

    public List<Contact> findPaged(Invoicer invoicer, String filter,
            int firstResult,
            int maxResults) {

        QContact c = QContact.contact;

        return new JPAQuery(em).from(c).where(c.invoicer.eq(invoicer).and(
                c.name.startsWithIgnoreCase(filter))).offset(firstResult)
                .limit(maxResults).list(c);

//        Contact contact = new Contact();
//        contact.setInvoicer(invoicer);
//        contact.setName(filter);
//        return repository.findByLike(contact, firstResult, maxResults,
//                Contact_.invoicer, Contact_.name);
    }

    public Contact findBy(Integer id) {
        return repository.findBy(id);
    }

    public Integer countContacts(Invoicer invoicer, String filter) {
        QContact c = QContact.contact;
         return (int) new JPAQuery(em).from(c).where(c.invoicer.eq(invoicer).and(
                c.name.startsWithIgnoreCase(filter))).count();
//        final Contact contact = new Contact();
//        contact.setInvoicer(invoicer);
//        contact.setName(filter);
//        return repository.countLike(contact, Contact_.invoicer, Contact_.name).
//                intValue();
    }

    public Contact findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Contact save(Contact entity) {
        return repository.save(entity);
    }

}
