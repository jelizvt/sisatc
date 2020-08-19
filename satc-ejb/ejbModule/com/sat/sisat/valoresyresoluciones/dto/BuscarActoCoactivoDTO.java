package com.sat.sisat.valoresyresoluciones.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class BuscarActoCoactivoDTO implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private String nroRec;
	private Timestamp fechaRec;
	private BigDecimal deudaTotal;
	
	/*Ago-2016*/
	private Timestamp fechaNotificacionRec;
	private Timestamp fechaCosta;
	private BigDecimal deudaCosta;
	private String tipoActo;
	private Integer anioRec;
	private Integer loteExigible;
	private Integer loteRec;
	private String estadoRec;
	private String tipoRec;
	
	public String getNroRec() {
		return nroRec;
	}
	public void setNroRec(String nroRec) {
		this.nroRec = nroRec;
	}
	public Timestamp getFechaRec() {
		return fechaRec;
	}
	public void setFechaRec(Timestamp fechaRec) {
		this.fechaRec = fechaRec;
	}
	public BigDecimal getDeudaTotal() {
		return deudaTotal;
	}
	public void setDeudaTotal(BigDecimal deudaTotal) {
		this.deudaTotal = deudaTotal;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Timestamp getFechaNotificacionRec() {
		return fechaNotificacionRec;
	}
	public void setFechaNotificacionRec(Timestamp fechaNotificacionRec) {
		this.fechaNotificacionRec = fechaNotificacionRec;
	}
	public Timestamp getFechaCosta() {
		return fechaCosta;
	}
	public void setFechaCosta(Timestamp fechaCosta) {
		this.fechaCosta = fechaCosta;
	}
	public BigDecimal getDeudaCosta() {
		return deudaCosta;
	}
	public void setDeudaCosta(BigDecimal deudaCosta) {
		this.deudaCosta = deudaCosta;
	}
	public String getTipoActo() {
		return tipoActo;
	}
	public void setTipoActo(String tipoActo) {
		this.tipoActo = tipoActo;
	}
	public Integer getAnioRec() {
		return anioRec;
	}
	public void setAnioRec(Integer anioRec) {
		this.anioRec = anioRec;
	}
	public Integer getLoteExigible() {
		return loteExigible;
	}
	public void setLoteExigible(Integer loteExigible) {
		this.loteExigible = loteExigible;
	}
	public Integer getLoteRec() {
		return loteRec;
	}
	public void setLoteRec(Integer loteRec) {
		this.loteRec = loteRec;
	}
	public String getEstadoRec() {
		return estadoRec;
	}
	public void setEstadoRec(String estadoRec) {
		this.estadoRec = estadoRec;
	}
	public String getTipoRec() {
		return tipoRec;
	}
	public void setTipoRec(String tipoRec) {
		this.tipoRec = tipoRec;
	}
	
}
