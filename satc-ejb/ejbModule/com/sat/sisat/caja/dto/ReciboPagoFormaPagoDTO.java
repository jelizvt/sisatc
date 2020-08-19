package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReciboPagoFormaPagoDTO implements Serializable{
	
	private static final long serialVersionUID = 5568116970030237756L;
	
	private int reciboId;
	private int reciboDetalleId;
	private String formaPagoDes;
	private String monedaDes;
	private String bancoDes;
	private String tarjetaDes;
	private BigDecimal monto;
	private BigDecimal montoTotalSoles;
	private String nroCheque;
	private String observacion;
	
	public ReciboPagoFormaPagoDTO(){
	}

	public ReciboPagoFormaPagoDTO(int reciboId, int reciboDetalleId,
			String formaPagoDes, String monedaDes, String bancoDes,
			String tarjetaDes, BigDecimal monto, BigDecimal montoTotalSoles,
			String nroCheque, String observacion) {
		super();
		this.reciboId = reciboId;
		this.reciboDetalleId = reciboDetalleId;
		this.formaPagoDes = formaPagoDes;
		this.monedaDes = monedaDes;
		this.bancoDes = bancoDes;
		this.tarjetaDes = tarjetaDes;
		this.monto = monto;
		this.montoTotalSoles = montoTotalSoles;
		this.nroCheque = nroCheque;
		this.observacion = observacion;
	}

	public int getReciboId() {
		return reciboId;
	}

	public void setReciboId(int reciboId) {
		this.reciboId = reciboId;
	}

	public int getReciboDetalleId() {
		return reciboDetalleId;
	}

	public void setReciboDetalleId(int reciboDetalleId) {
		this.reciboDetalleId = reciboDetalleId;
	}

	public String getFormaPagoDes() {
		return formaPagoDes;
	}

	public void setFormaPagoDes(String formaPagoDes) {
		this.formaPagoDes = formaPagoDes;
	}

	public String getMonedaDes() {
		return monedaDes;
	}

	public void setMonedaDes(String monedaDes) {
		this.monedaDes = monedaDes;
	}

	public String getBancoDes() {
		return bancoDes;
	}

	public void setBancoDes(String bancoDes) {
		this.bancoDes = bancoDes;
	}

	public String getTarjetaDes() {
		return tarjetaDes;
	}

	public void setTarjetaDes(String tarjetaDes) {
		this.tarjetaDes = tarjetaDes;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public BigDecimal getMontoTotalSoles() {
		return montoTotalSoles;
	}

	public void setMontoTotalSoles(BigDecimal montoTotalSoles) {
		this.montoTotalSoles = montoTotalSoles;
	}

	public String getNroCheque() {
		return nroCheque;
	}

	public void setNroCheque(String nroCheque) {
		this.nroCheque = nroCheque;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
}
