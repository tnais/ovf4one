/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.emotivecloud.utils.ovf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.dmtf.schemas.ovf.envelope._1.EnvelopeType;
import org.dmtf.schemas.ovf.envelope._1.ObjectFactory;

/**
 * Handles the information in an OVF file that is relevant for EMOTIVE Cloud
 * See OVFParser data to know the restrictions in the OVF support.
 * @author Mario Macías: mario.macias@bsc.es
 */
public class OVFWrapper {
    private static ObjectFactory of = new ObjectFactory();
    protected EnvelopeType envelope;
    private static JAXBContext jbc = null;
    private static Marshaller m = null;


    /**
     * Describes the architecture of the CPU
     */
    public enum CPUArch {
        x86("x86"), 
        x86_64("x86_64"),
        PowerPC("PoerPC");
        
        private String asString;
        private CPUArch(String asString) {
        	this.asString = asString;
        }
        public String toString() { return asString; } 
    };
    
    protected String id;
    protected int memoryMB;
    protected int CPUs;
    protected CPUArch architecture;
    
    public CPUArch getArchitecture() {
		return architecture;
	}

	public void setArchitecture(CPUArch architecture) {
		this.architecture = architecture;
	}

	protected static Logger log;
    static {
        log = Logger.getLogger(OVFWrapper.class.getName());
        try {
            jbc = JAXBContext.newInstance(EnvelopeType.class);
            m = jbc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (JAXBException e) {
            log.severe(e.getMessage());
        }
    }

    protected Map<String,String> productProperties = new TreeMap<String, String>();
    protected Map<String,OVFDisk> disks = new HashMap<String, OVFDisk>();
    protected Map<String,OVFNetwork> networks = new HashMap<String, OVFNetwork>();

    protected OVFWrapper() {
        
    }

    /**
     *
     * @return the Identifier of the virtual machine
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param key
     * @return return the string value of a property in the ProductSection
     */
    public String getProductProperty(String key) {
        return productProperties.get(key);
    }

    /**
     *
     * @return the number of CPUs of the virtual machine
     */
    public int getCPUsNumber() {
        return CPUs;
    }

     /**
     * @return The map of disk interfaces in the Virtual Machine
     * where the Key is the name of the disk and the value is an @OVFDisk object.
     */
    public Map<String,OVFDisk> getDisks() {
        return disks;
    }

    /**
     * @return The map of network interfaces in the Virtual Machine
     * where the Key is the name of the network and the value is an @OVFNetwork object.
     */
    public Map<String, OVFNetwork> getNetworks() {
        return networks;
    }

    /**
     *
     * @return The RAM memory in MBs
     */
    public int getMemoryMB() {
        return memoryMB;
    }

    @Override
    public String toString() {
        StringWriter sw = new StringWriter();
        try {
            m.marshal(of.createEnvelope(envelope), sw);
        } catch (JAXBException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        return sw.toString();
    }

    /**
     *
     * @return The XML string of the EnvelopeType, without any null elements
     */
    public String toCleanString() {
        BufferedReader r = new BufferedReader(new StringReader(toString()));
        StringBuilder o = new StringBuilder();
        String line;
        try {
            line = r.readLine();
        } catch (IOException ex) {
            line = null;
        }
        if (line != null) {
            do {
                if (!line.contains("xsi:nil=\"true\"")) {
                    o.append(line).append(new Character((char) Character.LINE_SEPARATOR));
                }
                try {
                    line = r.readLine();
                } catch (IOException ex) {
                    line = null;
                }
            } while (line != null);
        }

        return o.toString();

    }

}
