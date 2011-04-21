/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.emotivecloud.utils.ovf;

/**
 * Unifies individual Network information of the network interfaces in OVF and InstantiateOvfParams
 * @author Mario Macias: mario.macias@bsc.es
 */
public class OVFNetwork {
    private String connectionName;
    private String ip;
    private String mac;
    
    public OVFNetwork() {

    }

    public OVFNetwork(String connectionName, String ip, String mac) {
        this.connectionName = connectionName;
        this.ip = ip;
        this.mac = mac;
    }

    protected void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    protected void setIp(String ip) {
        this.ip = ip;
    }

    protected void setMac(String mac) {
        this.mac = mac;
    }

    /**
     *
     * @return the IP address of the interface or null if undefined
     */
    public String getIp() {
        return ip;
    }

    /**
     *
     * @return The MAC address of the interface, or null if undefined
     */
    public String getMac() {
        return mac;
    }

    /**
     *
     * @return The name of the network as specified in the OVF
     */
    public String getConnectionName() {
        return connectionName;
    }

}
