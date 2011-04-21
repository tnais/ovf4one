/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.emotivecloud.utils.ovf;

/**
 * Unifies individual Disks information of the disks in OVF
 * @author Mario Macias: mario.macias@bsc.es
 */
public class OVFDisk {
    private String id;
    private String href;
    private Long capacityMB;

    public OVFDisk() {

    }
    public OVFDisk(String id, String href, Long capacityMB) {
        this.id = id;
        this.href = href;
        this.capacityMB = capacityMB;
    }

    /**
     *
     * @return the capacity of the disk in MB or null if unknown
     */
    public Long getCapacityMB() {
        return capacityMB;
    }

    /**
     *
     * @return The URL or the location of the disk image
     */
    public String getHref() {
        return href;
    }

    /**
     *
     * @return The identifier name of the disk in the OVF
     */
    public String getId() {
        return id;
    }

    

}
