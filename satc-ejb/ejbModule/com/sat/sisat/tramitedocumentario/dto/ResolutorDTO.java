package com.sat.sisat.tramitedocumentario.dto;

import java.io.Serializable;
import java.util.Date;

public class ResolutorDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2749196933226484690L;
	
	private Integer resolutorId;
	
	private Integer unidadId;
	
	private Integer usuarioResolutorId;
	
	private Date fechaInicio;
	
	private Date fechaFin;	
	
	private String nombreUsuario;
	
	private String unidadAsDescripcion;

	public Integer getResolutorId() {
		return resolutorId;
	}

	public void setResolutorId(Integer resolutorId) {
		this.resolutorId = resolutorId;
	}

	public Integer getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(Integer unidadId) {
		this.unidadId = unidadId;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getUnidadAsDescripcion() {
		return unidadAsDescripcion;
	}

	public void setUnidadAsDescripcion(String unidadAsDescripcion) {
		this.unidadAsDescripcion = unidadAsDescripcion;
	}

	public Integer getUsuarioResolutorId() {
		return usuarioResolutorId;
	}

	public void setUsuarioResolutorId(Integer usuarioResolutorId) {
		this.usuarioResolutorId = usuarioResolutorId;
	}	
}
