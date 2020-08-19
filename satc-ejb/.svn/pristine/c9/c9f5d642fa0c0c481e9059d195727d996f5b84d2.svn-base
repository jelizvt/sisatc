package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the cc_acto database table.
 * 
 */
@Entity
@Table(name="dt_determinacion_arbitrios")
@NamedQueries({
@NamedQuery(name="getAllDtDeterminacionArbitrioByDjId", query="SELECT m FROM DtDeterminacionArbitrio m WHERE m.estado='1' AND m.djId=:p_dj_id "),
})
public class DtDeterminacionArbitrio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="determinacion_arbitrios_id")
	private Integer determinacionArbitriosId;

	@Column(name="arbitrio_limpieza")
	private BigDecimal arbitrioLimpieza;

	@Column(name="arbitrio_parques")
	private BigDecimal arbitrioParques;

	@Column(name="arbitrio_recojo")
	private BigDecimal arbitrioRecojo;

	@Column(name="arbitrio_seguridad")
	private BigDecimal arbitrioSeguridad;

	@Column(name="grupo_cercania_id")
	private Integer grupoCercaniaId;

	@Column(name="dj_id")
	private Integer djId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="frecuencia_limpieza_id")
	private Integer frecuenciaLimpiezaId;

	@Column(name="frecuencia_recojo_id")
	private Integer frecuenciaRecojoId;

	@Column(name="frente_ml_limpieza")
	private BigDecimal frenteMlLimpieza;

	@Column(name="tasa_ml_anual_limpieza")
	private BigDecimal tasaMlAnualLimpieza;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="zona_seguridad_id")
	private Integer zonaSeguridadId;

	/**
	 * Campos que se usan para almacenar el calculo de arbitrios antes de las subvenciones 
	 */
	@Column(name="arbitrio_limpieza_presubven")
	private BigDecimal arbitrioLimpiezaPreSubven;

	@Column(name="arbitrio_recojo_presubven")
	private BigDecimal arbitrioRecojoPreSubven;
	
	@Column(name="arbitrio_parques_presubven")
	private BigDecimal arbitrioParquesPreSubven;
	
	@Column(name="arbitrio_seguridad_presubven")
	private BigDecimal arbitrioSeguridadPreSubven;
	
	/**
	 * Campos que se usan para almacenar el calculo de arbitrios antes de los beneficios 
	 */
	@Column(name="arbitrio_limpieza_prebenef")
	private BigDecimal arbitrioLimpiezaPreBenef;

	@Column(name="arbitrio_recojo_prebenef")
	private BigDecimal arbitrioRecojoPreBenef;
	
	@Column(name="arbitrio_parques_prebenef")
	private BigDecimal arbitrioParquesPreBenef;
	
	@Column(name="arbitrio_seguridad_prebenef")
	private BigDecimal arbitrioSeguridadPreBenef;
	
	public BigDecimal getArbitrioLimpiezaPreSubven() {
		return arbitrioLimpiezaPreSubven;
	}

	public void setArbitrioLimpiezaPreSubven(BigDecimal arbitrioLimpiezaPreSubven) {
		this.arbitrioLimpiezaPreSubven = arbitrioLimpiezaPreSubven;
	}

	public BigDecimal getArbitrioRecojoPreSubven() {
		return arbitrioRecojoPreSubven;
	}

	public void setArbitrioRecojoPreSubven(BigDecimal arbitrioRecojoPreSubven) {
		this.arbitrioRecojoPreSubven = arbitrioRecojoPreSubven;
	}

	public BigDecimal getArbitrioParquesPreSubven() {
		return arbitrioParquesPreSubven;
	}

	public void setArbitrioParquesPreSubven(BigDecimal arbitrioParquesPreSubven) {
		this.arbitrioParquesPreSubven = arbitrioParquesPreSubven;
	}

	public BigDecimal getArbitrioSeguridadPreSubven() {
		return arbitrioSeguridadPreSubven;
	}

	public void setArbitrioSeguridadPreSubven(BigDecimal arbitrioSeguridadPreSubven) {
		this.arbitrioSeguridadPreSubven = arbitrioSeguridadPreSubven;
	}

	public BigDecimal getArbitrioLimpiezaPreBenef() {
		return arbitrioLimpiezaPreBenef;
	}

	public void setArbitrioLimpiezaPreBenef(BigDecimal arbitrioLimpiezaPreBenef) {
		this.arbitrioLimpiezaPreBenef = arbitrioLimpiezaPreBenef;
	}

	public BigDecimal getArbitrioRecojoPreBenef() {
		return arbitrioRecojoPreBenef;
	}

	public void setArbitrioRecojoPreBenef(BigDecimal arbitrioRecojoPreBenef) {
		this.arbitrioRecojoPreBenef = arbitrioRecojoPreBenef;
	}

	public BigDecimal getArbitrioParquesPreBenef() {
		return arbitrioParquesPreBenef;
	}

	public void setArbitrioParquesPreBenef(BigDecimal arbitrioParquesPreBenef) {
		this.arbitrioParquesPreBenef = arbitrioParquesPreBenef;
	}

	public BigDecimal getArbitrioSeguridadPreBenef() {
		return arbitrioSeguridadPreBenef;
	}

	public void setArbitrioSeguridadPreBenef(BigDecimal arbitrioSeguridadPreBenef) {
		this.arbitrioSeguridadPreBenef = arbitrioSeguridadPreBenef;
	}

	public Integer getGrupoCercaniaId() {
		return grupoCercaniaId;
	}

	public void setGrupoCercaniaId(Integer grupoCercaniaId) {
		this.grupoCercaniaId = grupoCercaniaId;
	}
	
	public Integer getDeterminacionArbitriosId() {
		return determinacionArbitriosId;
	}

	public void setDeterminacionArbitriosId(Integer determinacionArbitriosId) {
		this.determinacionArbitriosId = determinacionArbitriosId;
	}

	public BigDecimal getArbitrioLimpieza() {
		return arbitrioLimpieza;
	}

	public void setArbitrioLimpieza(BigDecimal arbitrioLimpieza) {
		this.arbitrioLimpieza = arbitrioLimpieza;
	}

	public BigDecimal getArbitrioParques() {
		return arbitrioParques;
	}

	public void setArbitrioParques(BigDecimal arbitrioParques) {
		this.arbitrioParques = arbitrioParques;
	}

	public BigDecimal getArbitrioRecojo() {
		return arbitrioRecojo;
	}

	public void setArbitrioRecojo(BigDecimal arbitrioRecojo) {
		this.arbitrioRecojo = arbitrioRecojo;
	}

	public BigDecimal getArbitrioSeguridad() {
		return arbitrioSeguridad;
	}

	public void setArbitrioSeguridad(BigDecimal arbitrioSeguridad) {
		this.arbitrioSeguridad = arbitrioSeguridad;
	}
	
	public Integer getDjId() {
		return djId;
	}

	public void setDjId(Integer djId) {
		this.djId = djId;
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

	public Integer getFrecuenciaLimpiezaId() {
		return frecuenciaLimpiezaId;
	}

	public void setFrecuenciaLimpiezaId(Integer frecuenciaLimpiezaId) {
		this.frecuenciaLimpiezaId = frecuenciaLimpiezaId;
	}

	public Integer getFrecuenciaRecojoId() {
		return frecuenciaRecojoId;
	}

	public void setFrecuenciaRecojoId(Integer frecuenciaRecojoId) {
		this.frecuenciaRecojoId = frecuenciaRecojoId;
	}

	public BigDecimal getFrenteMlLimpieza() {
		return frenteMlLimpieza;
	}

	public void setFrenteMlLimpieza(BigDecimal frenteMlLimpieza) {
		this.frenteMlLimpieza = frenteMlLimpieza;
	}

	public BigDecimal getTasaMlAnualLimpieza() {
		return tasaMlAnualLimpieza;
	}

	public void setTasaMlAnualLimpieza(BigDecimal tasaMlAnualLimpieza) {
		this.tasaMlAnualLimpieza = tasaMlAnualLimpieza;
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

	public Integer getZonaSeguridadId() {
		return zonaSeguridadId;
	}

	public void setZonaSeguridadId(Integer zonaSeguridadId) {
		this.zonaSeguridadId = zonaSeguridadId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}