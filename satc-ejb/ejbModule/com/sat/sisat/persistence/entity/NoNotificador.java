package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the no_notificador database table.
 * 
 */
@Entity
@Table(name="no_notificador")
public class NoNotificador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="notificador_id")
	private int notificadorId;

	@Column(name="apellidos_nombres")
	private String apellidosNombres;

	private String dni;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public NoNotificador() {
    }

	public int getNotificadorId() {
		return this.notificadorId;
	}

	public void setNotificadorId(int notificadorId) {
		this.notificadorId = notificadorId;
	}

	public String getApellidosNombres() {
		return this.apellidosNombres;
	}

	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
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