//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.11 at 07:41:07 PM CET 
//


package com.paperpublish.model.sciencepapers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TPaperData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPaperData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Title" form="qualified">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="documentTitle" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ShortTitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PublicationDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Citation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DocumentLink" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Author" type="{http://localhost:8080/SciencePapers}TAuthors" maxOccurs="unbounded"/>
 *         &lt;element name="DownloadInformation" type="{http://localhost:8080/SciencePapers}TDownloadInformation"/>
 *       &lt;/sequence>
 *       &lt;attribute name="numberOfReferences" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="contact" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="numberOfDownloads" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPaperData", propOrder = {
    "title",
    "shortTitle",
    "publicationDate",
    "citation",
    "documentLink",
    "author",
    "downloadInformation"
})
public class TPaperData {

    @XmlElement(name = "Title", required = true)
    protected Title title;
    @XmlElement(name = "ShortTitle", required = true)
    protected String shortTitle;
    @XmlElement(name = "PublicationDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar publicationDate;
    @XmlElement(name = "Citation", required = true)
    protected String citation;
    @XmlElement(name = "DocumentLink", required = true)
    protected String documentLink;
    @XmlElement(name = "Author", required = true)
    protected List<TAuthors> author;
    @XmlElement(name = "DownloadInformation", required = true)
    protected TDownloadInformation downloadInformation;
    @XmlAttribute(name = "numberOfReferences")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger numberOfReferences;
    @XmlAttribute(name = "contact")
    protected String contact;
    @XmlAttribute(name = "numberOfDownloads")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger numberOfDownloads;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link Title }
     *     
     */
    public Title getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link Title }
     *     
     */
    public void setTitle(Title value) {
        this.title = value;
    }

    /**
     * Gets the value of the shortTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortTitle() {
        return shortTitle;
    }

    /**
     * Sets the value of the shortTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortTitle(String value) {
        this.shortTitle = value;
    }

    /**
     * Gets the value of the publicationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets the value of the publicationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPublicationDate(XMLGregorianCalendar value) {
        this.publicationDate = value;
    }

    /**
     * Gets the value of the citation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitation() {
        return citation;
    }

    /**
     * Sets the value of the citation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitation(String value) {
        this.citation = value;
    }

    /**
     * Gets the value of the documentLink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentLink() {
        return documentLink;
    }

    /**
     * Sets the value of the documentLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentLink(String value) {
        this.documentLink = value;
    }

    /**
     * Gets the value of the author property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the author property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TAuthors }
     * 
     * 
     */
    public List<TAuthors> getAuthor() {
        if (author == null) {
            author = new ArrayList<TAuthors>();
        }
        return this.author;
    }

    /**
     * Gets the value of the downloadInformation property.
     * 
     * @return
     *     possible object is
     *     {@link TDownloadInformation }
     *     
     */
    public TDownloadInformation getDownloadInformation() {
        return downloadInformation;
    }

    /**
     * Sets the value of the downloadInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDownloadInformation }
     *     
     */
    public void setDownloadInformation(TDownloadInformation value) {
        this.downloadInformation = value;
    }

    /**
     * Gets the value of the numberOfReferences property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfReferences() {
        return numberOfReferences;
    }

    /**
     * Sets the value of the numberOfReferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfReferences(BigInteger value) {
        this.numberOfReferences = value;
    }

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContact(String value) {
        this.contact = value;
    }

    /**
     * Gets the value of the numberOfDownloads property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfDownloads() {
        return numberOfDownloads;
    }

    /**
     * Sets the value of the numberOfDownloads property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfDownloads(BigInteger value) {
        this.numberOfDownloads = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="documentTitle" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Title {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "documentTitle", required = true)
        protected String documentTitle;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the documentTitle property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDocumentTitle() {
            return documentTitle;
        }

        /**
         * Sets the value of the documentTitle property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDocumentTitle(String value) {
            this.documentTitle = value;
        }

    }

}
