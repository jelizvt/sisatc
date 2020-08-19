package com.sat.sisat.controlycobranza.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CarteraItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7973242438729489799L;

	private Integer personaId;

	private Integer anhoDeuda;

	private Integer determinacionId;

	private BigDecimal insoluto = BigDecimal.ZERO;

	private BigDecimal insolutoDscto = BigDecimal.ZERO;

	private BigDecimal reajuste = BigDecimal.ZERO;
	
	private BigDecimal reajusteDscto = BigDecimal.ZERO;

	private BigDecimal intereses = BigDecimal.ZERO;
	
	private BigDecimal interesesDscto = BigDecimal.ZERO;
	
	private BigDecimal totalDeuda = BigDecimal.ZERO;
	
	private BigDecimal totalCancelado = BigDecimal.ZERO;
	
	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public Integer getAnhoDeuda() {
		return anhoDeuda;
	}

	public void setAnhoDeuda(Integer anhoDeuda) {
		this.anhoDeuda = anhoDeuda;
	}

	public Integer getDeterminacionId() {
		return determinacionId;
	}

	public void setDeterminacionId(Integer determinacionId) {
		this.determinacionId = determinacionId;
	}

	public BigDecimal getInsoluto() {
		return insoluto;
	}

	public void setInsoluto(BigDecimal insoluto) {
		this.insoluto = insoluto;
	}

	public BigDecimal getInsolutoDscto() {
		return insolutoDscto;
	}

	public void setInsolutoDscto(BigDecimal insolutoDscto) {
		this.insolutoDscto = insolutoDscto;
	}

	public BigDecimal getReajuste() {
		return reajuste;
	}

	public void setReajuste(BigDecimal reajuste) {
		this.reajuste = reajuste;
	}

	public BigDecimal getReajusteDscto() {
		return reajusteDscto;
	}

	public void setReajusteDscto(BigDecimal reajusteDscto) {
		this.reajusteDscto = reajusteDscto;
	}

	public BigDecimal getIntereses() {
		return intereses;
	}

	public void setIntereses(BigDecimal intereses) {
		this.intereses = intereses;
	}

	public BigDecimal getInteresesDscto() {
		return interesesDscto;
	}

	public void setInteresesDscto(BigDecimal interesesDscto) {
		this.interesesDscto = interesesDscto;
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
}
