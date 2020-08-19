package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rv_clase_vehiculo database table.
 * 
 */
@Entity
@Table(name="rv_clase_vehiculo")
public class RvClaseVehiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="clase_vehiculo_id")
	private int claseVehiculoId;

	private String descripcion;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="letra_clase_vehiculo")
	private String letraClaseVehiculo;

	@Column(name="peso_maximo")
	private int pesoMaximo;

	@Column(name="peso_minimo")
	private int pesoMinimo;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public RvClaseVehiculo() {
    }

	public int getClaseVehiculoId() {
		return this.claseVehiculoId;
	}

	public void setClaseVehiculoId(int claseVehiculoId) {
		this.claseVehiculoId = claseVehiculoId;
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

	public String getLetraClaseVehiculo() {
		return this.letraClaseVehiculo;
	}

	public void setLetraClaseVehiculo(String letraClaseVehiculo) {
		this.letraClaseVehiculo = letraClaseVehiculo;
	}

	public int getPesoMaximo() {
		return this.pesoMaximo;
	}

	public void setPesoMaximo(int pesoMaximo) {
		this.pesoMaximo = pesoMaximo;
	}

	public int getPesoMinimo() {
		return this.pesoMinimo;
	}

	public void setPesoMinimo(int pesoMinimo) {
		this.pesoMinimo = pesoMinimo;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}