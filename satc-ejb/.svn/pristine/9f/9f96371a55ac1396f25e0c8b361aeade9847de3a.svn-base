package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import com.sat.sisat.persistence.entity.DtCercaniaParque;
import com.sat.sisat.persistence.entity.DtFrecuenciaLimpieza;
import com.sat.sisat.persistence.entity.DtFrecuenciaRecojo;
import com.sat.sisat.persistence.entity.DtGrupoCercania;
import com.sat.sisat.persistence.entity.DtMatrizRecojo;
import com.sat.sisat.persistence.entity.DtZonaSeguridadUbicacion;
import com.sat.sisat.persistence.entity.DtZonaSeguridadUso;
import com.sat.sisat.persistence.entity.RpDjarbitrio;
import com.sat.sisat.persistence.entity.RpDjuso;

public class DeterminacionArbitriosDTO implements Serializable {
	
	//Calculo arbitrio de Limpieza
	private Integer frecuenciaLimpieza;
	private BigDecimal tasaMlAnualLimpieza;
	private BigDecimal frenteMlLimpieza;
	
	//Calculo arbitrio de Recojo
	private Integer frecuenciaRecojo;
	private List<RecojoArbitriosDTO> lRecojoArbitrio;
	
	//Calculo arbitrio Parques
	private String grupoCercaniaParques;
	private BigDecimal costoAnualParques;
	
    //Calculo arbitrio de Seguridad
	private String zonaSeguridad;
	private List<SeguridadArbitriosDTO> lSeguridadArbitrio;
	
	//general
	private Integer djId;
	private Integer annoDeterminacion;
	private java.util.Date fechaRegistro;
	private String direccionCompleta;
	//--
	private Integer grupoCercaniaParquesId;
	private Integer frecuenciaLimpiezaId;
	private Integer frecuenciaRecojoId;
	private Integer zonaSeguridadId;
	//--
	private Integer determinacionArbitriosId;
	
	//--Antes de Subvencion 2013
	private BigDecimal arbitrioSeguridadAntesSubvencion;
	private BigDecimal arbitrioParquesAntesSubvencion;
	private BigDecimal arbitrioLimpiezaAntesSubvencion;
	private BigDecimal arbitrioRecojoAntesSubvencion;
	
	//--Antes de Beneficio 2013
	private BigDecimal arbitrioSeguridadAntesBeneficio;
	private BigDecimal arbitrioParquesAntesBeneficio;
	private BigDecimal arbitrioLimpiezaAntesBeneficio;
	private BigDecimal arbitrioRecojoAntesBeneficio;
	
	//Arbitrios netos
	private BigDecimal arbitrioLimpieza;
	private BigDecimal arbitrioRecojo;
	private BigDecimal arbitrioParques;
	private BigDecimal arbitrioSeguridad;
	
	public BigDecimal getArbitrioSeguridadAntesSubvencion() {
		return arbitrioSeguridadAntesSubvencion;
	}
	public void setArbitrioSeguridadAntesSubvencion(
			BigDecimal arbitrioSeguridadAntesSubvencion) {
		this.arbitrioSeguridadAntesSubvencion = arbitrioSeguridadAntesSubvencion;
	}
	public BigDecimal getArbitrioParquesAntesSubvencion() {
		return arbitrioParquesAntesSubvencion;
	}
	public void setArbitrioParquesAntesSubvencion(
			BigDecimal arbitrioParquesAntesSubvencion) {
		this.arbitrioParquesAntesSubvencion = arbitrioParquesAntesSubvencion;
	}
	public BigDecimal getArbitrioLimpiezaAntesSubvencion() {
		return arbitrioLimpiezaAntesSubvencion;
	}
	public void setArbitrioLimpiezaAntesSubvencion(
			BigDecimal arbitrioLimpiezaAntesSubvencion) {
		this.arbitrioLimpiezaAntesSubvencion = arbitrioLimpiezaAntesSubvencion;
	}
	public BigDecimal getArbitrioRecojoAntesSubvencion() {
		return arbitrioRecojoAntesSubvencion;
	}
	public void setArbitrioRecojoAntesSubvencion(
			BigDecimal arbitrioRecojoAntesSubvencion) {
		this.arbitrioRecojoAntesSubvencion = arbitrioRecojoAntesSubvencion;
	}
	
	public BigDecimal getArbitrioSeguridadAntesBeneficio() {
		return arbitrioSeguridadAntesBeneficio;
	}
	public void setArbitrioSeguridadAntesBeneficio(
			BigDecimal arbitrioSeguridadAntesBeneficio) {
		this.arbitrioSeguridadAntesBeneficio = arbitrioSeguridadAntesBeneficio;
	}
	public BigDecimal getArbitrioParquesAntesBeneficio() {
		return arbitrioParquesAntesBeneficio;
	}
	public void setArbitrioParquesAntesBeneficio(
			BigDecimal arbitrioParquesAntesBeneficio) {
		this.arbitrioParquesAntesBeneficio = arbitrioParquesAntesBeneficio;
	}
	public Integer getDeterminacionArbitriosId() {
		return determinacionArbitriosId;
	}
	public void setDeterminacionArbitriosId(Integer determinacionArbitriosId) {
		this.determinacionArbitriosId = determinacionArbitriosId;
	}
	public Integer getGrupoCercaniaParquesId() {
		return grupoCercaniaParquesId;
	}
	public void setGrupoCercaniaParquesId(Integer grupoCercaniaParquesId) {
		this.grupoCercaniaParquesId = grupoCercaniaParquesId;
	}
	public Integer getFrecuenciaLimpiezaId() {
		return frecuenciaLimpiezaId;
	}
	public void setFrecuenciaLimpiezaId(Integer frecuenciaLimpiezaId) {
		this.frecuenciaLimpiezaId = frecuenciaLimpiezaId;
	}
	public Integer getFrecuenciaRecojoId() {
		return frecuenciaRecojoId;
	}
	public void setFrecuenciaRecojoId(Integer frecuenciaRecojoId) {
		this.frecuenciaRecojoId = frecuenciaRecojoId;
	}
	public Integer getZonaSeguridadId() {
		return zonaSeguridadId;
	}
	public void setZonaSeguridadId(Integer zonaSeguridadId) {
		this.zonaSeguridadId = zonaSeguridadId;
	}
	public Integer getFrecuenciaLimpieza() {
		return frecuenciaLimpieza;
	}
	public void setFrecuenciaLimpieza(Integer frecuenciaLimpieza) {
		this.frecuenciaLimpieza = frecuenciaLimpieza;
	}
	public BigDecimal getTasaMlAnualLimpieza() {
		return tasaMlAnualLimpieza;
	}
	public void setTasaMlAnualLimpieza(BigDecimal tasaMlAnualLimpieza) {
		this.tasaMlAnualLimpieza = tasaMlAnualLimpieza;
	}
	public BigDecimal getFrenteMlLimpieza() {
		return frenteMlLimpieza;
	}
	public void setFrenteMlLimpieza(BigDecimal frenteMlLimpieza) {
		this.frenteMlLimpieza = frenteMlLimpieza;
	}
	public Integer getFrecuenciaRecojo() {
		return frecuenciaRecojo;
	}
	public void setFrecuenciaRecojo(Integer frecuenciaRecojo) {
		this.frecuenciaRecojo = frecuenciaRecojo;
	}
	public List<RecojoArbitriosDTO> getlRecojoArbitrio() {
		return lRecojoArbitrio;
	}
	public void setlRecojoArbitrio(List<RecojoArbitriosDTO> lRecojoArbitrio) {
		this.lRecojoArbitrio = lRecojoArbitrio;
	}
	public String getGrupoCercaniaParques() {
		return grupoCercaniaParques;
	}
	public void setGrupoCercaniaParques(String grupoCercaniaParques) {
		this.grupoCercaniaParques = grupoCercaniaParques;
	}
	public BigDecimal getCostoAnualParques() {
		return costoAnualParques;
	}
	public void setCostoAnualParques(BigDecimal costoAnualParques) {
		this.costoAnualParques = costoAnualParques;
	}
	public String getZonaSeguridad() {
		return zonaSeguridad;
	}
	public void setZonaSeguridad(String zonaSeguridad) {
		this.zonaSeguridad = zonaSeguridad;
	}
	public List<SeguridadArbitriosDTO> getlSeguridadArbitrio() {
		return lSeguridadArbitrio;
	}
	public void setlSeguridadArbitrio(List<SeguridadArbitriosDTO> lSeguridadArbitrio) {
		this.lSeguridadArbitrio = lSeguridadArbitrio;
	}
	public BigDecimal getArbitrioLimpieza() {
		return arbitrioLimpieza;
	}
	public void setArbitrioLimpieza(BigDecimal arbitrioLimpieza) {
		this.arbitrioLimpieza = arbitrioLimpieza;
	}
	public BigDecimal getArbitrioRecojo() {
		return arbitrioRecojo;
	}
	public void setArbitrioRecojo(BigDecimal arbitrioRecojo) {
		this.arbitrioRecojo = arbitrioRecojo;
	}
	public BigDecimal getArbitrioParques() {
		return arbitrioParques;
	}
	public void setArbitrioParques(BigDecimal arbitrioParques) {
		this.arbitrioParques = arbitrioParques;
	}
	public BigDecimal getArbitrioSeguridad() {
		return arbitrioSeguridad;
	}
	public void setArbitrioSeguridad(BigDecimal arbitrioSeguridad) {
		this.arbitrioSeguridad = arbitrioSeguridad;
	}
	public Integer getDjId() {
		return djId;
	}
	public void setDjId(Integer djId) {
		this.djId = djId;
	}
	public Integer getAnnoDeterminacion() {
		return annoDeterminacion;
	}
	public void setAnnoDeterminacion(Integer annoDeterminacion) {
		this.annoDeterminacion = annoDeterminacion;
	}
	public java.util.Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(java.util.Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getDireccionCompleta() {
		return direccionCompleta;
	}
	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}
	public BigDecimal getArbitrioLimpiezaAntesBeneficio() {
		return arbitrioLimpiezaAntesBeneficio;
	}
	public void setArbitrioLimpiezaAntesBeneficio(
			BigDecimal arbitrioLimpiezaAntesBeneficio) {
		this.arbitrioLimpiezaAntesBeneficio = arbitrioLimpiezaAntesBeneficio;
	}
	public BigDecimal getArbitrioRecojoAntesBeneficio() {
		return arbitrioRecojoAntesBeneficio;
	}
	public void setArbitrioRecojoAntesBeneficio(
			BigDecimal arbitrioRecojoAntesBeneficio) {
		this.arbitrioRecojoAntesBeneficio = arbitrioRecojoAntesBeneficio;
	}
}
