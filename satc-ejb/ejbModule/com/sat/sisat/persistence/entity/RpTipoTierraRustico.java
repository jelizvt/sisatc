package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rp_tipo_tierra_rustico database table.
 * 
 */
@Entity
@Table(name="rp_tipo_tierra_rustico")
public class RpTipoTierraRustico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_tierra_id")
	private int tipoTierraId;

	private String descripcion;

	private int estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public RpTipoTierraRustico() {
    }

	public int getTipoTierraId() {
		return this.tipoTierraId;
	}

	public void setTipoTierraId(int tipoTierraId) {
		this.tipoTierraId = tipoTierraId;
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

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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