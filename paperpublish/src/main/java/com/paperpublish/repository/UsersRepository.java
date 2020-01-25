package com.paperpublish.repository;

import com.paperpublish.model.sciencepapers.TSciencePaper;
import com.paperpublish.model.users.User;
import com.paperpublish.model.users.Users;
import com.paperpublish.utils.ConnectionProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class UsersRepository {
    @Autowired
    @Lazy
    Collection collection;

    public List<User> getAll() throws Exception {
        XMLResource resource = (XMLResource) collection.getResource(ConnectionProperties.USERS_ID);
        JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.USERS_PACKAGE);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        Users sp = (Users) unmarshaller.unmarshal(resource.getContentAsDOM());
        return sp.getUser();
    }

    public List<User> getAuthors() throws Exception {
        XPathQueryService queryService = ConnectionProperties.getXPathService(collection);
        queryService.setNamespace("",ConnectionProperties.USERS_NAMESPACE);
        ResourceSet result = queryService.query("//Users/User[./Roles/Role[text()='ROLE_USER']]");

        JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.USERS_PACKAGE);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        ResourceIterator i = result.getIterator();
        List<User> userList = new ArrayList<>();

        while(i.hasMoreResources()) {
            userList.add((User) unmarshaller.unmarshal(new StringReader(i.nextResource().getContent().toString())));
        }

        return userList;
    }

    public List<User> getProposals(TSciencePaper sciencePaper) throws Exception {
        List<User> users = this.getAuthors();
        System.out.println(UsersRepository.findMatchCount(users.get(0).getExpertises() != null ? users.get(0).getExpertises().getExpertise() : new ArrayList<>(), sciencePaper.getPaperData().getKeywords() != null ? sciencePaper.getPaperData().getKeywords().getKeyword() : new ArrayList<>()));

        Collections.sort(users, Comparator.comparingInt(o -> -1 * UsersRepository.findMatchCount(o.getExpertises() != null ? o.getExpertises().getExpertise() : new ArrayList<>(), sciencePaper.getPaperData().getKeywords() != null ? sciencePaper.getPaperData().getKeywords().getKeyword() : new ArrayList<>())));
        return users;
    }

    public static int findMatchCount(List<String> l1, List<String> l2){
        int matchCount = 0;

        for(String s1: l1) {
            for(String s2: l2) {
                if(s1.toLowerCase().equals(s2.toLowerCase())) {
                    matchCount++;
                }
            }
        }
        return matchCount;
    }
}
