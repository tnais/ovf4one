//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.17 at 06:51:11 PM CEST 
//


package org.opennebula.xmlschema.net;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualNetworkDetailsLease complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualNetworkDetailsLease">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MAC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="USED" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="VID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualNetworkDetailsLease", propOrder = {
    "ip",
    "mac",
    "used",
    "vid"
})
public class VirtualNetworkDetailsLease {

    @XmlElement(name = "IP", required = true)
    protected String ip;
    @XmlElement(name = "MAC", required = true)
    protected String mac;
    @XmlElement(name = "USED")
    protected int used;
    @XmlElement(name = "VID")
    protected int vid;

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
     * Gets the value of the used property.
     * 
     */
    public int getUSED() {
        return used;
    }

    /**
     * Sets the value of the used property.
     * 
     */
    public void setUSED(int value) {
        this.used = value;
    }

    /**
     * Gets the value of the vid property.
     * 
     */
    public int getVID() {
        return vid;
    }

    /**
     * Sets the value of the vid property.
     * 
     */
    public void setVID(int value) {
        this.vid = value;
    }

}
