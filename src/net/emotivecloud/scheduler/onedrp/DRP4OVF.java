package net.emotivecloud.scheduler.onedrp;


import java.util.Collection;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.PropertyException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.ValidationException;

import net.emotivecloud.utils.oca.OCADiskWrapper;
import net.emotivecloud.utils.oca.OCANicWrapper;
import net.emotivecloud.utils.oca.OCAWrapper;
import net.emotivecloud.utils.oca.OCAWrapperFactory;
import net.emotivecloud.utils.ovf.OVFDisk;
import net.emotivecloud.utils.ovf.OVFException;
import net.emotivecloud.utils.ovf.OVFNetwork;
import net.emotivecloud.utils.ovf.OVFWrapper;
import net.emotivecloud.utils.ovf.OVFWrapperFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.vm.VirtualMachine;
import org.xml.sax.SAXException;

import com.sun.jersey.spi.resource.Singleton;


    /***************************************************************************
     * Este es el componente con el cual la capa de Gestión de Cloud Federada  *
     * interactúa directamente. Se trata de un interfaz REST. La implementación*
     * que se hará será mediante el protocolo HTTP y los métodos GET, POST, PUT*
     * y DELETE.                                                               *
     * ------------------------------------------------------------------------*
     * Skeleton reused by ENG with BSC fine OVFWrapper to use OpenNebula OCA   *
     **************************************************************************/

@Path("/")
@Singleton
public class DRP4OVF{

    @Context
    UriInfo uriInfo;
//    private static int machineId = 0;
    private Log log = LogFactory.getLog(DRP4OVF.class);
    
    /*
     * If Client throws an Exception (puke) is when is instantiated, and that
     * Exception shoud be an Error, since there's nothing else to do than stop
     * everything, correct the configuration errors and restart the JVM.
     */
    static {
    	try {
    		new Client();
    	}
    	catch (Exception badChoice) {
    		String tmp = badChoice.getMessage();
    		if("ONE_AUTH file not present".equals(badChoice.getMessage()) ||
    				"Error initializing MessageDigest with SHA-1".endsWith(badChoice.getMessage()) )
    			throw new OpenNebulaConfigurationError(tmp);
    	}
    }
    
	/* ===============================================================
	 *
	 * These constants describe the supported Product Properties
	 * We use Product Properties to hold informations meaningful for
	 * Opennebula but not supported by OVF
	 */

	// Path to the kernel image - mostly unused if you don't use XEN
    public final static String KERNEL="KERNEL";

	// Path to the initrd image - mostly unused if you don't use XEN
	public final static String INITRD="INITRD";

	// Device to be mounted as root
	public final static String ROOT="ROOT";

	// Extra arguments to boot the kernel
	public final static String KERNEL_CMD="KERNEL_CMD";

	// Path to the bootloader executable  - mostly unused if you don't use XEN
	public final static String BOOTLOADER="BOOTLOADER";

	// Tyoe if the boot device, must be one of those in BootType enum
	public final static String BOOT="BOOT";

	// Architecture to be emulated. We support i386 (default) and x86_64
	public final static String ARCH="ARCH";
	public final static String I_386="i386";
	public final static String I_686="i686";
	public final static String X86_64="x86_64";


	// Hypervisor in use - we support KVM and XEN
	public final static String HYPERVISOR="HYPERVISOR";


	private final static String productPropertiesList[] = 
		new String[] { KERNEL, INITRD, ROOT, KERNEL_CMD, BOOTLOADER, BOOT };


	/* ===============================================================
	 *
	 * This enum models the supported types for the BOOT.
	 *
	 */
	private enum BootType {
		hd("hd"),
		fd("fd"),
		cdrom("cdrom"),
		network("network");

		private BootType(String asString) {
			this.asString = asString;
		}

		private String asString;

		public String toString() { return asString;	}

		public static boolean isValid(String toTest) {
			return hd.asString.equals(toTest) ||
				fd.asString.equals(toTest) ||
				cdrom.asString.equals(toTest) ||
				network.asString.equals(toTest);
		}


	}

	/* ===============================================================
	 *
	 * This enum models the supported types for the DISK. We consider
	 * virtual CD, floppies and such all being 'disks'
	 *
	 */
	private enum DskType {
		virtualDisk("disk"),
		virtualSwap("swap"),
		blockDevice("block"),
		onTheFlyDisk("fs");

		private String asString;

		private DskType(String asString) {
			this.asString = asString;
		}

		public String toString() { return asString;	}

		public static DskType fromString(String startingString) {
			return virtualDisk.asString.equals(startingString) ? virtualDisk
					: virtualSwap.asString.equals(startingString) ? virtualSwap
							: blockDevice.asString.equals(startingString) ? blockDevice
									: onTheFlyDisk.asString.equals(startingString) ? onTheFlyDisk : null;
		}
	}
    
	/**
	 * getGreeting: a toy method that helps us to see that this class
	 * is correctly handled by jersey
	 */
	@GET 
	@Produces("text/plain")
    public String getGreeting() {

        return "It seems that the GET works";

    }

    /***************************************************************************
     *         Methods with a correspondence in the OCCI interface.            *
     **************************************************************************/

    /****************************COMPUTE methods.******************************/

    @POST
    @Path("/compute")
    @Consumes("application/xml")
    /**
	 * Method that managers the post to
	 * http://host/ovf4one/compute. This creates a vm instance
	 * @param ovfXml a <code>String</code> an OVF file with the
	 * informations that drive the VM instance creation
	 * @return a <code>String</code> an OVF input with the VM instance ID
	 * @exception DRPOneException If something goes wrong, what else ?
	 */
	public String createCompute(String ovfXml) throws DRPOneException { // (Map<String, Object>);
        String id = "";
        
        Client ocaClient = null;
        try {
        	ocaClient = new Client();
        }
        catch (Exception nevermind) {}

        
        OVFWrapper ovf;
        ovf = parse(ovfXml);
        
        String vmTemplate = ovf2OneDescription(ovf);

        OneResponse rc = VirtualMachine.allocate(ocaClient, vmTemplate);
        
        if(rc.isError()) {
        	log.error("Failed to create VM " + ovf.getId() +": " + rc.getErrorMessage());
        	throw new DRPOneException(rc.getErrorMessage(),StatusCodes.ONE_FAILURE);
        }
        log.info("Environment "+id+" was created.");


		//Returning created domain.
		Collection<OVFDisk> tmpDisks = ovf.getDisks().values();
		OVFDisk disks[] = tmpDisks.toArray(new OVFDisk[tmpDisks.size()]);

		Collection<OVFNetwork> tmpNets = ovf.getNetworks().values();
		OVFNetwork nets[] = tmpNets.toArray(new OVFNetwork[tmpNets.size()]);

		return OVFWrapperFactory.create(rc.getMessage(),
						ovf.getCPUsNumber(),
						ovf.getMemoryMB(),
						disks,
						nets,
						new HashMap<String,String>(0)).toCleanString();

    }


	/**
	 * Method to manage the get to
	 * http://host/ovf4one/compute/[envid], that retrieves data about
	 * the given VM instance
	 *
	 * @param envId a <code>String</code> the ID of the VM instance we are
	 * interested in
	 * @return a <code>String</code> an OVF file with the required
	 * informations
	 * @exception DRPOneException If something goes wrong, what else ?
	 */
    @GET
    @Path("/compute/{envid}")
    @Produces("application/xml")
	public String getCompute(@PathParam("envid") String envId) throws DRPOneException {

        Client ocaClient = null;
        try {
        	ocaClient = new Client();
        }
        catch (Exception nevermind) {}

        int machineId = 0;
        
        try {
        	machineId = Integer.parseInt(envId);
        }
        catch(NumberFormatException nfe) {
        	throw new DRPOneException("Illegal VM ID "+envId,StatusCodes.BAD_OVF );
        }
        
        OneResponse rc = VirtualMachine.info(ocaClient, machineId);
        
        if(rc.isError()) {
        	log.error("Failed to retrieve " + envId +": " + rc.getErrorMessage());
        }
        
        String tmp = rc.getMessage();
        StringBuilder xmlReply = new StringBuilder(64+tmp.length());
        xmlReply.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        xmlReply.append(tmp);
  
		OCAWrapper oca = parseOca(xmlReply.toString());
                
		Collection<OCADiskWrapper> diskList = oca.getTemplate().getDisks().values();
		
		OVFDisk diskArray[] = new OVFDisk[diskList.size()];
		
		int counter = 0;
		
		for(OCADiskWrapper disk: diskList) {
			diskArray[counter++] = new OVFDisk(disk.getDiskId(), 
					disk.getSource(), 
					disk.getSize());
		}
		
		Collection<OCANicWrapper> nicList = oca.getTemplate().getNics().values();
		
		OVFNetwork nicArray[] = new OVFNetwork[nicList.size()];
		
		counter = 0;
		
		for(OCANicWrapper nic: nicList) {
			nicArray[counter++] = new OVFNetwork(nic.getNetwork(), 
					nic.getIp(), 
					nic.getMac());
		}
		
        String rv = OVFWrapperFactory.create(""+oca.getId(),
					oca.getCpu(),
					oca.getMemory(),
					diskArray,
					nicArray,
					new HashMap<String,String>(0)).toCleanString();
		return rv;
    }

	/* ==============================================================
	 * ==============================================================
	 *
	 * private helpers for the createCompute method
	 *
	 */
    
    private OVFWrapper parse(String ovfXml) throws DRPOneException {
    	OVFWrapper rv = null;
		StringBuilder cause= new StringBuilder();

		try {
			rv = OVFWrapperFactory.parse(ovfXml);
		} catch (JAXBException e) {
			if (e instanceof PropertyException)
				cause.append("Access to property failed: ");
			else if (e instanceof MarshalException)
				cause.append("Marshalling failed: ");
			else if (e instanceof UnmarshalException)
				cause.append("Unmarshalling failed: ");
			else if (e instanceof ValidationException)
				cause.append("XML Validation failed: ");
			else {
				cause.append("Unespected ");
				cause.append(e.getClass().getName());
				cause.append(": ");
			}
			cause.append(e.getMessage());
			log.error(cause.toString());
			if(log.isTraceEnabled())
				log.trace(cause, e);
			throw new DRPOneException(cause.toString(),e,StatusCodes.XML_PROBLEM);
		} catch (OVFException e) {
			cause.append("Problems parsing OVF file: ");
			cause.append(e.getMessage());
			log.error(cause.toString());
			if(log.isTraceEnabled()) 
				log.trace(cause, e);
			e.printStackTrace();
		}
		return rv;
	}


	private boolean isKVMInUse(OVFWrapper ovf) {

		String hyperName = ovf.getProductProperty(HYPERVISOR);

		return hyperName == null || "kvm".equalsIgnoreCase(hyperName) ;
	}

	// Generates a description starting from an ovf wrapper.
    private String ovf2OneDescription(OVFWrapper ovf) {
		StringBuilder buf = new StringBuilder(1024);

		// Name, CPUs and Memory

		boolean weUseKVM =  isKVMInUse(ovf);

		// These are flags to check if a certain mandatory[*] values are
		// supplied with the OVF. 
		//
		// [*] some values are mandatory with XEN, other with KVM...
		//
		boolean weGotKernel = false;
		boolean weGotBootloader = false;
		boolean weGotBoot = false;
		boolean weGotDisk = false;


		Object tmp = ovf.getId();
		if(tmp != null) {
			buf.append("NAME = \""); buf.append(tmp); buf.append("\"\n");
		}

		tmp = ovf.getMemoryMB();
		if(tmp != null) {
			buf.append("MEMORY = "); buf.append(tmp); buf.append("\n");
		}
		else
			throw new DRPOneException("OVF file is missing mandatory memory specification",StatusCodes.BAD_OVF);

		tmp = ovf.getCPUsNumber();
		if(tmp != null) {
			// It seems that in our OVF VCPUs and CPUs are the same thing.
			buf.append("CPU = "); buf.append(tmp); buf.append("\n");
			buf.append("VCPU = "); buf.append(tmp); buf.append("\n");
		}
		else
			throw new DRPOneException("OVF file is missing mandatory CPU number specification",StatusCodes.BAD_OVF);

		// OS attribute 
		// we need a separator there, since there's a comma after the 
		// first element in the array.

		buf.append("OS = [\n");
		tmp = ovf.getArchitecture();

		// ARCH is the firs element we place, no separator goes
		// before, but if we put one in the file we need to change
		// the separator
		//
		String archName = ovf.getProductProperty(ARCH);

		if (archName == null)
			archName = I_386;
		else if( 
			! ( I_386.equals(archName) ||
				I_686.equals(archName) ||
				X86_64.equals(archName) )
			)
			throw new DRPOneException("Illegal architecture "+archName,StatusCodes.BAD_OVF );


		buf.append("ARCH = \"");
		buf.append(archName);
		buf.append("\"");

		// Here we can define the separator with the final value
		// therefore we need not to update the variable...
		String separator = ",\n";

		for( String productProperty: productPropertiesList) {
			String value = ovf.getProductProperty(productProperty);

			if(value != null && ! "".equals(value)) {
				// These are sensible test here! I am iterating on an
				// array of constant references
				if(productProperty == BOOT) {
					if(BootType.isValid(value)) {
						buf.append(separator);
						buf.append(BOOT);
						buf.append(" = \""); buf.append(value); buf.append("\"");
						weGotBoot = true;

					}
					else
						throw new DRPOneException("OVF file contains a bad value for BOOT",StatusCodes.BAD_OVF);
				}
				else if(productProperty == BOOTLOADER) {
					buf.append(separator);
					buf.append(BOOTLOADER);
					buf.append(" = \""); buf.append(value); buf.append("\"");
					weGotBootloader = true;

				}
				else if(productProperty == KERNEL) {
					buf.append(separator);
					buf.append(KERNEL);
					buf.append(" = \""); buf.append(value); buf.append("\"");
					weGotKernel = true;
				}

				else {
					buf.append(separator);
					buf.append(productProperty);
					buf.append(" = \""); buf.append(value); buf.append("\"");
				}
			}
		}
		buf.append("]\n");

		/*
		 *  Disk attributes
		 */


		// Let's avoid some work to the VM :)
		// 100 characters should avoid buffer resizing in most cases. 
		// Don't worry, no buffer overflow ahead :)
		StringBuilder propertyName = new StringBuilder(100);

		for(OVFDisk ovfDisk : ovf.getDisks().values()) {
			buf.append("DISK = [\n");

			String dskName = ovfDisk.getId();
			int dskNameLen = dskName.length();
			String pathOrURL = ovfDisk.getHref();

			propertyName.delete(0, propertyName.length());
			propertyName.append(dskName);
			propertyName.append(".type");

			String typeName = ovf.getProductProperty(propertyName.toString());

			// Whe no disk type is specified, OpenNebula defaults to disk, and we do so.
			DskType dskType = typeName == null ? DskType.virtualDisk : DskType.fromString(typeName);


			Long size = ovfDisk.getCapacityMB();

			if(size == null && (dskType == DskType.virtualSwap || dskType == DskType.onTheFlyDisk))
				throw new DRPOneException("OVF file is missing mandatory size specification for a disk",StatusCodes.BAD_OVF);


			propertyName.delete(dskNameLen, propertyName.length());
			propertyName.append(".target");

			String target = ovf.getProductProperty(propertyName.toString());

			if(target == null && dskType != DskType.virtualSwap)
				throw new DRPOneException("OVF file is missing mandatory target specification for a disk",StatusCodes.BAD_OVF);

			switch (dskType) {

			case virtualDisk :
				// If we have a path or URL specified, we have a pysical
				// disk. Else we have a pre-registered disk resource.

				if(pathOrURL != null && ! "".equals(pathOrURL)) {
					// We are using a physical disk image
					buf.append("SOURCE = \""); buf.append(pathOrURL); buf.append("\",\n");
					buf.append("TARGET = \""); buf.append(target); buf.append("\"");

				}
				else {
					// No disk source, we have a pre-registered image.
					if(dskName == null || "".equals(dskName))
						throw new DRPOneException("OVF file is missing mandatory size specification for a disk",StatusCodes.BAD_OVF);
					buf.append("IMAGE = \""); buf.append(dskName); buf.append("\"");
				}
				separator = ",\n";

				// This COULD be a good system image...
				weGotDisk = true;
				break;
			case virtualSwap:
				buf.append("SOURCE = \""); buf.append(pathOrURL); buf.append("\",\n");
				buf.append("SIZE = "); buf.append(size); buf.append("\",\n");
				buf.append("TARGET = \""); buf.append(target); buf.append("\"");

				break;

			case blockDevice:
				buf.append("SOURCE = \""); buf.append(pathOrURL); buf.append("\",\n");
				buf.append("TARGET = \""); buf.append(target); buf.append("\"");

				break;

			case onTheFlyDisk:
				propertyName.delete(dskNameLen, propertyName.length());
				propertyName.append(".format");

				String format = ovf.getProductProperty(propertyName.toString());

				if(format == null)
					throw new DRPOneException("OVF file is missing mandatory format specification for an ont the fly disk image",StatusCodes.BAD_OVF);

				buf.append("SOURCE = \""); buf.append(pathOrURL); buf.append("\",\n");
				buf.append("SIZE = "); buf.append(size); buf.append("\",\n");
				buf.append("FORMAT = \""); buf.append(format); buf.append("\",\n");
				buf.append("TARGET = \""); buf.append(target); buf.append("\"");
			}
			typeName = "";
			pathOrURL= "";
			dskName = "";
			buf.append("]\n");
		}

		// Network attributes
		for(OVFNetwork ovfNetwork : ovf.getNetworks().values()) {
			buf.append("NIC = [\n");
			String nicName = ovfNetwork.getConnectionName();

			// I am working on a new array, so I need to reset the separator
			separator = "";

			if(nicName == null || "".equals(nicName)) {
				// We supply IP and MAC for this NIC
				tmp = ovfNetwork.getIp();
				if( tmp != null) {
					buf.append("IP = \""); buf.append(tmp); buf.append("\"");
					separator = ",\n";
				}

				tmp = ovfNetwork.getMac();
				if( tmp != null ) {
					buf.append(separator);
					buf.append("MAC = \""); buf.append(tmp); buf.append("\"");
				}
			}
			else {
				// We ask OpenNebula to assign us IP and MAC
				buf.append("NETWORK = \""); buf.append(nicName); buf.append("\"");
			}
			nicName = "";
			buf.append("]\n");

		}

		// Context - requires again network attributes
		// 
		// We list devices in order eth0, eth1, eth2... The context script will get
		// them out and prepare the network configuration files.

		buf.append("CONTEXT = [\n");
		separator = "";
		int ethNumber = 0;
		for(OVFNetwork ovfNetwork : ovf.getNetworks().values()) {
			String nicName = ovfNetwork.getConnectionName();

			if(nicName == null || "".equals(nicName)) {
				tmp = ovfNetwork.getIp();
				if( tmp != null) {
					buf.append(separator);
					buf.append("IP_"); buf.append(ethNumber); buf.append(" = \""); buf.append(tmp); buf.append("\"\n");
					separator = ",\n";
				}

				tmp = ovfNetwork.getMac();
				if( tmp != null ) {
					buf.append(separator);
					buf.append("MAC_"); buf.append(ethNumber); buf.append(" = \""); buf.append(tmp); buf.append("\"\n");
					separator = ",\n";

				}

			}
			else {
				buf.append(separator);
				buf.append("IP_"); buf.append(ethNumber); buf.append(" = \"$NIC[IP, NETWORK=\\\""); buf.append(nicName); buf.append("\\\"]\",\n");
				buf.append("MAC_"); buf.append(ethNumber); buf.append(" = \"$NIC[MAC, NETWORK=\\\""); buf.append(nicName); buf.append("\\\"]\",\n");
				buf.append("NETMASK_"); buf.append(ethNumber); buf.append("=\"$NETWORK[NETMASK, NAME=\\\""); buf.append(nicName); buf.append("\\\"]\",\n");
				buf.append("GATEWAY_"); buf.append(ethNumber); buf.append("=\"$NETWORK[GATEWAY, NAME=\\\""); buf.append(nicName); buf.append("\\\"]\",\n");
				buf.append("BROADCAST_"); buf.append(ethNumber); buf.append("=\"$NETWORK[BROADCAST, NAME=\\\""); buf.append(nicName); buf.append("\\\"]\",\n");
				buf.append("NETWORK_"); buf.append(ethNumber); buf.append("=\"$NETWORK[NETWORK, NAME=\\\""); buf.append(nicName); buf.append("\\\"]\"");
				separator = ",\n";
			}
			ethNumber++;
		}
		buf.append("]\n");
		log.trace(buf.toString());
		System.out.println(buf.toString());

		if(! ( (weGotKernel || weUseKVM)     // this is mandatory for XEN only
			   && (weGotBootloader || weUseKVM) // this is mandatory for XEN only
			   && (weGotBoot  || ! weUseKVM)    // this is mandatory for KVM only
			   && weGotDisk) ) {
			StringBuilder msg = new StringBuilder("These problem in the OVF file prevent correct execution of the command:");
			msg.append( (weGotKernel || weUseKVM)     ? "" : "\n- Missing KERNEL specification for use with XEN");
			msg.append( (weGotBootloader || weUseKVM) ? "" : "\n- Missing BOOTLOADER specification for use with XEN");
			msg.append( (weGotBoot  || ! weUseKVM)    ? "" : "\n- Missing BOOT device specification for use with KVM");
			msg.append(          weGotDisk            ? "" : "\n- Missing any DISK image specification");

			throw new DRPOneException(msg.toString(),StatusCodes.BAD_OVF);

		}

		return buf.toString();
	}
    
	/* ==============================================================
	 * ==============================================================
	 *
	 * private helpers for the getCompute method
	 *
	 */

    private OCAWrapper parseOca(String s) {
    	OCAWrapper rv = null;
		StringBuilder cause= new StringBuilder();
    	try {
    		rv=  OCAWrapperFactory.parse(s);
    	}
    	catch(SAXException se) {
    		
    		throw new DRPOneException("XML Parsing error",se,StatusCodes.INTERNAL);
    	}
    	catch(JAXBException e)  {
			if (e instanceof PropertyException)
				cause.append("Access to property failed: ");
			else if (e instanceof MarshalException)
				cause.append("Marshalling failed: ");
			else if (e instanceof UnmarshalException)
				cause.append("Unmarshalling failed: ");
			else if (e instanceof ValidationException)
				cause.append("XML Validation failed: ");
			else {
				cause.append("Unespected ");
				cause.append(e.getClass().getName());
				cause.append(": ");
			}
			cause.append(e.getMessage());
			log.error(cause.toString());
			if(log.isTraceEnabled())
				log.trace(cause, e);
			throw new DRPOneException(cause.toString(),e,StatusCodes.ONE_FAILURE);
    	}
    	return rv;
    }
    
/* REMOVE THIS COMMEND AND IMPLEMENT
 
    @GET
    @Path("/compute")
    @Produces("application/xml")
    public ListStrings getComputes() {
        ListStrings ret = new ListStrings();

        List<Compute> domains = super.getDomains();

        for (int i=0; i<domains.size(); i++) {
            ret.add(domains.get(i).getId());
        }

        return ret;
    }
REMOVE THIS COMMEND AND IMPLEMENT */

    
/* REMOVE THIS COMMEND AND IMPLEMENT    
    //Delete
    @DELETE
    @Path("/compute/{envid}")
    public void deleteCompute(@PathParam("envid") String envid) throws VRMMSchedulerException {
        System.out.println("terminateEnvironment " + envid);

        try {
            super.destroy(envid);
            System.out.println("Environment "+envid+" was destroyed.");
        } catch (VRMMSchedulerException e) { //catch (ItemNotFoundException e) {
            e.printStackTrace();
            if (e.getMessage().contains("cannot be found in any node")) {
                throw new WebApplicationException(426);
            } else if (e.getMessage().contains("VirtMonitor")) {//REPAIR:VirtMonitor: getDomainId error.
                throw new WebApplicationException(420);
            } else if (e.getMessage().contains("Not enough resources")) {//REPAIR:INFO: Not enough resources: Memory
                throw new WebApplicationException(425);
            } else if (e.getMessage().contains("VM does not exist")) {//REPAIR:INFO: Not enough resources: Memory
                throw new WebApplicationException(427);
            } else if (e.getMessage().contains("No available nodes")) {
                throw new WebApplicationException(428);
            } else if (e.getMessage().contains("Cannot connect with the Scheduler")) {
                throw new WebApplicationException(429);
            } else if (e.getMessage().contains("Cannot connect with Simple Scheduler")) {
                throw new WebApplicationException(430);
            } else if (e.getMessage().contains("Cannot connect with Hadoop Scheduler")) {
                throw new WebApplicationException(431);
            } else if (e.getMessage().contains("Cannot recognize address")) {
                throw new WebApplicationException(432);
            } else if (e.getMessage().contains("is not in any VM")) {
                throw new WebApplicationException(433);
            } else if (e.getMessage().contains("cannot be found in any node")) {
                throw new WebApplicationException(434);
            } else if (e.getMessage().contains("Can not delete")) {
                throw new WebApplicationException(435);
            } else {
                throw new WebApplicationException(424);
            }
        }
    }
REMOVE THIS COMMEND AND IMPLEMENT */

    /***************************************************************************
     *         Methods without a correspondence in the OCCI interface.         *
     **************************************************************************/

/* REMOVE THIS COMMEND AND IMPLEMENT
    @GET
    @Path("/info")
    @Produces("text/plain")
    public String info() {
        return "DRP is running";
    }
REMOVE THIS COMMEND AND IMPLEMENT */

/* REMOVE THIS COMMEND AND IMPLEMENT
    @GET
    @Path("/")
    @Produces("application/xml")
    public List<Compute> rootMethod() {
        return super.getDomains();
    }
REMOVE THIS COMMEND AND IMPLEMENT */

    /***************************COMPUTE methods.*******************************/

/* REMOVE THIS COMMEND AND IMPLEMENT
    @GET
    @Path("/compute/all")
    @Produces("application/xml")
    public List<Compute> getAllEnvironments() {

            return super.getDomains();
    }
REMOVE THIS COMMEND AND IMPLEMENT */

    
    /***********************NODE MANAGEMENT methods.***************************/
    
/* REMOVE THIS COMMEND AND IMPLEMENT
    @GET
    @Path("/resources")
    @Produces("application/xml")
    public List<String> getNodes(){
            ListStrings list = new ListStrings();

            list.addAll(super.getNodes());
            //GenericEntity<ListStrings> entity = new GenericEntity<ListStrings>(list) {};
            //Response response = Response.ok(entity).build();
            return list;
    }
REMOVE THIS COMMEND AND IMPLEMENT */

/* REMOVE THIS COMMEND AND IMPLEMENT
    @POST
    @Path("/resources")
    public void nodeUp(@QueryParam("node") String nodeId) {
            super.nodeUp(nodeId);
    }
REMOVE THIS COMMEND AND IMPLEMENT */

/* REMOVE THIS COMMEND AND IMPLEMENT
    @DELETE
    @Path("/resources")
    public void nodeDown(@QueryParam("node") String node, @QueryParam("cause") String type) {
            super.nodeDown(node, type);
    }
REMOVE THIS COMMEND AND IMPLEMENT */

    
    /***************************************************************************
     *                              END OF CLASS                               *
     **************************************************************************/
    


    /*@GET
    @Path("/environments")
    @Produces("application/xml")
    public ListStrings getAllComputes() {
        ListStrings ret = new ListStrings();
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI uri = ub.build();

        List<Compute> domains = super.getDomains();
        for (int i = 0; i < domains.size(); i++) {
            ret.add(uri.toString() + "/" + domains.get(i).getId());
            //	URI uri2 = ub.path(domains.get(i).getId()).build();//ret.add(uri.toString());//	ret.add(domains.get(i).getId());
        }


        return ret;
    }*/

    /*
     * Si bien el interfaz permanecerá durante el proyecto como descrito, se realizará una ampliación
     * de la especificación para incluir los parámetros necesarios para ser usados por el Cliente de
     * Cloud externa. La ampliación de dicha especificación se hará extendiendo el XML que se usa para
     * describir el render aceptado por este interfaz OCCI.
     */
    /*
     *
     * NETWORK
     *
     */
    /*@POST
    @Path("/network/{id}/{name}/{ip}/{size}")
    @Consumes("application/xml")
    public String newNetwork(@PathParam("id") String id, @PathParam("name") String name, @PathParam("ip") String ip, @PathParam("size") String size) {

        Network network = new Network();

        network.setName(name);
        network.setBridge(name);
        network.setAddress(ip);
        network.setMode("private");
        network.setDev(null);
        network.setId(null);
        //network.setId(id);

        String[] parts = ip.split("\\.");
        String A = null;
        String B = null;
        String C = null;
        String D = null;

        for (String s : parts) {
            if (A == null) {
                A = s;
            } else if (B == null) {
                B = s;
            } else if (C == null) {
                C = s;
            } else if (D == null) {
                D = s;
            } else {
                return "bad ip";
            }
        }

        if (size.startsWith("A")) {
            network.setIp_start(A + "." + 255 + "." + 255 + "." + 2);
            network.setIp_end(A + "." + 255 + "." + 255 + "." + 255);
            network.setNetmask("255.0.0.0");
        } else if (size.startsWith("B")) {
            network.setIp_start(A + "." + B + "." + 255 + "." + 2);
            network.setIp_end(A + "." + B + "." + 255 + "." + 255);
            network.setNetmask("255.255.0.0");
        } else if (size.startsWith("C")) {
            network.setIp_start(A + "." + B + "." + C + "." + 2);
            network.setIp_end(A + "." + B + "." + C + "." + 255);
            network.setNetmask("255.255.255.0");
        } else if (size.startsWith("D")) {
            network.setIp_start(A + "." + B + "." + C + "." + 2);
            network.setIp_end(A + "." + B + "." + C + "." + 255);
        } else {
            network.setIp_start(A + "." + B + "." + C + "." + 2);
            network.setIp_end(A + "." + B + "." + C + "." + 255);
        }


        try {
            //Created Network
            System.out.println(" - " + network.getName() + " - " + network.getAddress());
            return super.createNetwork(network);

        } catch (VRMMSchedulerException e) {
            e.printStackTrace();
            if (e.getMessage().contains("cannot create bridge")) {
                throw new WebApplicationException(426);
            } else if (e.getMessage().contains("internal error cannot parse")) {
                throw new WebApplicationException(420);
            } else if (e.getMessage().contains("libvir: Network Config error")) {
                throw new WebApplicationException(425);
            } else if (e.getMessage().contains("no starting network")) {
                throw new WebApplicationException(427);
            } else if (e.getMessage().contains("Starting network")) {
                throw new WebApplicationException(428);
            } else if (e.getMessage().contains("Getting network info, refreshing cache")) {
                throw new WebApplicationException(432);
            } else if (e.getMessage().contains("Error getting networks full XML")) {
                throw new WebApplicationException(433);
            } else if (e.getMessage().contains("Error getting network XML")) {
                throw new WebApplicationException(434);
            } else {
                throw new WebApplicationException(424);
            }
        }

    }

    @POST
    @Path("/network")
    @Consumes("application/xml")
    public String newNetwork(Network network) {
        try {
            //Created Network
            System.out.println(" - " + network.getName() + " - " + network.getAddress());
            return super.createNetwork(network);
        } catch (VRMMSchedulerException e) {
            e.printStackTrace();
            if (e.getMessage().contains("cannot create bridge")) {
                throw new WebApplicationException(426);
            } else if (e.getMessage().contains("internal error cannot parse")) {
                throw new WebApplicationException(420);
            } else if (e.getMessage().contains("libvir: Network Config error")) {
                throw new WebApplicationException(425);
            } else if (e.getMessage().contains("no starting network")) {
                throw new WebApplicationException(427);
            } else if (e.getMessage().contains("Starting network")) {
                throw new WebApplicationException(428);
            } else if (e.getMessage().contains("Getting network info, refreshing cache")) {
                throw new WebApplicationException(432);
            } else if (e.getMessage().contains("Error getting networks full XML")) {
                throw new WebApplicationException(433);
            } else if (e.getMessage().contains("Error getting network XML")) {
                throw new WebApplicationException(434);
            } else {
                throw new WebApplicationException(424);
            }
        }
    }

    @GET
    @Path("/network/")
    @Produces("application/xml")
    public ListStrings getNetworks() {
        try {
            List<String> networks = super.getNetworksInfo();
            ListStrings ret = new ListStrings();

            for (int i = 0; i < networks.size(); i++) {
                ret.add(networks.get(i));
            }
            return ret;
        } catch (VRMMSchedulerException e) {
            e.printStackTrace();
            if (e.getMessage().contains("cannot create bridge")) {
                throw new WebApplicationException(426);
            } else if (e.getMessage().contains("internal error cannot parse")) {
                throw new WebApplicationException(420);
            } else if (e.getMessage().contains("libvir: Network Config error")) {
                throw new WebApplicationException(425);
            } else if (e.getMessage().contains("no starting network")) {
                throw new WebApplicationException(427);
            } else if (e.getMessage().contains("Starting network")) {
                throw new WebApplicationException(428);
            } else if (e.getMessage().contains("Getting network info, refreshing cache")) {
                throw new WebApplicationException(432);
            } else if (e.getMessage().contains("Error getting networks full XML")) {
                throw new WebApplicationException(433);
            } else if (e.getMessage().contains("Error getting network XML")) {
                throw new WebApplicationException(434);
            } else {
                throw new WebApplicationException(424);
            }
        }
    }

    @GET
    @Path("/network/all")
    @Produces("application/xml")
    public List<Network> getAllNetworks() {
        try {
            List<Network> networks = super.getAllNetworks();
            return networks;
        } catch (VRMMSchedulerException e) {
            e.printStackTrace();
            if (e.getMessage().contains("cannot create bridge")) {
                throw new WebApplicationException(426);
            } else if (e.getMessage().contains("internal error cannot parse")) {
                throw new WebApplicationException(420);
            } else if (e.getMessage().contains("libvir: Network Config error")) {
                throw new WebApplicationException(425);
            } else if (e.getMessage().contains("no starting network")) {
                throw new WebApplicationException(427);
            } else if (e.getMessage().contains("Starting network")) {
                throw new WebApplicationException(428);
            } else if (e.getMessage().contains("Getting network info, refreshing cache")) {
                throw new WebApplicationException(432);
            } else if (e.getMessage().contains("Error getting networks full XML")) {
                throw new WebApplicationException(433);
            } else if (e.getMessage().contains("Error getting network XML")) {
                throw new WebApplicationException(434);
            } else {
                throw new WebApplicationException(424);
            }
        }
    }

    @GET
    @Path("/network/conflicts")
    @Produces("text/plain")
    public String NetworkConflict() {
        try {
            return super.NetworkConflict();
        } catch (VRMMSchedulerException e) {
            e.printStackTrace();
            throw new WebApplicationException(424);
        }
    }

    @GET
    @Path("/network/conflicts/{taskid}")
    @Produces("text/plain")
    public String NetworkConflict(@PathParam("taskid") String taskid) {
        try {
            return super.NetworkConflict(taskid);
        } catch (VRMMSchedulerException e) {
            e.printStackTrace();
            throw new WebApplicationException(424);

        }
    }

    @DELETE
    @Path("/network/{taskid}")
    public void cancelNetwork(@PathParam("taskid") String taskid) {
        String ret = "not canceled";
        try {
            super.destroyNetwork(taskid);
        } catch (VRMMSchedulerException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Destroying network")) {
                throw new WebApplicationException(429);
            } else if (e.getMessage().contains("Finalized network")) {
                throw new WebApplicationException(430);
            } else if (e.getMessage().contains("free network")) {
                throw new WebApplicationException(431);
            } else {
                throw new WebApplicationException(424);
            }
        }
    }

    /*
     *
     *  NO API OCCI / NO NECESARIO PARA NUBA
     *
     *  Métodos que no pertenecen a la API OCCI.
     *  Métodos del SimpleSchedulerREST.
     *
     */
    /////////////////////////////////////////////////////////////////////////////
	/* 
     * EXTRA FUNCTIONS
     */
    /*@GET
    @Path("/environments/{envid}/status") //antic state, falta el destroy per arreglar, al VtM ho fa be
    @Produces("text/plain")
    public String getState(@PathParam("envid") String envId) throws VRMMSchedulerException {
        String status = "Unknown";
        Compute d = null;

        try {

            d = super.getDomain(envId);
            if (d == null) {
                status = "Failed";
                return status;
            }

            status = super.getState(envId);
            //return status;
            //creating??
            System.out.println("VRMMSchedulerException?");
        } catch (VRMMSchedulerException e) { //catch (ItemNotFoundException e) {
            e.printStackTrace();
            if (e.getMessage().contains("cannot be found in any node")) {
                throw new WebApplicationException(426);
            } else if (e.getMessage().contains("VirtMonitor")) {//REPAIR:VirtMonitor: getDomainId error.
                throw new WebApplicationException(420);
            } else if (e.getMessage().contains("Not enough resources")) {//REPAIR:INFO: Not enough resources: Memory
                throw new WebApplicationException(425);
            } else if (e.getMessage().contains("VM does not exist")) {//REPAIR:INFO: Not enough resources: Memory
                throw new WebApplicationException(427);
            } else if (e.getMessage().contains("No available nodes")) {
                throw new WebApplicationException(428);
            } else if (e.getMessage().contains("Cannot connect with the Scheduler ")) {
                throw new WebApplicationException(429);
            } else if (e.getMessage().contains("Cannot connect with Simple Scheduler")) {
                throw new WebApplicationException(430);
            } else if (e.getMessage().contains("Cannot connect with Hadoop Scheduler")) {
                throw new WebApplicationException(431);
            } else if (e.getMessage().contains("Cannot recognize address ")) {
                throw new WebApplicationException(432);
            } else if (e.getMessage().contains("is not in any VM")) {
                throw new WebApplicationException(433);
            } else if (e.getMessage().contains("cannot be found in any node")) {
                throw new WebApplicationException(434);
            } else {
                throw new WebApplicationException(424);
            }
        }

        return status;
    }

    @GET
    @Path("/environments/{envid}/activities")
    @Produces("application/xml")
    public ListStrings getActivities(@PathParam("envid") String envid) {
        ListStrings ls = new ListStrings();

        ls.addAll(super.getTasks());

        return ls;
    }

    @GET
    @Path("/activities")
    @Produces("application/xml")
    public ListStrings getAllActivities() {
        ListStrings ls = new ListStrings();

        for (String taskId : super.getTasks()) {
            ls.add(taskId);
        }

        return ls;
    }

    @POST
    @Path("/environments/{envid}")
    @Consumes("application/xml")
    public String submitActivity(@PathParam("envid") String envid, @QueryParam("user") String user, @QueryParam("background") String back, @QueryParam("jsdl") String jsdl) {
        String taskId = null;
        boolean background = true;

        if (envid == null) {
            return "Error, you need to send some activity";
        } else {

            if (user == null) {
                user = "user";
            }

            if (back.contains("false")) {
                background = false;
            }

            try {
                /*if(!jsdl.startsWith("<?xml")){
                JSDL jsdlaux = new JSDL();
                jsdlaux.setCommand(jsdl);
                jsdl =  jsdlaux.toString();
                }*/

               /* taskId = super.runTask(envid, user, jsdl, background);

            } catch (VRMMSchedulerException e) {
                e.printStackTrace();
                if (e.getMessage().contains("cannot be found in any node")) {
                    throw new WebApplicationException(426);
                } else if (e.getMessage().contains("VirtMonitor")) {//REPAIR:VirtMonitor: getDomainId error.
                    throw new WebApplicationException(420);
                } else if (e.getMessage().contains("Not enough resources")) {//REPAIR:INFO: Not enough resources: Memory
                    throw new WebApplicationException(425);
                } else if (e.getMessage().contains("VM does not exist")) {//REPAIR:INFO: Not enough resources: Memory
                    throw new WebApplicationException(427);
                } else if (e.getMessage().contains("No available nodes")) {
                    throw new WebApplicationException(428);
                } else if (e.getMessage().contains("Cannot connect with the Scheduler ")) {
                    throw new WebApplicationException(429);
                } else if (e.getMessage().contains("Cannot connect with Simple Scheduler")) {
                    throw new WebApplicationException(430);
                } else if (e.getMessage().contains("Cannot connect with Hadoop Scheduler")) {
                    throw new WebApplicationException(431);
                } else if (e.getMessage().contains("Cannot recognize address ")) {
                    throw new WebApplicationException(432);
                } else if (e.getMessage().contains("is not in any VM")) {
                    throw new WebApplicationException(433);
                } else if (e.getMessage().contains("cannot be found in any node")) {
                    throw new WebApplicationException(434);
                } else {
                    throw new WebApplicationException(424);
                }
            }
        }

        return taskId;
    }

    @DELETE
    @Path("/environments/{taskid}/activities")
    @Produces("text/plain")
    public String cancelActivity(@PathParam("taskid") String taskid) {

        String taskId = "not canceled";
        try {
            taskId = super.cancelTask(taskid);
        } catch (VRMMSchedulerException e) {
            e.printStackTrace();
            if (e.getMessage().contains("cannot be found in any node")) {
                throw new WebApplicationException(426);
            } else if (e.getMessage().contains("VirtMonitor")) {//REPAIR:VirtMonitor: getDomainId error.
                throw new WebApplicationException(420);
            } else if (e.getMessage().contains("Not enough resources")) {//REPAIR:INFO: Not enough resources: Memory
                throw new WebApplicationException(425);
            } else if (e.getMessage().contains("VM does not exist")) {//REPAIR:INFO: Not enough resources: Memory
                throw new WebApplicationException(427);
            } else if (e.getMessage().contains("No available nodes")) {
                throw new WebApplicationException(428);
            } else if (e.getMessage().contains("Cannot connect with the Scheduler ")) {
                throw new WebApplicationException(429);
            } else if (e.getMessage().contains("Cannot connect with Simple Scheduler")) {
                throw new WebApplicationException(430);
            } else if (e.getMessage().contains("Cannot connect with Hadoop Scheduler")) {
                throw new WebApplicationException(431);
            } else if (e.getMessage().contains("Cannot recognize address ")) {
                throw new WebApplicationException(432);
            } else if (e.getMessage().contains("is not in any VM")) {
                throw new WebApplicationException(433);
            } else if (e.getMessage().contains("cannot be found in any node")) {
                throw new WebApplicationException(434);
            } else {
                throw new WebApplicationException(424);
            }
        }
        return taskId;
    }

    @GET
    @Path("/cancel/{taskid}")
    @Produces("text/plain")
    public String cancelActivity2(@PathParam("taskid") String taskid) {

        String taskId = "not canceled";
        try {
            taskId = super.cancelTask(taskid);
        } catch (VRMMSchedulerException e) {
            e.printStackTrace();
            if (e.getMessage().contains("cannot be found in any node")) {
                throw new WebApplicationException(426);
            } else if (e.getMessage().contains("VirtMonitor")) {//REPAIR:VirtMonitor: getDomainId error.
                throw new WebApplicationException(420);
            } else if (e.getMessage().contains("Not enough resources")) {//REPAIR:INFO: Not enough resources: Memory
                throw new WebApplicationException(425);
            } else if (e.getMessage().contains("VM does not exist")) {//REPAIR:INFO: Not enough resources: Memory
                throw new WebApplicationException(427);
            } else if (e.getMessage().contains("No available nodes")) {
                throw new WebApplicationException(428);
            } else if (e.getMessage().contains("Cannot connect with the Scheduler ")) {
                throw new WebApplicationException(429);
            } else if (e.getMessage().contains("Cannot connect with Simple Scheduler")) {
                throw new WebApplicationException(430);
            } else if (e.getMessage().contains("Cannot connect with Hadoop Scheduler")) {
                throw new WebApplicationException(431);
            } else if (e.getMessage().contains("Cannot recognize address ")) {
                throw new WebApplicationException(432);
            } else if (e.getMessage().contains("is not in any VM")) {
                throw new WebApplicationException(433);
            } else if (e.getMessage().contains("cannot be found in any node")) {
                throw new WebApplicationException(434);
            } else {
                throw new WebApplicationException(424);
            }
        }
        return taskId;
    }

    @GET
    @Path("/environments/{envid}/{taskid}")
    @Produces("text/plain")
    public String getActivityStatus(@PathParam("envid") String envid, @PathParam("taskid") String taskid) {
        String status = null;
        try {
            status = super.getTaskStatus(taskid);
        } catch (VRMMSchedulerException e) { //catch (ItemNotFoundException e) {
            e.printStackTrace();
            if (e.getMessage().contains("cannot be found in any node")) {
                throw new WebApplicationException(426);
            } else if (e.getMessage().contains("VirtMonitor")) {//REPAIR:VirtMonitor: getDomainId error.
                throw new WebApplicationException(420);
            } else if (e.getMessage().contains("Not enough resources")) {//REPAIR:INFO: Not enough resources: Memory
                throw new WebApplicationException(425);
            } else if (e.getMessage().contains("VM does not exist")) {//REPAIR:INFO: Not enough resources: Memory
                throw new WebApplicationException(427);
            } else if (e.getMessage().contains("No available nodes")) {
                throw new WebApplicationException(428);
            } else if (e.getMessage().contains("Cannot connect with the Scheduler ")) {
                throw new WebApplicationException(429);
            } else if (e.getMessage().contains("Cannot connect with Simple Scheduler")) {
                throw new WebApplicationException(430);
            } else if (e.getMessage().contains("Cannot connect with Hadoop Scheduler")) {
                throw new WebApplicationException(431);
            } else if (e.getMessage().contains("Cannot recognize address ")) {
                throw new WebApplicationException(432);
            } else if (e.getMessage().contains("is not in any VM")) {
                throw new WebApplicationException(433);
            } else if (e.getMessage().contains("cannot be found in any node")) {
                throw new WebApplicationException(434);
            } else if (e.getMessage().contains("Could not get status: java.net.NoRouteToHostException: No route to host")) { // net.emotivecloud.vrmm.vtm.VtMException: Could not get status: java.net.NoRouteToHostException: No route to host
                throw new WebApplicationException(435);
            } else if (e.getMessage().contains("Can not show VM status")) {
                throw new WebApplicationException(436);
            } else {
                throw new WebApplicationException(424);
            }
        }
        return status;
    }

    /*
    END EXTRA FUNCTIONS
     * */

    /*
    NODES MANAGEMENT
     * */
    /*

    @GET
    @Produces("text/plain")
    @Override
    public String getLocation(@QueryParam("id") String id) {
        String ret = "";
        if (id != null) {
            try {
                ret = super.getLocation(id);
            } catch (VRMMSchedulerException e) { //catch (ItemNotFoundException e) {
                e.printStackTrace();
                if (e.getMessage().contains("cannot be found in any node")) {
                    throw new WebApplicationException(426);
                } else if (e.getMessage().contains("VirtMonitor")) {//REPAIR:VirtMonitor: getDomainId error.
                    throw new WebApplicationException(420);
                } else if (e.getMessage().contains("Not enough resources")) {//REPAIR:INFO: Not enough resources: Memory
                    throw new WebApplicationException(425);
                } else if (e.getMessage().contains("VM does not exist")) {//REPAIR:INFO: Not enough resources: Memory
                    throw new WebApplicationException(427);
                } else if (e.getMessage().contains("No available nodes")) {
                    throw new WebApplicationException(428);
                } else if (e.getMessage().contains("Cannot connect with the Scheduler ")) {
                    throw new WebApplicationException(429);
                } else if (e.getMessage().contains("Cannot connect with Simple Scheduler")) {
                    throw new WebApplicationException(430);
                } else if (e.getMessage().contains("Cannot connect with Hadoop Scheduler")) {
                    throw new WebApplicationException(431);
                } else if (e.getMessage().contains("Cannot recognize address ")) {
                    throw new WebApplicationException(432);
                } else if (e.getMessage().contains("is not in any VM")) {
                    throw new WebApplicationException(433);
                } else if (e.getMessage().contains("cannot be found in any node")) {
                    throw new WebApplicationException(434);
                } else {
                    throw new WebApplicationException(424);
                }
            }
        }
        return ret;
    }



    @GET
    @Path("/environments/{envid}/location")
    @Produces("text/plain")
    public String getEnvironmentLocation(@QueryParam("envid") String envid) {
        String ret = "";
        if (envid != null) {
            try {
                ret = super.getLocation(envid);
            } catch (VRMMSchedulerException e) { //catch (ItemNotFoundException e) {
                e.printStackTrace();
                if (e.getMessage().contains("cannot be found in any node")) {
                    throw new WebApplicationException(426);
                } else if (e.getMessage().contains("VirtMonitor")) {//REPAIR:VirtMonitor: getDomainId error.
                    throw new WebApplicationException(420);
                } else if (e.getMessage().contains("Not enough resources")) {//REPAIR:INFO: Not enough resources: Memory
                    throw new WebApplicationException(425);
                } else if (e.getMessage().contains("VM does not exist")) {//REPAIR:INFO: Not enough resources: Memory
                    throw new WebApplicationException(427);
                } else if (e.getMessage().contains("No available nodes")) {
                    throw new WebApplicationException(428);
                } else if (e.getMessage().contains("Cannot connect with the Scheduler ")) {
                    throw new WebApplicationException(429);
                } else if (e.getMessage().contains("Cannot connect with Simple Scheduler")) {
                    throw new WebApplicationException(430);
                } else if (e.getMessage().contains("Cannot connect with Hadoop Scheduler")) {
                    throw new WebApplicationException(431);
                } else if (e.getMessage().contains("Cannot recognize address ")) {
                    throw new WebApplicationException(432);
                } else if (e.getMessage().contains("is not in any VM")) {
                    throw new WebApplicationException(433);
                } else if (e.getMessage().contains("cannot be found in any node")) {
                    throw new WebApplicationException(434);
                } else {
                    throw new WebApplicationException(424);
                }
            }
        }
        return ret;
    }
*/

	/**
	 * <code>main</code> 
	 *
	 * @param args a <code>String</code> 
	 */
	public static final void main(final String[] args) {

        Client ocaClient = null;
        try {
        	ocaClient = new Client();
		}
        catch(Exception whocares) {
        	System.out.println(whocares.getMessage());
        }
        
		OneResponse rc = VirtualMachine.info(ocaClient, 14);

		System.out.println(rc.getMessage());

		System.out.println(rc.getErrorMessage());

	}

}
