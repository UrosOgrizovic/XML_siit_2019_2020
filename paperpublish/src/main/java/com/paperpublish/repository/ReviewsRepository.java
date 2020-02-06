package com.paperpublish.repository;

import com.paperpublish.model.blindedreviews.Comment;
import com.paperpublish.model.blindedreviews.Comments;
import com.paperpublish.model.blindedreviews.TBlindedReview;
import com.paperpublish.model.letter.CoverLetters;
import com.paperpublish.model.letter.TAuthor;
import com.paperpublish.model.letter.TCoverLetter;
import com.paperpublish.model.reviewassignments.TReviewAssignment;
import com.paperpublish.model.reviews.TReview;
import com.paperpublish.model.sciencepapers.TAuthors;
import com.paperpublish.model.sciencepapers.TSciencePaper;
import com.paperpublish.utils.ConnectionProperties;
import com.paperpublish.utils.SparqlUtil;
import com.paperpublish.utils.XUpdateTemplate;
import org.apache.jena.rdf.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewsRepository {
    @Autowired
    @Lazy
    Collection collection;

    public List<TReview> getAllByDocumentId(String documentId) throws Exception {
            XPathQueryService queryService = ConnectionProperties.getXPathService(collection);
            queryService.setNamespace("",ConnectionProperties.REVIEWS_NAMESPACE);
            ResourceSet result = queryService.query("//Reviews/Review[./Paper[@paperId='" + documentId + "']]");

            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.REVIEWS_PACKAGE);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            ResourceIterator i = result.getIterator();
            List<TReview> reviewList = new ArrayList<>();

            while(i.hasMoreResources()) {
                reviewList.add((TReview) unmarshaller.unmarshal(new StringReader(i.nextResource().getContent().toString())));
            }

            return reviewList;
    }

    public Long create(TReview review) throws Exception {
        XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.REVIEWS_PACKAGE);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(review, writer);

            long res = updateQueryService.updateResource(ConnectionProperties.REVIEWS_ID,
                    String.format(XUpdateTemplate.APPEND, ConnectionProperties.REVIEWS_NAMESPACE, "//Reviews", writer.toString()));
            XMLResource allReviewsXML = (XMLResource) collection.getResource(ConnectionProperties.BLINDED_REVIEWS_ID);
            FileWriter fw = new FileWriter(new File("src/main/resources/data/BlindedReview1.xml"));
            fw.write(allReviewsXML.getContent().toString());
            fw.close();
            
//            saveRDFModel(review);
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Long createFromXML(String xml) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.REVIEWS_PACKAGE);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            TReview reviewToCreate = (TReview) unmarshaller.unmarshal(new StringReader(xml));
            return create(reviewToCreate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

    public TBlindedReview createMergedReview(TBlindedReview blindedReview) throws Exception {
        XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.BLINDED_REVIEWS_PACKAGE);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(blindedReview, writer);

            long res = updateQueryService.updateResource(ConnectionProperties.BLINDED_REVIEWS_ID,
                    String.format(XUpdateTemplate.APPEND, ConnectionProperties.BLINDED_REVIEWS_NAMESPACE, "//BlindedReviews", writer.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return blindedReview;
    }

    public TBlindedReview merge(String documentId) throws Exception {
        TBlindedReview blindedReview = new TBlindedReview();
        Comments comments = new Comments();
        blindedReview.setComments(comments);
        TBlindedReview.Paper paper = new TBlindedReview.Paper();
        paper.setPaperId(documentId);
        blindedReview.setPaper(paper);
        List<TReview> reviews = this.getAllByDocumentId(documentId);

        for(TReview t: reviews) {
            for(com.paperpublish.model.reviews.Comment c: t.getComments().getComment()) {
                Comment newCommentToAdd = new Comment();
                newCommentToAdd.setParagrafId(c.getParagrafId());
                newCommentToAdd.setValue(c.getValue());
                blindedReview.getComments().getComment().add(newCommentToAdd);
            }
        }
        return this.createMergedReview(blindedReview);
    }


    public List<TReviewAssignment> getAllAssignments(String userName) throws Exception {
        XPathQueryService queryService = ConnectionProperties.getXPathService(collection);
        queryService.setNamespace("",ConnectionProperties.REVIEW_ASSIGNMENTS_NAMESPACE);
        ResourceSet result = queryService.query("//ReviewAssignments/ReviewAssignment[./reviewerUserName[text()='"+userName+"']]");

        JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.REVIEW_ASSIGNMENTS_PACKAGE);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        ResourceIterator i = result.getIterator();
        List<TReviewAssignment> assignmentList = new ArrayList<>();

        while(i.hasMoreResources()) {
            assignmentList.add((TReviewAssignment) unmarshaller.unmarshal(new StringReader(i.nextResource().getContent().toString())));
        }

        return assignmentList;
    }


}
