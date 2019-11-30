package com.paperpublish;

import com.paperpublish.utils.ConnectionProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import javax.annotation.PostConstruct;
import java.io.File;

@SpringBootApplication
public class PaperpublishApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PaperpublishApplication.class, args);
	}

	@PostConstruct
	public static void createDB() throws Exception {
		ConnectionProperties conn = ConnectionProperties.loadProperties();
		Class<?> cl =Class.forName(conn.driver);


		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");

		DatabaseManager.registerDatabase(database);
		String collectionId = "db/apps/paper-publish";
		Collection col = null;
		XMLResource res = null;

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

}
