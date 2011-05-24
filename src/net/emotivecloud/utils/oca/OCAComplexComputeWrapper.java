package net.emotivecloud.utils.oca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.opennebula.xmlschema.compute.VMCOMPLEX;
import org.opennebula.xmlschema.compute.VMCOMPLEXObjectFactory;

/**
 * Class <code>OCAComplexComputeWrapper</code> models the items in the OCA
 * VirtualMachinePool.info() reply, whose type is VMCOMPLEX. VMCOMPLEX
 * is ubdeed a VMSIMPLE item extended with the username attribute. It
 * does not seem this complex, but that's the name :). Of course
 * extends OCAComputeWrapper.
 *
 * @author <a href="mailto:saint@eng.it">Gian Uberto Lauri</a>
 * @version $Revision$
 */
public class OCAComplexComputeWrapper extends OCAComputeWrapper implements VmType {
    private static VMCOMPLEXObjectFactory of = new VMCOMPLEXObjectFactory();
	private VMCOMPLEX vm;
    private static JAXBContext jbc = null;
    private static Marshaller m = null;

	private static Logger log;
    static {
        log = Logger.getLogger(OCAComputeWrapper.class.getName());
        try {
            jbc = JAXBContext.newInstance(VMCOMPLEX.class);
            m = jbc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (JAXBException e) {
            log.severe(e.getMessage());
        }
    }

	String userName;

	/**
	 * <code>getUserName</code> 
	 *
	 * @return a <code>String</code> 
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * <code>setUserName</code> 
	 *
	 * @param userName a <code>String</code> 
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
    @Override
    public String toString() {
        StringWriter sw = new StringWriter();
        try {
            m.marshal(of.createVM(vm), sw);
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
