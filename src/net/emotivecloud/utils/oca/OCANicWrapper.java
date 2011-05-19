package net.emotivecloud.utils.oca;

/**
 * Class <code>OCANicWrapper</code> wrapper for part of the XML output
 * of the OCA info() method.
 *
 * @author <a href="mailto:saint@eng.it">Gian Uberto Lauri</a>
 * @version $Revision$
 */
public class OCANicWrapper {

	/**
	 * Describe bridge here.
	 */
	private String bridge;

	/**
	 * Describe ip here.
	 */
	private String ip;

	/**
	 * Describe mac here.
	 */
	private String mac;

	/**
	 * Describe network here.
	 */
	private String network;

	/**
	 * Describe networkId here.
	 */
	private String networkId;

	/**
	 * Get the <code>Bridge</code> value.
	 *
	 * @return a <code>String</code> 
	 */
	public final String getBridge() {
		return bridge;
	}

	/**
	 * Set the <code>Bridge</code> value.
	 *
	 * @param newBridge The new Bridge value.
	 */
	public final void setBridge(final String newBridge) {
		this.bridge = newBridge;
	}

	/**
	 * Get the <code>Ip</code> value.
	 *
	 * @return a <code>String</code> 
	 */
	public final String getIp() {
		return ip;
	}

	/**
	 * Set the <code>Ip</code> value.
	 *
	 * @param newIp The new Ip value.
	 */
	public final void setIp(final String newIp) {
		this.ip = newIp;
	}

	/**
	 * Get the <code>Mac</code> value.
	 *
	 * @return a <code>String</code> 
	 */
	public final String getMac() {
		return mac;
	}

	/**
	 * Set the <code>Mac</code> value.
	 *
	 * @param newMac The new Mac value.
	 */
	public final void setMac(final String newMac) {
		this.mac = newMac;
	}

	/**
	 * Get the <code>Network</code> value.
	 *
	 * @return a <code>String</code> 
	 */
	public final String getNetwork() {
		return network;
	}

	/**
	 * Set the <code>Network</code> value.
	 *
	 * @param newNetwork The new Network value.
	 */
	public final void setNetwork(final String newNetwork) {
		this.network = newNetwork;
	}

	/**
	 * Get the <code>NetworkId</code> value.
	 *
	 * @return a <code>String</code> 
	 */
	public final String getNetworkId() {
		return networkId;
	}

	/**
	 * Set the <code>NetworkId</code> value.
	 *
	 * @param newNetworkId The new NetworkId value.
	 */
	public final void setNetworkId(final String newNetworkId) {
		this.networkId = newNetworkId;
	}
}
