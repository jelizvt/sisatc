package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the no_motivo_notificacion database table.
 * 
 */
@Entity
@Table(name="no_motivo_notificacion")
public class NoMotivoNotificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="motivo_notificacion_id")
	private int motivoNotificacionId;

	private String descripcion;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public NoMotivoNotificacion() {
    }

	public int getMotivoNotificacionId() {
		return this.motivoNotificacionId;
	}

	public void setMotivoNotificacionId(int motivoNotificacionId) {
		this.motivoNotificacionId = motivoNotificacionId;
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