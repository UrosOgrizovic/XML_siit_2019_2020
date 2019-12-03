package com.paperpublish.service;

import com.paperpublish.model.sciencepapers.SciencePapers;
import com.paperpublish.repository.SciencePapersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SciencePapersService {
    @Autowired
    SciencePapersRepository sciencePapersRepository;

    public SciencePapers getAll(){
        SciencePapers papers = null;
        try {
            papers = sciencePapersRepository.getAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return papers;
    }
}
