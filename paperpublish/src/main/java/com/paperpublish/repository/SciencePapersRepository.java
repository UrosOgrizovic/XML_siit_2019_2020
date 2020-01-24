package com.paperpublish.repository;

import com.paperpublish.model.DTO.XMLDTO;
import com.paperpublish.model.sciencepapers.ObjectFactory;
import com.paperpublish.model.sciencepapers.SciencePapers;
import com.paperpublish.model.sciencepapers.TAuthors;
import com.paperpublish.model.sciencepapers.TSciencePaper;
import com.paperpublish.model.users.User;
import com.paperpublish.utils.ConnectionProperties;
import com.paperpublish.utils.FileUtil;
import com.paperpublish.utils.SparqlUtil;
import com.paperpublish.utils.XUpdateTemplate;

import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class SciencePapersRepository {

    @Autowired
    @Lazy
    Collection collection;

    public static final String findByTitleQr = "findSciencePaperByTitle.rq";
    
    public List<TSciencePaper> getAll() throws Exception {
		XMLResource resource = (XMLResource) collection.getResource(ConnectionProperties.SCIENCE_PAPER_ID);
		JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		SciencePapers sp = (SciencePapers) unmarshaller.unmarshal(resource.getContentAsDOM());
		return sp.getSciencePaper();
    }

	public List<TSciencePaper> getAllInProcedure() throws Exception {
		XPathQueryService queryService = ConnectionProperties.getXPathService(collection);
		queryService.setNamespace("",ConnectionProperties.SCIENCE_PAPERS_NAMESPACE);
		ResourceSet result = queryService.query("//SciencePapers/SciencePaper[@status='in_procedure']");

		JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		ResourceIterator i = result.getIterator();
		List<TSciencePaper> paperList = new ArrayList<>();

		while(i.hasMoreResources()) {
			paperList.add((TSciencePaper) unmarshaller.unmarshal(new StringReader(i.nextResource().getContent().toString())));
		}

		return paperList;
	}
	
	public List<TSciencePaper> getByAuthorUsername(String username) throws Exception {
		XPathQueryService queryService = ConnectionProperties.getXPathService(collection);
		queryService.setNamespace("",ConnectionProperties.SCIENCE_PAPERS_NAMESPACE);
		ResourceSet result = queryService.query("//SciencePapers/SciencePaper[./PaperData/Author/authorUserName[text()='"+username+"']]");

		JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		ResourceIterator i = result.getIterator();
		List<TSciencePaper> paperList = new ArrayList<>();

		while(i.hasMoreResources()) {
			paperList.add((TSciencePaper) unmarshaller.unmarshal(new StringReader(i.nextResource().getContent().toString())));
		}

		return paperList;
	}

    public SciencePapers getAllXML() throws Exception {
        XMLResource resource = (XMLResource) collection.getResource(ConnectionProperties.SCIENCE_PAPER_ID);
        JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return  (SciencePapers) unmarshaller.unmarshal(resource.getContentAsDOM());
    }

    public List<TSciencePaper> getAllJsonAndFilter(String query) throws Exception {
    	XPathQueryService queryService = ConnectionProperties.getXPathService(collection);
		queryService.setNamespace("",ConnectionProperties.SCIENCE_PAPERS_NAMESPACE);
		ResourceSet result = queryService.query("//SciencePapers/SciencePaper[@" + query + "]");

		JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		ResourceIterator i = result.getIterator();
		List<TSciencePaper> paperList = new ArrayList<>();

		while(i.hasMoreResources()) {
			paperList.add((TSciencePaper) unmarshaller.unmarshal(new StringReader(i.nextResource().getContent().toString())));
		}

		return paperList;
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
    
    /**
     * checks if paper exists in db
     * @param paperTitle
     * @return true if exists, false otherwise
     * @throws IOException
     */
    public boolean doesPaperExist(String paperTitle) throws IOException {
    	String query = String.format(FileUtil.readFile(ConnectionProperties.QUERY_LOCATION + findByTitleQr),
                ConnectionProperties.dataEndpoint + ConnectionProperties.SCIENCE_PAPER_METADATA, paperTitle);
        ResultSet resultSet = ConnectionProperties.executeQueryMetadata(query);
        if(resultSet.hasNext()){
            return true;
        }
        return false;
    }
	public Long deleteLogical(String documentId) throws Exception {
    	TSciencePaper sciencePaper = this.findByDocumentId(documentId);
//		if (!this.doesPaperExist(sciencePaper.getPaperData().getTitle().getDocumentTitle())) {
//			throw new ResourceNotFoundException("Science paper with document id: '" + documentId + "' not found");
//		}
		XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			StringWriter writer = new StringWriter();

			this.delete(sciencePaper.getDocumentId());
			sciencePaper.setStatus("deleted");
			marshaller.marshal(sciencePaper, writer);
			this.saveRDFModel(sciencePaper);
			return updateQueryService.updateResource(ConnectionProperties.SCIENCE_PAPER_ID,
					String.format(XUpdateTemplate.APPEND, ConnectionProperties.SCIENCE_PAPERS_NAMESPACE, "//SciencePapers", writer.toString()));

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	public void delete(String documentId) throws Exception {
		TSciencePaper sciencePaper = this.findByDocumentId(documentId);
		if (sciencePaper == null) {
			throw new ResourceNotFoundException("Science paper with document id: '" + documentId + "' not found");
		}
		XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
		try {
			deleteNodeFromRDF(sciencePaper.getDocumentId());
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
            if (this.doesPaperExist(sciencePaper.getPaperData().getTitle().getDocumentTitle())) {
            	return -1L;
            }
            
            
            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(sciencePaper, writer);
            
            long res = updateQueryService.updateResource(ConnectionProperties.SCIENCE_PAPER_ID,
                    String.format(XUpdateTemplate.APPEND, ConnectionProperties.SCIENCE_PAPERS_NAMESPACE, "//SciencePapers", writer.toString()));
        	saveRDFModel(sciencePaper);
            return res;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Long createFromXML(String xml) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			TSciencePaper paperToCreate = (TSciencePaper) unmarshaller.unmarshal(new StringReader(xml));
			paperToCreate.setDocumentId(getNextID(getAllXML().getSciencePaper()));
			return create(paperToCreate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1L;
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

	private String getNextID(List<TSciencePaper> sciencePapers) {
		int indexOfSciencePaper = -1;
		for (int i = 0; i < sciencePapers.size(); i++) {
			int parsedIndex = Integer.parseInt(sciencePapers.get(i).getDocumentId());
			if (parsedIndex > indexOfSciencePaper) {
				indexOfSciencePaper = parsedIndex;
			}
		}
		return Integer.toString(indexOfSciencePaper + 1);
	}

	public Long update(XMLDTO paperXMLDto) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		TSciencePaper sciencePaper = (TSciencePaper) unmarshaller.unmarshal(new StringReader(paperXMLDto.getXml()));

		XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
		try {
			jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(sciencePaper, writer);
            
            this.delete(sciencePaper.getDocumentId());
            this.saveRDFModel(sciencePaper);
            
            return updateQueryService.updateResource(ConnectionProperties.SCIENCE_PAPER_ID,
                    String.format(XUpdateTemplate.APPEND, ConnectionProperties.SCIENCE_PAPERS_NAMESPACE, "//SciencePapers", writer.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public void saveRDFModel(TSciencePaper sciencePaper) {
		Model model = ModelFactory.createDefaultModel();

		org.apache.jena.rdf.model.Resource resource = ResourceFactory.createResource("http://localhost:8080/SciencePapers/" + sciencePaper.getDocumentId());
  
		this.addPropertyAndLiteralToModel(model, resource, "title", sciencePaper.getPaperData().getTitle().getDocumentTitle());
		this.addPropertyAndLiteralToModel(model, resource, "shortTitle", sciencePaper.getPaperData().getShortTitle());
		for (TAuthors author : sciencePaper.getPaperData().getAuthor()) {
			for (String un : author.getAuthorUserName()) {
				this.addPropertyAndLiteralToModel(model, resource, "authorUserName", un);
			}
		}
		  
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		model.write(out,SparqlUtil.NTRIPLES);
		
		String sparqlUpdate = SparqlUtil.insertData(ConnectionProperties.dataEndpoint + ConnectionProperties.SCIENCE_PAPER_METADATA, new String(out.toByteArray()));
		System.out.println(sparqlUpdate);
		
		ConnectionProperties.executeUpdateMetadata(sparqlUpdate);
	}
	
	public void addPropertyAndLiteralToModel(Model model, org.apache.jena.rdf.model.Resource resource, String propertyLocalName, String literalValue) {
		Property property = model.createProperty(ConnectionProperties.SCIENCE_PAPER_PREDICATE_NAMESPACE, propertyLocalName);
		Literal literal = model.createLiteral(literalValue);
	    model.add(model.createStatement(resource, property, literal));
	}
	
	public void deleteNodeFromRDF(String sciencePaperId) {
		Model model = ModelFactory.createDefaultModel();
		
		String nodeURI = "http://localhost:8080/SciencePapers/" + sciencePaperId;
		org.apache.jena.rdf.model.Resource resource = ResourceFactory.createResource(nodeURI);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		model.write(out,SparqlUtil.NTRIPLES);
		
		String sparqlUpdate = SparqlUtil.deleteNodeFromGraph(ConnectionProperties.dataEndpoint + ConnectionProperties.SCIENCE_PAPER_METADATA, nodeURI);
		System.out.println(sparqlUpdate);
		
		ConnectionProperties.executeUpdateMetadata(sparqlUpdate);
		
	}
}
