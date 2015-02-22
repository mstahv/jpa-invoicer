
package org.example.backend.service;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DeltaspikeEntityMananegerProvider {

    @Produces
    @Dependent
    @PersistenceContext(unitName = "invoicerdb")
    public EntityManager entityManager;
    
}
