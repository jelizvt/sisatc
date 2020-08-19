package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.util.Date;

public class CajeroDTO implements Serializable{

	private static final long serialVersionUID = -8751308866999531739L;

	private String nombreUsuario;
	
	private Date fechaApertura;
	
	private Date fechaCierre;
	
	private Integer aperturaId;
	
	private int estado;
	
	private String estadoDescripcion;
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public Integer getAperturaId() {
		return aperturaId;
	}

	public int getEstado() {
		return estado;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public void setAperturaId(Integer aperturaId) {
		this.aperturaId = aperturaId;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getEstadoDescripcion() {
		return estadoDescripcion;
	}

	public void setEstadoDescripcion(String estadoDescripcion) {
		this.estadoDescripcion = estadoDescripcion;
	}
	
}
