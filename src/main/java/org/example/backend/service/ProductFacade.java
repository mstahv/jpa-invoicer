package org.example.backend.service;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.backend.Invoicer;
import org.example.backend.Product;
import org.example.backend.Product.State;

/**
 *
 * @author Mortoza Khan
 */
@Stateless
public class ProductFacade {

    @PersistenceContext
    EntityManager em;
    
    public Product save(Product entity) {
        return em.merge(entity);
    }

    public List<Product> findByInvoicer(Invoicer invoicer) {
        return em.createQuery("SELECT p FROM Product p WHERE p.invoicer = :invoicer", Product.class)
                .setParameter("invoicer", invoicer)
                .getResultList();
    }

    public  List<Product> findActiveByInvoicer(Invoicer invoicer) {
        return em.createQuery("SELECT p FROM Product p WHERE p.productState = :state AND p.invoicer = :invoicer", Product.class)
                .setParameter("state", State.Active)
                .setParameter("invoicer", invoicer)
                .getResultList();
    }
       
}
