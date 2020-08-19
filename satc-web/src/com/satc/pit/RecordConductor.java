
package com.satc.pit;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for recordConductor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recordConductor">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descuento" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="estadoPapeleta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estadoResolucion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaInfraccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="infraccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="infractor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="mensaje" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="montoMulta" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="nivelGravedad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nroPapeleta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroResolucion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="placa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reincidente" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="subTotal" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recordConductor", propOrder = {
    "descuento",
    "estadoPapeleta",
    "estadoResolucion",
    "fechaInfraccion",
    "infraccion",
    "infractor",
    "item",
    "mensaje",
    "montoMulta",
    "nivelGravedad",
    "nroPapeleta",
    "numeroResolucion",
    "placa",
    "reincidente",
    "subTotal"
})
@XmlSeeAlso({
    RecordVehiculo.class
})
public class RecordConductor {

    protected BigDecimal descuento;
    protected String estadoPapeleta;
    protected String estadoResolucion;
    protected String fechaInfraccion;
    protected String infraccion;
    protected String infractor;
    protected Integer item;
    protected String mensaje;
    protected BigDecimal montoMulta;
    protected String nivelGravedad;
    protected String nroPapeleta;
    protected String numeroResolucion;
    protected String placa;
    protected Integer reincidente;
    protected BigDecimal subTotal;

    /**
     * Gets the value of the descuento property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDescuento() {
        return descuento;
    }

    /**
     * Sets the value of the descuento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDescuento(BigDecimal value) {
        this.descuento = value;
    }

    /**
     * Gets the value of the estadoPapeleta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstadoPapeleta() {
        return estadoPapeleta;
    }

    /**
     * Sets the value of the estadoPapeleta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstadoPapeleta(String value) {
        this.estadoPapeleta = value;
    }

    /**
     * Gets the value of the estadoResolucion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstadoResolucion() {
        return estadoResolucion;
    }

    /**
     * Sets the value of the estadoResolucion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstadoResolucion(String value) {
        this.estadoResolucion = value;
    }

    /**
     * Gets the value of the fechaInfraccion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaInfraccion() {
        return fechaInfraccion;
    }

    /**
     * Sets the value of the fechaInfraccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaInfraccion(String value) {
        this.fechaInfraccion = value;
    }

    /**
     * Gets the value of the infraccion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfraccion() {
        return infraccion;
    }

    /**
     * Sets the value of the infraccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfraccion(String value) {
        this.infraccion = value;
    }

    /**
     * Gets the value of the infractor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfractor() {
        return infractor;
    }

    /**
     * Sets the value of the infractor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfractor(String value) {
        this.infractor = value;
    }

    /**
     * Gets the value of the item property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getItem() {
        return item;
    }

    /**
     * Sets the value of the item property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setItem(Integer value) {
        this.item = value;
    }

    /**
     * Gets the value of the mensaje property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Sets the value of the mensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensaje(String value) {
        this.mensaje = value;
    }

    /**
     * Gets the value of the montoMulta property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoMulta() {
        return montoMulta;
    }

    /**
     * Sets the value of the montoMulta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoMulta(BigDecimal value) {
        this.montoMulta = value;
    }

    /**
     * Gets the value of the nivelGravedad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNivelGravedad() {
        return nivelGravedad;
    }

    /**
     * Sets the value of the nivelGravedad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNivelGravedad(String value) {
        this.nivelGravedad = value;
    }

    /**
     * Gets the value of the nroPapeleta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNroPapeleta() {
        return nroPapeleta;
    }

    /**
     * Sets the value of the nroPapeleta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNroPapeleta(String value) {
        this.nroPapeleta = value;
    }

    /**
     * Gets the value of the numeroResolucion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    /**
     * Sets the value of the numeroResolucion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroResolucion(String value) {
        this.numeroResolucion = value;
    }

    /**
     * Gets the value of the placa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Sets the value of the placa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlaca(String value) {
        this.placa = value;
    }

    /**
     * Gets the value of the reincidente property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getReincidente() {
        return reincidente;
    }

    /**
     * Sets the value of the reincidente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReincidente(Integer value) {
        this.reincidente = value;
    }

    /**
     * Gets the value of the subTotal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSubTotal() {
        return subTotal;
    }

    /**
     * Sets the value of the subTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSubTotal(BigDecimal value) {
        this.subTotal = value;
    }

}
