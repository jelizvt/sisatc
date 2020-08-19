package com.sat.sisat.reportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ReporRecaudacionDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigDecimal totalDeuda;
	private BigDecimal totalCancelado;
	private int anioDeuda;
	private int personaId;
	private int tiempoId;
	private Timestamp fecha;
	private int anio;
	private BigDecimal montoPago;
	
	private BigDecimal milesSoles;
	private BigDecimal millonesSoles;
	private BigDecimal porcentajeDeuda;
	
	private String concepto;
	private String subConcepto;
	
	private BigDecimal recaudacionAnio;
	
	private BigDecimal recaudacionAnioDeuda;
	
	private BigDecimal recaConceptoPorcen;
	
	public BigDecimal getRecaudacionAnio() {
		return recaudacionAnio;
	}
	public void setRecaudacionAnio(BigDecimal recaudacionAnio) {
		this.recaudacionAnio = recaudacionAnio;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getSubConcepto() {
		return subConcepto;
	}
	public void setSubConcepto(String subConcepto) {
		this.subConcepto = subConcepto;
	}
	public BigDecimal getMilesSoles() {
		return milesSoles;
	}
	public void setMilesSoles(BigDecimal milesSoles) {
		this.milesSoles = milesSoles;
	}
	public BigDecimal getMillonesSoles() {
		return millonesSoles;
	}
	public void setMillonesSoles(BigDecimal millonesSoles) {
		this.millonesSoles = millonesSoles;
	}
	public BigDecimal getPorcentajeDeuda() {
		return porcentajeDeuda;
	}
	public void setPorcentajeDeuda(BigDecimal porcentajeDeuda) {
		this.porcentajeDeuda = porcentajeDeuda;
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
	public int getAnioDeuda() {
		return anioDeuda;
	}
	public void setAnioDeuda(int anioDeuda) {
		this.anioDeuda = anioDeuda;
	}
	public int getPersonaId() {
		return personaId;
	}
	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}
	public int getTiempoId() {
		return tiempoId;
	}
	public void setTiempoId(int tiempoId) {
		this.tiempoId = tiempoId;
	}
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public BigDecimal getMontoPago() {
		return montoPago;
	}
	public void setMontoPago(BigDecimal montoPago) {
		this.montoPago = montoPago;
	}
	public BigDecimal getRecaudacionAnioDeuda() {
		return recaudacionAnioDeuda;
	}
	public void setRecaudacionAnioDeuda(BigDecimal recaudacionAnioDeuda) {
		this.recaudacionAnioDeuda = recaudacionAnioDeuda;
	}
	public BigDecimal getRecaConceptoPorcen() {
		return recaConceptoPorcen;
	}
	public void setRecaConceptoPorcen(BigDecimal recaConceptoPorcen) {
		this.recaConceptoPorcen = recaConceptoPorcen;
	}
	
}
