package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReciboPagoDetalleDTO implements Serializable{

	private static final long serialVersionUID = 1262731198896139880L;

	private String concepto;
	private String cuota;
	private BigDecimal monto;
	private BigDecimal reajuste;
	private BigDecimal interes;
	private BigDecimal emision;
	private BigDecimal total;	 
	/** Variable usada para los pagos tupa*/
	private Integer cantidad;
	
	private Boolean select;
	private String referencia;
	private String anio;
	private Integer deudaId;
	private Boolean estado;
	
	private ReciboPagoDetallePieDTO reciboPagoDetallePieDTO;
	
	public ReciboPagoDetalleDTO(){
	}

	public ReciboPagoDetalleDTO(String concepto, String cuota,
			BigDecimal monto, BigDecimal reajuste, BigDecimal interes,
			BigDecimal emision, BigDecimal total) {
		super();
		this.concepto = concepto;
		this.cuota = cuota;
		this.monto = monto;
		this.reajuste = reajuste;
		this.interes = interes;
		this.emision = emision;
		this.total = total;
	}
	
	public ReciboPagoDetalleDTO(String concepto,
			String cuota,
			BigDecimal monto,
			BigDecimal reajuste,
			BigDecimal interes,
			BigDecimal emision,
			BigDecimal total,
			Integer cantidad,
			ReciboPagoDetallePieDTO reciboPagoDetallePieDTO) {
		super();
		this.concepto = concepto;
		this.cuota = cuota;
		this.monto = monto;
		this.reajuste = reajuste;
		this.interes = interes;
		this.emision = emision;
		this.total = total;
		this.cantidad = cantidad;
		this.reciboPagoDetallePieDTO = reciboPagoDetallePieDTO;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getCuota() {
		return cuota;
	}

	public void setCuota(String cuota) {
		this.cuota = cuota;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public BigDecimal getReajuste() {
		return reajuste;
	}

	public void setReajuste(BigDecimal reajuste) {
		this.reajuste = reajuste;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public BigDecimal getEmision() {
		return emision;
	}

	public void setEmision(BigDecimal emision) {
		this.emision = emision;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public ReciboPagoDetallePieDTO getReciboPagoDetallePieDTO() {
		return reciboPagoDetallePieDTO;
	}

	public void setReciboPagoDetallePieDTO(ReciboPagoDetallePieDTO reciboPagoDetallePieDTO) {
		this.reciboPagoDetallePieDTO = reciboPagoDetallePieDTO;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Boolean getSelect() {
		return select;
	}

	public void setSelect(Boolean select) {
		this.select = select;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public Integer getDeudaId() {
		return deudaId;
	}

	public void setDeudaId(Integer deudaId) {
		this.deudaId = deudaId;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
}
