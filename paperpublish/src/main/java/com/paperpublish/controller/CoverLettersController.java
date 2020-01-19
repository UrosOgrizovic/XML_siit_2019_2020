package com.paperpublish.controller;

import com.paperpublish.model.DTO.XMLDTO;
import com.paperpublish.service.CoverLettersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/coverletters")
public class CoverLettersController {

    @Autowired
    public CoverLettersService coverLettersService;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody XMLDTO paperXMLDTO) {
        try {
            Long res = coverLettersService.createFromXml(paperXMLDTO.getXml());
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
}
