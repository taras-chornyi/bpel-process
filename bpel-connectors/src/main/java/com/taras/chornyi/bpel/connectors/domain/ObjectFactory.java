//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.07.03 at 02:43:37 PM EEST 
//


package com.taras.chornyi.bpel.connectors.domain;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.taras.chornyi.bpel.connectors.domain package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.taras.chornyi.bpel.connectors.domain
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BusinessProcess }
     * 
     */
    public BusinessProcess createBusinessProcess() {
        return new BusinessProcess();
    }

    /**
     * Create an instance of {@link BusinessProcess.Attributes }
     * 
     */
    public BusinessProcess.Attributes createBusinessProcessAttributes() {
        return new BusinessProcess.Attributes();
    }

    /**
     * Create an instance of {@link BusinessProcess.Attributes.Attribute }
     * 
     */
    public BusinessProcess.Attributes.Attribute createBusinessProcessAttributesAttribute() {
        return new BusinessProcess.Attributes.Attribute();
    }

}
