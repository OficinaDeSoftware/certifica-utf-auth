package com.OficinaDeSoftware.Auth.service;

import org.springframework.stereotype.Service;

import com.OficinaDeSoftware.Auth.converter.UserConverter;
import com.OficinaDeSoftware.Auth.domain.User;
import com.OficinaDeSoftware.Auth.dto.UserDto;
import com.OficinaDeSoftware.Auth.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    private final UserConverter userConverter;

    public UserService( UserRepository repository, UserConverter userConverter) {
        this.repository = repository;
        this.userConverter = userConverter;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void save(final UserDto userDto) {
        repository.save( userConverter.convertToEntity( userDto ) );
    }

    public User findByNrUuid( final String nrUuid ) {
        return repository.findById(nrUuid).orElse(null);
    }
    
}