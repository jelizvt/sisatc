package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the dt_zona_seguridad database table.
 * 
 */
@Entity
@Table(name="dt_zona_seguridad")
@NamedQuery(name="getAllDtZonaSeguridadByPeriodo", query="SELECT m FROM DtZonaSeguridad m WHERE m.estado='1' AND m.periodo=:p_periodo ")
public class DtZonaSeguridad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="zona_seguridad_id")
	private Integer zonaSeguridadId;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	private String descripcion;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="indice_peligrosidad")
	private BigDecimal indicePeligrosidad;

	@Column(name = "periodo")
	private Integer periodo;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

    public DtZonaSeguridad() {
    }

	public Integer getZonaSeguridadId() {
		return this.zonaSeguridadId;
	}

	public void setZonaSeguridadId(Integer zonaSeguridadId) {
		this.zonaSeguridadId = zonaSeguridadId;
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

	public BigDecimal getIndicePeligrosidad() {
		return this.indicePeligrosidad;
	}

	public void setIndicePeligrosidad(BigDecimal indicePeligrosidad) {
		this.indicePeligrosidad = indicePeligrosidad;
	}

	public Integer getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
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

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

}