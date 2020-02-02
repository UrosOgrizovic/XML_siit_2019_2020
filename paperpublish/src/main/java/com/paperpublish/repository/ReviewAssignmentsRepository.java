package com.paperpublish.repository;


import com.paperpublish.model.reviewassignments.TReviewAssignment;
import com.paperpublish.model.sciencepapers.TSciencePaper;
import com.paperpublish.utils.ConnectionProperties;
import com.paperpublish.utils.XUpdateTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class ReviewAssignmentsRepository {
    @Autowired
    @Lazy
    Collection collection;


    public Long create(TReviewAssignment assignment) throws Exception {
        XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.REVIEW_ASSIGNMENTS_PACKAGE);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(assignment, writer);

            long res = updateQueryService.updateResource(ConnectionProperties.REVIEW_ASSIGNMENTS_ID,
                    String.format(XUpdateTemplate.APPEND, ConnectionProperties.REVIEW_ASSIGNMENTS_NAMESPACE, "//ReviewAssignments", writer.toString()));
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }



    public TReviewAssignment findByDocumentIdAndUsername(String documentId, String userName) throws Exception {
        XPathQueryService queryService = ConnectionProperties.getXPathService(collection);
        try {
            queryService.setNamespace("",ConnectionProperties.REVIEW_ASSIGNMENTS_NAMESPACE);
            ResourceSet result = queryService.query("//ReviewAssignments/ReviewAssignment[@paperId='"+documentId+"'][./reviewerUserName[text()='"+userName+"']]");


            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.REVIEW_ASSIGNMENTS_PACKAGE);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            ResourceIterator i = result.getIterator();
            Resource res = i.nextResource();

            return (TReviewAssignment) unmarshaller.unmarshal(new StringReader(res.getContent().toString()));

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }




    public void delete(String documentId, String userName) throws Exception {
        TReviewAssignment assignment = this.findByDocumentIdAndUsername(documentId, userName);
        if (assignment == null) {
            throw new ResourceNotFoundException("Not found");
        }
        XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
        try {
//            deleteNodeFromRDF(sciencePaper.getDocumentId());
            updateQueryService.updateResource(ConnectionProperties.REVIEW_ASSIGNMENTS_ID,
                    String.format(XUpdateTemplate.REMOVE, ConnectionProperties.REVIEW_ASSIGNMENTS_NAMESPACE,
                            "//ReviewAssignments/ReviewAssignment[@paperId='"+documentId+"'][./reviewerUserName[text()='"+userName+"']]"));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

}
