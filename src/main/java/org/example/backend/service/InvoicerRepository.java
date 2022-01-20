package org.example.backend.service;


import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.example.backend.Invoicer;
import org.example.backend.Product;
import org.example.backend.User;

@Repository(forEntity = Invoicer.class)
public interface InvoicerRepository extends EntityRepository<Invoicer, Long> {

    @Query("SELECT iv FROM Invoicer iv JOIN FETCH iv.administrators a WHERE a = ?1 ")
    List<Invoicer> findFor(User user);
}