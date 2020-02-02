package com.paperpublish.controller;

import com.paperpublish.model.DTO.XMLDTO;
import com.paperpublish.service.CoverLettersService;
import com.paperpublish.service.ReviewsService;
import com.paperpublish.service.SciencePapersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.paperpublish.model.blindedreviews.TBlindedReview;

@RestController
@RequestMapping(path = "/reviews")
public class ReviewsController {

    @Autowired
    public ReviewsService reviewsService;

    @Autowired
    public SciencePapersService sciencePapersService;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody XMLDTO paperXMLDTO) {
        try {
            Long res = reviewsService.createFromXml(paperXMLDTO.getXml());
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

    @PostMapping(path = "{documentId}/merge", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> merge(@PathVariable String documentId) {
        try {
            TBlindedReview res = reviewsService.merge(documentId);
            if (res != null) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else if (res == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(path="{documentId}/accept-assign/{userName}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> acceptReviewAssignment(@PathVariable String documentId, @PathVariable String userName) {
        try {
            return ResponseEntity.ok(sciencePapersService.acceptReviewAssignment(documentId, userName));
        } catch(Exception e) {
            if (e.getClass() == ResourceNotFoundException.class) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(path="{documentId}/decline-assign/{userName}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> declineReviewAssignment(@PathVariable String documentId, @PathVariable String userName) {
        try {
            return ResponseEntity.ok(sciencePapersService.declineReviewAssignment(documentId, userName));
        } catch(Exception e) {
            if (e.getClass() == ResourceNotFoundException.class) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path="{userName}/assignments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllByUsername(@PathVariable String userName){
        try {
            return ResponseEntity.ok(reviewsService.getAllAssignments(userName));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
