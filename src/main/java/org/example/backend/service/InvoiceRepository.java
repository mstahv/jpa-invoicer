
package org.example.backend.service;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.example.backend.Invoice;

/**
 *
 * @author Matti Tahvonen
 */
@Repository(forEntity = Invoice.class)
public interface InvoiceRepository extends EntityRepository<Invoice, Long> {

    
}