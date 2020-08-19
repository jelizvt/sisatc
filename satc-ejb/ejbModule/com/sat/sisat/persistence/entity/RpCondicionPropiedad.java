package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rp_condicion_propiedad database table.
 * 
 */
@Entity
@Table(name="rp_condicion_propiedad")
public class RpCondicionPropiedad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="condicion_propiedad_id")
	private int condicionPropiedadId;

	private String descripcion;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="usuario_id")
	private int usuarioId;

    public RpCondicionPropiedad() {
    }

	public int getCondicionPropiedadId() {
		return this.condicionPropiedadId;
	}

	public void setCondicionPropiedadId(int condicionPropiedadId) {
		this.condicionPropiedadId = condicionPropiedadId;
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