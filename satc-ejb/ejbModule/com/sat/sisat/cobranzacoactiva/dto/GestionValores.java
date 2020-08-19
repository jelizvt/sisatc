package com.sat.sisat.cobranzacoactiva.dto;

import java.io.Serializable;

import com.sat.sisat.common.dto.BaseDTO;

public class GestionValores implements Serializable {
	private Integer actoId;
	private Integer expedienteId;
	private String tipoValor;
	private String nroActo;
	private Double montoDeuda;
	private String exigibiliad;
	private String periodoActo;
	private String nroExpediente;
	private Double deudaActual;
	private String estadoDeuda;
	private String concepto;
	private String placaNroPap;
	
	
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getEstadoDeuda() {
		return estadoDeuda;
	}
	public void setEstadoDeuda(String estadoDeuda) {
		this.estadoDeuda = estadoDeuda;
	}
	public Double getDeudaActual() {
		return deudaActual;
	}
	public void setDeudaActual(Double deudaActual) {
		this.deudaActual = deudaActual;
	}
	public String getPeriodoActo() {
		return periodoActo;
	}
	public void setPeriodoActo(String periodoActo) {
		this.periodoActo = periodoActo;
	}
	public String getNroExpediente() {
		return nroExpediente;
	}
	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}
	public Integer getActoId() {
		return actoId;
	}
	public void setActoId(Integer actoId) {
		this.actoId = actoId;
	}
	public Integer getExpedienteId() {
		return expedienteId;
	}
	public void setExpedienteId(Integer expedienteId) {
		this.expedienteId = expedienteId;
	}
	public String getTipoValor() {
		return tipoValor;
	}
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}
	public String getNroActo() {
		return nroActo;
	}
	public void setNroActo(String nroActo) {
		this.nroActo = nroActo;
	}
	public Double getMontoDeuda() {
		return montoDeuda;
	}
	public void setMontoDeuda(Double montoDeuda) {
		this.montoDeuda = montoDeuda;
	}
	public String getExigibiliad() {
		return exigibiliad;
	}
	public void setExigibiliad(String exigibiliad) {
		this.exigibiliad = exigibiliad;
	}
	public String getPlacaNroPap() {
		return placaNroPap;
	}
	public void setPlacaNroPap(String placaNroPap) {
		this.placaNroPap = placaNroPap;
	}
	
	
}
