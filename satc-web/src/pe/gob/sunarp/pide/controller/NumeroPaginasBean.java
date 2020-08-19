
package pe.gob.sunarp.pide.controller;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for numeroPaginasBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="numeroPaginasBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nroPagRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pagina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "numeroPaginasBean", propOrder = {
    "nroPagRef",
    "pagina"
})
public class NumeroPaginasBean {

    protected String nroPagRef;
    protected String pagina;

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
