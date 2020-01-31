package com.paperpublish.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XQueryService;

public class XQuery {
    
	private static final String TARGET_NAMESPACE = ConnectionProperties.SCIENCE_PAPERS_NAMESPACE;
	
	public static final String RESOURCES_PATH = "src/main/resources"; 
	
	public static void main(String[] args) throws Exception {
		ConnectionProperties.createDB();
		ConnectionProperties.initData();
		ConnectionProperties.readDataTest();
		XQuery.run(ConnectionProperties.loadProperties(), "admin", "a different one");
	}

	/**
	 * conn XML DB connection properties
     * args[0] Should be the collection ID to access
     * args[1] Should be the xquery file path
     */
    public static String run(ConnectionProperties conn, String authorUserName, String textToMatch) throws Exception {
    	String toReturn = "";
    	System.out.println("[INFO] " + XQuery.class.getSimpleName());
    	
    	// initialize collection and document identifiers
        String collectionId = null;
		String xqueryFilePath = null, xqueryFilePathAllPapers = null, xqueryFilePathMyPapers = null, xqueryExpression = null; 
        
		collectionId = "/db/apps/paper-publish";
    	xqueryFilePathAllPapers = RESOURCES_PATH + "/data/xquery/findAllPapersByText.xqy";
    	xqueryFilePathMyPapers = RESOURCES_PATH + "/data/xquery/findMyPapersByText.xqy";

        /*
    	// initialize database driver
    	System.out.println("[INFO] Loading driver class: " + conn.driver);
        Class<?> cl = Class.forName(conn.driver);
        
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        
        DatabaseManager.registerDatabase(database);*/
        
        Collection col = null;
        
        try { 

        	// get the collection
        	System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = DatabaseManager.getCollection(conn.uri + collectionId);
        	
            // get an instance of xquery service
            XQueryService xqueryService = (XQueryService) col.getService("XQueryService", "1.0");
            xqueryService.setProperty("indent", "yes");
            
            // make the service aware of namespaces
            xqueryService.setNamespace("Papers", TARGET_NAMESPACE);

            
             
            if (authorUserName.equals("")) {
            	xqueryFilePath = xqueryFilePathAllPapers;
            } else {
            	xqueryFilePath = xqueryFilePathMyPapers;
            }
            
            // read xquery
            System.out.println("[INFO] Invoking XQuery service for: " + xqueryFilePath);
            xqueryExpression = readFile(xqueryFilePath, StandardCharsets.UTF_8);
            
            
            xqueryService.declareVariable("authorUserName", authorUserName);
            xqueryService.declareVariable("textToMatch", textToMatch);
            
            // compile and execute the expression
            CompiledExpression compiledXquery = xqueryService.compile(xqueryExpression);
            ResourceSet result = xqueryService.execute(compiledXquery);
            
            // handle the results
            System.out.println("[INFO] Handling the results... ");
            
            ResourceIterator i = result.getIterator();
            Resource res = null;
            
            while(i.hasMoreResources()) {
            
            	try {
                    res = i.nextResource();
                    System.out.println(res.getContent());
                    
                    toReturn += res.getContent().toString();
                } finally {
                    
                	// don't forget to cleanup resources
                    try { 
                    	((EXistResource)res).freeResources(); 
                    } catch (XMLDBException xe) {
                    	xe.printStackTrace();
                    }
                }
            }
            
        } finally {
        	
            // don't forget to cleanup
            if(col != null) {
                try { 
                	col.close();
                } catch (XMLDBException xe) {
                	xe.printStackTrace();
                }
            }
        }
        return toReturn;
    }
    
    /**
	 * Convenience method for reading file contents into a string.
	 */
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
    
}
