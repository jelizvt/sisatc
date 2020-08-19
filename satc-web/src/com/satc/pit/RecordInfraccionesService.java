package com.satc.pit;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.6
 * 2018-09-18T16:04:52.032-05:00
 * Generated source version: 2.4.6
 * 
 */
@WebServiceClient(name = "RecordInfraccionesService", 
                  wsdlLocation = "http://190.116.36.140:8888/sisatc-ws/RecordInfracciones?wsdl",
                  targetNamespace = "http://pit.satc.com/") 
public class RecordInfraccionesService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://pit.satc.com/", "RecordInfraccionesService");
    public final static QName RecordInfraccionesPort = new QName("http://pit.satc.com/", "RecordInfraccionesPort");
    static {
        URL url = null;
        try {
            url = new URL("http://190.116.36.140:8888/sisatc-ws/RecordInfracciones?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(RecordInfraccionesService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://190.116.36.140:8888/sisatc-ws/RecordInfracciones?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public RecordInfraccionesService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public RecordInfraccionesService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RecordInfraccionesService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public RecordInfraccionesService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public RecordInfraccionesService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public RecordInfraccionesService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns RecordInfractorRemote
     */
    @WebEndpoint(name = "RecordInfraccionesPort")
    public RecordInfractorRemote getRecordInfraccionesPort() {
        return super.getPort(RecordInfraccionesPort, RecordInfractorRemote.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns RecordInfractorRemote
     */
    @WebEndpoint(name = "RecordInfraccionesPort")
    public RecordInfractorRemote getRecordInfraccionesPort(WebServiceFeature... features) {
        return super.getPort(RecordInfraccionesPort, RecordInfractorRemote.class, features);
    }

}