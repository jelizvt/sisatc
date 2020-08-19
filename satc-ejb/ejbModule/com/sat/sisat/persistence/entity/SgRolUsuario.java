package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the sg_rol_usuario database table.
 * 
 */
@Entity
@Table(name="sg_rol_usuario")
public class SgRolUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="rol_usuario_id")
	private int rolUsuarioId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="rol_id")
	private int rolId;

	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="terminal")
	private String terminal;

    public SgRolUsuario() {
    }

	public int getRolUsuarioId() {
		return this.rolUsuarioId;
	}

	public void setRolUsuarioId(int rolUsuarioId) {
		this.rolUsuarioId = rolUsuarioId;
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

	public int getRolId() {
		return this.rolId;
	}

	public void setRolId(int rolId) {
		this.rolId = rolId;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
}