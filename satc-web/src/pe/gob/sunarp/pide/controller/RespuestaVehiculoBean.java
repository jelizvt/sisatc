
package pe.gob.sunarp.pide.controller;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaVehiculoBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaVehiculoBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="placa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nro_motor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="color" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="marca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modelo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sede" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="propietarios" type="{http://controller.pide.sunarp.gob.pe/}propietarioBean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaVehiculoBean", propOrder = {
    "placa",
    "serie",
    "vin",
    "nroMotor",
    "color",
    "marca",
    "modelo",
    "estado",
    "sede",
    "propietarios"
})
public class RespuestaVehiculoBean {

    protected String placa;
    protected String serie;
    protected String vin;
    @XmlElement(name = "nro_motor")
    protected String nroMotor;
    protected String color;
    protected String marca;
    protected String modelo;
    protected String estado;
    protected String sede;
    protected PropietarioBean propietarios;

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
     * Gets the value of the serie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerie() {
        return serie;
    }

    /**
     * Sets the value of the serie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerie(String value) {
        this.serie = value;
    }

    /**
     * Gets the value of the vin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVin() {
        return vin;
    }

    /**
     * Sets the value of the vin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVin(String value) {
        this.vin = value;
    }

    /**
     * Gets the value of the nroMotor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNroMotor() {
        return nroMotor;
    }

    /**
     * Sets the value of the nroMotor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNroMotor(String value) {
        this.nroMotor = value;
    }

    /**
     * Gets the value of the color property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the value of the color property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColor(String value) {
        this.color = value;
    }

    /**
     * Gets the value of the marca property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Sets the value of the marca property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarca(String value) {
        this.marca = value;
    }

    /**
     * Gets the value of the modelo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Sets the value of the modelo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelo(String value) {
        this.modelo = value;
    }

    /**
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstado(String value) {
        this.estado = value;
    }

    /**
     * Gets the value of the sede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSede() {
        return sede;
    }

    /**
     * Sets the value of the sede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSede(String value) {
        this.sede = value;
    }

    /**
     * Gets the value of the propietarios property.
     * 
     * @return
     *     possible object is
     *     {@link PropietarioBean }
     *     
     */
    public PropietarioBean getPropietarios() {
        return propietarios;
    }

    /**
     * Sets the value of the propietarios property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropietarioBean }
     *     
     */
    public void setPropietarios(PropietarioBean value) {
        this.propietarios = value;
    }

}
