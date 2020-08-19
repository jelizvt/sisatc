package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rv_adquisicion database table.
 * 
 */
@Entity
@Table(name="rv_adquisicion")
public class RvAdquisicion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_adquisicion_id")
	private int tipoAdquisicionId;

	@Column(name="caso_transferencia")
	private int casoTransferencia;

	private String descripcion;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="usuario_id")
	private int usuarioId;

    public RvAdquisicion() {
    }

	public int getTipoAdquisicionId() {
		return this.tipoAdquisicionId;
	}

	public void setTipoAdquisicionId(int tipoAdquisicionId) {
		this.tipoAdquisicionId = tipoAdquisicionId;
	}

	public int getCasoTransferencia() {
		return this.casoTransferencia;
	}

	public void setCasoTransferencia(int casoTransferencia) {
		this.casoTransferencia = casoTransferencia;
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