package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rv_categoria_vehiculo database table.
 * 
 */
@Entity
@Table(name="rv_categoria_vehiculo")
public class RvCategoriaVehiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="categoria_vehiculo_id")
	private int categoriaVehiculoId;

	@Column(name="cilindraje_final")
	private int cilindrajeFinal;

	@Column(name="cilindraje_inicial")
	private int cilindrajeInicial;

	private String descripcion;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="usuario_id")
	private int usuarioId;

    public RvCategoriaVehiculo() {
    }

	public int getCategoriaVehiculoId() {
		return this.categoriaVehiculoId;
	}

	public void setCategoriaVehiculoId(int categoriaVehiculoId) {
		this.categoriaVehiculoId = categoriaVehiculoId;
	}

	public int getCilindrajeFinal() {
		return this.cilindrajeFinal;
	}

	public void setCilindrajeFinal(int cilindrajeFinal) {
		this.cilindrajeFinal = cilindrajeFinal;
	}

	public int getCilindrajeInicial() {
		return this.cilindrajeInicial;
	}

	public void setCilindrajeInicial(int cilindrajeInicial) {
		this.cilindrajeInicial = cilindrajeInicial;
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