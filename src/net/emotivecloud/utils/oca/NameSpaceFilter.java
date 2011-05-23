package net.emotivecloud.utils.oca;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.XMLFilterImpl;

/**
 * Class <code>NameSpaceFilter</code> A filter that adds the namespace
 * information to the XML being parsed, allowing us to parse the oned
 * reply for the getCompute() method. 
 *
 * Got this class here:
 * http://stackoverflow.com/questions/277502/jaxb-how-to-ignore-namespace-during-unmarshalling-xml-document
 * and included in the project without change. Assuming the code is in
 * the public domain due the lack of copyright notices.
 *
 * @author <a href="src/net/emotivecloud/utils/oca/NameSpaceFilter.java">Kristofer</a>
 * @version $Revision$
 */
public class NameSpaceFilter extends XMLFilterImpl {

    private String usedNamespaceUri;
    private boolean addNamespace;

    //State variable
    private boolean addedNamespace = false;

    public NameSpaceFilter(String namespaceUri,
            boolean addNamespace) {
        super();

        if (addNamespace)
            this.usedNamespaceUri = namespaceUri;
        else 
            this.usedNamespaceUri = "";
        this.addNamespace = addNamespace;
    }



    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        if (addNamespace) {
            startControlledPrefixMapping();
        }
    }



    @Override
    public void startElement(String arg0, String arg1, String arg2,
            Attributes arg3) throws SAXException {

        super.startElement(this.usedNamespaceUri, arg1, arg2, arg3);
    }

    @Override
    public void endElement(String arg0, String arg1, String arg2)
            throws SAXException {

        super.endElement(this.usedNamespaceUri, arg1, arg2);
    }

    @Override
    public void startPrefixMapping(String prefix, String url)
            throws SAXException {


        if (addNamespace) {
            this.startControlledPrefixMapping();
        } else {
            //Remove the namespace, i.e. don´t call startPrefixMapping for parent!
        }

    }

    private void startControlledPrefixMapping() throws SAXException {

        if (this.addNamespace && !this.addedNamespace) {
            //We should add namespace since it is set and has not yet been done.
            super.startPrefixMapping("", this.usedNamespaceUri);

            //Make sure we dont do it twice
            this.addedNamespace = true;
        }
    }

}