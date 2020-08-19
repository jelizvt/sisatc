package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rv_motivo_declaracion database table.
 * 
 */
@Entity
@Table(name="rv_motivo_declaracion")
public class RvMotivoDeclaracion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="motivo_declaracion_id")
	private int motivoDeclaracionId;

	private String descripcion;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="usuario_id")
	private int usuarioId;

    public RvMotivoDeclaracion() {
    }

	public int getMotivoDeclaracionId() {
		return this.motivoDeclaracionId;
	}

	public void setMotivoDeclaracionId(int motivoDeclaracionId) {
		this.motivoDeclaracionId = motivoDeclaracionId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}