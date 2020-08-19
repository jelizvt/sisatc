package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DtDeterminacionConstruccionDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -194025080060431690L;
	private Integer nroNivel;
	private Integer antiguedad;
	private String descripcionNivel;
	private String muros;
	private String techo;
	private String pisos;
	private String puertas;
	private String revestimiento;
	private String bannos;
	private String electricos;
	private Double valorUnitario;
	private Double incremento;
	private Double valorUnitarioDepre;
	private Double areaConstruccion;
	private Double valorAreaConstruida;
	private Double valorAreaComun;
	private Double valorConstruccion;
	private Integer deterPredioId;
	private Integer construccionId;
	//--
	private Integer annoConstruccion;
	private Integer annoActualizacion;
	private String estadoConcervacion;
	private String materialPredominante;
	private String tipoDepreciacion;
	//--
	private String seccion;
	private String referencia;
	
	public Integer getAnnoConstruccion() {
		return annoConstruccion;
	}
	public void setAnnoConstruccion(Integer annoConstruccion) {
		this.annoConstruccion = annoConstruccion;
	}
	public String getEstadoConcervacion() {
		return estadoConcervacion;
	}
	public void setEstadoConcervacion(String estadoConcervacion) {
		this.estadoConcervacion = estadoConcervacion;
	}
	public String getMaterialPredominante() {
		return materialPredominante;
	}
	public void setMaterialPredominante(String materialPredominante) {
		this.materialPredominante = materialPredominante;
	}
	public Integer getConstruccionId() {
		return construccionId;
	}
	public void setConstruccionId(Integer construccionId) {
		this.construccionId = construccionId;
	}
	public Integer getNroNivel() {
		return nroNivel;
	}
	public void setNroNivel(Integer nroNivel) {
		this.nroNivel = nroNivel;
	}
	public Integer getAntiguedad() {
		return antiguedad;
	}
	public void setAntiguedad(Integer antiguedad) {
		this.antiguedad = antiguedad;
	}
	public String getDescripcionNivel() {
		return descripcionNivel;
	}
	public void setDescripcionNivel(String descripcionNivel) {
		this.descripcionNivel = descripcionNivel;
	}
	public String getMuros() {
		return muros;
	}
	public void setMuros(String muros) {
		this.muros = muros;
	}
	public String getTecho() {
		return techo;
	}
	public void setTecho(String techo) {
		this.techo = techo;
	}
	public String getPisos() {
		return pisos;
	}
	public void setPisos(String pisos) {
		this.pisos = pisos;
	}
	public String getPuertas() {
		return puertas;
	}
	public void setPuertas(String puertas) {
		this.puertas = puertas;
	}
	public String getRevestimiento() {
		return revestimiento;
	}
	public void setRevestimiento(String revestimiento) {
		this.revestimiento = revestimiento;
	}
	public String getBannos() {
		return bannos;
	}
	public void setBannos(String bannos) {
		this.bannos = bannos;
	}
	public String getElectricos() {
		return electricos;
	}
	public void setElectricos(String electricos) {
		this.electricos = electricos;
	}
	public Double getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public Double getIncremento() {
		return incremento;
	}
	public void setIncremento(Double incremento) {
		this.incremento = incremento;
	}
	public Double getValorUnitarioDepre() {
		return valorUnitarioDepre;
	}
	public void setValorUnitarioDepre(Double valorUnitarioDepre) {
		this.valorUnitarioDepre = valorUnitarioDepre;
	}
	public Double getAreaConstruccion() {
		return areaConstruccion;
	}
	public void setAreaConstruccion(Double areaConstruccion) {
		this.areaConstruccion = areaConstruccion;
	}
	public Double getValorAreaConstruida() {
		return valorAreaConstruida;
	}
	public void setValorAreaConstruida(Double valorAreaConstruida) {
		this.valorAreaConstruida = valorAreaConstruida;
	}
	public Double getValorAreaComun() {
		return valorAreaComun;
	}
	public void setValorAreaComun(Double valorAreaComun) {
		this.valorAreaComun = valorAreaComun;
	}
	public Double getValorConstruccion() {
		return valorConstruccion;
	}
	public void setValorConstruccion(Double valorConstruccion) {
		this.valorConstruccion = valorConstruccion;
	}
	public Integer getDeterPredioId() {
		return deterPredioId;
	}
	public void setDeterPredioId(Integer deterPredioId) {
		this.deterPredioId = deterPredioId;
	}
	public String getTipoDepreciacion() {
		return tipoDepreciacion;
	}
	public void setTipoDepreciacion(String tipoDepreciacion) {
		this.tipoDepreciacion = tipoDepreciacion;
	}
	public String getSeccion() {
		return seccion;
	}
	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public Integer getAnnoActualizacion() {
		return annoActualizacion;
	}
	public void setAnnoActualizacion(Integer annoActualizacion) {
		this.annoActualizacion = annoActualizacion;
	}	
}
