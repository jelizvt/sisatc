package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DeudaPagosPredialDTO implements Serializable {

	private static final long serialVersionUID = 3685127841578229990L;

	private BigDecimal totalDeuda = BigDecimal.ZERO;
	private BigDecimal totalCancelado = BigDecimal.ZERO;
	private BigDecimal deudaMenosCancelado = BigDecimal.ZERO;

	public DeudaPagosPredialDTO() {
	}

	public BigDecimal getTotalDeuda() {
		return totalDeuda;
	}

	public void setTotalDeuda(BigDecimal totalDeuda) {
		this.totalDeuda = totalDeuda;
	}

	public BigDecimal getTotalCancelado() {
		return totalCancelado;
	}

	public void setTotalCancelado(BigDecimal totalCancelado) {
		this.totalCancelado = totalCancelado;
	}

	public BigDecimal getDeudaMenosCancelado() {
		return deudaMenosCancelado;
	}

	public void setDeudaMenosCancelado(BigDecimal deudaMenosCancelado) {
		this.deudaMenosCancelado = deudaMenosCancelado;
	}
}
