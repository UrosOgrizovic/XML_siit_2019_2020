package com.paperpublish.security.repository;

import com.paperpublish.utils.ConnectionProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

@Component
public class AuthorityRepository{

    @Autowired
    @Lazy
    private Collection collection;

    public String findAuthorityByName(String name) {
        try {


            XMLResource resource = (XMLResource) collection.getResource(ConnectionProperties.ROLES_ID);
            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.SCIENCE_PAPER_PACKAGE);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        }catch (Exception e){

        }
//        return  (Role) unmarshaller.unmarshal(resource.getContentAsDOM());
        return "";
    }
}
