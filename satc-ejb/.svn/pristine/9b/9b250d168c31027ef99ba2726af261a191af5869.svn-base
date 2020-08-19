package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the sg_usuario_acceso database table.
 * 
 */
@Entity
@Table(name="sg_usuario_acceso")
public class SgUsuarioAcceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="usuario_acceso_id")
	private int usuarioAccesoId;
	
	@Column(name="rol_usuario_id")
	private int rolUsuarioId;

	@Column(name="fecha_inicio")
	private Timestamp fechaInicio;

	@Column(name="fecha_fin")
	private Timestamp fechaFin;
	
	@Column(name="estado")
	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="terminal")
	private String terminal;
	
	@Column(name="usuario_id")
	private int usuarioId;

    public SgUsuarioAcceso() {
    }

	public int getUsuarioAccesoId() {
		return usuarioAccesoId;
	}

	public void setUsuarioAccesoId(int usuarioAccesoId) {
		this.usuarioAccesoId = usuarioAccesoId;
	}

	public int getRolUsuarioId() {
		return rolUsuarioId;
	}

	public void setRolUsuarioId(int rolUsuarioId) {
		this.rolUsuarioId = rolUsuarioId;
	}

	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}
}