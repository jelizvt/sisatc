
package pe.gob.sunarp.pide.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for asientosBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="asientosBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idImgAsiento" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="numPag" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listPag" type="{http://controller.pide.sunarp.gob.pe/}numeroPaginasBean" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "asientosBean", propOrder = {
    "idImgAsiento",
    "numPag",
    "tipo",
    "listPag"
})
public class AsientosBean {

    protected BigDecimal idImgAsiento;
    protected BigDecimal numPag;
    protected String tipo;
    @XmlElement(nillable = true)
    protected List<NumeroPaginasBean> listPag;

    /**
     * Gets the value of the idImgAsiento property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIdImgAsiento() {
        return idImgAsiento;
    }

    /**
     * Sets the value of the idImgAsiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIdImgAsiento(BigDecimal value) {
        this.idImgAsiento = value;
    }

    /**
     * Gets the value of the numPag property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNumPag() {
        return numPag;
    }

    /**
     * Sets the value of the numPag property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNumPag(BigDecimal value) {
        this.numPag = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the listPag property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listPag property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListPag().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NumeroPaginasBean }
     * 
     * 
     */
    public List<NumeroPaginasBean> getListPag() {
        if (listPag == null) {
            listPag = new ArrayList<NumeroPaginasBean>();
        }
        return this.listPag;
    }

}
