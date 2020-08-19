
package pe.gob.reniec.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for peticionActualizarCredencial complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="peticionActualizarCredencial">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="credencialAnterior" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="credencialNueva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nuDni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nuRuc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "peticionActualizarCredencial", propOrder = {
    "credencialAnterior",
    "credencialNueva",
    "nuDni",
    "nuRuc"
})
public class PeticionActualizarCredencial {

    protected String credencialAnterior;
    protected String credencialNueva;
    protected String nuDni;
    protected String nuRuc;

    /**
     * Gets the value of the credencialAnterior property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCredencialAnterior() {
        return credencialAnterior;
    }

    /**
     * Sets the value of the credencialAnterior property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCredencialAnterior(String value) {
        this.credencialAnterior = value;
    }

    /**
     * Gets the value of the credencialNueva property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCredencialNueva() {
        return credencialNueva;
    }

    /**
     * Sets the value of the credencialNueva property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCredencialNueva(String value) {
        this.credencialNueva = value;
    }

    /**
     * Gets the value of the nuDni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNuDni() {
        return nuDni;
    }

    /**
     * Sets the value of the nuDni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNuDni(String value) {
        this.nuDni = value;
    }

    /**
     * Gets the value of the nuRuc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNuRuc() {
        return nuRuc;
    }

    /**
     * Sets the value of the nuRuc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNuRuc(String value) {
        this.nuRuc = value;
    }

}
