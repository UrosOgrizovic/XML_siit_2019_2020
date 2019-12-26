package com.paperpublish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paperpublish.model.DTO.TSciencePaperDTO;
import com.paperpublish.model.sciencepapers.TSciencePaper;
import com.paperpublish.service.SciencePapersService;

@RestController
@RequestMapping(path = "/sciencepapers")
public class SciencePapersController {

    @Autowired
    public SciencePapersService sciencePapersService;

    @GetMapping(path = "/findall", produces = {MediaType.APPLICATION_ATOM_XML_VALUE})
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(sciencePapersService.getAll());
    }
    
    @GetMapping(path = "/findByDocumentId/{documentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByDocumentId(@PathVariable String documentId) {
    	try {
			return ResponseEntity.ok(sciencePapersService.findByDocumentId(documentId));
		} catch (Exception e) {
			if (e.getClass() == NullPointerException.class) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }
    
    @DeleteMapping(path = "/{documentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable String documentId) {
    	try {
			sciencePapersService.delete(documentId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			if (e.getClass() == ResourceNotFoundException.class) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }
    
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody TSciencePaperDTO sciencePaperDTO) {
		try {
			Long res = sciencePapersService.create(sciencePaperDTO);
			if (res != -1) {
	    		return ResponseEntity.status(HttpStatus.CREATED).build();
	    	} else if (res == -1) {
	    		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	    	}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }
    
    @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody TSciencePaperDTO sciencePaperDTO) {
    	try {
			sciencePapersService.update(sciencePaperDTO);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			if (e.getClass() == ResourceNotFoundException.class) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }
}
