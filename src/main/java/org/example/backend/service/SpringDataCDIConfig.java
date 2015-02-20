package org.example.backend.service;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * This is just a hint for SpringData to use the PersistenceContext provided by
 * the Java EE container.
 */
public class SpringDataCDIConfig {

    @Produces
    @Dependent
    @PersistenceContext(unitName = "invoicerdb")
    public EntityManager entityManager;
    
}
