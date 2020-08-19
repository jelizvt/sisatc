package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the pa_medio_probatorio database table.
 * 
 */
@Entity
@Table(name="pa_medio_probatorio")
@NamedQuery(name="getAllPaMedioProbatorio", query="SELECT m FROM PaMedioProbatorio m WHERE m.estado='1'")
public class PaMedioProbatorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="medio_probatorio_id")
	private Integer medioProbatorioId;

	private String descripcion;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

    public Integer getMedioProbatorioId() {
		return medioProbatorioId;
	}

	public void setMedioProbatorioId(Integer medioProbatorioId) {
		this.medioProbatorioId = medioProbatorioId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
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

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public PaMedioProbatorio() {
    	
    }
}