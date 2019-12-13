package com.paperpublish.security.service;

import com.paperpublish.model.DTO.UserDTO;
import com.paperpublish.model.users.ObjectFactory;
import com.paperpublish.model.users.Roles;
import com.paperpublish.model.users.User;
import com.paperpublish.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public void registerUser(UserDTO userDTO){
        User newUser = (new ObjectFactory()).createUser();
        Roles roles = (new ObjectFactory()).createRoles();
        roles.getRole().add("ROLE_USER");
        newUser.setUserName(userDTO.getUsername());
        newUser.setEMail(userDTO.getEmail());
        newUser.setFullName(userDTO.getName());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setRoles(roles);
        userRepository.save(newUser);
    }
    
    
}
