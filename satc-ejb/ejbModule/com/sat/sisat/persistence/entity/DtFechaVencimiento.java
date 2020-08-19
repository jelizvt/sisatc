package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the dt_fecha_vencimiento database table.
 * 
 */
@Entity
@Table(name="dt_fecha_vencimiento")
public class DtFechaVencimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="vencimiento_id")
	private int vencimientoId;

	@Column(name="concepto_id")
	private int conceptoId;

	@Column(name="cuota")
	private int cuota;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="fecha_vencimiento")
	private Timestamp fechaVencimiento;

	@Column(name="mes_fin",nullable = true)
	private Integer mesFin;

	@Column(name="mes_inicio",nullable = true)
	private Integer mesInicio;

	@Column(name="periodo")
	private int periodo;

	@Column(name="subconcepto_id")
	private int subconceptoId;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public DtFechaVencimiento() {
    }

	public int getVencimientoId() {
		return this.vencimientoId;
	}

	public void setVencimientoId(int vencimientoId) {
		this.vencimientoId = vencimientoId;
	}

	public int getConceptoId() {
		return this.conceptoId;
	}

	public void setConceptoId(int conceptoId) {
		this.conceptoId = conceptoId;
	}

	public int getCuota() {
		return this.cuota;
	}

	public void setCuota(int cuota) {
		this.cuota = cuota;
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

	public Timestamp getFechaVencimiento() {
		return this.fechaVencimiento;
	}

	public void setFechaVencimiento(Timestamp fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Integer getMesFin() {
		return this.mesFin;
	}

	public void setMesFin(Integer mesFin) {
		this.mesFin = mesFin;
	}

	public Integer getMesInicio() {
		return this.mesInicio;
	}

	public void setMesInicio(Integer mesInicio) {
		this.mesInicio = mesInicio;
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