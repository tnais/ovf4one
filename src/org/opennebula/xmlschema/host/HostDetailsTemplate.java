//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.17 at 06:51:09 PM CEST 
//


package org.opennebula.xmlschema.host;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostDetailsTemplate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostDetailsTemplate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ARCH" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CPUSPEED" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FREECPU" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="FREEMEMORY" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="HOSTNAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="HYPERVISOR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MODELNAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NETRX" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NETTX" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TOTALCPU" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TOTALMEMORY" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="USEDCPU" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="USEDMEMORY" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostDetailsTemplate", propOrder = {
    "arch",
    "cpuspeed",
    "freecpu",
    "freememory",
    "hostname",
    "hypervisor",
    "modelname",
    "netrx",
    "nettx",
    "totalcpu",
    "totalmemory",
    "usedcpu",
    "usedmemory"
})
public class HostDetailsTemplate {

    @XmlElement(name = "ARCH", required = true)
    protected String arch;
    @XmlElement(name = "CPUSPEED")
    protected int cpuspeed;
    @XmlElement(name = "FREECPU", required = true)
    protected BigDecimal freecpu;
    @XmlElement(name = "FREEMEMORY")
    protected int freememory;
    @XmlElement(name = "HOSTNAME", required = true)
    protected String hostname;
    @XmlElement(name = "HYPERVISOR", required = true)
    protected String hypervisor;
    @XmlElement(name = "MODELNAME", required = true)
    protected String modelname;
    @XmlElement(name = "NETRX", required = true)
    protected String netrx;
    @XmlElement(name = "NETTX", required = true)
    protected String nettx;
    @XmlElement(name = "TOTALCPU")
    protected int totalcpu;
    @XmlElement(name = "TOTALMEMORY")
    protected int totalmemory;
    @XmlElement(name = "USEDCPU", required = true)
    protected BigDecimal usedcpu;
    @XmlElement(name = "USEDMEMORY")
    protected int usedmemory;

    /**
     * Gets the value of the arch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getARCH() {
        return arch;
    }

    /**
     * Sets the value of the arch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setARCH(String value) {
        this.arch = value;
    }

    /**
     * Gets the value of the cpuspeed property.
     * 
     */
    public int getCPUSPEED() {
        return cpuspeed;
    }

    /**
     * Sets the value of the cpuspeed property.
     * 
     */
    public void setCPUSPEED(int value) {
        this.cpuspeed = value;
    }

    /**
     * Gets the value of the freecpu property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFREECPU() {
        return freecpu;
    }

    /**
     * Sets the value of the freecpu property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFREECPU(BigDecimal value) {
        this.freecpu = value;
    }

    /**
     * Gets the value of the freememory property.
     * 
     */
    public int getFREEMEMORY() {
        return freememory;
    }

    /**
     * Sets the value of the freememory property.
     * 
     */
    public void setFREEMEMORY(int value) {
        this.freememory = value;
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
     * Gets the value of the hypervisor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHYPERVISOR() {
        return hypervisor;
    }

    /**
     * Sets the value of the hypervisor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHYPERVISOR(String value) {
        this.hypervisor = value;
    }

    /**
     * Gets the value of the modelname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMODELNAME() {
        return modelname;
    }

    /**
     * Sets the value of the modelname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMODELNAME(String value) {
        this.modelname = value;
    }

    /**
     * Gets the value of the netrx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETRX() {
        return netrx;
    }

    /**
     * Sets the value of the netrx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETRX(String value) {
        this.netrx = value;
    }

    /**
     * Gets the value of the nettx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETTX() {
        return nettx;
    }

    /**
     * Sets the value of the nettx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETTX(String value) {
        this.nettx = value;
    }

    /**
     * Gets the value of the totalcpu property.
     * 
     */
    public int getTOTALCPU() {
        return totalcpu;
    }

    /**
     * Sets the value of the totalcpu property.
     * 
     */
    public void setTOTALCPU(int value) {
        this.totalcpu = value;
    }

    /**
     * Gets the value of the totalmemory property.
     * 
     */
    public int getTOTALMEMORY() {
        return totalmemory;
    }

    /**
     * Sets the value of the totalmemory property.
     * 
     */
    public void setTOTALMEMORY(int value) {
        this.totalmemory = value;
    }

    /**
     * Gets the value of the usedcpu property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUSEDCPU() {
        return usedcpu;
    }

    /**
     * Sets the value of the usedcpu property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUSEDCPU(BigDecimal value) {
        this.usedcpu = value;
    }

    /**
     * Gets the value of the usedmemory property.
     * 
     */
    public int getUSEDMEMORY() {
        return usedmemory;
    }

    /**
     * Sets the value of the usedmemory property.
     * 
     */
    public void setUSEDMEMORY(int value) {
        this.usedmemory = value;
    }

}
