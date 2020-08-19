package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReciboPagoDescuentoDetalleDTO implements Serializable{
	
	private static final long serialVersionUID = 5568116970030237756L;
	
	private int reciboId;	
	private BigDecimal montoDescuento;	
	
	private String referencia;	
	
	public ReciboPagoDescuentoDetalleDTO() {
		super();
	}

	public ReciboPagoDescuentoDetalleDTO(int reciboId, BigDecimal montoDescuento, String referencia) {
		super();
		this.reciboId = reciboId;
		this.montoDescuento = montoDescuento;
		this.referencia = referencia;
	}

	public int getReciboId() {
		return reciboId;
	}

	public BigDecimal getMontoDescuento() {
		return montoDescuento;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReciboId(int reciboId) {
		this.reciboId = reciboId;
	}

	public void setMontoDescuento(BigDecimal montoDescuento) {
		this.montoDescuento = montoDescuento;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}	
	
		
}
