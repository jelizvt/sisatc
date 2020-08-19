package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the pa_infraccion database table.
 * 
 */
@Entity
@Table(name="pa_infraccion")
public class PaInfraccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="infraccion_id")
	private Integer infraccionId;

	@Column(name="tipo_infraccion_id")
	private Integer tipoInfraccionId;
	
	private String descripcion;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	@Column(name="multa_uit")
	private BigDecimal multaUit;
	
	private Integer puntos;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;
	
	@Column(name="ley_id")
	private Integer leyId;

	@Column(name="nivel_gravedad_id")
	private Integer nivelGravedadId;

	@Column(name="subconcepto_id")
	private Integer subConceptoId;

	@Column(name = "concepto_id")
	private Integer conceptoId;

	public Integer getSubConceptoId() {
		return subConceptoId;
	}

	public void setSubConceptoId(Integer subConceptoId) {
		this.subConceptoId = subConceptoId;
	}

	public Integer getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}

	public Integer getInfraccionId() {
		return infraccionId;
	}

	public void setInfraccionId(Integer infraccionId) {
		this.infraccionId = infraccionId;
	}

	public Integer getTipoInfraccionId() {
		return tipoInfraccionId;
	}

	public void setTipoInfraccionId(Integer tipoInfraccionId) {
		this.tipoInfraccionId = tipoInfraccionId;
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

	public BigDecimal getMultaUit() {
		return multaUit;
	}

	public void setMultaUit(BigDecimal multaUit) {
		this.multaUit = multaUit;
	}

	public Integer getPuntos() {
		return puntos;
	}

	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
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

	public Integer getLeyId() {
		return leyId;
	}

	public void setLeyId(Integer leyId) {
		this.leyId = leyId;
	}

	public Integer getNivelGravedadId() {
		return nivelGravedadId;
	}

	public void setNivelGravedadId(Integer nivelGravedadId) {
		this.nivelGravedadId = nivelGravedadId;
	}
}