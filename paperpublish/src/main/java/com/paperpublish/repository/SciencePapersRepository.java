package com.paperpublish.repository;

import com.paperpublish.model.sciencepapers.SciencePapers;
import com.paperpublish.model.sciencepapers.TSciencePaper;
import com.paperpublish.utils.ConnectionProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XMLResource;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.util.List;

@Component
public class SciencePapersRepository {

    @Autowired
    Collection collection;

    public SciencePapers getAll() throws Exception {
        XMLResource resource = (XMLResource) collection.getResource(ConnectionProperties.SCIENCE_PAPER_ID);
        JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return  (SciencePapers) unmarshaller.unmarshal(resource.getContentAsDOM());
    }

}
