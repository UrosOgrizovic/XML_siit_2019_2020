package com.paperpublish.security.repository;


import com.paperpublish.model.users.ObjectFactory;
import com.paperpublish.model.users.User;
import com.paperpublish.model.users.Users;
import com.paperpublish.utils.ConnectionProperties;
import com.paperpublish.utils.FileUtil;
import com.paperpublish.utils.SparqlUtil;
import com.paperpublish.utils.XUpdateTemplate;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.*;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.Node;
import javax.xml.transform.Result;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import com.paperpublish.model.users.User;
import com.paperpublish.model.users.Users;
import com.paperpublish.utils.ConnectionProperties;
import com.paperpublish.utils.XUpdateTemplate;

@Component
public class UserRepository{

    @Autowired
    private Collection collection;

    public static final String findByUsernameQr = "findUserByUsername.rq";
    public static final String findByEmailQr = "findUserByEmail.rq";

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

    /**
     * 
     * @param newUser user to be saved
     * @return 1 if success, -1 if conflict, else null
     */
    public Long save(User newUser) {
        XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
        try {
            String query = String.format(FileUtil.readFile(ConnectionProperties.QUERY_LOCATION + findByUsernameQr),
                    ConnectionProperties.dataEndpoint + ConnectionProperties.USERS_METADATA, newUser.getUsername());
            ResultSet resultSet = ConnectionProperties.executeQueryMetadata(query);
            if(resultSet.hasNext()){
                return -1L;
            }

            query = String.format(FileUtil.readFile(ConnectionProperties.QUERY_LOCATION + findByEmailQr),
                    ConnectionProperties.dataEndpoint + ConnectionProperties.USERS_METADATA, newUser.getEMail());
            resultSet = ConnectionProperties.executeQueryMetadata(query);
            if(resultSet.hasNext()){
                return -1L;
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.USERS_PACKAGE);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(newUser, writer);
            
            if (getIndexOfUserInListOfUsers(getAll().getUser(), newUser.getUsername()) == -1) {
            	long res = updateQueryService.updateResource(ConnectionProperties.USERS_ID,
                        String.format(XUpdateTemplate.APPEND, ConnectionProperties.USERS_NAMESPACE, "//Users", writer.toString()));
                saveRDFModel(newUser);
                return res;
            } else {
            	return -1L;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveRDFModel(User user){
        Model model = ModelFactory.createDefaultModel();
//        model.setNsPrefix("pred", ConnectionProperties.USERS_PREDICATE_NAMESPACE);

        org.apache.jena.rdf.model.Resource resource = ResourceFactory.createResource("http://localhost:8080/Users/" + user.getUserName());

        Property property = model.createProperty(ConnectionProperties.USERS_PREDICATE_NAMESPACE, "username");
        Literal literal = model.createLiteral(user.getUsername());

        model.add(model.createStatement(resource,property,literal));

        property = model.createProperty(ConnectionProperties.USERS_PREDICATE_NAMESPACE, "email");
        literal = model.createLiteral(user.getEMail());

        model.add(model.createStatement(resource,property,literal));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        model.write(out,SparqlUtil.NTRIPLES);

        String sparqlUpdate = SparqlUtil.insertData(ConnectionProperties.dataEndpoint + ConnectionProperties.USERS_METADATA, new String(out.toByteArray()));
        System.out.println(sparqlUpdate);

        ConnectionProperties.executeUpdateMetadata(sparqlUpdate);
    }
    public Users getAll() throws Exception {
    	XMLResource resource = (XMLResource) collection.getResource(ConnectionProperties.USERS_ID);
    	JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.USERS_PACKAGE);
    	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    	return (Users) unmarshaller.unmarshal(resource.getContentAsDOM());
    }
    
	public Long update(User user) throws Exception {
		String username = user.getUsername();
		User oldUser = this.findByUsername(username);
		if (oldUser == null) {
			throw new ResourceNotFoundException("User with username: '" + username + "' not found");
		}
		user.setRoles(oldUser.getRoles());

		XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.USERS_PACKAGE);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(user, writer);
            String userRoles = "<Roles>\r\n";
            Iterator<? extends GrantedAuthority> it = user.getAuthorities().iterator();
            while (it.hasNext()) {
            	GrantedAuthority ga = it.next();
            	userRoles += "<Role>" + ga.getAuthority() + "</Role>\r\n";
            }
            userRoles += "</Roles>";
            String patch = "<UserName>"+user.getUsername()+"</UserName>\r\n" + 
            		"        <Password>"+user.getPassword()+"</Password>\r\n" + 
            		"        <FullName>"+user.getFullName()+"</FullName>\r\n" + 
            		"        <Institution>"+user.getInstitution()+"</Institution>\r\n" + 
            		"        <EMail>"+user.getEMail()+"</EMail>\r\n" + 
            				userRoles;
            List<User> users = getAll().getUser();
            int indexOfUser = getIndexOfUserInListOfUsers(users, username);
            
            return updateQueryService.updateResource(ConnectionProperties.USERS_ID, String.format(XUpdateTemplate.UPDATE, 
            		ConnectionProperties.USERS_NAMESPACE, "//Users/User["+indexOfUser+"]", patch));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
	}

	/**
	 * @param users list of users to search
	 * @param username username to search for
	 * @return -1 if user not in list, otherwise index+1 (because indexing in XPath starts at 1)
	 * */
	private int getIndexOfUserInListOfUsers(List<User> users, String username) {
		int indexOfUser = -1;
		for (int i = 0; i < users.size(); i++) {
	    	if (users.get(i).getUsername().equals(username)) {
	    		indexOfUser = i+1;
	    		break;
	    	}
	    }
		return indexOfUser;
	}

	public void delete(String username) throws Exception {
		if (this.findByUsername(username) == null) {
			throw new ResourceNotFoundException("User with username: '" + username + "' not found");
		}
		XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
		try {
			List<User> users = getAll().getUser();
	        int indexOfUser = getIndexOfUserInListOfUsers(users, username);
	        updateQueryService.updateResource(ConnectionProperties.USERS_ID,
                    String.format(XUpdateTemplate.REMOVE, ConnectionProperties.USERS_NAMESPACE, "//Users/User["+indexOfUser+"]"));
		} catch (Exception e) {
			e.printStackTrace();
            throw e;
		}
		
		
	}
}
