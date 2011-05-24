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

import org.opennebula.xmlschema.compute.VMSIMPLE;
import org.opennebula.xmlschema.compute.VMSIMPLEObjectFactory;

/**
 * Class <code>OCAWrapper</code> A wrapper for the XML returned by OCA
 * info() method.
 *
 * @author <a href="mailto:saint@eng.it">Gian Uberto Lauri</a>
 * @version $Revision$
 */
public class OCAComputeWrapper implements VmType{
    private static VMSIMPLEObjectFactory of = new VMSIMPLEObjectFactory();
	private VMSIMPLE vm;
    private static JAXBContext jbc = null;
    private static Marshaller m = null;

	private static Logger log;
    static {
        log = Logger.getLogger(OCAComputeWrapper.class.getName());
        try {
            jbc = JAXBContext.newInstance(VMSIMPLE.class);
            m = jbc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (JAXBException e) {
            log.severe(e.getMessage());
        }
    }

	/**
	 * Describe id here.
	 */
	private int id;

	/**
	 * Describe uid here.
	 */
	private int uid;

	/**
	 * Describe name here.
	 */
	private String name;

	/**
	 * Describe lastPoll here.
	 */
	private int lastPoll;

	/**
	 * Describe state here.
	 */
	private int state;

	/**
	 * Describe lcmState here.
	 */
	private int lcmState;

	/**
	 * Describe stime here.
	 */
	private int stime;

	/**
	 * Describe etime here.
	 */
	private int etime;

	/**
	 * Describe deployId here.
	 */
	private String deployId;

	/**
	 * Describe memory here.
	 */
	private int memory;

	/**
	 * Describe cpu here.
	 */
	private int cpu;

	/**
	 * Describe netTx here.
	 */
	private int netTx;

	/**
	 * Describe netRx here.
	 */
	private int netRx;

	/**
	 * Describe lastSeq here.
	 */
	private int lastSeq;

	/**
	 * Describe template here.
	 */
	private OCATemplateWrapper template;

	/**
	 * Describe history here.
	 */
	private Object history;

	/**
	 * Get the <code>Id</code> value.
	 *
	 * @return an <code>int</code> 
	 */
	public final int getId() {
		return id;
	}

	/**
	 * Set the <code>Id</code> value.
	 *
	 * @param newId The new Id value.
	 */
	public final void setId(final int newId) {
		this.id = newId;
	}

	/**
	 * Get the <code>Uid</code> value.
	 *
	 * @return an <code>int</code> 
	 */
	public final int getUid() {
		return uid;
	}

	/**
	 * Set the <code>Uid</code> value.
	 *
	 * @param newUid The new Uid value.
	 */
	public final void setUid(final int newUid) {
		this.uid = newUid;
	}

	/**
	 * Get the <code>Name</code> value.
	 *
	 * @return a <code>String</code> 
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Set the <code>Name</code> value.
	 *
	 * @param newName The new Name value.
	 */
	public final void setName(final String newName) {
		this.name = newName;
	}

	/**
	 * Get the <code>LastPoll</code> value.
	 *
	 * @return an <code>int</code> 
	 */
	public final int getLastPoll() {
		return lastPoll;
	}

	/**
	 * Set the <code>LastPoll</code> value.
	 *
	 * @param newLastPoll The new LastPoll value.
	 */
	public final void setLastPoll(final int newLastPoll) {
		this.lastPoll = newLastPoll;
	}

	/**
	 * Get the <code>State</code> value.
	 *
	 * @return an <code>int</code> 
	 */
	public final int getState() {
		return state;
	}

	/**
	 * Set the <code>State</code> value.
	 *
	 * @param newState The new State value.
	 */
	public final void setState(final int newState) {
		this.state = newState;
	}

	/**
	 * Get the <code>LcmState</code> value.
	 *
	 * @return an <code>int</code> 
	 */
	public final int getLcmState() {
		return lcmState;
	}

	/**
	 * Set the <code>LcmState</code> value.
	 *
	 * @param newLcmState The new LcmState value.
	 */
	public final void setLcmState(final int newLcmState) {
		this.lcmState = newLcmState;
	}

	/**
	 * Get the <code>Stime</code> value.
	 *
	 * @return an <code>int</code> 
	 */
	public final int getStime() {
		return stime;
	}

	/**
	 * Set the <code>Stime</code> value.
	 *
	 * @param newStime The new Stime value.
	 */
	public final void setStime(final int newStime) {
		this.stime = newStime;
	}

	/**
	 * Get the <code>Etime</code> value.
	 *
	 * @return an <code>int</code> 
	 */
	public final int getEtime() {
		return etime;
	}

	/**
	 * Set the <code>Etime</code> value.
	 *
	 * @param newEtime The new Etime value.
	 */
	public final void setEtime(final int newEtime) {
		this.etime = newEtime;
	}

	/**
	 * Get the <code>DeployId</code> value.
	 *
	 * @return a <code>String</code> 
	 */
	public final String getDeployId() {
		return deployId;
	}

	/**
	 * Set the <code>DeployId</code> value.
	 *
	 * @param newDeployId The new DeployId value.
	 */
	public final void setDeployId(final String newDeployId) {
		this.deployId = newDeployId;
	}

	/**
	 * Get the <code>Memory</code> value.
	 *
	 * @return an <code>int</code> 
	 */
	public final int getMemory() {
		return memory;
	}

	/**
	 * Set the <code>Memory</code> value.
	 *
	 * @param newMemory The new Memory value.
	 */
	public final void setMemory(final int newMemory) {
		this.memory = newMemory;
	}

	/**
	 * Get the <code>Cpu</code> value.
	 *
	 * @return an <code>int</code> 
	 */
	public final int getCpu() {
		return cpu;
	}

	/**
	 * Set the <code>Cpu</code> value.
	 *
	 * @param newCpu The new Cpu value.
	 */
	public final void setCpu(final int newCpu) {
		this.cpu = newCpu;
	}

	/**
	 * Get the <code>NetTx</code> value.
	 *
	 * @return an <code>int</code> 
	 */
	public final int getNetTx() {
		return netTx;
	}

	/**
	 * Set the <code>NetTx</code> value.
	 *
	 * @param newNetTx The new NetTx value.
	 */
	public final void setNetTx(final int newNetTx) {
		this.netTx = newNetTx;
	}

	/**
	 * Get the <code>NetRx</code> value.
	 *
	 * @return an <code>int</code> 
	 */
	public final int getNetRx() {
		return netRx;
	}

	/**
	 * Set the <code>NetRx</code> value.
	 *
	 * @param newNetRx The new NetRx value.
	 */
	public final void setNetRx(final int newNetRx) {
		this.netRx = newNetRx;
	}

	/**
	 * Get the <code>LastSeq</code> value.
	 *
	 * @return an <code>int</code> 
	 */
	public final int getLastSeq() {
		return lastSeq;
	}

	/**
	 * Set the <code>LastSeq</code> value.
	 *
	 * @param newLastSeq The new LastSeq value.
	 */
	public final void setLastSeq(final int newLastSeq) {
		this.lastSeq = newLastSeq;
	}

	/**
	 * Get the <code>Template</code> value.
	 *
	 * @return a <code>OCATemplateWrapper</code> 
	 */
	public final OCATemplateWrapper getTemplate() {
		return template;
	}

	/**
	 * Set the <code>Template</code> value.
	 *
	 * @param newTemplate The new Template value.
	 */
	public final void setTemplate(final OCATemplateWrapper newTemplate) {
		this.template = newTemplate;
	}

	/**
	 * Get the <code>History</code> value.
	 *
	 * @return an <code>Object</code> 
	 */
	public final Object getHistory() {
		return history;
	}

	/**
	 * Set the <code>History</code> value.
	 *
	 * @param newHistory The new History value.
	 */
	public final void setHistory(final Object newHistory) {
		this.history = newHistory;
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
