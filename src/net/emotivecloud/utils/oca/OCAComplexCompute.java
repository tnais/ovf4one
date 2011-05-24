package net.emotivecloud.utils.oca;

/**
 * Class <code>OCAComplexCompute</code> models the items in the OCA
 * VirtualMachinePool.info() reply, whose type is VMCOMPLEX. VMCOMPLEX
 * is ubdeed a VMSIMPLE item extended with the username attribute. It
 * does not seem this complex, but that's the name :). Of course
 * extends OCAComputeWrapper.
 *
 * @author <a href="mailto:saint@eng.it">Gian Uberto Lauri</a>
 * @version $Revision$
 */
public class OCAComplexCompute extends OCAComputeWrapper {
	
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
	
	

}
