package com.sat.sisat.fiscalizacion.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FindInspeccionByIdAsociada implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer inspeccionId;
	private Integer tipoDocumentoId;
	private String tipoDocumentoNombre;
	private Integer personaId;
	private String apellidosNombres;
	private Integer programaId;
	private Integer inspectorId;
	private Integer horarioDetalleId;
	private Integer horarioId;
	private Integer inspeccionDetalleId;
	private Integer djId;
	private Integer predioId;
	
	/**
	 ** Al asociar un requerimiento desde la lista de Djs
	 */
	private Date fechaInspeccion;
	private Date fechaInspeccionAr;
	private Integer inspectorIdAr;
	private Integer tipoDocumentoIdResultado;
	
	public Integer getInspeccionId() {
		return inspeccionId;
	}
	public void setInspeccionId(Integer inspeccionId) {
		this.inspeccionId = inspeccionId;
	}
	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}
	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
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
	public Integer getProgramaId() {
		return programaId;
	}
	public void setProgramaId(Integer programaId) {
		this.programaId = programaId;
	}
	public Integer getInspectorId() {
		return inspectorId;
	}
	public void setInspectorId(Integer inspectorId) {
		this.inspectorId = inspectorId;
	}
	public Integer getHorarioDetalleId() {
		return horarioDetalleId;
	}
	public void setHorarioDetalleId(Integer horarioDetalleId) {
		this.horarioDetalleId = horarioDetalleId;
	}
	public Integer getHorarioId() {
		return horarioId;
	}
	public void setHorarioId(Integer horarioId) {
		this.horarioId = horarioId;
	}
	public Integer getInspeccionDetalleId() {
		return inspeccionDetalleId;
	}
	public void setInspeccionDetalleId(Integer inspeccionDetalleId) {
		this.inspeccionDetalleId = inspeccionDetalleId;
	}
	public Integer getDjId() {
		return djId;
	}
	public void setDjId(Integer djId) {
		this.djId = djId;
	}
	public Integer getPredioId() {
		return predioId;
	}
	public void setPredioId(Integer predioId) {
		this.predioId = predioId;
	}
	public String getTipoDocumentoNombre() {
		return tipoDocumentoNombre;
	}
	public void setTipoDocumentoNombre(String tipoDocumentoNombre) {
		this.tipoDocumentoNombre = tipoDocumentoNombre;
	}
	public Date getFechaInspeccion() {
		return fechaInspeccion;
	}
	public void setFechaInspeccion(Date fechaInspeccion) {
		this.fechaInspeccion = fechaInspeccion;
	}
	public Date getFechaInspeccionAr() {
		return fechaInspeccionAr;
	}
	public void setFechaInspeccionAr(Date fechaInspeccionAr) {
		this.fechaInspeccionAr = fechaInspeccionAr;
	}
	public Integer getInspectorIdAr() {
		return inspectorIdAr;
	}
	public void setInspectorIdAr(Integer inspectorIdAr) {
		this.inspectorIdAr = inspectorIdAr;
	}
	public Integer getTipoDocumentoIdResultado() {
		return tipoDocumentoIdResultado;
	}
	public void setTipoDocumentoIdResultado(Integer tipoDocumentoIdResultado) {
		this.tipoDocumentoIdResultado = tipoDocumentoIdResultado;
	}
	
	



}
