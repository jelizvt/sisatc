package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the no_masiva_notificacion database table.
 * 
 */
@Entity
@Table(name="no_masiva_notificacion")
public class NoMasivaNotificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="masiva_notificacion_id")
	private int masivaNotificacionId;

	private String archivo;

	@Column(name="cant_actos")
	private int cantActos;

	@Column(name="cant_error")
	private int cantError;

	@Column(name="cant_rec")
	private int cantRec;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public NoMasivaNotificacion() {
    }

	public int getMasivaNotificacionId() {
		return this.masivaNotificacionId;
	}

	public void setMasivaNotificacionId(int masivaNotificacionId) {
		this.masivaNotificacionId = masivaNotificacionId;
	}

	public String getArchivo() {
		return this.archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public int getCantActos() {
		return this.cantActos;
	}

	public void setCantActos(int cantActos) {
		this.cantActos = cantActos;
	}

	public int getCantError() {
		return this.cantError;
	}

	public void setCantError(int cantError) {
		this.cantError = cantError;
	}

	public int getCantRec() {
		return this.cantRec;
	}

	public void setCantRec(int cantRec) {
		this.cantRec = cantRec;
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