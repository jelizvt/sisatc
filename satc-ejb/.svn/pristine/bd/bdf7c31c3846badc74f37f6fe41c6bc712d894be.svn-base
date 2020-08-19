package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.sat.sisat.persistence.entity.RpDjconstruccion;

public class NivelConstruccion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6658383484189398254L;
	private int djId;
	private int construccionId;
	private BigDecimal areaConstruccion;
	private int nroNivel;
	private int tipoNivelId;
	private String denoTipoNivel;
	
	private BigDecimal areaUsada;
	private BigDecimal areaAsignada;
	private String mesInicio;
	private String mesFin;
	private Boolean confirmacion;
	
	private String referencia;
	private String seccion;
	
	public NivelConstruccion(RpDjconstruccion obj){
		this.djId=obj.getDjId();
		this.construccionId=obj.getConstruccionId();
		this.areaConstruccion=obj.getAreaConstruccion();
		this.nroNivel=obj.getNroNivel();
		this.tipoNivelId=obj.getTipoNivelId();
		this.denoTipoNivel=obj.getDentiponivel();
		this.seccion=obj.getSeccion();
	}
	
	public int getDjId() {
		return djId;
	}
	public void setDjId(int djId) {
		this.djId = djId;
	}
	public int getConstruccionId() {
		return construccionId;
	}
	public void setConstruccionId(int construccionId) {
		this.construccionId = construccionId;
	}
	public BigDecimal getAreaConstruccion() {
		return areaConstruccion;
	}
	public void setAreaConstruccion(BigDecimal areaConstruccion) {
		this.areaConstruccion = areaConstruccion;
	}
	public int getNroNivel() {
		return nroNivel;
	}
	public void setNroNivel(int nroNivel) {
		this.nroNivel = nroNivel;
	}
	public int getTipoNivelId() {
		return tipoNivelId;
	}
	public void setTipoNivelId(int tipoNivelId) {
		this.tipoNivelId = tipoNivelId;
	}
	public String getDenoTipoNivel() {
		return denoTipoNivel;
	}
	public void setDenoTipoNivel(String denoTipoNivel) {
		this.denoTipoNivel = denoTipoNivel;
	}
	public BigDecimal getAreaUsada() {
		return areaUsada;
	}
	public void setAreaUsada(BigDecimal areaUsada) {
		this.areaUsada = areaUsada;
	}
	public BigDecimal getAreaAsignada() {
		return areaAsignada;
	}
	public void setAreaAsignada(BigDecimal areaAsignada) {
		this.areaAsignada = areaAsignada;
	}
	
	public Boolean getConfirmacion() {
		return confirmacion;
	}
	public void setConfirmacion(Boolean confirmacion) {
		this.confirmacion = confirmacion;
	}
	public String getMesInicio() {
		return mesInicio;
	}

	public void setMesInicio(String mesInicio) {
		this.mesInicio = mesInicio;
	}

	public String getMesFin() {
		return mesFin;
	}

	public void setMesFin(String mesFin) {
		this.mesFin = mesFin;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}	
}
