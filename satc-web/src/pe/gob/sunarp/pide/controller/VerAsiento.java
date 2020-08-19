
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
 *         &lt;element name="transaccion" type="{http://www.w3.org/2001/XMLSchema}long" form="unqualified"/>
 *         &lt;element name="idImg" type="{http://www.w3.org/2001/XMLSchema}long" form="unqualified"/>
 *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *         &lt;element name="nroTotalPag" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *         &lt;element name="nroPagRef" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *         &lt;element name="pagina" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
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
    "transaccion",
    "idImg",
    "tipo",
    "nroTotalPag",
    "nroPagRef",
    "pagina"
})
@XmlRootElement(name = "verAsiento")
public class VerAsiento {

    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long transaccion;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long idImg;
    @XmlElement(required = true, nillable = true)
    protected String tipo;
    @XmlElement(required = true, nillable = true)
    protected String nroTotalPag;
    @XmlElement(required = true, nillable = true)
    protected String nroPagRef;
    @XmlElement(required = true, nillable = true)
    protected String pagina;

    /**
     * Gets the value of the transaccion property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTransaccion() {
        return transaccion;
    }

    /**
     * Sets the value of the transaccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTransaccion(Long value) {
        this.transaccion = value;
    }

    /**
     * Gets the value of the idImg property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdImg() {
        return idImg;
    }

    /**
     * Sets the value of the idImg property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdImg(Long value) {
        this.idImg = value;
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
     * Gets the value of the nroPagRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNroPagRef() {
        return nroPagRef;
    }

    /**
     * Sets the value of the nroPagRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNroPagRef(String value) {
        this.nroPagRef = value;
    }

    /**
     * Gets the value of the pagina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPagina() {
        return pagina;
    }

    /**
     * Sets the value of the pagina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPagina(String value) {
        this.pagina = value;
    }

}
