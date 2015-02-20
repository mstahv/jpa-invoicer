package org.example.backend.service;

import java.util.List;
import org.example.backend.Contact;
import org.example.backend.Invoicer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface ContactRepository extends JpaRepository<Contact, Integer>,
        QueryDslPredicateExecutor {
    
    List<Contact> findByInvoicer(Invoicer invoicer);

}
