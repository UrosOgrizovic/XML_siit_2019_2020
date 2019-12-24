//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.11 at 07:41:07 PM CET 
//


package com.paperpublish.model.letter;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TCoverLetter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TCoverLetter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Content" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Author" type="{http://localhost:8080/Letter}TAuthor" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="journalName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="journalAddress" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="manuscriptTitle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="articleType" default="review">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="review"/>
 *             &lt;enumeration value="research"/>
 *             &lt;enumeration value="caseStudy"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="submissionDate" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TCoverLetter", propOrder = {
    "content",
    "author"
})
public class TCoverLetter {

    @XmlElement(name = "Content", required = true)
    protected String content;
    @XmlElement(name = "Author", required = true)
    protected List<TAuthor> author;
    @XmlAttribute(name = "journalName", required = true)
    protected String journalName;
    @XmlAttribute(name = "journalAddress")
    protected String journalAddress;
    @XmlAttribute(name = "manuscriptTitle")
    protected String manuscriptTitle;
    @XmlAttribute(name = "articleType")
    protected String articleType;
    @XmlAttribute(name = "submissionDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar submissionDate;

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
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
     * {@link TAuthor }
     * 
     * 
     */
    public List<TAuthor> getAuthor() {
        if (author == null) {
            author = new ArrayList<TAuthor>();
        }
        return this.author;
    }

    /**
     * Gets the value of the journalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJournalName() {
        return journalName;
    }

    /**
     * Sets the value of the journalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJournalName(String value) {
        this.journalName = value;
    }

    /**
     * Gets the value of the journalAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJournalAddress() {
        return journalAddress;
    }

    /**
     * Sets the value of the journalAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJournalAddress(String value) {
        this.journalAddress = value;
    }

    /**
     * Gets the value of the manuscriptTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManuscriptTitle() {
        return manuscriptTitle;
    }

    /**
     * Sets the value of the manuscriptTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManuscriptTitle(String value) {
        this.manuscriptTitle = value;
    }

    /**
     * Gets the value of the articleType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArticleType() {
        if (articleType == null) {
            return "review";
        } else {
            return articleType;
        }
    }

    /**
     * Sets the value of the articleType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArticleType(String value) {
        this.articleType = value;
    }

    /**
     * Gets the value of the submissionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSubmissionDate() {
        return submissionDate;
    }

    /**
     * Sets the value of the submissionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSubmissionDate(XMLGregorianCalendar value) {
        this.submissionDate = value;
    }

}
