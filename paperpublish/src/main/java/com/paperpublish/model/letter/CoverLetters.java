//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.01.19 at 07:43:51 PM CET 
//


package com.paperpublish.model.letter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CoverLetter" type="{http://localhost:8080/Letter}TCoverLetter"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "coverLetter"
})
@XmlRootElement(name = "CoverLetters")
public class CoverLetters {

    @XmlElement(name = "CoverLetter", required = true)
    protected TCoverLetter coverLetter;

    /**
     * Gets the value of the coverLetter property.
     * 
     * @return
     *     possible object is
     *     {@link TCoverLetter }
     *     
     */
    public TCoverLetter getCoverLetter() {
        return coverLetter;
    }

    /**
     * Sets the value of the coverLetter property.
     * 
     * @param value
     *     allowed object is
     *     {@link TCoverLetter }
     *     
     */
    public void setCoverLetter(TCoverLetter value) {
        this.coverLetter = value;
    }

}