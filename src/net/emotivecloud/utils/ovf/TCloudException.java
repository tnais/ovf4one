/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.emotivecloud.utils.ovf;

/**
 *
 * @author Mario Macías: mario.macias@bsc.es
 */
public class TCloudException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7779179619282268006L;

	public TCloudException(Throwable cause) {
        super(cause);
    }

    public TCloudException(String message, Throwable cause) {
        super(message, cause);
    }

    public TCloudException(String message) {
        super(message);
    }

    public TCloudException() {

    }

}
