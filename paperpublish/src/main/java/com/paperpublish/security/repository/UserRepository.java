package com.paperpublish.security.repository;

import com.paperpublish.model.users.ObjectFactory;
import com.paperpublish.model.users.User;
import com.paperpublish.model.users.Users;
import com.paperpublish.utils.ConnectionProperties;
import com.paperpublish.utils.XUpdateTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.Node;
import javax.xml.transform.Result;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class UserRepository{

    @Autowired
    private Collection collection;

    public User findByUsername(String username) {
        XPathQueryService queryService = ConnectionProperties.getXPathService(collection);
        try {

            queryService.setNamespace("",ConnectionProperties.USERS_NAMESPACE);
            ResourceSet result = queryService.query("//Users/User[UserName=\""+username+"\"]");

            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.USERS_PACKAGE);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            ResourceIterator i = result.getIterator();
            Resource res = i.nextResource();
            return (User) unmarshaller.unmarshal(new StringReader(res.getContent().toString()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long save(User newUser){
        XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.USERS_PACKAGE);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(newUser, writer);

            long res = updateQueryService.updateResource(ConnectionProperties.USERS_ID,
                    String.format(XUpdateTemplate.APPEND, ConnectionProperties.USERS_NAMESPACE, "//Users", writer.toString()));

            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
