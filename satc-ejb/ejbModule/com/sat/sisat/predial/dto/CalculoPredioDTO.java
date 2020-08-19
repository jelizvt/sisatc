package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CalculoPredioDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private int tramo;
	private BigDecimal monto;
	private BigDecimal tasa;
	private BigDecimal impuesto;
	
	public int getTramo() {
		return tramo;
	}
	public void setTramo(int tramo) {
		this.tramo = tramo;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	public BigDecimal getTasa() {
		return tasa;
	}
	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}
	public BigDecimal getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(BigDecimal impuesto) {
		this.impuesto = impuesto;
	}
	
}
