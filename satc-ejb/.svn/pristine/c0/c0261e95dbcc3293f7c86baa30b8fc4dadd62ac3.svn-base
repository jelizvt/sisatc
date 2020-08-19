package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rp_altitud database table.
 * 
 */
@Entity
@Table(name="rp_altitud")
public class RpAltitud implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="altitud_id")
	private int altitudId;

	private String descripcion;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="por_defecto")
	private String porDefecto;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public RpAltitud() {
    }

	public int getAltitudId() {
		return this.altitudId;
	}

	public void setAltitudId(int altitudId) {
		this.altitudId = altitudId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionCorta() {
		return this.descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
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

	public String getPorDefecto() {
		return this.porDefecto;
	}

	public void setPorDefecto(String porDefecto) {
		this.porDefecto = porDefecto;
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