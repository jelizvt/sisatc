package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.math.BigDecimal;


public class RecojoArbitriosDTO implements Serializable {
	
	private Integer categoriaUsoId;
	private String uso;
	private BigDecimal areaM2;
	private BigDecimal costoM2Anual;
	
	/** antes de beneficio */
	private BigDecimal montoRecojoUsoPreSubven;//areaM2*costoM2Anual
	private BigDecimal montoRecojoUso;//montoRecojoPreSubven*(1-%)
	
	public BigDecimal getMontoRecojoUsoPreSubven() {
		return montoRecojoUsoPreSubven;
	}
	public void setMontoRecojoUsoPreSubven(BigDecimal montoRecojoUsoPreSubven) {
		this.montoRecojoUsoPreSubven = montoRecojoUsoPreSubven;
	}
	public BigDecimal getMontoRecojoUso() {
		return montoRecojoUso;
	}
	public void setMontoRecojoUso(BigDecimal montoRecojoUso) {
		this.montoRecojoUso = montoRecojoUso;
	}
	public Integer getCategoriaUsoId() {
		return categoriaUsoId;
	}
	public void setCategoriaUsoId(Integer categoriaUsoId) {
		this.categoriaUsoId = categoriaUsoId;
	}
	public BigDecimal getAreaM2() {
		return areaM2;
	}
	public void setAreaM2(BigDecimal areaM2) {
		this.areaM2 = areaM2;
	}
	public BigDecimal getCostoM2Anual() {
		return costoM2Anual;
	}
	public void setCostoM2Anual(BigDecimal costoM2Anual) {
		this.costoM2Anual = costoM2Anual;
	}
	public String getUso() {
		return uso;
	}
	public void setUso(String uso) {
		this.uso = uso;
	}
		
}
