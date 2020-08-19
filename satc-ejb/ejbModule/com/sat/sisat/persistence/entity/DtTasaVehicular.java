package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the dt_tasa_vehicular database table.
 * 
 */
@Entity
@Table(name="dt_tasa_vehicular")
public class DtTasaVehicular implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer periodo;

	@Column(name="annos_afecta")
	private Integer annosAfecta;

	private int cuotas;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="porc_uit_min")
	private BigDecimal porcUitMin;

	@Column(name="tasa_anual")
	private BigDecimal tasaAnual;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

    public DtTasaVehicular() {
    }

	public int getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public Integer getAnnosAfecta() {
		return this.annosAfecta;
	}

	public void setAnnosAfecta(Integer annosAfecta) {
		this.annosAfecta = annosAfecta;
	}

	public int getCuotas() {
		return this.cuotas;
	}

	public void setCuotas(int cuotas) {
		this.cuotas = cuotas;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public BigDecimal getPorcUitMin() {
		return this.porcUitMin;
	}

	public void setPorcUitMin(BigDecimal porcUitMin) {
		this.porcUitMin = porcUitMin;
	}

	public BigDecimal getTasaAnual() {
		return this.tasaAnual;
	}

	public void setTasaAnual(BigDecimal tasaAnual) {
		this.tasaAnual = tasaAnual;
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