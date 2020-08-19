package com.sat.sisat.cobranzacoactiva.dto;

import java.io.Serializable;

import com.sat.sisat.common.dto.BaseDTO;

public class ControlExpediente extends BaseDTO implements Serializable {
	
	private Integer expedienteId;
	private String nroExpediente;
	private String fechaRegistro;
	
	private String tipoValor;
	private String periodoValor;
	private String nroValor;
	private Double deudaValor;
	private String concepto;
	
	private Integer personaId;
	private String apellidosNombres;
	
	private Integer cantidadRC;
	private String tipoUltimaRC;
	private String fechaNotificaUltimaRC;
	private String nroRC;
	
	public String getNroRC() {
		return nroRC;
	}
	public void setNroRC(String nroRC) {
		this.nroRC = nroRC;
	}
	public Integer getExpedienteId() {
		return expedienteId;
	}
	public void setExpedienteId(Integer expedienteId) {
		this.expedienteId = expedienteId;
	}
	public String getNroExpediente() {
		return nroExpediente;
	}
	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getTipoValor() {
		return tipoValor;
	}
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}
	public String getPeriodoValor() {
		return periodoValor;
	}
	public void setPeriodoValor(String periodoValor) {
		this.periodoValor = periodoValor;
	}
	public String getNroValor() {
		return nroValor;
	}
	public void setNroValor(String nroValor) {
		this.nroValor = nroValor;
	}
	public Double getDeudaValor() {
		return deudaValor;
	}
	public void setDeudaValor(Double deudaValor) {
		this.deudaValor = deudaValor;
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
	public Integer getCantidadRC() {
		return cantidadRC;
	}
	public void setCantidadRC(Integer cantidadRC) {
		this.cantidadRC = cantidadRC;
	}
	public String getTipoUltimaRC() {
		return tipoUltimaRC;
	}
	public void setTipoUltimaRC(String tipoUltimaRC) {
		this.tipoUltimaRC = tipoUltimaRC;
	}
	public String getFechaNotificaUltimaRC() {
		return fechaNotificaUltimaRC;
	}
	public void setFechaNotificaUltimaRC(String fechaNotificaUltimaRC) {
		this.fechaNotificaUltimaRC = fechaNotificaUltimaRC;
	}
	
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	
}
