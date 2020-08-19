
package pe.gob.sunarp.pide.controller;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaPartidaBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaPartidaBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transaccion" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="nroTotalPag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listAsientos" type="{http://controller.pide.sunarp.gob.pe/}asientosBean" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="listFichas" type="{http://controller.pide.sunarp.gob.pe/}fichaBean" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="listFolios" type="{http://controller.pide.sunarp.gob.pe/}tomoFolioBean" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaPartidaBean", propOrder = {
    "transaccion",
    "nroTotalPag",
    "listAsientos",
    "listFichas",
    "listFolios"
})
public class RespuestaPartidaBean {

    protected long transaccion;
    protected String nroTotalPag;
    @XmlElement(nillable = true)
    protected List<AsientosBean> listAsientos;
    @XmlElement(nillable = true)
    protected List<FichaBean> listFichas;
    @XmlElement(nillable = true)
    protected List<TomoFolioBean> listFolios;

    /**
     * Gets the value of the transaccion property.
     * 
     */
    public long getTransaccion() {
        return transaccion;
    }

    /**
     * Sets the value of the transaccion property.
     * 
     */
    public void setTransaccion(long value) {
        this.transaccion = value;
    }

    /**
     * Gets the value of the nroTotalPag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNroTotalPag() {
        return nroTotalPag;
    }

    /**
     * Sets the value of the nroTotalPag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNroTotalPag(String value) {
        this.nroTotalPag = value;
    }

    /**
     * Gets the value of the listAsientos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listAsientos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListAsientos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AsientosBean }
     * 
     * 
     */
    public List<AsientosBean> getListAsientos() {
        if (listAsientos == null) {
            listAsientos = new ArrayList<AsientosBean>();
        }
        return this.listAsientos;
    }

    /**
     * Gets the value of the listFichas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listFichas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListFichas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FichaBean }
     * 
     * 
     */
    public List<FichaBean> getListFichas() {
        if (listFichas == null) {
            listFichas = new ArrayList<FichaBean>();
        }
        return this.listFichas;
    }

    /**
     * Gets the value of the listFolios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listFolios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListFolios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TomoFolioBean }
     * 
     * 
     */
    public List<TomoFolioBean> getListFolios() {
        if (listFolios == null) {
            listFolios = new ArrayList<TomoFolioBean>();
        }
        return this.listFolios;
    }

}
