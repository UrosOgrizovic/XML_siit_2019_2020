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

            saveRDFModel(coverLetter);
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

    public void saveRDFModel(TCoverLetter coverLetter) {
        Model model = ModelFactory.createDefaultModel();

        org.apache.jena.rdf.model.Resource resource = ResourceFactory.createResource("http://localhost:8080/Letter/" + coverLetter.getJournalName());
        this.addPropertyAndLiteralToModel(model, resource, "journalName", coverLetter.getJournalName());
        this.addPropertyAndLiteralToModel(model, resource, "articleType", coverLetter.getArticleType());
        for (TAuthor author : coverLetter.getAuthor()) {
            this.addPropertyAndLiteralToModel(model, resource, "authorFullName", author.getFullName());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        model.write(out, SparqlUtil.NTRIPLES);

        String sparqlUpdate = SparqlUtil.insertData(ConnectionProperties.dataEndpoint + ConnectionProperties.COVER_LETTER_METADATA, new String(out.toByteArray()));
        System.out.println(sparqlUpdate);

        ConnectionProperties.executeUpdateMetadata(sparqlUpdate);
    }

    public void addPropertyAndLiteralToModel(Model model, org.apache.jena.rdf.model.Resource resource, String propertyLocalName, String literalValue) {
        Property property = model.createProperty(ConnectionProperties.COVER_LETTER_PREDICATE_NAMESPACE, propertyLocalName);
        Literal literal = model.createLiteral(literalValue);
        model.add(model.createStatement(resource, property, literal));
    }
}
