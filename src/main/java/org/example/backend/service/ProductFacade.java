/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.backend.service;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.example.backend.Invoicer;
import org.example.backend.Product;
import org.example.backend.Product.State;

/**
 *
 * @author Mortoza Khan
 */
@Stateless
public class ProductFacade {
    
    @Inject ProductRepository repo;

    public void save(Product entity) {
        repo.save(entity);
    }

    public List<Product> findByInvoicer(Invoicer value) {
        return repo.findByInvoicer(value);
    }

    public  List<Product> findActiveByInvoicer(Invoicer invoicer) {
        return repo.findByInvoicerAndProductState(invoicer, State.Active);
    }
       
}
