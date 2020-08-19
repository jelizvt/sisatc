package com.sat.sisat.cobranzacoactiva.dto;

import java.io.Serializable;

import com.sat.sisat.common.dto.BaseDTO;


public class GestionCostas implements Serializable {
	private Integer recId;
	private String concepto;
	private String subConcepto;
	private String fechaEmision;
	private Double montoDeuda;
	private String nroExpediente;
	private String nroRec;
	private String estadoDeuda;
	private Integer deudaId;
	
	
	public String getNroRec() {
		return nroRec;
	}
	public void setNroRec(String nroRec) {
		this.nroRec = nroRec;
	}
	public String getNroExpediente() {
		return nroExpediente;
	}
	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}
	public Integer getRecId() {
		return recId;
	}
	public void setRecId(Integer recId) {
		this.recId = recId;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getSubConcepto() {
		return subConcepto;
	}
	public void setSubConcepto(String subConcepto) {
		this.subConcepto = subConcepto;
	}
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public Double getMontoDeuda() {
		return montoDeuda;
	}
	public void setMontoDeuda(Double montoDeuda) {
		this.montoDeuda = montoDeuda;
	}
	public String getEstadoDeuda() {
		return estadoDeuda;
	}
	public void setEstadoDeuda(String estadoDeuda) {
		this.estadoDeuda = estadoDeuda;
	}
	public Integer getDeudaId() {
		return deudaId;
	}
	public void setDeudaId(Integer deudaId) {
		this.deudaId = deudaId;
	}
	
}
