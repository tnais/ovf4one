package net.emotivecloud.utils.oca;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;

import net.emotivecloud.scheduler.onedrp.DRPOneException;
import net.emotivecloud.scheduler.onedrp.StatusCodes;

import org.opennebula.xmlschema.DiskType;
import org.opennebula.xmlschema.NicType;
import org.opennebula.xmlschema.OsType;
import org.opennebula.xmlschema.TemplateType;
import org.opennebula.xmlschema.VMSIMPLE;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


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

		BigInteger tmp = vmSimple.getID();
		if( tmp != null )
			rv.setId( tmp.intValue() );

		tmp = vmSimple.getUID();
		if( tmp != null )
			rv.setUid( tmp.intValue() );

		rv.setName(vmSimple.getNAME());

		tmp = vmSimple.getLASTPOLL();
		if( tmp != null )
			rv.setLastPoll( tmp.intValue() );

		tmp = vmSimple.getSTATE();
		if( tmp != null )
			rv.setState( tmp.intValue() );

		tmp = vmSimple.getLCMSTATE();
		if( tmp != null )
			rv.setLcmState( tmp.intValue() );

		tmp = vmSimple.getSTIME();
		if( tmp != null )
			rv.setStime( tmp.intValue() );

		tmp = vmSimple.getETIME();
		if( tmp != null )
			rv.setEtime( tmp.intValue() );

		rv.setDeployId(vmSimple.getDEPLOYID());

		tmp = vmSimple.getMEMORY();
		if( tmp != null )
			rv.setMemory( tmp.intValue() );

		tmp = vmSimple.getCPU();
		if( tmp != null )
			rv.setCpu( tmp.intValue() );

		tmp = vmSimple.getNETTX();
		if( tmp != null )
			rv.setNetTx( tmp.intValue() );

		tmp = vmSimple.getNETRX();
		if( tmp != null )
			rv.setNetRx( tmp.intValue() );

		tmp = vmSimple.getLASTSEQ();
		if( tmp != null )
			rv.setLastSeq( tmp.intValue() );

		rv.setTemplate( templateHelper( vmSimple.getTEMPLATE() ) );

		rv.setHistory(vmSimple.getHISTORY());

		return rv;
	}

	// Almnost syntactic shugar, to prevent methods getting as long as
	// the Trans-Siberian railway :)
	private static OCATemplateWrapper templateHelper(TemplateType templateType) {

		OCATemplateWrapper newTemplate = new OCATemplateWrapper();

		if ( templateType == null )
			// This avoid one level of indentation of a block
			// statement that shoul be too long.
			// The Patiarchs would approve.
			return newTemplate;

		// assert templateType != null 

		// the contect pass throught uhharmed...
		newTemplate.setContext(templateType.getCONTEXT());

		BigInteger tmpNum = templateType.getCPU();
		if (tmpNum != null)
			newTemplate.setCpu( tmpNum.intValue() );


		String tmp = templateType.getMEMORY();

		if (tmp != null) // It's lile in python... indentation
			try {        // matters :)

				newTemplate.setMemory(Integer.parseInt(tmp));
			}
			catch(NumberFormatException nfe) {
				throw new DRPOneException("oned replied with an invalid memory size " 
										  + templateType.getMEMORY()
										  ,StatusCodes.ONE_FAILURE);
			}



		newTemplate.setName( templateType.getNAME() );

		newTemplate.setOs( osSubHelper(templateType.getOS()) );

		newTemplate.setDisks( disksSubHelper(templateType.getDISK()) );

		newTemplate.setNics( nicsSubHelper(templateType.getNIC()) );
		
		return newTemplate;

	}

	// An helper of temlateHelper
	// again, to prevent method bloating
	private static OCAOsWrapper osSubHelper(OsType osType) {
		OCAOsWrapper newOs = new OCAOsWrapper();

		if(osType != null) {
			newOs.setArch(osType.getARCH());
			newOs.setBoot(osType.getBOOT());
			newOs.setBootloader(osType.getBOOTLOADER());
			newOs.setInitrd(osType.getINITRD());
			newOs.setKernel(osType.getKERNEL());
			newOs.setKernelCmd(osType.getKERNELCMD());
			newOs.setRoot(osType.getROOT());
		}

		return newOs;
	}

	// Another helper of temlateHelper
	// again, to prevent method bloating
	private static Map<String, OCADiskWrapper> disksSubHelper(Collection<DiskType> templateDisks) {

		Map<String, OCADiskWrapper> diskMap = new HashMap<String, OCADiskWrapper>();

		if (templateDisks == null)
			// The other block is too long to use the
			// block statement, again we use a quick
			// bailout.
			return diskMap;

		int counter = 0;

		for(DiskType disk: templateDisks) {
			OCADiskWrapper newDisk = new OCADiskWrapper();

			newDisk.setReadonly(disk.getREADONLY());
			
			String tmp = disk.getSIZE();
			
			// If we don't suppy OpenNebula the disk size when we create the VM, we can't get
			// this information back
			if (tmp != null)
				try {
					newDisk.setSize(Long.parseLong(tmp));
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

			if(tmpId == null)
				tmpId = "_AutoId_4Disk__" + counter;

			counter++;

			newDisk.setDiskId(tmpId);

			diskMap.put(tmpId, newDisk);

		}

		return diskMap;
	}

	// One more helper of temlateHelper.
	// Always to prevent method bloating
	private static Map<String, OCANicWrapper> nicsSubHelper(Collection<NicType> templateNics) {
		Map<String, OCANicWrapper> nicMap = new HashMap<String, OCANicWrapper>();

		if( templateNics == null )
			// This avoid one level of indentation of a block
			// statement that shoul be too long.
			// The Patiarchs would approve!
			return nicMap;
		
		int nicNumber=0;

		for(NicType nic: templateNics) {
			OCANicWrapper newNic = new OCANicWrapper();

			newNic.setBridge(nic.getBRIDGE());
			newNic.setIp(nic.getIP());
			newNic.setNetwork(nic.getNETWORK());

			String tmpId = nic.getNETWORKID();

			if (tmpId == null)
				tmpId = "_AutoId_4Nic__"+nicNumber;

			nicNumber++;
			
			newNic.setNetworkId(tmpId);

			nicMap.put(tmpId, newNic);
			tmpId=null;
		}
		return nicMap;
	}
	
	public static OCAWrapper parse(String xml) throws JAXBException, SAXException {
		return parse(new ByteArrayInputStream(xml.getBytes()));
	}

	public static OCAWrapper parse(InputStream is) throws JAXBException, SAXException {
		XMLReader reader = XMLReaderFactory.createXMLReader();

		NameSpaceFilter inputFilter = new NameSpaceFilter("http://opennebula.org/XMLSchema",true);
		inputFilter.setParent(reader);

		// kudos to kristofer http://stackoverflow.com/users/259485/kristofer
		// in http://stackoverflow.com/questions/277502/jaxb-how-to-ignore-namespace-during-unmarshalling-xml-document
		VMSIMPLE tmp = (VMSIMPLE) (
				(JAXBElement<?>) u.unmarshal(
				new SAXSource(inputFilter, new InputSource(is)),VMSIMPLE.class)
				).getValue(); 
		return create( 
			tmp);

	}

}
