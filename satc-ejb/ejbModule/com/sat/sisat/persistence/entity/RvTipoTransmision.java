package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rv_tipo_transmision database table.
 * 
 */
@Entity
@Table(name="rv_tipo_transmision")
public class RvTipoTransmision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_transmision_id")
	private int tipoTransmisionId;

	private String descripcion;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="usuario_id")
	private int usuarioId;

    public RvTipoTransmision() {
    }

	public int getTipoTransmisionId() {
		return this.tipoTransmisionId;
	}

	public void setTipoTransmisionId(int tipoTransmisionId) {
		this.tipoTransmisionId = tipoTransmisionId;
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