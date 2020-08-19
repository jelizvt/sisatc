package com.sat.sisat.valoresyresoluciones.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PagosActoDTO implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private String nroRecibo;
	private String concepto;
	private int nroCuota;
	private BigDecimal montoPago;
	private int anio;
	
	public String getNroRecibo() {
		return nroRecibo;
	}
	public void setNroRecibo(String nroRecibo) {
		this.nroRecibo = nroRecibo;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public int getNroCuota() {
		return nroCuota;
	}
	public void setNroCuota(int nroCuota) {
		this.nroCuota = nroCuota;
	}
	public BigDecimal getMontoPago() {
		return montoPago;
	}
	public void setMontoPago(BigDecimal montoPago) {
		this.montoPago = montoPago;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}

	
}
