package com.paperpublish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paperpublish.model.DTO.UserDTO;
import com.paperpublish.security.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping(path = "", produces = MediaType.APPLICATION_ATOM_XML_VALUE)
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(userService.getAll());
	}
	
	@GetMapping(path = "/findByUsername/{username}", produces = MediaType.APPLICATION_ATOM_XML_VALUE)
	public ResponseEntity<?> findByUsername(@PathVariable String username) {
		return ResponseEntity.ok(userService.loadUserByUsername(username));
	}
	
	@PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody UserDTO userDTO) {
		try {
			userService.update(userDTO);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			if (e.getClass() == ResourceNotFoundException.class) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(@PathVariable String username) {
		try {
			userService.delete(username);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			if (e.getClass() == ResourceNotFoundException.class) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
