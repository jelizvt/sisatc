package com.sat.sisat.reportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ReporDeudaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String concepto;
	private BigDecimal insoluto;
	private BigDecimal interesSimple;
	private BigDecimal interesCapitalizado;
	private BigDecimal totalDeudaActual;
	private BigDecimal porcenDeuda;
	private String estadoDeudaDesc;
	private int nroCuota;
	private Timestamp fechaEmision;
	private Timestamp fechaVencimiento;
	private BigDecimal montoDeuda;
	private int anioDeuda;
	private String conceptoValor;
	
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public int getAnioDeuda() {
		return anioDeuda;
	}
	public void setAnioDeuda(int anioDeuda) {
		this.anioDeuda = anioDeuda;
	}

	public BigDecimal getInsoluto() {
		return insoluto;
	}
	public void setInsoluto(BigDecimal insoluto) {
		this.insoluto = insoluto;
	}
	public BigDecimal getInteresSimple() {
		return interesSimple;
	}
	public void setInteresSimple(BigDecimal interesSimple) {
		this.interesSimple = interesSimple;
	}
	public BigDecimal getInteresCapitalizado() {
		return interesCapitalizado;
	}
	public void setInteresCapitalizado(BigDecimal interesCapitalizado) {
		this.interesCapitalizado = interesCapitalizado;
	}
	public BigDecimal getTotalDeudaActual() {
		return totalDeudaActual;
	}
	public void setTotalDeudaActual(BigDecimal totalDeudaActual) {
		this.totalDeudaActual = totalDeudaActual;
	}

	public String getEstadoDeudaDesc() {
		return estadoDeudaDesc;
	}
	public void setEstadoDeudaDesc(String estadoDeudaDesc) {
		this.estadoDeudaDesc = estadoDeudaDesc;
	}
	public int getNroCuota() {
		return nroCuota;
	}
	public void setNroCuota(int nroCuota) {
		this.nroCuota = nroCuota;
	}
	public Timestamp getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Timestamp fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public Timestamp getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Timestamp fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public BigDecimal getMontoDeuda() {
		return montoDeuda;
	}
	public void setMontoDeuda(BigDecimal montoDeuda) {
		this.montoDeuda = montoDeuda;
	}
	public BigDecimal getPorcenDeuda() {
		return porcenDeuda;
	}
	public void setPorcenDeuda(BigDecimal porcenDeuda) {
		this.porcenDeuda = porcenDeuda;
	}
	public String getConceptoValor() {
		return conceptoValor;
	}
	public void setConceptoValor(String conceptoValor) {
		this.conceptoValor = conceptoValor;
	}
	
}
