
package org.example.backend.service;

import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.FirstResult;
import org.apache.deltaspike.data.api.MaxResults;
import org.apache.deltaspike.data.api.Repository;
import org.example.backend.Contact;
import org.example.backend.Invoicer;

@Repository(forEntity = Contact.class)
public interface ContactRepository extends EntityRepository<Contact, Integer> {

    List<Contact> findByInvoicer(Invoicer invoicer);
    
    public Contact findByEmail(String email);

    public List<Contact> findByInvoicerAndNameUpperLike(Invoicer invoicer,
            String filter, @FirstResult int firstResult, @MaxResults int maxResults);

}
