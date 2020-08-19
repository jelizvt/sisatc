package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the dt_zona_recoleccion database table.
 * 
 */
@Entity
@Table(name="dt_zona_recoleccion")
public class DtZonaRecoleccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="zona_recoleccion_id")
	private Integer zonaRecoleccionId;

	private String descripcion;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

    public DtZonaRecoleccion() {
    }

	public Integer getZonaRecoleccionId() {
		return this.zonaRecoleccionId;
	}

	public void setZonaRecoleccionId(Integer zonaRecoleccionId) {
		this.zonaRecoleccionId = zonaRecoleccionId;
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