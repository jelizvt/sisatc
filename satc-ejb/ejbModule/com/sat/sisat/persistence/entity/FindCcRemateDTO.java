package com.sat.sisat.persistence.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class FindCcRemateDTO {
	private Integer propietarioId;
	private String propietario;
	private String placa;
	private BigDecimal montoAdjudicado;
	private Timestamp fechaRemate;
	private  String justificacion;

	public FindCcRemateDTO() {
		super();
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public BigDecimal getMontoAdjudicado() {
		return montoAdjudicado;
	}

	public void setMontoAdjudicado(BigDecimal montoAdjudicado) {
		this.montoAdjudicado = montoAdjudicado;
	}

	public Timestamp getFechaRemate() {
		return fechaRemate;
	}

	public void setFechaRemate(Timestamp fechaRemate) {
		this.fechaRemate = fechaRemate;
	}

	public Integer getPropietarioId() {
		return propietarioId;
	}

	public void setPropietarioId(Integer propietarioId) {
		this.propietarioId = propietarioId;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}

	public String getPropietario() {
		return propietario;
	}
}
