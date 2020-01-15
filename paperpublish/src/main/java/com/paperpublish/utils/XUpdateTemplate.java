package com.paperpublish.utils;

import org.springframework.stereotype.Component;

@Component
public class XUpdateTemplate {

    public static final String APPEND = "<xu:modifications version=\"1.0\" xmlns:xu=\"http://www.xmldb.org/xupdate\" " +
            "xmlns=\"%1$s\">"
            + "<xu:append select=\"%2$s\" child=\"last()\">%3$s</xu:append>"
            + "</xu:modifications>";
    
    public static final String UPDATE = "<xu:modifications version=\"1.0\" xmlns:xu=\"http://www.xmldb.org/xupdate\" " +
			"xmlns=\"%1$s\">"
    		+ "<xu:update select=\"%2$s\">%3$s</xu:update>"
			+ "</xu:modifications>";
    
    public static final String REMOVE = "<xu:modifications version=\"1.0\" xmlns:xu=\"http://www.xmldb.org/xupdate\" " +
			"xmlns=\"%1$s\">"
    		+ "<xu:remove select=\"%2$s\"/>"
			+ "</xu:modifications>";
}
