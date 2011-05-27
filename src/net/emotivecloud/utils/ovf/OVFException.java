/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.emotivecloud.utils.ovf;

/**
 *
 * @author Mario Macías: mario.macias@bsc.es
 */
public class OVFException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3944938626743591093L;

	public OVFException(Throwable cause) {
        super(cause);
    }

    public OVFException(String message, Throwable cause) {
        super(message, cause);
    }

    public OVFException(String message) {
        super(message);
    }

    public OVFException() {
        super();
    }


}
