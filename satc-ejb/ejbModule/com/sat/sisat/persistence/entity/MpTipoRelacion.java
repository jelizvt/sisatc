package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the mp_tipo_relacion database table.
 * 
 */
@Entity
@Table(name="mp_tipo_relacion")
public class MpTipoRelacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_relacion_id")
	private Integer tipoRelacionId;

	private String descripcion;

	@Column(name="descripcion_alterna")
	private String descripcionAlterna;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

    public MpTipoRelacion() {
    }

	public Integer getTipoRelacionId() {
		return this.tipoRelacionId;
	}

	public void setTipoRelacionId(Integer tipoRelacionId) {
		this.tipoRelacionId = tipoRelacionId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionAlterna() {
		return this.descripcionAlterna;
	}

	public void setDescripcionAlterna(String descripcionAlterna) {
		this.descripcionAlterna = descripcionAlterna;
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

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

}