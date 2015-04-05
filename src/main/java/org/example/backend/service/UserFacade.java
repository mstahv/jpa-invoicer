package org.example.backend.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import org.example.backend.User;

/**
 *
 * @author Matti Tahvonen
 */
@Stateless
public class UserFacade {
    
    @Inject
    UserRepository repo;

    public User findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public User save(User user) {
        return repo.save(user);
    }

}
