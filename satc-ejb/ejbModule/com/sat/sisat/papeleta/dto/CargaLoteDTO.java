package com.sat.sisat.papeleta.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CargaLoteDTO implements Serializable {
	private String numOficio;
	private Integer corrOficio;
	private Timestamp fecOficio;
	private Timestamp fecRecepcion;
	private String numExpediente;
	private Integer estado;
	private String estadoDescripcion;
	private Integer cantidad;
	private Integer cantidadOficio;
	private String origen;
	private Integer cargaLotesId;
	//--
	private Integer cantDefinitivo;
	private Integer cantNoCoincide;
	private Integer cantRegistrado;
	private Integer cantEnVerificacion;
	
	public Integer getCantDefinitivo() {
		return cantDefinitivo;
	}
	public void setCantDefinitivo(Integer cantDefinitivo) {
		this.cantDefinitivo = cantDefinitivo;
	}
	public Integer getCantNoCoincide() {
		return cantNoCoincide;
	}
	public void setCantNoCoincide(Integer cantNoCoincide) {
		this.cantNoCoincide = cantNoCoincide;
	}
	public Integer getCantRegistrado() {
		return cantRegistrado;
	}
	public void setCantRegistrado(Integer cantRegistrado) {
		this.cantRegistrado = cantRegistrado;
	}
	public Integer getCantEnVerificacion() {
		return cantEnVerificacion;
	}
	public void setCantEnVerificacion(Integer cantEnVerificacion) {
		this.cantEnVerificacion = cantEnVerificacion;
	}
	public Integer getCargaLotesId() {
		return cargaLotesId;
	}
	public void setCargaLotesId(Integer cargaLotesId) {
		this.cargaLotesId = cargaLotesId;
	}
	public String getNumOficio() {
		return numOficio;
	}
	public void setNumOficio(String numOficio) {
		this.numOficio = numOficio;
	}
	public Integer getCorrOficio() {
		return corrOficio;
	}
	public void setCorrOficio(Integer corrOficio) {
		this.corrOficio = corrOficio;
	}
	public Timestamp getFecOficio() {
		return fecOficio;
	}
	public void setFecOficio(Timestamp fecOficio) {
		this.fecOficio = fecOficio;
	}
	public Timestamp getFecRecepcion() {
		return fecRecepcion;
	}
	public void setFecRecepcion(Timestamp fecRecepcion) {
		this.fecRecepcion = fecRecepcion;
	}
	public String getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public String getEstadoDescripcion() {
		return estadoDescripcion;
	}
	public void setEstadoDescripcion(String estadoDescripcion) {
		this.estadoDescripcion = estadoDescripcion;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public Integer getCantidadOficio() {
		return cantidadOficio;
	}
	public void setCantidadOficio(Integer cantidadOficio) {
		this.cantidadOficio = cantidadOficio;
	}
	
}
