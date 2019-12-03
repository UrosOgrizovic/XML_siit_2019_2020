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
	public static void createDB(){
		try {
			ConnectionProperties.createDB();
			ConnectionProperties.initData();
			ConnectionProperties.readDataTest();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

}
