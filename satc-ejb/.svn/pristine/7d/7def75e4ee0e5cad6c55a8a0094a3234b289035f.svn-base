package com.sat.sisat.papeleta.dto;

import java.io.Serializable;
import com.sat.sisat.common.util.Constante;

/**
 * @author Arturo
 *
 */
public class RecordPapeletaDTO implements Serializable {
	private Integer item;
	private Integer papeleId;
	private String placa;
	private String nroPapeleta;
	private String fechaInfraccion;
	private String infraccion;
	private String nivelGravedad;
	private Integer puntos;
	private Double montoMulta;
	private Integer reincidencia;
	private String estadoPapeleta;
	private String apellidosNombresInfractor;
	private String apellidosNombresPropietario;
	private String direccionPropietario;
	private String tipoDocumento;
	private Integer tipoDocumentoId;
	
	private String nroDocumento;
	private String nroLicencia;
	private String nroResolucion;
	private String estadoResolucion;
	private Integer personaPropietarioId;
	private Integer personaInfractorId;
	
	private String estadoPapeletaString;	
	private String razonSocial;
	
	public void setEstadoPapeletaString(String estadoPapeletaString) {
		this.estadoPapeletaString = estadoPapeletaString;
	}
	
	public String getEstadoPapeletaString() {
		if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_PENDIENTE)) {
			return "verificacion";
		} else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_REGISTRADO)) {
			return "Registrado";
		} else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_NO_COINCIDE)) {
			return "No Coincide";
		} else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_DEFINITIVO)) {
			return "Pendiente";
		}
		// agregado
		else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_CANCELADO)) {
			return "Cancelado";
		} else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_DESCARGADO)) {
			return "Descargado";
		} else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_ANULADO)) {
			return "Anulado";
		} else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_COMPENSADO)) {
			return "Compensado";
		} else if (estadoPapeleta.equals(Constante.ESTADO_PAPELETA_PRESCRITO)) {
			return "Prescrito";
		}

		return estadoPapeletaString;
	}
	
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public Integer getPersonaPropietarioId() {
		return personaPropietarioId;
	}
	public void setPersonaPropietarioId(Integer personaPropietarioId) {
		this.personaPropietarioId = personaPropietarioId;
	}
	public Integer getPersonaInfractorId() {
		return personaInfractorId;
	}
	public void setPersonaInfractorId(Integer personaInfractorId) {
		this.personaInfractorId = personaInfractorId;
	}
	public Integer getItem() {
		return item;
	}
	public void setItem(Integer item) {
		this.item = item;
	}
	public Integer getPapeleId() {
		return papeleId;
	}
	public void setPapeleId(Integer papeleId) {
		this.papeleId = papeleId;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getNroPapeleta() {
		return nroPapeleta;
	}
	public void setNroPapeleta(String nroPapeleta) {
		this.nroPapeleta = nroPapeleta;
	}
	public String getFechaInfraccion() {
		return fechaInfraccion;
	}
	public void setFechaInfraccion(String fechaInfraccion) {
		this.fechaInfraccion = fechaInfraccion;
	}
	public String getInfraccion() {
		return infraccion;
	}
	public void setInfraccion(String infraccion) {
		this.infraccion = infraccion;
	}
	public String getNivelGravedad() {
		return nivelGravedad;
	}
	public void setNivelGravedad(String nivelGravedad) {
		this.nivelGravedad = nivelGravedad;
	}
	public Integer getPuntos() {
		return puntos;
	}
	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}
	public Double getMontoMulta() {
		return montoMulta;
	}
	public void setMontoMulta(Double montoMulta) {
		this.montoMulta = montoMulta;
	}
	public Integer getReincidencia() {
		return reincidencia;
	}
	public void setReincidencia(Integer reincidencia) {
		this.reincidencia = reincidencia;
	}
	public String getEstadoPapeleta() {
		return estadoPapeleta;
	}
	public void setEstadoPapeleta(String estadoPapeleta) {
		this.estadoPapeleta = estadoPapeleta;
	}
	public String getApellidosNombresInfractor() {
		return apellidosNombresInfractor;
	}
	public void setApellidosNombresInfractor(String apellidosNombresInfractor) {
		this.apellidosNombresInfractor = apellidosNombresInfractor;
	}
	public String getApellidosNombresPropietario() {
		return apellidosNombresPropietario;
	}
	public void setApellidosNombresPropietario(String apellidosNombresPropietario) {
		this.apellidosNombresPropietario = apellidosNombresPropietario;
	}
	public String getDireccionPropietario() {
		return direccionPropietario;
	}
	public void setDireccionPropietario(String direccionPropietario) {
		this.direccionPropietario = direccionPropietario;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNroDocumento() {
		return nroDocumento;
	}
	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	public String getNroLicencia() {
		return nroLicencia;
	}
	public void setNroLicencia(String nroLicencia) {
		this.nroLicencia = nroLicencia;
	}
	public String getNroResolucion() {
		return nroResolucion;
	}
	public void setNroResolucion(String nroResolucion) {
		this.nroResolucion = nroResolucion;
	}
	public String getEstadoResolucion() {
		return estadoResolucion;
	}
	public void setEstadoResolucion(String estadoResolucion) {
		this.estadoResolucion = estadoResolucion;
	}
	
	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}
	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}
	
}
