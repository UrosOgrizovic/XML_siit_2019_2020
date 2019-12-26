package com.paperpublish.controller;

import com.paperpublish.model.DTO.UserDTO;
import com.paperpublish.model.users.User;
import com.paperpublish.security.TokenUtils;
import com.paperpublish.security.auth.JwtAuthenticationRequest;
import com.paperpublish.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logOut(HttpServletRequest request) {
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logIn(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        final Authentication authentication = authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(
                                        authenticationRequest.getUsername(),
                                        authenticationRequest.getPassword()
                                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new UserDTO(user,tokenUtils.generateToken(user.getUsername())));
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO){
        Long res = userService.registerUser(userDTO);
        if (res != -1) {
        	return ResponseEntity.status(HttpStatus.CREATED).build();
        } else if (res == -1) {
        	return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(value = "/testUser")
    @PreAuthorize("hasRole('USER')")
    public String testUser(){
        return "USER WORKS";
    }

    @GetMapping(value = "/testAdmin")
    @PreAuthorize("hasRole('EDITOR')")
    public String testAdmin(){
        return "ADMIN WORKS";
    }
}
