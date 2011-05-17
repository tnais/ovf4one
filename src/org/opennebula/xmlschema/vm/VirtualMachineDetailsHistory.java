//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.17 at 06:51:10 PM CEST 
//


package org.opennebula.xmlschema.vm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualMachineDetailsHistory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualMachineDetailsHistory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SEQ" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="HOSTNAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="HID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="STIME" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ETIME" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="PSTIME" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="PETIME" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="RSTIME" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="RETIME" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ESTIME" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="EETIME" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="REASON" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualMachineDetailsHistory", propOrder = {
    "seq",
    "hostname",
    "hid",
    "stime",
    "etime",
    "pstime",
    "petime",
    "rstime",
    "retime",
    "estime",
    "eetime",
    "reason"
})
public class VirtualMachineDetailsHistory {

    @XmlElement(name = "SEQ")
    protected int seq;
    @XmlElement(name = "HOSTNAME", required = true)
    protected String hostname;
    @XmlElement(name = "HID")
    protected int hid;
    @XmlElement(name = "STIME")
    protected long stime;
    @XmlElement(name = "ETIME")
    protected long etime;
    @XmlElement(name = "PSTIME")
    protected long pstime;
    @XmlElement(name = "PETIME")
    protected long petime;
    @XmlElement(name = "RSTIME")
    protected long rstime;
    @XmlElement(name = "RETIME")
    protected long retime;
    @XmlElement(name = "ESTIME")
    protected long estime;
    @XmlElement(name = "EETIME")
    protected long eetime;
    @XmlElement(name = "REASON")
    protected int reason;

    /**
     * Gets the value of the seq property.
     * 
     */
    public int getSEQ() {
        return seq;
    }

    /**
     * Sets the value of the seq property.
     * 
     */
    public void setSEQ(int value) {
        this.seq = value;
    }

    /**
     * Gets the value of the hostname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHOSTNAME() {
        return hostname;
    }

    /**
     * Sets the value of the hostname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHOSTNAME(String value) {
        this.hostname = value;
    }

    /**
     * Gets the value of the hid property.
     * 
     */
    public int getHID() {
        return hid;
    }

    /**
     * Sets the value of the hid property.
     * 
     */
    public void setHID(int value) {
        this.hid = value;
    }

    /**
     * Gets the value of the stime property.
     * 
     */
    public long getSTIME() {
        return stime;
    }

    /**
     * Sets the value of the stime property.
     * 
     */
    public void setSTIME(long value) {
        this.stime = value;
    }

    /**
     * Gets the value of the etime property.
     * 
     */
    public long getETIME() {
        return etime;
    }

    /**
     * Sets the value of the etime property.
     * 
     */
    public void setETIME(long value) {
        this.etime = value;
    }

    /**
     * Gets the value of the pstime property.
     * 
     */
    public long getPSTIME() {
        return pstime;
    }

    /**
     * Sets the value of the pstime property.
     * 
     */
    public void setPSTIME(long value) {
        this.pstime = value;
    }

    /**
     * Gets the value of the petime property.
     * 
     */
    public long getPETIME() {
        return petime;
    }

    /**
     * Sets the value of the petime property.
     * 
     */
    public void setPETIME(long value) {
        this.petime = value;
    }

    /**
     * Gets the value of the rstime property.
     * 
     */
    public long getRSTIME() {
        return rstime;
    }

    /**
     * Sets the value of the rstime property.
     * 
     */
    public void setRSTIME(long value) {
        this.rstime = value;
    }

    /**
     * Gets the value of the retime property.
     * 
     */
    public long getRETIME() {
        return retime;
    }

    /**
     * Sets the value of the retime property.
     * 
     */
    public void setRETIME(long value) {
        this.retime = value;
    }

    /**
     * Gets the value of the estime property.
     * 
     */
    public long getESTIME() {
        return estime;
    }

    /**
     * Sets the value of the estime property.
     * 
     */
    public void setESTIME(long value) {
        this.estime = value;
    }

    /**
     * Gets the value of the eetime property.
     * 
     */
    public long getEETIME() {
        return eetime;
    }

    /**
     * Sets the value of the eetime property.
     * 
     */
    public void setEETIME(long value) {
        this.eetime = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     */
    public int getREASON() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     */
    public void setREASON(int value) {
        this.reason = value;
    }

}
