package net.emotivecloud.utils.oca;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.util.logging.Logger;
import java.io.ByteArrayInputStream;
import javax.xml.bind.JAXBElement;

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
        log = Logger.getLogger(OVFWrapperFactory.class.getName());
        try {
            jbc = JAXBContext.newInstance(EnvelopeType.class);
            u = jbc.createUnmarshaller();
        } catch (JAXBException e) {
            log.severe(e.getMessage());
        }
    }

	public static OCAWrapper create (OCAWrapper ) {
		
	}
	
			
	public static VMSIMPLE parse(String xml) throws JAXBException{
		return parse(new ByteArrayInputStream(xml.getBytes()));
	}
	
	public static VMSIMPLE parse(InputStream is) throws JAXBException {
		return create( 
			(OCAWrapper) ((JAXBElement) u.unmarshal(is)).getValue());
		
	}
	
}
