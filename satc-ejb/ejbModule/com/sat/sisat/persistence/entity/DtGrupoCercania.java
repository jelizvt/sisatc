package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the dt_grupo_cercania database table.
 * 
 */
@Entity
@Table(name="dt_grupo_cercania")
public class DtGrupoCercania implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="grupo_cercania_id")
	private Integer grupoCercaniaId;

	@Column(name="costo_anual")
	private BigDecimal costoAnual;

	@Column(name="descipcion_corta")
	private String descipcionCorta;

	private String descripcion;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="indice_disfrute")
	private BigDecimal indiceDisfrute;

	@Column(name="periodo")
	private Integer periodo;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="porcentaje_subvencion")
	private BigDecimal porcentajeSubvencion;

    public DtGrupoCercania() {
    }

	public Integer getGrupoCercaniaId() {
		return this.grupoCercaniaId;
	}

	public void setGrupoCercaniaId(Integer grupoCercaniaId) {
		this.grupoCercaniaId = grupoCercaniaId;
	}

	public BigDecimal getCostoAnual() {
		return this.costoAnual;
	}

	public void setCostoAnual(BigDecimal costoAnual) {
		this.costoAnual = costoAnual;
	}

	public String getDescipcionCorta() {
		return this.descipcionCorta;
	}

	public void setDescipcionCorta(String descipcionCorta) {
		this.descipcionCorta = descipcionCorta;
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

	public BigDecimal getIndiceDisfrute() {
		return this.indiceDisfrute;
	}

	public void setIndiceDisfrute(BigDecimal indiceDisfrute) {
		this.indiceDisfrute = indiceDisfrute;
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

	public BigDecimal getPorcentajeSubvencion() {
		return porcentajeSubvencion;
	}

	public void setPorcentajeSubvencion(BigDecimal porcentajeSubvencion) {
		this.porcentajeSubvencion = porcentajeSubvencion;
	}

}