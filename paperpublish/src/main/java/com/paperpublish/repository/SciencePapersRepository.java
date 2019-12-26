package com.paperpublish.repository;

import com.paperpublish.model.sciencepapers.SciencePapers;
import com.paperpublish.model.sciencepapers.TSciencePaper;
import com.paperpublish.model.users.User;
import com.paperpublish.utils.ConnectionProperties;
import com.paperpublish.utils.XUpdateTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

@Component
public class SciencePapersRepository {

    @Autowired
    @Lazy
    Collection collection;

    public SciencePapers getAll() throws Exception {
        XMLResource resource = (XMLResource) collection.getResource(ConnectionProperties.SCIENCE_PAPER_ID);
        JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return  (SciencePapers) unmarshaller.unmarshal(resource.getContentAsDOM());
    }
    
    public TSciencePaper findByDocumentId(String documentId) throws Exception {
        XPathQueryService queryService = ConnectionProperties.getXPathService(collection);
        try {
        	queryService.setNamespace("",ConnectionProperties.SCIENCE_PAPERS_NAMESPACE);
            ResourceSet result = queryService.query("//SciencePapers/SciencePaper[@documentId='"+documentId+"']");

            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            ResourceIterator i = result.getIterator();
            Resource res = i.nextResource();
            
            return (TSciencePaper) unmarshaller.unmarshal(new StringReader(res.getContent().toString()));
            
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    
	public void delete(String documentId) throws Exception {
		if (this.findByDocumentId(documentId) == null) {
			throw new ResourceNotFoundException("Science paper with document id: '" + documentId + "' not found");
		}
		XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
		try {

	        updateQueryService.updateResource(ConnectionProperties.SCIENCE_PAPER_ID,
                    String.format(XUpdateTemplate.REMOVE, ConnectionProperties.SCIENCE_PAPERS_NAMESPACE, 
                    		"//SciencePapers/SciencePaper[@documentId='"+documentId+"']"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	public Long create(TSciencePaper sciencePaper) throws Exception {
        XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(sciencePaper, writer);
            
            if (getIndexOfSciencePaperInListOfSciencePapers(getAll().getSciencePaper(), sciencePaper.getDocumentId()) == -1) {
            	long res = updateQueryService.updateResource(ConnectionProperties.SCIENCE_PAPER_ID,
                        String.format(XUpdateTemplate.APPEND, ConnectionProperties.SCIENCE_PAPERS_NAMESPACE, "//SciencePapers", writer.toString()));

                return res;
            } else {
            	return -1L;
            }

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * @param sciencePapers list of science papers to search
	 * @param documentId document id to search for
	 * @return -1 if science paper not in list, otherwise index+1 (because indexing in XPath starts at 1)
	 * */
	private int getIndexOfSciencePaperInListOfSciencePapers(List<TSciencePaper> sciencePapers, String documentId) {
		int indexOfSciencePaper = -1;
		for (int i = 0; i < sciencePapers.size(); i++) {
	    	if (sciencePapers.get(i).getDocumentId().equals(documentId)) {
	    		indexOfSciencePaper = i+1;
	    		break;
	    	}
	    }
		return indexOfSciencePaper;
	}

	public Long update(TSciencePaper sciencePaper) throws Exception {
		String documentId = sciencePaper.getDocumentId();
		TSciencePaper oldSciencePaper = this.findByDocumentId(documentId);
		if (oldSciencePaper == null) {
			throw new ResourceNotFoundException("Science paper with document id: '" + documentId + "' not found");
		}
		XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(sciencePaper, writer);
            
            
            List<TSciencePaper> sciencePapers = getAll().getSciencePaper();
            int indexOfSciencePaper = getIndexOfSciencePaperInListOfSciencePapers(sciencePapers, documentId);
            
            return updateQueryService.updateResource(ConnectionProperties.SCIENCE_PAPER_ID, String.format(XUpdateTemplate.UPDATE, 
            		ConnectionProperties.SCIENCE_PAPERS_NAMESPACE, "//SciencePapers/SciencePaper["+indexOfSciencePaper+"]", writer.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	

}
