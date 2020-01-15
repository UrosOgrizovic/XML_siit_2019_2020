package com.paperpublish;

import com.paperpublish.model.sciencepapers.*;
import com.paperpublish.utils.MetadataExtractor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class test {

    public static void main(String[] args) throws Exception {
//        JAXBContext context = JAXBContext.newInstance("com.paperpublish.model.sciencepapers");
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//
//        SciencePapers papers = (SciencePapers) unmarshaller.unmarshal(new File("src/main/resources/data/science_paper.xml"));
//
//
//        for(TSciencePaper paper : papers.getSciencePaper() ){
//            System.out.println(paper.getPaperData().getTitle().getDocumentTitle());
//        }
        MetadataExtractor metadataExtractor = new MetadataExtractor();
        metadataExtractor.extract();
    }
}
