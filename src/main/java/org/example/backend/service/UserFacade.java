package org.example.backend.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import org.example.backend.Invoicer;
import org.example.backend.User;

import java.util.List;

/**
 *
 * @author Matti Tahvonen
 */
@Stateless
public class UserFacade {
    
    @Inject
    UserRepository repo;

    public User findByEmail(String email) {
        try {
            User user = repo.findByEmail(email);
            System.out.println(user.getAdministrates());
            user.getAdministrates().size();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public User save(User user) {
        return repo.save(user);
    }

}
