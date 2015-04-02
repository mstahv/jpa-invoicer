package org.example.backend.service;


import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.example.backend.Invoicer;
import org.example.backend.Product;
import org.example.backend.User;

@Repository(forEntity = Invoicer.class)
public interface InvoicerRepository extends EntityRepository<Invoicer, Long> {

    public List<Product> findByUser(User invoicer);
    
}