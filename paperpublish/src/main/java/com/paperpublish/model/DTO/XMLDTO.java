package com.paperpublish.model.DTO;

public class XMLDTO {
    private String xml;
    public XMLDTO(String xml) {
        super();
        this.xml = xml;
    }

    public XMLDTO() {
        super();
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }
}
