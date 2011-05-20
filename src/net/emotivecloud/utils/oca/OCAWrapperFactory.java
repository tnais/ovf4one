package net.emotivecloud.utils.oca;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.emotivecloud.scheduler.onedrp.DRPOneException;
import net.emotivecloud.scheduler.onedrp.StatusCodes;

import org.opennebula.xmlschema.DiskType;
import org.opennebula.xmlschema.NicType;
import org.opennebula.xmlschema.OsType;
import org.opennebula.xmlschema.TemplateType;
import org.opennebula.xmlschema.VMSIMPLE;


/**
 * Class <code>OCAWrapperFactory</code> Creates an OCAWrapper from the
 * OCA.info() output.
 *
 * @author <a href="mailto:saint@eng.it">Gian Uberto Lauri</a>
 * @version $Revision$
 */
public class OCAWrapperFactory {

    private static JAXBContext jbc = null;
    private static Unmarshaller u = null;
    private static Logger log;
    static {
        log = Logger.getLogger(OCAWrapperFactory.class.getName());
        try {
            jbc = JAXBContext.newInstance(VMSIMPLE.class);
            u = jbc.createUnmarshaller();
        } catch (JAXBException e) {
            log.severe(e.getMessage());
        }
    }

	public static OCAWrapper create (VMSIMPLE vmSimple) {
		OCAWrapper rv = new OCAWrapper();
		
		rv.setId(vmSimple.getID().intValue());
		rv.setUid(vmSimple.getUID().intValue());
		rv.setName(vmSimple.getNAME());
		rv.setLastPoll(vmSimple.getLASTPOLL().intValue());
		rv.setState(vmSimple.getSTATE().intValue());
		rv.setLcmState(vmSimple.getLCMSTATE().intValue());
		rv.setStime(vmSimple.getSTIME().intValue());
		rv.setEtime(vmSimple.getETIME().intValue());
		rv.setDeployId(vmSimple.getDEPLOYID());
		rv.setMemory(vmSimple.getMEMORY().intValue());
		rv.setCpu(vmSimple.getCPU().intValue());
		rv.setNetTx(vmSimple.getNETTX().intValue());
		rv.setNetRx(vmSimple.getNETRX().intValue());
		rv.setLastSeq(vmSimple.getLASTSEQ().intValue());
		
		OCATemplateWrapper newTemplate = new OCATemplateWrapper();
		TemplateType templateType = vmSimple.getTEMPLATE();
		
		newTemplate.setContext(templateType.getCONTEXT());
		newTemplate.setCpu(templateType.getCPU().intValue());
		try {
			newTemplate.setMemory(Integer.parseInt(templateType.getMEMORY()));
		}
		catch(NumberFormatException nfe) {
			throw new DRPOneException("oned replied with an invalid memory size " 
					+ templateType.getMEMORY()
					,StatusCodes.ONE_FAILURE);
		}
		newTemplate.setName(templateType.getNAME());

		OCAOsWrapper newOs = new OCAOsWrapper();
		OsType osType = templateType.getOS();
		
		newOs.setArch(osType.getARCH());
		newOs.setBoot(osType.getBOOT());
		newOs.setBootloader(osType.getBOOTLOADER());
		newOs.setInitrd(osType.getINITRD());
		newOs.setKernel(osType.getKERNEL());
		newOs.setKernelCmd(osType.getKERNELCMD());
		newOs.setRoot(osType.getROOT());
		
		newTemplate.setOs(newOs);
		
		Map<String, OCADiskWrapper> diskMap = new HashMap<String, OCADiskWrapper>();
		
		for(DiskType disk: templateType.getDISK()) {
			OCADiskWrapper newDisk = new OCADiskWrapper();

			newDisk.setReadonly(disk.getREADONLY());
			try {
				newDisk.setSize(Long.parseLong(disk.getSIZE()));
			}
			catch(NumberFormatException nfe) {
				throw new DRPOneException("oned replied with an invalid disk size " 
						+ disk.getSIZE()
						,StatusCodes.ONE_FAILURE);
			}
			newDisk.setSource(disk.getSOURCE());
			newDisk.setTgarget(disk.getTARGET());
			newDisk.setType(disk.getTYPE());
			String tmpId = disk.getDISKID(); 
			newDisk.setDiskId(tmpId);
			
			diskMap.put(tmpId, newDisk);
			tmpId = null;
		}
		newTemplate.setDisks(diskMap);
		
		Map<String, OCANicWrapper> nicMap = new HashMap<String, OCANicWrapper>();
		int nicNumber=0;
		for(NicType nic: templateType.getNIC()) {
			OCANicWrapper newNic = new OCANicWrapper();
			
			newNic.setBridge(nic.getBRIDGE());
			newNic.setIp(nic.getIP());
			newNic.setNetwork(nic.getNETWORK());
			
			String tmpId = nic.getNETWORKID();
			
			if (tmpId == null)
				tmpId = "AUTOID_"+nicNumber;
			
			newNic.setNetworkId(tmpId);
			
			nicMap.put(tmpId, newNic);
			tmpId=null;
		}
		newTemplate.setNics(nicMap);
		
		rv.setTemplate(newTemplate);
		rv.setHistory(vmSimple.getHISTORY());

		return rv;
	}


	public static OCAWrapper parse(String xml) throws JAXBException {
		return parse(new ByteArrayInputStream(xml.getBytes()));
	}

	public static OCAWrapper parse(InputStream is) throws JAXBException {
		return create( 
			(VMSIMPLE) ((JAXBElement<?>) u.unmarshal(is)).getValue());

	}

}
