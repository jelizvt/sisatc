package com.sat.sisat.cobranzacoactiva.dto;

import java.io.Serializable;

import com.sat.sisat.common.dto.BaseDTO;


public class SituacionDeuda extends BaseDTO implements Serializable {
	private Integer personaId;
	private String apellidosNombres;
	private String tipoActo;
	private String nroActo;
	private Integer annoActo;
	private Double insoluto;
	private Double reajuste;
	private Double intereses;
	private Double derechoEmision;
	private Double totalDeuda;
	
	private String nroExpediente;
	private String fechaRegistro;
	private String nroREC;
	private String tipoRec;
	private String fechaNotificacion;
	
	private String coactivo;
	private String estadoDeuda;
	private Double totalCancelado;
	private String concepto;
	
	public String getFechaNotificacion() {
		return fechaNotificacion;
	}
	public void setFechaNotificacion(String fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getNroREC() {
		return nroREC;
	}
	public void setNroREC(String nroREC) {
		this.nroREC = nroREC;
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
	public String getTipoActo() {
		return tipoActo;
	}
	public void setTipoActo(String tipoActo) {
		this.tipoActo = tipoActo;
	}
	public String getNroActo() {
		return nroActo;
	}
	public void setNroActo(String nroActo) {
		this.nroActo = nroActo;
	}
	public Double getInsoluto() {
		return insoluto;
	}
	public void setInsoluto(Double insoluto) {
		this.insoluto = insoluto;
	}
	public Double getReajuste() {
		return reajuste;
	}
	public void setReajuste(Double reajuste) {
		this.reajuste = reajuste;
	}
	public Double getIntereses() {
		return intereses;
	}
	public void setIntereses(Double intereses) {
		this.intereses = intereses;
	}
	public Double getDerechoEmision() {
		return derechoEmision;
	}
	public void setDerechoEmision(Double derechoEmision) {
		this.derechoEmision = derechoEmision;
	}
	public Double getTotalDeuda() {
		return totalDeuda;
	}
	public void setTotalDeuda(Double totalDeuda) {
		this.totalDeuda = totalDeuda;
	}
	public Integer getAnnoActo() {
		return annoActo;
	}
	public void setAnnoActo(Integer annoActo) {
		this.annoActo = annoActo;
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
	public String getCoactivo() {
		return coactivo;
	}
	public void setCoactivo(String coactivo) {
		this.coactivo = coactivo;
	}
	public String getEstadoDeuda() {
		return estadoDeuda;
	}
	public void setEstadoDeuda(String estadoDeuda) {
		this.estadoDeuda = estadoDeuda;
	}
	public Double getTotalCancelado() {
		return totalCancelado;
	}
	public void setTotalCancelado(Double totalCancelado) {
		this.totalCancelado = totalCancelado;
	}
	public String getTipoRec() {
		return tipoRec;
	}
	public void setTipoRec(String tipoRec) {
		this.tipoRec = tipoRec;
	}
	
}
  