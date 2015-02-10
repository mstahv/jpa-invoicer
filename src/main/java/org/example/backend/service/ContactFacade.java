package org.example.backend.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.example.backend.AbstractFacade;
import org.example.backend.Contact;
import org.example.backend.Invoicer;

/**
 *
 * @author Matti Tahvonen
 */
@Stateless
public class ContactFacade extends AbstractFacade<Contact> {

    @PersistenceContext(unitName = "invoicerdb")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Contact> findAll(Invoicer invoicer) {
        return em.createQuery(
                "SELECT c FROM Contact c WHERE c.invoicer = :invoicer").
                setParameter("invoicer", invoicer).getResultList();
    }

    public List<Contact> findPaged(Invoicer invoicer, String filter,
            int firstResult,
            int maxResults) {
        return em.createQuery(
                "SELECT c FROM Contact c WHERE c.invoicer = :invoicer "
                + "AND LOWER(c.name) LIKE :f")
                .setParameter("invoicer", invoicer)
                .setParameter("f", "" + filter.toLowerCase() + "%")
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList();
    }

    public Integer countContacts(Invoicer invoicer, String filter) {
        return ((Long) em.createQuery(
                "SELECT COUNT (c) FROM Contact c WHERE c.invoicer = :invoicer "
                + "AND LOWER(c.name) LIKE :f")
                .setParameter("invoicer", invoicer)
                .setParameter("f", "" + filter.toLowerCase() + "%")
                .getSingleResult()).intValue();
    }

    public ContactFacade() {
        super(Contact.class);
    }

    public Contact findByEmail(String email) {
        final CriteriaBuilder cb = getEntityManager().
                getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root r = cq.from(Contact.class);
        CriteriaQuery<Contact> q = cq.select(r).where(cb.equal(r.get("email"),
                email));
        return em.createQuery(q).getSingleResult();
    }

}
