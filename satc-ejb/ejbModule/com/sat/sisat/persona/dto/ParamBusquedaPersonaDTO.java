package com.sat.sisat.persona.dto;

import java.io.Serializable;

public class ParamBusquedaPersonaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5605284010616442760L;
	
	private Integer personaId;
	
	private String apellidoPaterno;
	
	private String apellidoMaterno;
	
	private String primerNombre;
	
	private String razonSocial;
	
	private Integer tipoDocIdentidadId;
	
	private String nroDocumento;
	
	private String apellidosNombres;

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Integer getTipoDocIdentidadId() {
		return tipoDocIdentidadId;
	}

	public void setTipoDocIdentidadId(Integer tipoDocIdentidadId) {
		this.tipoDocIdentidadId = tipoDocIdentidadId;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getApellidosNombres() {
		return apellidosNombres;
	}

	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}
	

}
