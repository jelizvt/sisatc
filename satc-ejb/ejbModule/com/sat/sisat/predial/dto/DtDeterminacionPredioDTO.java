package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;

public class DtDeterminacionPredioDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7539246699866532100L;
	private Timestamp fechaDeclaracion;
	private Integer djId;
	private Integer predioId;
	private Integer motivoDeclaracionId;
	private String tipoPredio;
	private String codigoPredio;
	private Date 	fechaAdquisicion;
	private Double porcPropiedad;
	private String descDomicilio;
	//--
	private Double areaTerreno;
	private Double areaTerrenoHas;
	private Double arancel;
	private Double valorTerreno;
	private Double valorConstruccion;
	private Double valorInstalacion;

	private Double valorPredio;
	private Double baseImponible;
	private Double baseExonerada;
	private Double baseAfecta;
	private Double valorImpuesto;
	private String flagInafectacion;
	private Double valorInafectacion;
	//--
	private String estado;
	private String fiscalizado;
	private String fiscaAceptada;
	private String fiscaCerrada;
	//--
	
	private String secano;
	
	public String getFiscalizado() {
		return fiscalizado;
	}
	public void setFiscalizado(String fiscalizado) {
		this.fiscalizado = fiscalizado;
	}
	public String getFiscaAceptada() {
		return fiscaAceptada;
	}
	public void setFiscaAceptada(String fiscaAceptada) {
		this.fiscaAceptada = fiscaAceptada;
	}
	public String getFiscaCerrada() {
		return fiscaCerrada;
	}
	public void setFiscaCerrada(String fiscaCerrada) {
		this.fiscaCerrada = fiscaCerrada;
	}
	public Double getAreaTerreno() {
		return areaTerreno;
	}
	public void setAreaTerreno(Double areaTerreno) {
		this.areaTerreno = areaTerreno;
	}
	public Double getArancel() {
		return arancel;
	}
	public void setArancel(Double arancel) {
		this.arancel = arancel;
	}
	public Double getValorTerreno() {
		return valorTerreno;
	}
	public void setValorTerreno(Double valorTerreno) {
		this.valorTerreno = valorTerreno;
	}
	public Double getValorConstruccion() {
		return valorConstruccion;
	}
	public void setValorConstruccion(Double valorConstruccion) {
		this.valorConstruccion = valorConstruccion;
	}
	public Double getValorInstalacion() {
		return valorInstalacion;
	}
	public void setValorInstalacion(Double valorInstalacion) {
		this.valorInstalacion = valorInstalacion;
	}
	public Double getValorPredio() {
		return valorPredio;
	}
	public void setValorPredio(Double valorPredio) {
		this.valorPredio = valorPredio;
	}
	public Double getBaseImponible() {
		return baseImponible;
	}
	public void setBaseImponible(Double baseImponible) {
		this.baseImponible = baseImponible;
	}
	public Double getBaseExonerada() {
		return baseExonerada;
	}
	public void setBaseExonerada(Double baseExonerada) {
		this.baseExonerada = baseExonerada;
	}
	public Double getBaseAfecta() {
		return baseAfecta;
	}
	public void setBaseAfecta(Double baseAfecta) {
		this.baseAfecta = baseAfecta;
	}
	public Double getValorImpuesto() {
		return valorImpuesto;
	}
	public void setValorImpuesto(Double valorImpuesto) {
		this.valorImpuesto = valorImpuesto;
	}
	public String getFlagInafectacion() {
		return flagInafectacion;
	}
	public void setFlagInafectacion(String flagInafectacion) {
		this.flagInafectacion = flagInafectacion;
	}
	public Double getValorInafectacion() {
		return valorInafectacion;
	}
	public void setValorInafectacion(Double valorInafectacion) {
		this.valorInafectacion = valorInafectacion;
	}
	private Integer deterPredioId;
	
	public Integer getDeterPredioId() {
		return deterPredioId;
	}
	public void setDeterPredioId(Integer deterPredioId) {
		this.deterPredioId = deterPredioId;
	}
	public Timestamp getFechaDeclaracion() {
		return fechaDeclaracion;
	}
	public void setFechaDeclaracion(Timestamp fechaDeclaracion) {
		this.fechaDeclaracion = fechaDeclaracion;
	}
	public Integer getDjId() {
		return djId;
	}
	public void setDjId(Integer djId) {
		this.djId = djId;
	}
	public Integer getPredioId() {
		return predioId;
	}
	public void setPredioId(Integer predioId) {
		this.predioId = predioId;
	}
	public Integer getMotivoDeclaracionId() {
		return motivoDeclaracionId;
	}
	public void setMotivoDeclaracionId(Integer motivoDeclaracionId) {
		this.motivoDeclaracionId = motivoDeclaracionId;
	}
	public String getTipoPredio() {
		return tipoPredio;
	}
	public void setTipoPredio(String tipoPredio) {
		this.tipoPredio = tipoPredio;
	}
	public String getCodigoPredio() {
		return codigoPredio;
	}
	public void setCodigoPredio(String codigoPredio) {
		this.codigoPredio = codigoPredio;
	}
	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}
	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}
	public Double getPorcPropiedad() {
		return porcPropiedad;
	}
	public void setPorcPropiedad(Double porcPropiedad) {
		this.porcPropiedad = porcPropiedad;
	}
	public String getDescDomicilio() {
		return descDomicilio;
	}
	public void setDescDomicilio(String descDomicilio) {
		this.descDomicilio = descDomicilio;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getSecano() {
		return secano;
	}
	public void setSecano(String secano) {
		this.secano = secano;
	}
	public Double getAreaTerrenoHas() {
		return areaTerrenoHas;
	}
	public void setAreaTerrenoHas(Double areaTerrenoHas) {
		this.areaTerrenoHas = areaTerrenoHas;
	}

}
