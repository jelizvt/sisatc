package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rv_modelo_vehiculo database table.
 * 
 */
@Entity
@Table(name="rv_modelo_vehiculo")
public class RvModeloVehiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RvModeloVehiculoPK id;

	private String descripcion;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="usuario_id")
	private int usuarioId;

    public RvModeloVehiculo() {
    }

	public RvModeloVehiculoPK getId() {
		return this.id;
	}

	public void setId(RvModeloVehiculoPK id) {
		this.id = id;
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