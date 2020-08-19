
package pe.gob.sunarp.pide.controller;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaBusquedaPJBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaBusquedaPJBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resultado" type="{http://controller.pide.sunarp.gob.pe/}datosPJBean" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaBusquedaPJBean", propOrder = {
    "resultado"
})
public class RespuestaBusquedaPJBean {

    @XmlElement(nillable = true)
    protected List<DatosPJBean> resultado;

    /**
     * Gets the value of the resultado property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resultado property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResultado().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatosPJBean }
     * 
     * 
     */
    public List<DatosPJBean> getResultado() {
        if (resultado == null) {
            resultado = new ArrayList<DatosPJBean>();
        }
        return this.resultado;
    }

}
