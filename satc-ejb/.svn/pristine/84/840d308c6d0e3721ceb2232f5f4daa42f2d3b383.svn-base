package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the no_relacion_persona database table.
 * 
 */
@Entity
@Table(name="no_relacion_persona")
public class NoRelacionPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="relacion_notificacion_id")
	private int relacionNotificacionId;

	private String descripcion;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public NoRelacionPersona() {
    }

	public int getRelacionNotificacionId() {
		return this.relacionNotificacionId;
	}

	public void setRelacionNotificacionId(int relacionNotificacionId) {
		this.relacionNotificacionId = relacionNotificacionId;
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

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}