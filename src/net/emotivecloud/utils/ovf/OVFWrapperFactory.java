/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.emotivecloud.utils.ovf;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.dmtf.schemas.ovf.envelope._1.ContentType;
import org.dmtf.schemas.ovf.envelope._1.DiskSectionType;
import org.dmtf.schemas.ovf.envelope._1.EnvelopeType;
import org.dmtf.schemas.ovf.envelope._1.FileType;
import org.dmtf.schemas.ovf.envelope._1.MsgType;
import org.dmtf.schemas.ovf.envelope._1.NetworkSectionType;
import org.dmtf.schemas.ovf.envelope._1.ProductSectionType;
import org.dmtf.schemas.ovf.envelope._1.RASDType;
import org.dmtf.schemas.ovf.envelope._1.ReferencesType;
import org.dmtf.schemas.ovf.envelope._1.SectionType;
import org.dmtf.schemas.ovf.envelope._1.VirtualDiskDescType;
import org.dmtf.schemas.ovf.envelope._1.VirtualHardwareSectionType;
import org.dmtf.schemas.ovf.envelope._1.VirtualSystemCollectionType;
import org.dmtf.schemas.ovf.envelope._1.VirtualSystemType;
import org.dmtf.schemas.wbem.wscim._1.cim_schema._2.cim_resourceallocationsettingdata.ResourceType;
import org.dmtf.schemas.wbem.wscim._1.common.CimBoolean;

/**
 * This class creates an OVFWrapper from a subset of the OVF format with the next limitations:
 * <ul>
 *    <li>Only a single virtual machine is handled</li>
 *    <li>References/File Element: only attributes href, id and size are supported</li>
 *    <li>DiskSection/Disk Element: only attributes capacity, diskId and fileRef are supported</li>
 *    <li>RASDType (Item element): Parent subelement not supported</li>
 *    <li>ProductSection properties: assuming that all the properties are type="string"</li>
 *    <li>Only one ProductSection is allowed in each VirtualSystem. Many ProductSections in the Envelope</li>
 *    <li>TODO: add more</li>
 * </ul>
 * <a href="http://docs.redhat.com/docs/en-US/Red_Hat_Enterprise_Virtualization_for_Servers/2.2/html/Administration_Guide/Content.html">
 * Very useful reference</a>
 * @author Mario Macias: mario.macias@bsc.es
 * @author Javier Alvarez: javier.alvarez@bsc.es
 */
public class OVFWrapperFactory {
    protected static final String NETWORK_PROPERTY_PREFIX = "network.ip.";
    protected static final String PRODUCTSECTION_EMOTIVE_CLASS = "net.emotivecloud.utils.ovf";
    protected static final String PRODUCTSECTION_APPLICATION_CLASS = "com.sun.master";

    private static final String URL_DISK = "ovf://disk/";
    private static final String URL_FILE = "ovf://file/";
    
    protected static final QName QNAME_PRODUCTSECTION = new QName("http://schemas.dmtf.org/ovf/envelope/1", "ProductSection");
    protected static final QName QNAME_DISKSECTION = new QName("http://schemas.dmtf.org/ovf/envelope/1", "DiskSection");
    protected static final QName QNAME_NETWORKSECTION = new QName("http://schemas.dmtf.org/ovf/envelope/1", "NetworkSection");
    protected static final QName QNAME_VIRTUALSYSTEM = new QName("http://schemas.dmtf.org/ovf/envelope/1", "VirtualSystem");
    protected static final QName QNAME_VIRTUALHARDWARESECTION = new QName("http://schemas.dmtf.org/ovf/envelope/1", "VirtualHardwareSection");

    private static final int RESTYPE_CPU = 3;
    private static final int RESTYPE_MEMORY = 4;
    private static final int RESTYPE_NETWORK = 10;
    private static final int RESTYPE_DISK = 17;

    private static JAXBContext jbc = null;
    private static Unmarshaller u = null;
    private static Logger log;
    static {
        log = Logger.getLogger(OVFWrapperFactory.class.getName());
        try {
            jbc = JAXBContext.newInstance(EnvelopeType.class);
            u = jbc.createUnmarshaller();
        } catch (JAXBException e) {
            log.severe(e.getMessage());
        }
    }

    /**
     * Creates an OVFWrapper by specifying directly their attributes.
     * @param id Identifier of the VM to be created
     * @param CPUs Number of CPUs
     * @param memoryMB Memory in MegaBytes
     * @param disks A list with all the disks information
     * @param networks A list with all the networks information
     * @param productProperties A map with the product (generally the application) properties
     * @return
     */
    public static OVFWrapper create(
                    String id,
                    int CPUs,
                    int memoryMB,
                    OVFDisk[] disks,
                    OVFNetwork[] networks,
                    Map<String,String> productProperties

            ) {
        EnvelopeType envelope = new EnvelopeType();

        // References skeleton
        ReferencesType references = new ReferencesType();
        envelope.setReferences(references);
        // Disk section skeleton
        DiskSectionType diskSection = new DiskSectionType();
        envelope.getSection().add(new JAXBElement<DiskSectionType>(QNAME_DISKSECTION,DiskSectionType.class,diskSection));
        //Network section skeleton
        NetworkSectionType networkSection = new NetworkSectionType();
        envelope.getSection().add(new JAXBElement<NetworkSectionType>(QNAME_NETWORKSECTION,NetworkSectionType.class,networkSection));
        //Product section (of the vm, outside the virtual system) skeleton
        ProductSectionType vmConfig = new ProductSectionType();
        vmConfig.setInfo(OVFAux.getMsg("Some configuration information for the VM (e.g. IP addresses)"));
        vmConfig.setClazz(PRODUCTSECTION_EMOTIVE_CLASS);
        envelope.getSection().add(new JAXBElement<ProductSectionType>(QNAME_PRODUCTSECTION,ProductSectionType.class,vmConfig));
        //Virtual system skeleton
        VirtualSystemType virtualSystem = new VirtualSystemType();
        virtualSystem.setId(id);
        virtualSystem.setInfo(OVFAux.getMsg("VM description"));
        envelope.setContent(new JAXBElement<VirtualSystemType>(QNAME_VIRTUALSYSTEM, VirtualSystemType.class, virtualSystem));
        //VirtualSystem/VirtualHardware
        VirtualHardwareSectionType virtualHardware = new VirtualHardwareSectionType();
        virtualHardware.setInfo(OVFAux.getMsg("Virtual Hardware requirements"));
        virtualSystem.getSection().add(new JAXBElement<VirtualHardwareSectionType>(QNAME_VIRTUALHARDWARESECTION,VirtualHardwareSectionType.class,virtualHardware));

        int instanceId = 0;
        //virtualSystem/VirtualHardware/RASDTypes
        RASDType item = new RASDType();
        item.setDescription(OVFAux.cimConvert("Number of Virtual CPUs"));
        item.setElementName(OVFAux.cimConvert(CPUs+ " virtual CPU"));
        item.setInstanceID(OVFAux.cimConvert(new Integer(instanceId++).toString()));
        ResourceType resType = new ResourceType();
        resType.setValue(new Integer(RESTYPE_CPU).toString());
        item.setResourceType(resType);
        item.setVirtualQuantity(OVFAux.cimConvert(CPUs));
        virtualHardware.getItem().add(item);

        item = new RASDType();
        item.setAllocationUnits(OVFAux.cimConvert("MegaBytes"));
        item.setDescription(OVFAux.cimConvert("Memory Size"));
        item.setElementName(OVFAux.cimConvert(memoryMB+ " MB of Memory"));
        item.setInstanceID(OVFAux.cimConvert(new Integer(instanceId++).toString()));
        resType = new ResourceType();
        resType.setValue(new Integer(RESTYPE_MEMORY).toString());
        item.setResourceType(resType);
        item.setVirtualQuantity(OVFAux.cimConvert(memoryMB));
        virtualHardware.getItem().add(item);

        CimBoolean cimTrue = new CimBoolean();
        cimTrue.setValue(true);
        List<Object> cop = vmConfig.getCategoryOrProperty();
        for(OVFNetwork n : networks) {
            if(n.getIp() != null) {
                ProductSectionType.Property p = new ProductSectionType.Property();
                p.setKey(NETWORK_PROPERTY_PREFIX+n.getConnectionName());
                p.setType("string");
                p.setValueAttribute(n.getIp());
                vmConfig.getCategoryOrProperty().add(p);
            }

            item = new RASDType();
            item.setAutomaticAllocation(cimTrue);
            item.getConnection().add(OVFAux.cimConvert(n.getConnectionName()));
            item.setElementName(OVFAux.cimConvert("Ethernet adapter on " + n.getConnectionName() + " network"));
            item.setInstanceID(OVFAux.cimConvert(new Integer(instanceId++).toString()));
            resType = new ResourceType();
            resType.setValue(new Integer(RESTYPE_NETWORK).toString());
            item.setResourceType(resType);
            if (n.getMac() != null) {
                item.setAddress(OVFAux.cimConvert(n.getMac()));
            }
            virtualHardware.getItem().add(item);
        }

        int hardDiskNumber = 1;
        for(OVFDisk d : disks) {
            String idPath = URL_DISK;
            VirtualDiskDescType disk = new VirtualDiskDescType();
            if(d.getHref() != null) {
                FileType file = new FileType();
                file.setId(d.getId());                
                file.setHref(d.getHref());
                references.getFile().add(file);
                if(d.getCapacityMB() == null) {
                    idPath = URL_FILE;
                }
                disk.setFileRef(d.getId());
            }
            disk.setDiskId(d.getId());
            if(d.getCapacityMB() != null) {
                disk.setCapacity(d.getCapacityMB().toString()+"MB");
            }
            if(idPath == URL_DISK) {
                diskSection.getDisk().add(disk);
            }

            item = new RASDType();
            item.setElementName(OVFAux.cimConvert("Hardisk " + (hardDiskNumber++)));
            item.getHostResource().add(OVFAux.cimConvert(idPath + d.getId()));
            item.setInstanceID(OVFAux.cimConvert(new Integer(instanceId++).toString()));
            resType = new ResourceType();
            resType.setValue(new Integer(RESTYPE_DISK).toString());
            item.setResourceType(resType);
            virtualHardware.getItem().add(item);
        }

        //Product properties
        if(productProperties != null && !productProperties.isEmpty()) {
            ProductSectionType productSection = new ProductSectionType();
            productSection.setInfo(OVFAux.getMsg("Product customization for the installed software"));
            for(Map.Entry<String,String> entry : productProperties.entrySet()) {
                ProductSectionType.Property property = new ProductSectionType.Property();
                property.setType("string");
                property.setKey(entry.getKey());
                property.setValueAttribute(entry.getValue());
                productSection.getCategoryOrProperty().add(property);
            }
            virtualSystem.getSection().add(new JAXBElement(QNAME_PRODUCTSECTION, ProductSectionType.class, productSection));
        }

        OVFWrapper ow = null;
        try {
            ow = create(envelope);
        } catch (OVFException ex) {
            Logger.getLogger(OVFWrapperFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ow;
    }


    /**
     * Creates an OVFWrapper from an EnvelopeType XML String
     * @param xml
     * @return The created OVFWrapper
     * @throws JAXBException, OVFException
     */
    public static OVFWrapper parse(String xml) throws JAXBException, OVFException {
        ByteArrayInputStream bis = new ByteArrayInputStream(xml.getBytes());
        EnvelopeType e = unmarshallEnvelope(bis);
        return create(e);
    }

    /**
     * Creates an OVFWrapper from an Input Stream that contains an EnvelopeType
     * @param src
     * @return The created OVFWrapper object
     * @throws JAXBException, OVFException
     */
    public static OVFWrapper parse(InputStream src) throws JAXBException, OVFException {
        EnvelopeType e = unmarshallEnvelope(src);
        return create(e);
    }

    /**
     * Creates an OVFWrapper from an EnvelopeType
     * @param envelope
     * @return
     * @throws OVFException
     */
    public static OVFWrapper create(EnvelopeType envelope) throws OVFException {
        OVFWrapper ovf = new OVFWrapper();
        ovf.envelope = envelope;
        // build basic attributes: id
        VirtualSystemType vs = (VirtualSystemType) ovf.envelope.getContent().getValue();
        ovf.id = vs.getId();


        //NetworkProperties
        Map<String,String> emotiveProperties = new TreeMap<String, String>();

        ProductSectionType nets = getEmotiveProductSection(envelope, false);
        if(nets != null) {
            List cops = nets.getCategoryOrProperty();
            for(Object o : cops) {
                if(o instanceof ProductSectionType.Property) {
                    ProductSectionType.Property p = (ProductSectionType.Property) o;
                    emotiveProperties.put(p.getKey(),p.getValueAttribute());
                }
            }
        }

        //Product properties
        ProductSectionType ps = (ProductSectionType) OVFAux.findSection(vs.getSection(), ProductSectionType.class);
        if(ps != null) {
            List<Object> cops = ps.getCategoryOrProperty();
            for(Object o : cops) {
                if(o instanceof ProductSectionType.Property) {
                    ProductSectionType.Property p = (ProductSectionType.Property) o;
                    ovf.productProperties.put(p.getKey(),p.getValueAttribute());
                }
            }
        }

        //Virtual hardware items
        VirtualHardwareSectionType vh = (VirtualHardwareSectionType) 
                OVFAux.findSection(vs.getSection(), VirtualHardwareSectionType.class);
        List<RASDType> items = vh.getItem();
        for(RASDType i : items) {
        	assert i.getResourceType().getValue() instanceof String;
            switch(Integer.parseInt(((String) i.getResourceType().getValue()).trim())) {
                case RESTYPE_CPU:
                    ovf.CPUs = i.getVirtualQuantity().getValue().intValue();
                    break;
                case RESTYPE_MEMORY:
                    String au = i.getAllocationUnits() == null ? null :  i.getAllocationUnits().toString().trim().toLowerCase();
                    int mul = 1, div = 1;
                    if(au.startsWith("gigabyte")) {
                        mul = 1024;
                    } else if(au.startsWith("kilobyte")) {
                        div = 1024;
                    } else if(au.startsWith("byte")) {
                        div = 1024*1024;
                    }
                    ovf.memoryMB = i.getVirtualQuantity().getValue().multiply(BigInteger.valueOf(mul)).divide(BigInteger.valueOf(div)).intValue();
                    break;
                case RESTYPE_DISK:
                    OVFDisk d = createDisk(ovf,i);
                    ovf.disks.put(d.getId(), d);
                    break;
                case RESTYPE_NETWORK:
                    OVFNetwork n = createNetwork(i);
                    String ip = emotiveProperties.get(NETWORK_PROPERTY_PREFIX+n.getConnectionName());
                    if(ip != null) {
                        n.setIp(ip);
                    }
                    ovf.networks.put(n.getConnectionName(),n);
                    break;
            }
        }
        return ovf;
    }

    protected static ProductSectionType getEmotiveProductSection(EnvelopeType env, boolean create) {
        List<SectionType> sections = OVFAux.findSections(env.getSection(),ProductSectionType.class);
        ProductSectionType ps = null;
        for(SectionType s : sections) {
            if(s instanceof ProductSectionType && ((ProductSectionType)s).getClazz().equals(PRODUCTSECTION_EMOTIVE_CLASS)) {
                ps = (ProductSectionType) s;
            }
        }

        if(ps == null && create) {
            ps = new ProductSectionType();
            ps.setClazz(PRODUCTSECTION_EMOTIVE_CLASS);
            MsgType psInfo = new MsgType();
            psInfo.setValue("Some configuration information for the VM (e.g. IP addresses)");
            ps.setInfo(psInfo);
            env.getSection().add(new JAXBElement<ProductSectionType>(
                    QNAME_PRODUCTSECTION,
                    ProductSectionType.class,
                    ps
                    ));
        }
        return ps;
    }

    private static OVFNetwork createNetwork(RASDType item) throws OVFException {
        OVFNetwork n = new OVFNetwork();
        if(item.getConnection() != null && item.getConnection().get(0) != null) {
            n.setConnectionName(item.getConnection().get(0).getValue().trim());
        }
        if(item.getAddress() != null) {
            n.setMac(item.getAddress().getValue().trim());
        }
        return n;
    }
    
    private static OVFDisk createDisk(OVFWrapper ovf, RASDType item) throws OVFException {
        String hostResource = item.getHostResource().get(0).getValue().trim();
        String id = null;
        String href = null;
        Long sizeMB = null;
        List<FileType> files = ovf.envelope.getReferences().getFile();
        if(hostResource.startsWith(URL_DISK)) {
            id = hostResource.substring(URL_DISK.length());
            String fileRef = null;            
            Long capacityMB = null;
            List<VirtualDiskDescType> vdisk = ((DiskSectionType)OVFAux.findSection(ovf.envelope.getSection(), DiskSectionType.class)).getDisk();
            for(VirtualDiskDescType d : vdisk) {
                if(d.getDiskId().trim().equals(id)) {
                    fileRef = d.getFileRef();

                    if(d.getCapacity() != null) {
                        String c = d.getCapacity().trim().toLowerCase();
                        if(c.endsWith("mb")) {
                            capacityMB = new Long(c.substring(0, c.length() - 2).trim());
                        } else if(c.endsWith("gb")) {
                            capacityMB = new Long(c.substring(0, c.length() - 2).trim()) * 1024;
                        } else {
                            capacityMB = new Long(c.substring(0, c.length()).trim()) / (1024 * 1024);
                        }
                    }
                }
            }

            /////////////////
            if(fileRef != null) {
                boolean found = false;
                for(FileType f : files) {
                    if(f.getId().equals(fileRef)) {
                        found = true;
                        href = f.getHref();
                        if(href == null) {
                            log.warning("Adding file reference '" + fileRef + "' without href field");
                        }
                        if(f.getSize() != null) {
                            sizeMB = f.getSize().divide(BigInteger.valueOf(1024*1024)).longValue();
                        }
                    }
                }
                if(!found) {
                    throw new OVFException(hostResource + " references to file identifier '"+id+"' that does not exist.");
                } else {
                    if(sizeMB != null && capacityMB != null && Math.abs(sizeMB - capacityMB) > 1) {
                        log.warning("File/size and Disk/capacity values are different");
                    } else if(sizeMB == null && capacityMB != null) {
                        sizeMB = capacityMB;
                    }
                }
            }
            //////////////

        } else if(hostResource.startsWith(URL_FILE)) {
            id = hostResource.substring(URL_FILE.length());
            boolean found = false;
            for(FileType f : files) {
                if(f.getId().equals(id)) {
                    found = true;
                    href = f.getHref();
                    if(href == null) {
                        log.warning("Adding file reference '" + id + "' without href field");
                    }
                    if(f.getSize() != null) {
                        sizeMB = f.getSize().divide(BigInteger.valueOf(1024*1024)).longValue();
                    }
                }
            }
            if(!found) {
                throw new OVFException("file identifier '"+id+"' does not exist.");
            }
        } else {
            log.warning("Contents on <HostResource> element may be wrong in the RASD Item with ElementName = " + item.getElementName().getValue());
        }
        return new OVFDisk(id, href, sizeMB);
    }
    
    public static List<EnvelopeType> splitOvf (EnvelopeType envelope) throws OVFException {
	List<EnvelopeType> envelopes = new LinkedList<EnvelopeType>();
	
	JAXBElement<? extends ContentType> jbContent = envelope.getContent();

	if (jbContent == null)
	    throw new OVFException("No content inside Envelope");

	ContentType content = jbContent.getValue();
	
	if (content instanceof VirtualSystemType) {
	    envelopes.add(envelope);
	    
	} else if (content instanceof VirtualSystemCollectionType) {
	    List<JAXBElement<? extends SectionType>> sections = envelope.getSection();
	    ReferencesType references = envelope.getReferences();
	    VirtualSystemCollectionType vsCollection = (VirtualSystemCollectionType) content;
	    List<JAXBElement<? extends ContentType>> vsystems = vsCollection.getContent();
	    
	    if (vsystems == null) 
		throw new OVFException("No content inside VirtualSystemCollection");
	    
	    Iterator<JAXBElement<? extends ContentType>> it = vsystems.iterator();	    
	    EnvelopeType envelopeNew;
	    
	    while (it.hasNext()) {		
		envelopeNew = new EnvelopeType();
		envelopeNew.setContent(it.next());
		envelopeNew.getSection().addAll(sections);
		envelopeNew.setReferences(references);
		envelopes.add(envelopeNew);
	    }
	}
	return envelopes;
    }      
    
    private static EnvelopeType unmarshallEnvelope(InputStream src) throws JAXBException {
        return (EnvelopeType) ((JAXBElement) u.unmarshal(src)).getValue();
    }
}
