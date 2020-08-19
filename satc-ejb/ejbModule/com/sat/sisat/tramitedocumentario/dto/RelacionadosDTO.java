package com.sat.sisat.tramitedocumentario.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class RelacionadosDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1341744442758612123L;
	
	

	
	private Integer personaId;
	private Integer relacionadoId;
	private String tiporelacion;
	private String apellidosNombres;
	private String tipoDocIdentidad;
	private String nroDocuIdentidad;
	private BigDecimal porcParticipacion;
	private Integer item;
	
	
	public String getTiporelacion() {
		return tiporelacion;
	}
	public void setTiporelacion(String tiporelacion) {
		this.tiporelacion = tiporelacion;
	}
	public BigDecimal getPorcParticipacion() {
		return porcParticipacion;
	}
	public void setPorcParticipacion(BigDecimal porcParticipacion) {
		this.porcParticipacion = porcParticipacion;
	}
	public Integer getPersonaId() {
		return personaId;
	}
	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	public Integer getRelacionadoId() {
		return relacionadoId;
	}
	public void setRelacionadoId(Integer relacionadoId) {
		this.relacionadoId = relacionadoId;
	}
	public String getApellidosNombres() {
		return apellidosNombres;
	}
	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}
	public String getTipoDocIdentidad() {
		return tipoDocIdentidad;
	}
	public void setTipoDocIdentidad(String tipoDocIdentidad) {
		this.tipoDocIdentidad = tipoDocIdentidad;
	}
	public String getNroDocuIdentidad() {
		return nroDocuIdentidad;
	}
	public void setNroDocuIdentidad(String nroDocuIdentidad) {
		this.nroDocuIdentidad = nroDocuIdentidad;
	}
	public Integer getItem() {
		return item;
	}
	public void setItem(Integer item) {
		this.item = item;
	}
	
	
}
