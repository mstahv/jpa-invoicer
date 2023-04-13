/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.backend.service;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
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
    
    public void save(Product entity) {
        em.merge(entity);
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
