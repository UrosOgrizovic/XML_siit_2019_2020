package com.paperpublish.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paperpublish.model.DTO.TSciencePaperDTO;
import com.paperpublish.model.DTO.XMLDTO;
import com.paperpublish.service.SciencePapersService;
import com.paperpublish.utils.XSLFOTransformer;

@RestController
@RequestMapping(path = "/sciencepapers")
public class SciencePapersController {

    @Autowired
    public SciencePapersService sciencePapersService;
    
    public XSLFOTransformer xslfoTransformer = new XSLFOTransformer();

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllJsonAndFilter(@RequestParam(value = "query", required = false) String query) throws Exception {
    	return ResponseEntity.ok(sciencePapersService.getAllJsonAndFilter(query));
	}

    @GetMapping(path = "/findall", produces = {MediaType.APPLICATION_JSON_VALUE})
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
			e.printStackTrace();
			if (e.getClass() == ResourceNotFoundException.class) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }
    
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody XMLDTO paperXMLDTO) {
		try {
			Long res = sciencePapersService.createFromXml(paperXMLDTO.getXml());
			if (res != -1) {
	    		return ResponseEntity.status(HttpStatus.CREATED).build();
	    	} else if (res == -1) {
	    		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	    	}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (Exception e) {
			e.printStackTrace();
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
    
    @GetMapping(path = "downloadPDF/{documentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> downloadPDF(@PathVariable String documentId) {
    	try {
    		ByteArrayOutputStream out = xslfoTransformer.generatePDF(documentId);
    		InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
    		return ResponseEntity.ok()
    	            .contentLength(out.size())
    	            .contentType(MediaType.parseMediaType("application/pdf"))
    	            .body(resource); 
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    	
    }
    
    
    @GetMapping(path = "downloadHTML/{documentId}", produces = MediaType.APPLICATION_XHTML_XML_VALUE)
    public ResponseEntity<?> downloadHTML(@PathVariable String documentId) {
    	try {
    		ByteArrayOutputStream out = xslfoTransformer.generateHTML(documentId);
    		InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
    		return ResponseEntity.ok()
    	            .contentLength(out.size())
    	            .contentType(MediaType.parseMediaType("application/xhtml+xml"))
    	            .body(resource); 
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }
    
}
