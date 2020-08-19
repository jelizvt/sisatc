package com.sat.sisat.cobranzacoactiva.dto;

import java.io.Serializable;

import com.sat.sisat.common.dto.BaseDTO;


public class ValorCartera implements Serializable {
	
	/**
	 * fox
	 */
	private Integer carteraId;
	private String nroCartera;
	private String fechaAsignacion;
	private String horaAsignacion;
	private String ejecutorCoactivo;
	private Integer cantidadRegistros;
	private double totalExigible;
	private String situacionCartera;
	
	public Integer getCarteraId() {
		return carteraId;
	}
	public void setCarteraId(Integer carteraId) {
		this.carteraId = carteraId;
	}
	public String getNroCartera() {
		return nroCartera;
	}
	public void setNroCartera(String nroCartera) {
		this.nroCartera = nroCartera;
	}
	public String getFechaAsignacion() {
		return fechaAsignacion;
	}
	public void setFechaAsignacion(String fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}
	public String getHoraAsignacion() {
		return horaAsignacion;
	}
	public void setHoraAsignacion(String horaAsignacion) {
		this.horaAsignacion = horaAsignacion;
	}
	public String getEjecutorCoactivo() {
		return ejecutorCoactivo;
	}
	public void setEjecutorCoactivo(String ejecutorCoactivo) {
		this.ejecutorCoactivo = ejecutorCoactivo;
	}
	public Integer getCantidadRegistros() {
		return cantidadRegistros;
	}
	public void setCantidadRegistros(Integer cantidadRegistros) {
		this.cantidadRegistros = cantidadRegistros;
	}
	public double getTotalExigible() {
		return totalExigible;
	}
	public void setTotalExigible(double totalExigible) {
		this.totalExigible = totalExigible;
	}
	public String getSituacionCartera() {
		return situacionCartera;
	}
	public void setSituacionCartera(String situacionCartera) {
		this.situacionCartera = situacionCartera;
	}
	
	

}
