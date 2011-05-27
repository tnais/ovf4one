/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.emotivecloud.utils.ovf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dmtf.schemas.ovf.envelope._1.ProductSectionType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Parses a TCloud's InstantiateOVFParams XML file. Extracts its OVF EnvelopeType
 * and attaches the IPs defined in the Aspect with name="IP Config" to the
 * OVFNetwork data.
 *
 * @author Mario Macías: mario.macias@bsc.es
 */
public class TCloudParser {
    private static final String ENVELOPE_NS = "http://schemas.dmtf.org/ovf/envelope/1";
    private static final String ENVELOPE_NN = "Envelope";
//    private static final String ASPECT_NN = "Aspect";
    private static final String ASPECT_IP_NAME = "IP Config";
    private static final String PROPERTY_NN = "Property";

    /**
     * Creates an OVFWrapper object from an InputStream to a XML text
     * @param input
     * @return The create OVFWrapper
     * @throws TCloudException
     */
    public static OVFWrapper parse(InputStream input) throws TCloudException {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);

            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(input);
            doc.getDocumentElement().normalize();
            if(!doc.getDocumentElement().getNodeName().equals("InstantiateOvfParams")) {
                throw new TCloudException("Root element must be an <InstantiateOvfParams/> Element");
            }

            NodeList cn = doc.getElementsByTagNameNS(ENVELOPE_NS, ENVELOPE_NN);

            if(cn.getLength() != 1) {
                throw new TCloudException("There must be ONE and ONLY ONE Envelope. Actually there are " + cn.getLength());
            }
            //extract the envelope and create an OVFWrapper from it
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Source xmlSource = new DOMSource(cn.item(0));
            Result outputTarget = new StreamResult(outputStream);
            TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
            InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
            
            OVFWrapper ovf = OVFWrapperFactory.parse(is);

            List<Object> psProperties = null;

            //search for IP addresses within the <AspectsSection>
            NodeList asp = doc.getElementsByTagName("Aspect");
            for(int n = 0 ; n < asp.getLength() ; n++) {
                Node aspect = asp.item(n);
                Node nameAttr = aspect.getAttributes().getNamedItem("name");
                if(nameAttr != null && nameAttr.getNodeValue().trim().equalsIgnoreCase(ASPECT_IP_NAME)) {
                    NodeList properties = aspect.getOwnerDocument().getElementsByTagName(PROPERTY_NN);
                    for(int p = 0 ; p < properties.getLength() ; p++) {
                        Node property = properties.item(p);
                        String key = ((Element)property).getElementsByTagName("Key").item(0).getTextContent().trim();
                        String value = ((Element)property).getElementsByTagName("Value").item(0).getTextContent().trim();

                        int cut = key.lastIndexOf('.') + 1;
                        String networkName = key.substring(cut);
                        OVFNetwork net = ovf.getNetworks().get(networkName);
                        if(net == null) {
                            throw new TCloudException("Network " + networkName + " does not exist."
                                    + " Complete property key: " + key);
                        }
                        ProductSectionType.Property psp = new ProductSectionType.Property();
                        psp.setKey(OVFWrapperFactory.NETWORK_PROPERTY_PREFIX+networkName);
                        psp.setType("string");
                        psp.setValueAttribute(value);
                        if(psProperties == null) {
                            psProperties = OVFWrapperFactory.getEmotiveProductSection(ovf.envelope,true).getCategoryOrProperty();
                        }
                        psProperties.add(psp);

                        net.setIp(value);
                    }
                }
            }

            return ovf;
        } catch (JAXBException ex) {
            throw new TCloudException(ex);
        } catch (OVFException ex) {
            throw new TCloudException(ex);
        } catch (TransformerException ex) {
            throw new TCloudException(ex);
        } catch (ParserConfigurationException ex) {
            throw new TCloudException(ex);
        } catch (SAXException ex) {
            throw new TCloudException(ex);
        } catch (IOException ex) {
            throw new TCloudException(ex);
        }
    }

}
