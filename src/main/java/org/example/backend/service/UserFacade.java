package org.example.backend.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.example.backend.User;


/**
 *
 * @author Matti Tahvonen
 */
@Stateless
public class UserFacade {

    @PersistenceContext
    EntityManager em;
    
    public User findByEmail(String email) {
        try {
            User user = em.createQuery("Select u FROM User u where u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            System.out.println(user.getAdministrates());
            user.getAdministrates().size();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public User save(User user) {
        return em.merge(user);
    }

}
