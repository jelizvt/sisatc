package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the mp_tipo_condicion_especial database table.
 * 
 */
@Entity
@Table(name="mp_tipo_condicion_especial")
public class MpTipoCondicionEspecial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_cond_especial_id")
	private Integer tipoCondEspecialId;

	private String descripcion;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

    public MpTipoCondicionEspecial() {
    }

	public Integer getTipoCondEspecialId() {
		return this.tipoCondEspecialId;
	}

	public void setTipoCondEspecialId(Integer tipoCondEspecialId) {
		this.tipoCondEspecialId = tipoCondEspecialId;
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

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

}