package com.paperpublish.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paperpublish.model.DTO.UserDTO;
import com.paperpublish.model.users.ObjectFactory;
import com.paperpublish.model.users.Roles;
import com.paperpublish.model.users.User;
import com.paperpublish.model.users.Users;
import com.paperpublish.security.repository.UserRepository;

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

    public Long registerUser(UserDTO userDTO){
        User newUser = (new ObjectFactory()).createUser();
        Roles roles = (new ObjectFactory()).createRoles();
        roles.getRole().add("ROLE_USER");
        newUser.setUserName(userDTO.getUsername());
        newUser.setEMail(userDTO.getEmail());
        newUser.setFullName(userDTO.getName());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setRoles(roles);
        return userRepository.save(newUser);
    }
      
    public Users getAll() {
    	Users users = null;
    	try {
			users = userRepository.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return users;
    }

	public void update(UserDTO userDTO) throws Exception {
		User user = (new ObjectFactory()).createUser();
		user.setUserName(userDTO.getUsername());
		user.setEMail(userDTO.getEmail());
		user.setFullName(userDTO.getName());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		
		try {
			userRepository.update(user);
		} catch (Exception e) {
			throw e;
		}
		
	}

	public void delete(String username) throws Exception {
		try {
			userRepository.delete(username);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
	}
}
