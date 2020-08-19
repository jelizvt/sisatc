package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.math.BigDecimal;
public class CjTupaDTO implements Serializable {

	
	private static final long serialVersionUID = 9114729860328230778L;
	
	private int tupaId;
	private String descripcion;
	private int cant = 1;
	private BigDecimal subTotal = new BigDecimal("0.00");
	private BigDecimal tasa;
	private int periodo;
	private BigDecimal descuento = new BigDecimal("0.00");
	private BigDecimal totalDescuento = new BigDecimal("0.00");
	private BigDecimal total = new BigDecimal("0.00");
	
	
	
	public int getTupaId() {
		return tupaId;
	}
	public void setTupaId(int tupaId) {
		this.tupaId = tupaId;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getTasa() {
		return tasa;
	}
	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public int getCant() {
		return cant;
	}

	public void setCant(int cant) {
		
		this.cant = cant;		
		this.subTotal = this.tasa.multiply(new BigDecimal(cant));
		this.totalDescuento=this.subTotal.multiply(this.descuento);
		this.subTotal=this.subTotal.subtract(this.totalDescuento);
		
	}
	public BigDecimal getDescuento() {
		
		return this.totalDescuento;
	}
	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;		
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getTotalDescuento() {
		return totalDescuento;
	}
	public void setTotalDescuento(BigDecimal totalDescuento) {
		this.totalDescuento = totalDescuento;
	}
}
