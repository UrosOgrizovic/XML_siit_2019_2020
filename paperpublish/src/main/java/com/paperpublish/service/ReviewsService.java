package com.paperpublish.service;

import com.paperpublish.repository.CoverLettersRepository;
import com.paperpublish.repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paperpublish.model.DTO.TSciencePaperDTO;
import com.paperpublish.model.sciencepapers.ObjectFactory;
import com.paperpublish.model.sciencepapers.SciencePapers;
import com.paperpublish.model.sciencepapers.TSciencePaper;
import com.paperpublish.repository.SciencePapersRepository;

import java.util.List;

@Service
public class ReviewsService {
    @Autowired
    ReviewsRepository reviewsRepository;

    public Long createFromXml(String xml) {
        return reviewsRepository.createFromXML(xml);
    }


}
