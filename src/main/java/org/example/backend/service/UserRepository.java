package org.example.backend.service;


import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.example.backend.User;

@Repository(forEntity = User.class)
public interface UserRepository extends EntityRepository<User, Long> {
   
}