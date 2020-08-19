package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the dt_frecuencia_limpieza database table.
 * 
 */
@Entity
@Table(name="dt_frecuencia_limpieza")
@NamedQuery(name="getDtFrecuenciaLimpiezaByUbicacionId", query="SELECT m FROM DtFrecuenciaLimpieza m WHERE m.estado='1' AND m.ubicacionId=:p_ubicacion_id AND m.periodo=:p_periodo ")
public class DtFrecuenciaLimpieza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="frecuencia_limpieza_id")
	private Integer frecuenciaLimpiezaId;
	
	@Column(name="ubicacion_id")
	private Integer ubicacionId;
	
	@Column(name="periodo")
	private Integer periodo;

	@Column(name="frecuencia")
	private Integer frecuencia;
	
	@Column(name="tasa_ml_anual")
	private BigDecimal tasaMlAnual;
	
	@Column(name="subconcepto_id")
	private Integer subconceptoId;
	
	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="estado")
	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="terminal")
	private String terminal;

	@Column(name="concepto_id")
	private Integer conceptoId;
	
    public DtFrecuenciaLimpieza() {
    }

	public Integer getFrecuenciaLimpiezaId() {
		return this.frecuenciaLimpiezaId;
	}

	public void setFrecuenciaLimpiezaId(Integer frecuenciaLimpiezaId) {
		this.frecuenciaLimpiezaId = frecuenciaLimpiezaId;
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

	public Integer getFrecuencia() {
		return this.frecuencia;
	}

	public void setFrecuencia(Integer frecuencia) {
		this.frecuencia = frecuencia;
	}

	public Integer getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public Integer getSubconceptoId() {
		return this.subconceptoId;
	}

	public void setSubconceptoId(Integer subconceptoId) {
		this.subconceptoId = subconceptoId;
	}

	public BigDecimal getTasaMlAnual() {
		return this.tasaMlAnual;
	}

	public void setTasaMlAnual(BigDecimal tasaMlAnual) {
		this.tasaMlAnual = tasaMlAnual;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getUbicacionId() {
		return this.ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}

}