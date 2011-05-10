package net.emotivecloud.scheduler.onedrp;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Class <code>DRPOneException</code> An exception to get the error
 * condition back to the client.
 *
 * @author <a href="mailto:saint@eng.it">Gian Uberto Lauri</a>
 * @version $Revision$
 */
public class DRPOneException extends WebApplicationException {

	/*
	 * WebApplicationException does not provide signatures like those
	 * of the common Java exceptions, therefore I use this inner class
	 * rather than a raw exception... 
	 */
	private static class Problem extends Exception {
		/**
		 * I Hate warnings
		 */
		private static final long serialVersionUID = 4857887826790469885L;

		Problem(String message) {
			super(message);
		} 

		Problem(String message, Throwable cause) 
		{
			super(message, cause);
		}
	}

	/**
	 * I hate warnings!
	 */
	private static final long serialVersionUID = 7050899732693277740L;

	/**
	 * Creates a new instance of <code>DRPOneException</code> .
	 *
	 */
	public DRPOneException() {
		super();
	}

	/**
	 * Creates a new instance of <code>DRPOneException</code> .
	 *
	 * @param status an <code>int</code> HTTP status code to be
	 * returned to the remote client
	 */
	public DRPOneException(int status) {
		super(status);
	}

	/**
	 * Creates a new instance of <code>DRPOneException</code> .
	 *
	 * @param response a <code>Response</code> HTTP response to be
	 * returned to the remote client
	 */
	public DRPOneException(Response response) {
		super(response);
	}

	/**
	 * Creates a new instance of <code>DRPOneException</code> .
	 *
	 * @param status a <code>Status</code> the HTTP status to be
	 * returned to the remote client
	 */
	public DRPOneException(Status status) {
		super(status);
	}

	/**
	 * Creates a new instance of <code>DRPOneException</code> .
	 *
	 * @param cause a <code>Throwable</code> exception causing the trouble
	 * @param status an <code>int</code> the HTTP status code to be
	 * returned to the remote client
	 */
	public DRPOneException(Throwable cause, int status) {
		super(cause, status);
	}

	/**
	 * Creates a new instance of <code>DRPOneException</code>. This is
	 * to mimick standard Java Exceptions.
	 *
	 * @param message a <code>String</code> exception message
	 * @param status an <code>int</code> the HTTP status code to be
	 * returned to the remote client
	 */
	public DRPOneException(String message, int status) {
		super(new Problem(message), status);
	}

	/**
	 * Creates a new instance of <code>DRPOneException</code> .
	 *
	 * @param cause a <code>Throwable</code> exception causing the trouble
	 * @param response a <code>Response</code> the response to be sent
	 * back to the remote client
	 */
	public DRPOneException(Throwable cause, Response response) {
		super(cause, response);
	}

	/**
	 * Creates a new instance of <code>DRPOneException</code> .
	 *
	 * @param cause a <code>Throwable</code> exception causing the trouble
	 * @param status a <code>Status</code> the HTTP status to be
	 * returned to the remote client
	 */
	public DRPOneException(Throwable cause, Status status) {
		super(cause, status);
	}

	/**
	 * Creates a new instance of <code>DRPOneException</code> .
	 *
	 * @param cause a <code>Throwable</code> exception causing the trouble
	 */
	public DRPOneException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new instance of <code>DRPOneException</code> .
	 *
	 * @param message a <code>String</code> as used in java.lang.Exception
	 * @param cause a <code>Throwable</code> as used in java.lang.Exception
	 * @param Status an <code>int</code> HTTP Status
	 */
	public DRPOneException(String message, Throwable cause, int status)
	{
		super(new Problem(message,cause), status);
	}


}
