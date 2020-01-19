package com.paperpublish.service;

import com.paperpublish.repository.CoverLettersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paperpublish.model.DTO.TSciencePaperDTO;
import com.paperpublish.model.sciencepapers.ObjectFactory;
import com.paperpublish.model.sciencepapers.SciencePapers;
import com.paperpublish.model.sciencepapers.TSciencePaper;
import com.paperpublish.repository.SciencePapersRepository;

import java.util.List;

@Service
public class CoverLettersService {
    @Autowired
    CoverLettersRepository coverLettersRepository;

    public Long createFromXml(String xml) {
        return coverLettersRepository.createFromXML(xml);
    }


}
