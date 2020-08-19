package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the cc_acto database table.
 * 
 */
@Entity
@Table(name="dt_determinacion_seguridad")
public class DtDeterminacionSeguridad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="determinacion_seguridad_id")
	private Integer determinacionSeguridadId;

	@Column(name="categoria_uso_id")
	private Integer categoriaUsoId;

	@Column(name="determinacion_arbitrios_id")
	private Integer determinacionArbitriosId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="tasa_anual")
	private BigDecimal tasaAnual;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

	public Integer getDeterminacionSeguridadId() {
		return determinacionSeguridadId;
	}

	public void setDeterminacionSeguridadId(Integer determinacionSeguridadId) {
		this.determinacionSeguridadId = determinacionSeguridadId;
	}

	public Integer getCategoriaUsoId() {
		return categoriaUsoId;
	}

	public void setCategoriaUsoId(Integer categoriaUsoId) {
		this.categoriaUsoId = categoriaUsoId;
	}

	public Integer getDeterminacionArbitriosId() {
		return determinacionArbitriosId;
	}

	public void setDeterminacionArbitriosId(Integer determinacionArbitriosId) {
		this.determinacionArbitriosId = determinacionArbitriosId;
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

	public BigDecimal getTasaAnual() {
		return tasaAnual;
	}

	public void setTasaAnual(BigDecimal tasaAnual) {
		this.tasaAnual = tasaAnual;
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

}