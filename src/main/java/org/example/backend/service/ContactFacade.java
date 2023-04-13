package org.example.backend.service;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.backend.Contact;
import org.example.backend.Invoicer;

/**
 *
 * @author Matti Tahvonen
 */
@Stateless
public class ContactFacade {

    @PersistenceContext
    EntityManager em;

    public List<Contact> findAll(Invoicer invoicer) {
        return em.createQuery("SELECT c FROM Contact c WHERE c.invoicer = :invoicer", Contact.class)
                .setParameter("invoicer", invoicer).getResultList();
    }

    public List<Contact> findPaged(Invoicer invoicer, String filter,
            int firstResult,
            int maxResults) {
        // TODO filter
        return em.createQuery("SELECT c FROM Contact c WHERE c.invoicer = :invoicer", Contact.class)
                .setParameter("invoicer", invoicer)
                .getResultList();
    }

    public Contact save(Contact entity) {
        return em.merge(entity);
    }
    
    public Contact refresh(Contact entity) {
        return em.find(Contact.class, entity.getId());
    }
    
}
