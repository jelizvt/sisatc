package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.sat.sisat.persistence.entity.CjPartida;

public class CjPartidaEntity extends CjPartida implements Serializable {

	
	private String descripcion_corta;
	private String periodo;
	private BigDecimal monto;
	private BigDecimal montoNeto;
	private BigDecimal descuento;
	

	public String getDescripcion_corta() {
		return descripcion_corta;
	}

	public void setDescripcion_corta(String descripcion_corta) {
		this.descripcion_corta = descripcion_corta;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getMontoNeto() {
		return montoNeto;
	}

	public void setMontoNeto(BigDecimal montoNeto) {
		this.montoNeto = montoNeto;
	}


}
