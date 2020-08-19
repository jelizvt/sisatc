package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rp_tipo_adquisicion database table.
 * 
 */
@Entity
@Table(name="rp_tipo_adquisicion")
public class RpTipoAdquisicion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_adquisicion_id")
	private int tipoAdquisicionId;

	private String descripcion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="flag_transferente")
	private String flagTransferente;

	@Column(name="usuario_id")
	private int usuarioId;

    public RpTipoAdquisicion() {
    }

	public int getTipoAdquisicionId() {
		return this.tipoAdquisicionId;
	}

	public void setTipoAdquisicionId(int tipoAdquisicionId) {
		this.tipoAdquisicionId = tipoAdquisicionId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFlagTransferente() {
		return this.flagTransferente;
	}

	public void setFlagTransferente(String flagTransferente) {
		this.flagTransferente = flagTransferente;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}