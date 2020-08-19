
package com.satc.pit;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.satc.pit package. 
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

    private final static QName _ConsultaConductorResponse_QNAME = new QName("http://pit.satc.com/", "consultaConductorResponse");
    private final static QName _ConsultaVehiculoResponse_QNAME = new QName("http://pit.satc.com/", "consultaVehiculoResponse");
    private final static QName _ConsultaVehiculo_QNAME = new QName("http://pit.satc.com/", "consultaVehiculo");
    private final static QName _ConsultaConductor_QNAME = new QName("http://pit.satc.com/", "consultaConductor");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.satc.pit
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConsultaVehiculoResponse }
     * 
     */
    public ConsultaVehiculoResponse createConsultaVehiculoResponse() {
        return new ConsultaVehiculoResponse();
    }

    /**
     * Create an instance of {@link ConsultaConductorResponse }
     * 
     */
    public ConsultaConductorResponse createConsultaConductorResponse() {
        return new ConsultaConductorResponse();
    }

    /**
     * Create an instance of {@link ConsultaVehiculo }
     * 
     */
    public ConsultaVehiculo createConsultaVehiculo() {
        return new ConsultaVehiculo();
    }

    /**
     * Create an instance of {@link ConsultaConductor }
     * 
     */
    public ConsultaConductor createConsultaConductor() {
        return new ConsultaConductor();
    }

    /**
     * Create an instance of {@link RecordConductor }
     * 
     */
    public RecordConductor createRecordConductor() {
        return new RecordConductor();
    }

    /**
     * Create an instance of {@link RecordVehiculo }
     * 
     */
    public RecordVehiculo createRecordVehiculo() {
        return new RecordVehiculo();
    }

    /**
     * Create an instance of {@link ArrayList }
     * 
     */
    public ArrayList createArrayList() {
        return new ArrayList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaConductorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pit.satc.com/", name = "consultaConductorResponse")
    public JAXBElement<ConsultaConductorResponse> createConsultaConductorResponse(ConsultaConductorResponse value) {
        return new JAXBElement<ConsultaConductorResponse>(_ConsultaConductorResponse_QNAME, ConsultaConductorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaVehiculoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pit.satc.com/", name = "consultaVehiculoResponse")
    public JAXBElement<ConsultaVehiculoResponse> createConsultaVehiculoResponse(ConsultaVehiculoResponse value) {
        return new JAXBElement<ConsultaVehiculoResponse>(_ConsultaVehiculoResponse_QNAME, ConsultaVehiculoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaVehiculo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pit.satc.com/", name = "consultaVehiculo")
    public JAXBElement<ConsultaVehiculo> createConsultaVehiculo(ConsultaVehiculo value) {
        return new JAXBElement<ConsultaVehiculo>(_ConsultaVehiculo_QNAME, ConsultaVehiculo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaConductor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pit.satc.com/", name = "consultaConductor")
    public JAXBElement<ConsultaConductor> createConsultaConductor(ConsultaConductor value) {
        return new JAXBElement<ConsultaConductor>(_ConsultaConductor_QNAME, ConsultaConductor.class, null, value);
    }

}
