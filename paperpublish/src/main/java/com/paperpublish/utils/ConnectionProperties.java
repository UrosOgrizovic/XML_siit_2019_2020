package com.paperpublish.utils;

import com.paperpublish.model.sciencepapers.SciencePapers;
import com.paperpublish.model.sciencepapers.TSciencePaper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class ConnectionProperties {

    public String user;
    public String password;
    public String driver;
    public String uri;
    private static Collection col = null;

    public static final String initDataPath = "src/main/resources/data/";
    public static final String SCIENCE_PAPER_ID = "science_paper.xml";
    public static final String ROLES_ID = "Roles.xml";
    public static final String USERS_ID = "Users.xml";

    public static final String PACKAGE_PATH = "com.paperpublish.model.";
    public static final String SCIENCE_PAPER_PACKAGE = "sciencepapers";
    public static final String ROLES_PACKAGE = "users";
    public static final String USERS_PACKAGE = "users";

    public static final String USERS_NAMESPACE = "http://localhost:8080/Users";
    public static final String SCIENCE_PAPERS_NAMESPACE = "http://localhost:8080/SciencePapers";

    private static ConnectionProperties instance = null;

    private ConnectionProperties(){
        super();

        String propsName = "application.properties";

        InputStream propsStream = null;
        try {
            propsStream = openStream(propsName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (propsStream == null)
            System.out.println("Could not read properties " + propsName);

        Properties props = new Properties();
        try {
            props.load(propsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


        user = props.getProperty("conn.user").trim();
        password = props.getProperty("conn.password").trim();


        uri = props.getProperty("conn.uri").trim();

        driver = props.getProperty("conn.driver").trim();
    }

    @Bean
    public static Collection getCol() {
        return col;
    }

    public static XPathQueryService getXPathService(Collection collection){
        XPathQueryService xpathService = null;
        try {
            xpathService = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
            xpathService.setProperty("indent", "yes");
        } catch (XMLDBException e) {
            e.printStackTrace();
        }

        return xpathService;
    }

    public static XUpdateQueryService getXUpdateQueryService(Collection collection){
        XUpdateQueryService xupdateService = null;
        try {
            xupdateService = (XUpdateQueryService) collection.getService("XUpdateQueryService", "1.0");
            xupdateService.setProperty("indent", "yes");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xupdateService;
    }

    public static InputStream openStream(String fileName) throws IOException {
        return ConnectionProperties.class.getClassLoader().getResourceAsStream(fileName);
    }


    public static void createDB() throws Exception {
        ConnectionProperties conn = ConnectionProperties.loadProperties();
        Class<?> cl =Class.forName(conn.driver);


        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");

        DatabaseManager.registerDatabase(database);
        String collectionId = "db/apps/paper-publish";

        try {

            System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = getOrCreateCollection(conn, collectionId);


        } finally {
            if(col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
    }

    private static Collection getOrCreateCollection(ConnectionProperties conn, String collectionUri) throws XMLDBException {
        return getOrCreateCollection(conn, collectionUri, 0);
    }

    private static Collection getOrCreateCollection(ConnectionProperties conn, String collectionUri, int pathSegmentOffset) throws XMLDBException {

        Collection col = DatabaseManager.getCollection(conn.uri + collectionUri, conn.user, conn.password);

        // create the collection if it does not exist
        if(col == null) {

            if(collectionUri.startsWith("/")) {
                collectionUri = collectionUri.substring(1);
            }

            String pathSegments[] = collectionUri.split("/");

            if(pathSegments.length > 0) {
                StringBuilder path = new StringBuilder();

                for(int i = 0; i <= pathSegmentOffset; i++) {
                    path.append("/" + pathSegments[i]);
                }

                Collection startCol = DatabaseManager.getCollection(conn.uri + path, conn.user, conn.password);

                if (startCol == null) {

                    // child collection does not exist

                    String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parentCol = DatabaseManager.getCollection(conn.uri + parentPath, conn.user, conn.password);

                    CollectionManagementService mgt = (CollectionManagementService) parentCol.getService("CollectionManagementService", "1.0");

                    System.out.println("[INFO] Creating the collection: " + pathSegments[pathSegmentOffset]);
                    col = mgt.createCollection(pathSegments[pathSegmentOffset]);

                    col.close();
                    parentCol.close();

                } else {
                    startCol.close();
                }
            }
            return getOrCreateCollection(conn, collectionUri, ++pathSegmentOffset);
        } else {
            return col;
        }
    }

    public static ConnectionProperties loadProperties(){
        if(instance == null){
            instance = new ConnectionProperties();
        }
        return instance;
    }

    public static void initData() throws Exception{
        XMLResource resource = (XMLResource) col.createResource(SCIENCE_PAPER_ID,XMLResource.RESOURCE_TYPE);
        File f = new File(initDataPath + SCIENCE_PAPER_ID);
        resource.setContent(f);
        col.storeResource(resource);

        resource = (XMLResource) col.createResource(ROLES_ID,XMLResource.RESOURCE_TYPE);
        f = new File(initDataPath + ROLES_ID);
        resource.setContent(f);
        col.storeResource(resource);

        resource = (XMLResource) col.createResource(USERS_ID,XMLResource.RESOURCE_TYPE);
        f = new File(initDataPath + USERS_ID);
        resource.setContent(f);
        col.storeResource(resource);
    }

    public static void readDataTest() throws Exception {
        XMLResource resource = (XMLResource) col.getResource(SCIENCE_PAPER_ID);
        JAXBContext jaxbContext = JAXBContext.newInstance(PACKAGE_PATH + SCIENCE_PAPER_PACKAGE);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        SciencePapers papers = (SciencePapers) unmarshaller.unmarshal(resource.getContentAsDOM());
        for(TSciencePaper paper : papers.getSciencePaper() ){
            System.out.println(paper.getPaperData().getTitle().getDocumentTitle());
        }
    }

}
