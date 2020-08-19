
package pe.gob.sunarp.pide.controller;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaNaveAeronaveBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaNaveAeronaveBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="matricula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oficina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroPartida" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroFicha" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="registro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="libro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroSerie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modelo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoBien" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tomoFolio" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="tomoFolio" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="mensaje" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaNaveAeronaveBean", propOrder = {
    "matricula",
    "oficina",
    "numeroPartida",
    "numeroFicha",
    "registro",
    "libro",
    "numeroSerie",
    "modelo",
    "tipoBien",
    "tomoFolio",
    "mensaje"
})
public class RespuestaNaveAeronaveBean {

    protected String matricula;
    protected String oficina;
    protected String numeroPartida;
    protected String numeroFicha;
    protected String registro;
    protected String libro;
    protected String numeroSerie;
    protected String modelo;
    protected String tipoBien;
    protected RespuestaNaveAeronaveBean.TomoFolio tomoFolio;
    protected String mensaje;

    /**
     * Gets the value of the matricula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Sets the value of the matricula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatricula(String value) {
        this.matricula = value;
    }

    /**
     * Gets the value of the oficina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOficina() {
        return oficina;
    }

    /**
     * Sets the value of the oficina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOficina(String value) {
        this.oficina = value;
    }

    /**
     * Gets the value of the numeroPartida property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroPartida() {
        return numeroPartida;
    }

    /**
     * Sets the value of the numeroPartida property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroPartida(String value) {
        this.numeroPartida = value;
    }

    /**
     * Gets the value of the numeroFicha property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroFicha() {
        return numeroFicha;
    }

    /**
     * Sets the value of the numeroFicha property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroFicha(String value) {
        this.numeroFicha = value;
    }

    /**
     * Gets the value of the registro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistro() {
        return registro;
    }

    /**
     * Sets the value of the registro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistro(String value) {
        this.registro = value;
    }

    /**
     * Gets the value of the libro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLibro() {
        return libro;
    }

    /**
     * Sets the value of the libro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLibro(String value) {
        this.libro = value;
    }

    /**
     * Gets the value of the numeroSerie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroSerie() {
        return numeroSerie;
    }

    /**
     * Sets the value of the numeroSerie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroSerie(String value) {
        this.numeroSerie = value;
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
     * Gets the value of the tipoBien property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoBien() {
        return tipoBien;
    }

    /**
     * Sets the value of the tipoBien property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoBien(String value) {
        this.tipoBien = value;
    }

    /**
     * Gets the value of the tomoFolio property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaNaveAeronaveBean.TomoFolio }
     *     
     */
    public RespuestaNaveAeronaveBean.TomoFolio getTomoFolio() {
        return tomoFolio;
    }

    /**
     * Sets the value of the tomoFolio property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaNaveAeronaveBean.TomoFolio }
     *     
     */
    public void setTomoFolio(RespuestaNaveAeronaveBean.TomoFolio value) {
        this.tomoFolio = value;
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="tomoFolio" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
        "tomoFolio"
    })
    public static class TomoFolio {

        @XmlElement(nillable = true)
        protected List<String> tomoFolio;

        /**
         * Gets the value of the tomoFolio property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the tomoFolio property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTomoFolio().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getTomoFolio() {
            if (tomoFolio == null) {
                tomoFolio = new ArrayList<String>();
            }
            return this.tomoFolio;
        }

    }

}
