package com.sat.sisat.obligacion.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.sat.sisat.common.dto.BaseDTO;

public class MultaDTO extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9001787022490045236L;

	private Integer deudaId;

	private Integer determinacionId;

	private Integer personaId;

	private Integer conceptoId;

	private Integer subConceptoId;

	private String subConcepto;

	private BigDecimal valorUit;

	private Integer anho;

	private BigDecimal porcentajeSancion;

	private BigDecimal monto;

	private BigDecimal interes;

	private String nroRsMulta;

	private BigDecimal montoSinDscto;

	private BigDecimal montoDescuento;

	private BigDecimal montoConDscto;

	private Date fechaEmision;

	/** Fecha a partir de la cual se computara los intereses */
	private Date fechaVencimiento;

	private String unidad;

	private Date fechaNotificacion;
	
	public Integer getDeudaId() {
		return deudaId;
	}

	public void setDeudaId(Integer deudaId) {
		this.deudaId = deudaId;
	}

	public Integer getDeterminacionId() {
		return determinacionId;
	}

	public void setDeterminacionId(Integer determinacionId) {
		this.determinacionId = determinacionId;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public Integer getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}

	public Integer getSubConceptoId() {
		return subConceptoId;
	}

	public void setSubConceptoId(Integer subConceptoId) {
		this.subConceptoId = subConceptoId;
	}

	public BigDecimal getValorUit() {
		return valorUit;
	}

	public void setValorUit(BigDecimal valorUit) {
		this.valorUit = valorUit;
	}

	public Integer getAnho() {
		return anho;
	}

	public void setAnho(Integer anho) {
		this.anho = anho;
	}

	public BigDecimal getPorcentajeSancion() {
		return porcentajeSancion;
	}

	public void setPorcentajeSancion(BigDecimal porcentajeSancion) {
		this.porcentajeSancion = porcentajeSancion;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public BigDecimal getMontoConDscto() {
		return montoConDscto;
	}

	public void setMontoConDscto(BigDecimal montoConDscto) {
		this.montoConDscto = montoConDscto;
	}

	public BigDecimal getMontoSinDscto() {
		return montoSinDscto;
	}

	public void setMontoSinDscto(BigDecimal montoSinDscto) {
		this.montoSinDscto = montoSinDscto;
	}

	public String getNroRsMulta() {
		return nroRsMulta;
	}

	public void setNroRsMulta(String nroRsMulta) {
		this.nroRsMulta = nroRsMulta;
	}

	public String getSubConcepto() {
		return subConcepto;
	}

	public void setSubConcepto(String subConcepto) {
		this.subConcepto = subConcepto;
	}

	public BigDecimal getMontoDescuento() {
		return montoDescuento;
	}

	public void setMontoDescuento(BigDecimal montoDescuento) {
		this.montoDescuento = montoDescuento;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

}
