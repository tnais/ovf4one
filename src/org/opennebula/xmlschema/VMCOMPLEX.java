//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.19 at 12:33:00 PM CEST 
//


package org.opennebula.xmlschema;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VM_COMPLEX complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VM_COMPLEX">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="UID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="USERNAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LAST_POLL" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="STATE" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="LCM_STATE" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="STIME" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ETIME" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="DEPLOY_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MEMORY" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="CPU" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="NET_TX" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="NET_RX" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="LAST_SEQ" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="TEMPLATE" type="{http://opennebula.org/XMLSchema}TemplateType"/>
 *         &lt;element name="HISTORY" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VM_COMPLEX", propOrder = {
    "id",
    "uid",
    "username",
    "name",
    "lastpoll",
    "state",
    "lcmstate",
    "stime",
    "etime",
    "deployid",
    "memory",
    "cpu",
    "nettx",
    "netrx",
    "lastseq",
    "template",
    "history"
})
public class VMCOMPLEX {

    @XmlElement(name = "ID", required = true)
    protected BigInteger id;
    @XmlElement(name = "UID", required = true)
    protected BigInteger uid;
    @XmlElement(name = "USERNAME", required = true)
    protected String username;
    @XmlElement(name = "NAME", required = true)
    protected String name;
    @XmlElement(name = "LAST_POLL", required = true)
    protected BigInteger lastpoll;
    @XmlElement(name = "STATE", required = true)
    protected BigInteger state;
    @XmlElement(name = "LCM_STATE", required = true)
    protected BigInteger lcmstate;
    @XmlElement(name = "STIME", required = true)
    protected BigInteger stime;
    @XmlElement(name = "ETIME", required = true)
    protected BigInteger etime;
    @XmlElement(name = "DEPLOY_ID", required = true)
    protected String deployid;
    @XmlElement(name = "MEMORY", required = true)
    protected BigInteger memory;
    @XmlElement(name = "CPU", required = true)
    protected BigInteger cpu;
    @XmlElement(name = "NET_TX", required = true)
    protected BigInteger nettx;
    @XmlElement(name = "NET_RX", required = true)
    protected BigInteger netrx;
    @XmlElement(name = "LAST_SEQ", required = true)
    protected BigInteger lastseq;
    @XmlElement(name = "TEMPLATE", required = true)
    protected TemplateType template;
    @XmlElement(name = "HISTORY", required = true)
    protected Object history;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setID(BigInteger value) {
        this.id = value;
    }

    /**
     * Gets the value of the uid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUID() {
        return uid;
    }

    /**
     * Sets the value of the uid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUID(BigInteger value) {
        this.uid = value;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSERNAME() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSERNAME(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNAME() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNAME(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the lastpoll property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLASTPOLL() {
        return lastpoll;
    }

    /**
     * Sets the value of the lastpoll property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLASTPOLL(BigInteger value) {
        this.lastpoll = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSTATE() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSTATE(BigInteger value) {
        this.state = value;
    }

    /**
     * Gets the value of the lcmstate property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLCMSTATE() {
        return lcmstate;
    }

    /**
     * Sets the value of the lcmstate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLCMSTATE(BigInteger value) {
        this.lcmstate = value;
    }

    /**
     * Gets the value of the stime property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSTIME() {
        return stime;
    }

    /**
     * Sets the value of the stime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSTIME(BigInteger value) {
        this.stime = value;
    }

    /**
     * Gets the value of the etime property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getETIME() {
        return etime;
    }

    /**
     * Sets the value of the etime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setETIME(BigInteger value) {
        this.etime = value;
    }

    /**
     * Gets the value of the deployid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDEPLOYID() {
        return deployid;
    }

    /**
     * Sets the value of the deployid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEPLOYID(String value) {
        this.deployid = value;
    }

    /**
     * Gets the value of the memory property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMEMORY() {
        return memory;
    }

    /**
     * Sets the value of the memory property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMEMORY(BigInteger value) {
        this.memory = value;
    }

    /**
     * Gets the value of the cpu property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCPU() {
        return cpu;
    }

    /**
     * Sets the value of the cpu property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCPU(BigInteger value) {
        this.cpu = value;
    }

    /**
     * Gets the value of the nettx property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNETTX() {
        return nettx;
    }

    /**
     * Sets the value of the nettx property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNETTX(BigInteger value) {
        this.nettx = value;
    }

    /**
     * Gets the value of the netrx property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNETRX() {
        return netrx;
    }

    /**
     * Sets the value of the netrx property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNETRX(BigInteger value) {
        this.netrx = value;
    }

    /**
     * Gets the value of the lastseq property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLASTSEQ() {
        return lastseq;
    }

    /**
     * Sets the value of the lastseq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLASTSEQ(BigInteger value) {
        this.lastseq = value;
    }

    /**
     * Gets the value of the template property.
     * 
     * @return
     *     possible object is
     *     {@link TemplateType }
     *     
     */
    public TemplateType getTEMPLATE() {
        return template;
    }

    /**
     * Sets the value of the template property.
     * 
     * @param value
     *     allowed object is
     *     {@link TemplateType }
     *     
     */
    public void setTEMPLATE(TemplateType value) {
        this.template = value;
    }

    /**
     * Gets the value of the history property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getHISTORY() {
        return history;
    }

    /**
     * Sets the value of the history property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setHISTORY(Object value) {
        this.history = value;
    }

}
