package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the dt_cuota_concepto database table.
 * 
 */
@Entity
@Table(name="dt_cuota_concepto")
public class DtCuotaConcepto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cuota_concepto_id")
	private int cuotaConceptoId;

	@Column(name="concepto_id")
	private int conceptoId;

	@Column(name="cuota_derecho_emision")
	private int cuotaDerechoEmision;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="monto_derecho_emision")
	private BigDecimal montoDerechoEmision;

	@Column(name="nro_cuotas")
	private int nroCuotas;

	private int periodo;

	@Column(name="subconcepto_id")
	private int subconceptoId;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public DtCuotaConcepto() {
    }

	public int getCuotaConceptoId() {
		return this.cuotaConceptoId;
	}

	public void setCuotaConceptoId(int cuotaConceptoId) {
		this.cuotaConceptoId = cuotaConceptoId;
	}

	public int getConceptoId() {
		return this.conceptoId;
	}

	public void setConceptoId(int conceptoId) {
		this.conceptoId = conceptoId;
	}

	public int getCuotaDerechoEmision() {
		return this.cuotaDerechoEmision;
	}

	public void setCuotaDerechoEmision(int cuotaDerechoEmision) {
		this.cuotaDerechoEmision = cuotaDerechoEmision;
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

	public BigDecimal getMontoDerechoEmision() {
		return this.montoDerechoEmision;
	}

	public void setMontoDerechoEmision(BigDecimal montoDerechoEmision) {
		this.montoDerechoEmision = montoDerechoEmision;
	}

	public int getNroCuotas() {
		return this.nroCuotas;
	}

	public void setNroCuotas(int nroCuotas) {
		this.nroCuotas = nroCuotas;
	}

	public int getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public int getSubconceptoId() {
		return this.subconceptoId;
	}

	public void setSubconceptoId(int subconceptoId) {
		this.subconceptoId = subconceptoId;
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