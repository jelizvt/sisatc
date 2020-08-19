package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the gn_sector database table.
 * 
 */
@Entity
@Table(name="gn_lugar")
public class GnLugar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="lugar_id")
	private Integer lugarId;

	private String descripcion;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

	public Integer getLugarId() {
		return lugarId;
	}

	public void setLugarId(Integer lugarId) {
		this.lugarId = lugarId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public GnLugar() {
    }
    
    
}