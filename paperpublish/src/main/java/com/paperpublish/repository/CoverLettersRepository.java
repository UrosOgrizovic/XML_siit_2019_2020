package com.paperpublish.repository;

import com.paperpublish.model.letter.CoverLetters;
import com.paperpublish.model.letter.TAuthor;
import com.paperpublish.model.letter.TCoverLetter;
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
public class CoverLettersRepository {
    @Autowired
    @Lazy
    Collection collection;

    public Long create(TCoverLetter coverLetter) throws Exception {
        XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.COVER_LETTERS_PACKAGE);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(coverLetter, writer);

            long res = updateQueryService.updateResource(ConnectionProperties.COVER_LETTERS_ID,
                    String.format(XUpdateTemplate.APPEND, ConnectionProperties.COVER_LETTERS_NAMESPACE, "//Letter", writer.toString()));

            return res;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Long createFromXML(String xml) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.COVER_LETTERS_PACKAGE);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            TCoverLetter letterToCreate = (TCoverLetter) unmarshaller.unmarshal(new StringReader(xml));
            return create(letterToCreate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

}
