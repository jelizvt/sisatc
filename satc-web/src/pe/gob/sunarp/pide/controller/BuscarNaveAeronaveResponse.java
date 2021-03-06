
package pe.gob.sunarp.pide.controller;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="respuestaNaveAeronave" type="{http://controller.pide.sunarp.gob.pe/}respuestaNaveAeronave" form="unqualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "respuestaNaveAeronave"
})
@XmlRootElement(name = "buscarNaveAeronaveResponse")
public class BuscarNaveAeronaveResponse {

    @XmlElement(required = true, nillable = true)
    protected RespuestaNaveAeronave respuestaNaveAeronave;

    /**
     * Gets the value of the respuestaNaveAeronave property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaNaveAeronave }
     *     
     */
    public RespuestaNaveAeronave getRespuestaNaveAeronave() {
        return respuestaNaveAeronave;
    }

    /**
     * Sets the value of the respuestaNaveAeronave property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaNaveAeronave }
     *     
     */
    public void setRespuestaNaveAeronave(RespuestaNaveAeronave value) {
        this.respuestaNaveAeronave = value;
    }

}
