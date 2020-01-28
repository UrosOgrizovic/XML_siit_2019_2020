package com.paperpublish.repository;

import com.paperpublish.model.letter.CoverLetters;
import com.paperpublish.model.letter.TAuthor;
import com.paperpublish.model.letter.TCoverLetter;
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
import org.xmldb.api.modules.XUpdateQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class ReviewsRepository {
    @Autowired
    @Lazy
    Collection collection;

    public Long create(TReview review) throws Exception {
        XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.REVIEWS_PACKAGE);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(review, writer);

            long res = updateQueryService.updateResource(ConnectionProperties.REVIEWS_ID,
                    String.format(XUpdateTemplate.APPEND, ConnectionProperties.REVIEWS_NAMESPACE, "//Review", writer.toString()));

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


}
