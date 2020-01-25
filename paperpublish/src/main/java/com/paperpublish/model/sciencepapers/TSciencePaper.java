//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.01.25 at 09:28:33 AM CET 
//


package com.paperpublish.model.sciencepapers;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TSciencePaper complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TSciencePaper">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PaperData" type="{http://localhost:8080/SciencePapers}TPaperData"/>
 *         &lt;element name="Paragraf" type="{http://localhost:8080/SciencePapers}TParagraf" maxOccurs="unbounded"/>
 *         &lt;element name="Citations" type="{http://localhost:8080/SciencePapers}TCitations" minOccurs="0"/>
 *         &lt;element name="CitedBy" type="{http://localhost:8080/SciencePapers}TCitedBy" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="documentType" default="XML">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="RDF"/>
 *             &lt;enumeration value="XML"/>
 *             &lt;enumeration value="XHTML"/>
 *             &lt;whiteSpace value="preserve"/>
 *             &lt;enumeration value="PDF"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="documentId" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="\d{1,2}"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="versionId" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="\d{1,2}"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="received" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="revised" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="accepted" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="pp" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="status" default="in_procedure">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="in_procedure"/>
 *             &lt;enumeration value="deleted"/>
 *             &lt;enumeration value="accepted"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TSciencePaper", propOrder = {
    "paperData",
    "paragraf",
    "citations",
    "citedBy"
})
@XmlRootElement(name = "SciencePaper")
public class TSciencePaper {

    @XmlElement(name = "PaperData", required = true)
    protected TPaperData paperData;
    @XmlElement(name = "Paragraf", required = true)
    protected List<TParagraf> paragraf;
    @XmlElement(name = "Citations")
    protected TCitations citations;
    @XmlElement(name = "CitedBy", required = true)
    protected List<TCitedBy> citedBy;
    @XmlAttribute(name = "documentType")
    protected String documentType;
    @XmlAttribute(name = "documentId", required = true)
    protected String documentId;
    @XmlAttribute(name = "versionId", required = true)
    protected String versionId;
    @XmlAttribute(name = "received")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar received;
    @XmlAttribute(name = "revised")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar revised;
    @XmlAttribute(name = "accepted")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar accepted;
    @XmlAttribute(name = "pp")
    @XmlSchemaType(name = "anySimpleType")
    protected String pp;
    @XmlAttribute(name = "status")
    protected String status;

    /**
     * Gets the value of the paperData property.
     * 
     * @return
     *     possible object is
     *     {@link TPaperData }
     *     
     */
    public TPaperData getPaperData() {
        return paperData;
    }

    /**
     * Sets the value of the paperData property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPaperData }
     *     
     */
    public void setPaperData(TPaperData value) {
        this.paperData = value;
    }

    /**
     * Gets the value of the paragraf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paragraf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParagraf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TParagraf }
     * 
     * 
     */
    public List<TParagraf> getParagraf() {
        if (paragraf == null) {
            paragraf = new ArrayList<TParagraf>();
        }
        return this.paragraf;
    }

    /**
     * Gets the value of the citations property.
     * 
     * @return
     *     possible object is
     *     {@link TCitations }
     *     
     */
    public TCitations getCitations() {
        return citations;
    }

    /**
     * Sets the value of the citations property.
     * 
     * @param value
     *     allowed object is
     *     {@link TCitations }
     *     
     */
    public void setCitations(TCitations value) {
        this.citations = value;
    }

    /**
     * Gets the value of the citedBy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the citedBy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCitedBy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TCitedBy }
     * 
     * 
     */
    public List<TCitedBy> getCitedBy() {
        if (citedBy == null) {
            citedBy = new ArrayList<TCitedBy>();
        }
        return this.citedBy;
    }

    /**
     * Gets the value of the documentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentType() {
        if (documentType == null) {
            return "XML";
        } else {
            return documentType;
        }
    }

    /**
     * Sets the value of the documentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentType(String value) {
        this.documentType = value;
    }

    /**
     * Gets the value of the documentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Sets the value of the documentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentId(String value) {
        this.documentId = value;
    }

    /**
     * Gets the value of the versionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersionId() {
        return versionId;
    }

    /**
     * Sets the value of the versionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersionId(String value) {
        this.versionId = value;
    }

    /**
     * Gets the value of the received property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReceived() {
        return received;
    }

    /**
     * Sets the value of the received property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReceived(XMLGregorianCalendar value) {
        this.received = value;
    }

    /**
     * Gets the value of the revised property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRevised() {
        return revised;
    }

    /**
     * Sets the value of the revised property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRevised(XMLGregorianCalendar value) {
        this.revised = value;
    }

    /**
     * Gets the value of the accepted property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAccepted() {
        return accepted;
    }

    /**
     * Sets the value of the accepted property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAccepted(XMLGregorianCalendar value) {
        this.accepted = value;
    }

    /**
     * Gets the value of the pp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPp() {
        return pp;
    }

    /**
     * Sets the value of the pp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPp(String value) {
        this.pp = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        if (status == null) {
            return "in_procedure";
        } else {
            return status;
        }
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
