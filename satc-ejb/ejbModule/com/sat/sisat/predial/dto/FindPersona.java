package com.sat.sisat.predial.dto;

import java.io.Serializable;

public class FindPersona implements Serializable {
	
	private Integer personaId;
	private Integer tipoPersonaId;
	private Integer tipoDocIdentidadId;
	private String denTipoDocIdentidad;
	private String nroDocuIdentidad;
	private String apePaterno;
	private String apeMaterno;
	private String primerNombre;
	private String razonSocial;
	private String apellidosNombres;
	
	private String descSubTipoPersona;
	private String descTipoPersona;
	
	private String direccionCompleta;
	private Integer sectorId;
	
	public Integer getSectorId() {
		return sectorId;
	}
	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}
	public String getDireccionCompleta() {
		return direccionCompleta;
	}
	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}
	public String getDescSubTipoPersona() {
		return descSubTipoPersona;
	}
	public void setDescSubTipoPersona(String descSubTipoPersona) {
		this.descSubTipoPersona = descSubTipoPersona;
	}
	public String getDescTipoPersona() {
		return descTipoPersona;
	}
	public void setDescTipoPersona(String descTipoPersona) {
		this.descTipoPersona = descTipoPersona;
	}
	public String getApellidosNombres() {
		return apellidosNombres;
	}
	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}
	public Integer getPersonaId() {
		return personaId;
	}
	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	public Integer getTipoPersonaId() {
		return tipoPersonaId;
	}
	public void setTipoPersonaId(Integer tipoPersonaId) {
		this.tipoPersonaId = tipoPersonaId;
	}
	public Integer getTipoDocIdentidadId() {
		return tipoDocIdentidadId;
	}
	public void setTipoDocIdentidadId(Integer tipoDocIdentidadId) {
		this.tipoDocIdentidadId = tipoDocIdentidadId;
	}
	public String getNroDocuIdentidad() {
		return nroDocuIdentidad;
	}
	public void setNroDocuIdentidad(String nroDocuIdentidad) {
		this.nroDocuIdentidad = nroDocuIdentidad;
	}
	public String getApePaterno() {
		return apePaterno;
	}
	public void setApePaterno(String apePaterno) {
		this.apePaterno = apePaterno;
	}
	public String getApeMaterno() {
		return apeMaterno;
	}
	public void setApeMaterno(String apeMaterno) {
		this.apeMaterno = apeMaterno;
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
	public String getDenTipoDocIdentidad() {
		return denTipoDocIdentidad;
	}
	public void setDenTipoDocIdentidad(String denTipoDocIdentidad) {
		this.denTipoDocIdentidad = denTipoDocIdentidad;
	}	
}
