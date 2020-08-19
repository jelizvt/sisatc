
package pe.gob.sunarp.pide.controller;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaOficinaRegistralBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaOficinaRegistralBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oficina" type="{http://controller.pide.sunarp.gob.pe/}oficinaRegistralBean" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaOficinaRegistralBean", propOrder = {
    "oficina"
})
public class RespuestaOficinaRegistralBean {

    @XmlElement(nillable = true)
    protected List<OficinaRegistralBean> oficina;

    /**
     * Gets the value of the oficina property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oficina property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOficina().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OficinaRegistralBean }
     * 
     * 
     */
    public List<OficinaRegistralBean> getOficina() {
        if (oficina == null) {
            oficina = new ArrayList<OficinaRegistralBean>();
        }
        return this.oficina;
    }

}
