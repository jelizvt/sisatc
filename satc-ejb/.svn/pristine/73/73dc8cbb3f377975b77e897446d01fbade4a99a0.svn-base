package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rv_tipo_motor database table.
 * 
 */
@Entity
@Table(name="rv_tipo_motor")
public class RvTipoMotor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_motor_id")
	private int tipoMotorId;

	private String descripcion;

	private String estado;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public RvTipoMotor() {
    }

	public int getTipoMotorId() {
		return this.tipoMotorId;
	}

	public void setTipoMotorId(int tipoMotorId) {
		this.tipoMotorId = tipoMotorId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
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