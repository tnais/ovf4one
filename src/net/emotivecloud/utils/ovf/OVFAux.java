/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.emotivecloud.utils.ovf;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.dmtf.schemas.ovf.envelope._1.MsgType;
import org.dmtf.schemas.ovf.envelope._1.SectionType;
import org.dmtf.schemas.wbem.wscim._1.common.CimString;
import org.dmtf.schemas.wbem.wscim._1.common.CimUnsignedLong;

/**
 *
 * @author mmacias
 */
class OVFAux {
    public static CimString cimConvert(String str) {
        CimString s = new CimString();
        s.setValue(str);
        return s;
    }

    public static CimUnsignedLong cimConvert(long l) {
        CimUnsignedLong ul = new CimUnsignedLong();
        ul.setValue(BigInteger.valueOf(l));
        return ul;
    }

    public static List<SectionType> findSections(List<JAXBElement<? extends SectionType>> sections, Class<? extends SectionType> c) {
        List<SectionType> allSections = new ArrayList<SectionType>();
        for(JAXBElement<? extends SectionType> s : sections) {
            if(s.getDeclaredType().equals(c)) {
                allSections.add(s.getValue());
            }
        }
        return allSections;
    }

    public static SectionType findSection(List<JAXBElement<? extends SectionType>> sections, Class<? extends SectionType> c) {
        for(JAXBElement<? extends SectionType> s : sections) {
            if(s.getDeclaredType().equals(c)) {
                return s.getValue();
            }
        }
        
        return null;
    }

    public static MsgType getMsg(String value) {
        MsgType mt = new MsgType();
        mt.setValue(value);
        return mt;
    }
}
