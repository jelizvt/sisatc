package com.sat.sisat.cobranzacoactiva.dto;

import java.io.Serializable;

import com.sat.sisat.common.dto.BaseDTO;


public class InformeTransferido implements Serializable {
	
	/**
	 * fox
	 */
	private Integer loteTransferenciaId;
	private String nroInforme;
	private String fechaEmision;
	private String horaEmision;
	private String usuarioEmision;
	private String areaOrigen;
	private String nroLoteOrigen;
	private Integer cantidadValores;
	private Integer cantidadRecibido;
	private Integer cantidadDevuelto;
	private double totalExigible;
	private String estadoTransferencia;
	private String periodoDeuda;
	private String tipoValor;
	private String concepto;
	private Integer estadoTransferenciaId;
	
	public Integer getEstadoTransferenciaId() {
		return estadoTransferenciaId;
	}
	public void setEstadoTransferenciaId(Integer estadoTransferenciaId) {
		this.estadoTransferenciaId = estadoTransferenciaId;
	}
	public String getHoraEmision() {
		return horaEmision;
	}
	public void setHoraEmision(String horaEmision) {
		this.horaEmision = horaEmision;
	}
	public String getPeriodoDeuda() {
		return periodoDeuda;
	}
	public void setPeriodoDeuda(String periodoDeuda) {
		this.periodoDeuda = periodoDeuda;
	}
	public String getTipoValor() {
		return tipoValor;
	}
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getNroInforme() {
		return nroInforme;
	}
	public void setNroInforme(String nroInforme) {
		this.nroInforme = nroInforme;
	}
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getUsuarioEmision() {
		return usuarioEmision;
	}
	public void setUsuarioEmision(String usuarioEmision) {
		this.usuarioEmision = usuarioEmision;
	}
	public String getAreaOrigen() {
		return areaOrigen;
	}
	public void setAreaOrigen(String areaOrigen) {
		this.areaOrigen = areaOrigen;
	}
	public String getNroLoteOrigen() {
		return nroLoteOrigen;
	}
	public void setNroLoteOrigen(String nroLoteOrigen) {
		this.nroLoteOrigen = nroLoteOrigen;
	}
	public Integer getCantidadValores() {
		return cantidadValores;
	}
	public void setCantidadValores(Integer cantidadValores) {
		this.cantidadValores = cantidadValores;
	}
	public Integer getCantidadRecibido() {
		return cantidadRecibido;
	}
	public void setCantidadRecibido(Integer cantidadRecibido) {
		this.cantidadRecibido = cantidadRecibido;
	}
	public Integer getCantidadDevuelto() {
		return cantidadDevuelto;
	}
	public void setCantidadDevuelto(Integer cantidadDevuelto) {
		this.cantidadDevuelto = cantidadDevuelto;
	}
	public double getTotalExigible() {
		return totalExigible;
	}
	public void setTotalExigible(double totalExigible) {
		this.totalExigible = totalExigible;
	}
	public String getEstadoTransferencia() {
		return estadoTransferencia;
	}
	public void setEstadoTransferencia(String estadoTransferencia) {
		this.estadoTransferencia = estadoTransferencia;
	}
	public Integer getLoteTransferenciaId() {
		return loteTransferenciaId;
	}
	public void setLoteTransferenciaId(Integer loteTransferenciaId) {
		this.loteTransferenciaId = loteTransferenciaId;
	}	

}
