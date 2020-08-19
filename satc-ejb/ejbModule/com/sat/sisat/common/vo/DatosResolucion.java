package com.sat.sisat.common.vo;

import java.io.Serializable;
import java.util.Date;

public class DatosResolucion implements Serializable {

	private String nroResolInicial;
	private Date fechaResolucion;
	private Date fechaRecepcion;
	private String estado;
	
	
	
	

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public Date getFechaResolucion() {
		return fechaResolucion;
	}

	public void setFechaResolucion(Date fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	public String getNroResolInicial() {
		return nroResolInicial;
	}

	public void setNroResolInicial(String nroResolInicial) {
		this.nroResolInicial = nroResolInicial;
	}
	
	

}
