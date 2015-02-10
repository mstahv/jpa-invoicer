package org.example.backend.service;

import java.util.ArrayList;
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
import org.example.backend.User;

/**
 *
 * @author Matti Tahvonen
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "invoicerdb")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    public User findByEmail(String email) {
        final CriteriaBuilder cb = getEntityManager().
                getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root r = cq.from(User.class);
        CriteriaQuery<User> q =  cq.select(r).where(cb.equal(r.get("email"), email));
        try {
            return em.createQuery(q).getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }

    public List<Invoicer> getInvoicers(User user) {
        return new ArrayList(em.merge(user).getAdministrates());
    }

}
