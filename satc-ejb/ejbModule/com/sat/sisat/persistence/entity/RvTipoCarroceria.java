package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the rv_tipo_carroceria database table.
 * 
 */
@Entity
@Table(name="rv_tipo_carroceria")
public class RvTipoCarroceria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_carroceria_id")
	private int tipoCarroceriaId;

	private String descripcion;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="usuario_id")
	private int usuarioId;

	//bi-directional many-to-one association to RvVehiculo
	@Transient
	private Set<RvVehiculo> rvVehiculos;

    public RvTipoCarroceria() {
    }

	public int getTipoCarroceriaId() {
		return this.tipoCarroceriaId;
	}

	public void setTipoCarroceriaId(int tipoCarroceriaId) {
		this.tipoCarroceriaId = tipoCarroceriaId;
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

	public Set<RvVehiculo> getRvVehiculos() {
		return this.rvVehiculos;
	}

	public void setRvVehiculos(Set<RvVehiculo> rvVehiculos) {
		this.rvVehiculos = rvVehiculos;
	}
	
}