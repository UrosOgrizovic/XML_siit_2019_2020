package com.paperpublish.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionProperties {

    public String user;
    public String password;
    public String driver;
    public String uri;

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

    public static InputStream openStream(String fileName) throws IOException {
        return ConnectionProperties.class.getClassLoader().getResourceAsStream(fileName);
    }

    public static ConnectionProperties loadProperties(){
        if(instance == null){
            instance = new ConnectionProperties();
        }
        return instance;
    }
}
