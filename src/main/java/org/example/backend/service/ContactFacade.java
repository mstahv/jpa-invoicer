package org.example.backend.service;

import com.mysema.query.types.expr.BooleanExpression;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.example.backend.Contact;
import org.example.backend.Invoicer;
import org.example.backend.QContact;
import org.springframework.data.domain.PageRequest;

/**
 *
 * @author Matti Tahvonen
 */
@Stateless
public class ContactFacade {

    @Inject
    ContactRepository repository;

    public List<Contact> findPaged(Invoicer invoicer, String filter,
            int firstResult,
            int maxResults) {
        return repository.findAll(filterByNamePredicate(filter, invoicer),
                new PageRequest(firstResult / maxResults, maxResults)
        ).getContent();
    }

    protected static BooleanExpression filterByNamePredicate(String filter,
            Invoicer invoicer) {
        return QContact.contact.name.startsWithIgnoreCase(filter)
                .and(QContact.contact.invoicer.eq(invoicer));
    }

    public Integer countContacts(Invoicer invoicer, String filter) {
        return (int) repository.findAll(filterByNamePredicate(filter, invoicer),
                new PageRequest(1, 1)).getTotalElements();
    }

    public Contact save(Contact entity) {
        return repository.save(entity);
    }

    public Contact findOne(Integer id) {
        return repository.findOne(id);
    }

}
