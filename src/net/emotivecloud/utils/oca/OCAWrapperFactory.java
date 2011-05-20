package net.emotivecloud.utils.oca;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import org.opennebula.xmlschema.VMSIMPLE;
import javax.xml.bind.JAXBException;
import java.io.InputStream;


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
		return null;
	}


	public static OCAWrapper parse(String xml) throws JAXBException {
		return parse(new ByteArrayInputStream(xml.getBytes()));
	}

	public static OCAWrapper parse(InputStream is) throws JAXBException {
		return create( 
			(VMSIMPLE) ((JAXBElement) u.unmarshal(is)).getValue());

	}

}
