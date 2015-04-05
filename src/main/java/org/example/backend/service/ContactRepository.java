package org.example.backend.service;

import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;
import org.example.backend.Contact;
import org.example.backend.Invoicer;

@Repository(forEntity = Contact.class)
public interface ContactRepository extends EntityRepository<Contact, Long> {

    public List<Contact> findByInvoicer(Invoicer invoicer);

    public QueryResult<Contact> findByInvoicerAndNameLikeIgnoreCase(Invoicer invoicer,
            String filter);

}
