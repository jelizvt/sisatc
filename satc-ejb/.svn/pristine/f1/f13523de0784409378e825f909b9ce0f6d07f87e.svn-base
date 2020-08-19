package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mp_inspeccion_condicion_especial")
public class MpInspeccionCondicionEspecial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="inspeccion_ce_id")
	private Integer inspeccionId;
	
	@Column(name="requerimiento_ce_id")
	private int requerimientoId;
	
	@Column(name="flag_situacion")
	private int flagSituacion;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="estado")
	private Integer estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="terminal")
	private String terminal;

	public Integer getInspeccionId() {
		return inspeccionId;
	}

	public void setInspeccionId(Integer inspeccionId) {
		this.inspeccionId = inspeccionId;
	}

	public int getRequerimientoId() {
		return requerimientoId;
	}

	public void setRequerimientoId(int requerimientoId) {
		this.requerimientoId = requerimientoId;
	}

	public int getFlagSituacion() {
		return flagSituacion;
	}

	public void setFlagSituacion(int flagSituacion) {
		this.flagSituacion = flagSituacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
}
