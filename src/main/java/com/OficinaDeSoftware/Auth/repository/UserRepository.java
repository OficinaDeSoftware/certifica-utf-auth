package com.OficinaDeSoftware.Auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.OficinaDeSoftware.Auth.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
    public User findByEmail( String email ); 
}
