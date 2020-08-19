package com.sat.sisat.fiscalizacion.dto;

import java.io.Serializable;
import java.util.Date;

public class FindInspeccionHistorialDetalle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Integer  inspeccionId;
	private Integer  tipoDocumentoId;
	private String   tipoDocumentoNombre;
	private String nroRequerimiento;
	private Integer personaId;
	private String apellidosNombres;
	private String personaDomicilioFiscal;//
	private Integer resultadoId;//
	private String resultadoNombre;//
	private String resultadoNumero;//
	private Integer arId;//
	private String arNombre;//
	private String arNumero;//
	private Integer  inspeccionDetId;
	private Integer predioId;
	private Integer djId;
	private Integer djAnio;

	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}
	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}
	public String getTipoDocumentoNombre() {
		return tipoDocumentoNombre;
	}
	public void setTipoDocumentoNombre(String tipoDocumentoNombre) {
		this.tipoDocumentoNombre = tipoDocumentoNombre;
	}
	public Integer getPersonaId() {
		return personaId;
	}
	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	public String getApellidosNombres() {
		return apellidosNombres;
	}
	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}

	public Integer getInspeccionId() {
		return inspeccionId;
	}
	public void setInspeccionId(Integer inspeccionId) {
		this.inspeccionId = inspeccionId;
	}
	public String getNroRequerimiento() {
		return nroRequerimiento;
	}
	public void setNroRequerimiento(String nroRequerimiento) {
		this.nroRequerimiento = nroRequerimiento;
	}
	public String getPersonaDomicilioFiscal() {
		return personaDomicilioFiscal;
	}
	public void setPersonaDomicilioFiscal(String personaDomicilioFiscal) {
		this.personaDomicilioFiscal = personaDomicilioFiscal;
	}

	public Integer getResultadoId() {
		return resultadoId;
	}
	public void setResultadoId(Integer resultadoId) {
		this.resultadoId = resultadoId;
	}
	public String getResultadoNombre() {
		return resultadoNombre;
	}
	public void setResultadoNombre(String resultadoNombre) {
		this.resultadoNombre = resultadoNombre;
	}
	public String getResultadoNumero() {
		return resultadoNumero;
	}
	public void setResultadoNumero(String resultadoNumero) {
		this.resultadoNumero = resultadoNumero;
	}

	public Integer getArId() {
		return arId;
	}
	public void setArId(Integer arId) {
		this.arId = arId;
	}
	public String getArNombre() {
		return arNombre;
	}
	public void setArNombre(String arNombre) {
		this.arNombre = arNombre;
	}
	public String getArNumero() {
		return arNumero;
	}
	public void setArNumero(String arNumero) {
		this.arNumero = arNumero;
	}

	public Integer getInspeccionDetId() {
		return inspeccionDetId;
	}
	public void setInspeccionDetId(Integer inspeccionDetId) {
		this.inspeccionDetId = inspeccionDetId;
	}
	public Integer getPredioId() {
		return predioId;
	}
	public void setPredioId(Integer predioId) {
		this.predioId = predioId;
	}
	public Integer getDjId() {
		return djId;
	}
	public void setDjId(Integer djId) {
		this.djId = djId;
	}
	public Integer getDjAnio() {
		return djAnio;
	}
	public void setDjAnio(Integer djAnio) {
		this.djAnio = djAnio;
	}

}
