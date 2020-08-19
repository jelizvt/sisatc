package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the mp_condi_espe_predio database table.
 * 
 */
@Entity
@Table(name="mp_condi_espe_predio")
public class MpCondiEspePredio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cond_espe_predio_id")
	private int condEspePredioId;

	private String descripcion;

	private int estado;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public MpCondiEspePredio() {
    }

	public int getCondEspePredioId() {
		return this.condEspePredioId;
	}

	public void setCondEspePredioId(int condEspePredioId) {
		this.condEspePredioId = condEspePredioId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getEstado() {
		return this.estado;
	}

	public void setEstado(int estado) {
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