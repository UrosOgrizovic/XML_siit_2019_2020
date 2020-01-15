package com.paperpublish.model.DTO;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.paperpublish.model.sciencepapers.TCitations;
import com.paperpublish.model.sciencepapers.TPaperData;
import com.paperpublish.model.sciencepapers.TParagraf;

public class TSciencePaperDTO {
    private TPaperData paperData;
    private List<TParagraf> paragraf;
    private TCitations citations;
    private List<String> citedBy;
    private String documentType;
    private String documentId;
    private XMLGregorianCalendar received;
    private XMLGregorianCalendar revised;
    private XMLGregorianCalendar accepted;
    private String pp;
	public TPaperData getPaperData() {
		return paperData;
	}
	public void setPaperData(TPaperData paperData) {
		this.paperData = paperData;
	}
	public List<TParagraf> getParagraf() {
		return paragraf;
	}
	public void setParagraf(List<TParagraf> paragraf) {
		this.paragraf = paragraf;
	}
	public TCitations getCitations() {
		return citations;
	}
	public void setCitations(TCitations citations) {
		this.citations = citations;
	}
	public List<String> getCitedBy() {
		return citedBy;
	}
	public void setCitedBy(List<String> citedBy) {
		this.citedBy = citedBy;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public XMLGregorianCalendar getReceived() {
		return received;
	}
	public void setReceived(XMLGregorianCalendar received) {
		this.received = received;
	}
	public XMLGregorianCalendar getRevised() {
		return revised;
	}
	public void setRevised(XMLGregorianCalendar revised) {
		this.revised = revised;
	}
	public XMLGregorianCalendar getAccepted() {
		return accepted;
	}
	public void setAccepted(XMLGregorianCalendar accepted) {
		this.accepted = accepted;
	}
	public String getPp() {
		return pp;
	}
	public void setPp(String pp) {
		this.pp = pp;
	}
	public TSciencePaperDTO(TPaperData paperData, List<TParagraf> paragraf, TCitations citations, List<String> citedBy,
			String documentType, String documentId, XMLGregorianCalendar received, XMLGregorianCalendar revised,
			XMLGregorianCalendar accepted, String pp) {
		super();
		this.paperData = paperData;
		this.paragraf = paragraf;
		this.citations = citations;
		this.citedBy = citedBy;
		this.documentType = documentType;
		this.documentId = documentId;
		this.received = received;
		this.revised = revised;
		this.accepted = accepted;
		this.pp = pp;
	}
	public TSciencePaperDTO() {
		super();
	}
    
    
}
