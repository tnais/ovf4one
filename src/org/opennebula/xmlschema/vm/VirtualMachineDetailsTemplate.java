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
 * <p>Java class for VirtualMachineDetailsTemplate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualMachineDetailsTemplate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CPU" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DISK" type="{}VirtualMachineDetailsDisk"/>
 *         &lt;element name="GRAPHICS" type="{}VirtualMachineDetailsGraphics"/>
 *         &lt;element name="MEMORY" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NIC" type="{}VirtualMachineDetailsNIC"/>
 *         &lt;element name="OS" type="{}VirtualMachineDetailsOS"/>
 *         &lt;element name="VMID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualMachineDetailsTemplate", propOrder = {
    "cpu",
    "disk",
    "graphics",
    "memory",
    "name",
    "nic",
    "os",
    "vmid"
})
public class VirtualMachineDetailsTemplate {

    @XmlElement(name = "CPU")
    protected int cpu;
    @XmlElement(name = "DISK", required = true)
    protected VirtualMachineDetailsDisk disk;
    @XmlElement(name = "GRAPHICS", required = true)
    protected VirtualMachineDetailsGraphics graphics;
    @XmlElement(name = "MEMORY")
    protected int memory;
    @XmlElement(name = "NAME", required = true)
    protected String name;
    @XmlElement(name = "NIC", required = true)
    protected VirtualMachineDetailsNIC nic;
    @XmlElement(name = "OS", required = true)
    protected VirtualMachineDetailsOS os;
    @XmlElement(name = "VMID")
    protected int vmid;

    /**
     * Gets the value of the cpu property.
     * 
     */
    public int getCPU() {
        return cpu;
    }

    /**
     * Sets the value of the cpu property.
     * 
     */
    public void setCPU(int value) {
        this.cpu = value;
    }

    /**
     * Gets the value of the disk property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualMachineDetailsDisk }
     *     
     */
    public VirtualMachineDetailsDisk getDISK() {
        return disk;
    }

    /**
     * Sets the value of the disk property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualMachineDetailsDisk }
     *     
     */
    public void setDISK(VirtualMachineDetailsDisk value) {
        this.disk = value;
    }

    /**
     * Gets the value of the graphics property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualMachineDetailsGraphics }
     *     
     */
    public VirtualMachineDetailsGraphics getGRAPHICS() {
        return graphics;
    }

    /**
     * Sets the value of the graphics property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualMachineDetailsGraphics }
     *     
     */
    public void setGRAPHICS(VirtualMachineDetailsGraphics value) {
        this.graphics = value;
    }

    /**
     * Gets the value of the memory property.
     * 
     */
    public int getMEMORY() {
        return memory;
    }

    /**
     * Sets the value of the memory property.
     * 
     */
    public void setMEMORY(int value) {
        this.memory = value;
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
     * Gets the value of the nic property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualMachineDetailsNIC }
     *     
     */
    public VirtualMachineDetailsNIC getNIC() {
        return nic;
    }

    /**
     * Sets the value of the nic property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualMachineDetailsNIC }
     *     
     */
    public void setNIC(VirtualMachineDetailsNIC value) {
        this.nic = value;
    }

    /**
     * Gets the value of the os property.
     * 
     * @return
     *     possible object is
     *     {@link VirtualMachineDetailsOS }
     *     
     */
    public VirtualMachineDetailsOS getOS() {
        return os;
    }

    /**
     * Sets the value of the os property.
     * 
     * @param value
     *     allowed object is
     *     {@link VirtualMachineDetailsOS }
     *     
     */
    public void setOS(VirtualMachineDetailsOS value) {
        this.os = value;
    }

    /**
     * Gets the value of the vmid property.
     * 
     */
    public int getVMID() {
        return vmid;
    }

    /**
     * Sets the value of the vmid property.
     * 
     */
    public void setVMID(int value) {
        this.vmid = value;
    }

}
