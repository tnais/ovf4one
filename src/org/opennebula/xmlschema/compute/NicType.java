//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.24 at 01:21:00 PM CEST 
//


package org.opennebula.xmlschema.compute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NicType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NicType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BRIDGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MAC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NETWORK" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NETWORK_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NicType", propOrder = {
    "bridge",
    "ip",
    "mac",
    "network",
    "networkid"
})
public class NicType {

    @XmlElement(name = "BRIDGE")
    protected String bridge;
    @XmlElement(name = "IP")
    protected String ip;
    @XmlElement(name = "MAC")
    protected String mac;
    @XmlElement(name = "NETWORK")
    protected String network;
    @XmlElement(name = "NETWORK_ID")
    protected String networkid;

    /**
     * Gets the value of the bridge property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBRIDGE() {
        return bridge;
    }

    /**
     * Sets the value of the bridge property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBRIDGE(String value) {
        this.bridge = value;
    }

    /**
     * Gets the value of the ip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIP() {
        return ip;
    }

    /**
     * Sets the value of the ip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIP(String value) {
        this.ip = value;
    }

    /**
     * Gets the value of the mac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMAC() {
        return mac;
    }

    /**
     * Sets the value of the mac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMAC(String value) {
        this.mac = value;
    }

    /**
     * Gets the value of the network property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETWORK() {
        return network;
    }

    /**
     * Sets the value of the network property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETWORK(String value) {
        this.network = value;
    }

    /**
     * Gets the value of the networkid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETWORKID() {
        return networkid;
    }

    /**
     * Sets the value of the networkid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETWORKID(String value) {
        this.networkid = value;
    }

}
