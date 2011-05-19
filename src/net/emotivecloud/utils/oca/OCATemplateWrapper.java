package net.emotivecloud.utils.oca;

import java.util.Map;

/**
 * Describe class OCATemplateWrapper here.
 *
 *
 * Created: Thu May 19 12:31:32 2011
 *
 * @author <a href="mailto:saint@eng.it">Gian Uberto Lauri</a>
 * @version $Revision$
 */
public class OCATemplateWrapper {

	/**
	 * Describe cpu here.
	 */
	private int cpu;

	/**
	 * Describe memory here.
	 */
	private int memory;

	/**
	 * Describe name here.
	 */
	private String name;

	/**
	 * Describe os here.
	 */
	private OCAOsWrapper os;

	/**
	 * Describe vcpu here.
	 */
	private int vcpu;

	/**
	 * Describe vmId here.
	 */
	private String vmId;

	/**
	 * Describe OCANicWrapper> here.
	 */
	private Map<String, OCANicWrapper> nics;

	/**
	 * Describe OCADiskWrapper> here.
	 */
	private Map<String, OCADiskWrapper> disks;

	/**
	 * Creates a new instance of <code>OCATemplateWrapper</code> .
	 *
	 */
	public OCATemplateWrapper() {

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
	 * Get the <code>OCADiskWrapper></code> value.
	 *
	 * @return a <code>Map<String</code> 
	 */
	public final Map<String, OCADiskWrapper> getDisks() {
		return disks;
	}

	/**
	 * Set the <code>OCADiskWrapper></code> value.
	 *
	 * @param newOCADiskWrapper> The new OCADiskWrapper> value.
	 */
	public final void setDisks(final Map<String, OCADiskWrapper> newDisks) {
		this.disks = newDisks;
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
	 * Get the <code>OCANicWrapper></code> value.
	 *
	 * @return a <code>Map<String</code> 
	 */
	public final Map<String, OCANicWrapper> getNics() {
		return nics;
	}

	/**
	 * Set the <code>OCANicWrapper></code> value.
	 *
	 * @param newOCANicWrapper> The new OCANicWrapper> value.
	 */
	public final void setNics(final Map<String, OCANicWrapper> newNics) {
		this.nics = newNics;
	}

	/**
	 * Get the <code>Os</code> value.
	 *
	 * @return an <code>OCAOsWrapper</code> 
	 */
	public final OCAOsWrapper getOs() {
		return os;
	}

	/**
	 * Set the <code>Os</code> value.
	 *
	 * @param newOs The new Os value.
	 */
	public final void setOs(final OCAOsWrapper newOs) {
		this.os = newOs;
	}

	/**
	 * Get the <code>Vcpu</code> value.
	 *
	 * @return an <code>int</code> 
	 */
	public final int getVcpu() {
		return vcpu;
	}

	/**
	 * Set the <code>Vcpu</code> value.
	 *
	 * @param newVcpu The new Vcpu value.
	 */
	public final void setVcpu(final int newVcpu) {
		this.vcpu = newVcpu;
	}

	/**
	 * Get the <code>VmId</code> value.
	 *
	 * @return a <code>String</code> 
	 */
	public final String getVmId() {
		return vmId;
	}

	/**
	 * Set the <code>VmId</code> value.
	 *
	 * @param newVmId The new VmId value.
	 */
	public final void setVmId(final String newVmId) {
		this.vmId = newVmId;
	}
}
