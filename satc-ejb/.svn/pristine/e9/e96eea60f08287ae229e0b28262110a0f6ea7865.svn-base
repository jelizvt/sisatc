package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the cc_acto database table.
 * 
 */
@Entity
@Table(name="dt_factor_ofic")
@NamedQuery(name="getDtFactorOficByPeriodo", query="SELECT m FROM DtFactorOfic m WHERE m.estado='1' AND m.periodo=:p_periodo")
public class DtFactorOfic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="factor_ofic_id")
	private Integer factorOficId;
	
	@Column(name="periodo")
	private Integer periodo;
	
	@Column(name="factor")
	private BigDecimal factor;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String estado;
	
	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

	public Integer getFactorOficId() {
		return factorOficId;
	}

	public void setFactorOficId(Integer factorOficId) {
		this.factorOficId = factorOficId;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public BigDecimal getFactor() {
		return factor;
	}

	public void setFactor(BigDecimal factor) {
		this.factor = factor;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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