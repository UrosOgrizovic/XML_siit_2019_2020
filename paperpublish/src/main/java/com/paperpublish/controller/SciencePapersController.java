package com.paperpublish.controller;

import com.paperpublish.model.sciencepapers.SciencePapers;
import com.paperpublish.service.SciencePapersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/sciencepapers")
public class SciencePapersController {

    @Autowired
    public SciencePapersService sciencePapersService;

    @GetMapping(path = "/findall", produces = {MediaType.APPLICATION_ATOM_XML_VALUE})
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(sciencePapersService.getAll());
    }
}
