package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rv_tipo_traccion database table.
 * 
 */
@Entity
@Table(name="rv_tipo_traccion")
public class RvTipoTraccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_traccion_id")
	private int tipoTraccionId;

	private String descripcion;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="usuario_id")
	private int usuarioId;

    public RvTipoTraccion() {
    }

	public int getTipoTraccionId() {
		return this.tipoTraccionId;
	}

	public void setTipoTraccionId(int tipoTraccionId) {
		this.tipoTraccionId = tipoTraccionId;
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