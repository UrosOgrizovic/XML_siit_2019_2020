package com.paperpublish.security.service;

import com.paperpublish.security.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {
    @Autowired
    private AuthorityRepository repository;

    public String findAuthorityByName(String name){
        return repository.findAuthorityByName(name);
    }
}
