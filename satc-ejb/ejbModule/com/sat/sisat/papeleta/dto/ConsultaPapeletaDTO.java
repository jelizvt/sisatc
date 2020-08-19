package com.sat.sisat.papeleta.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConsultaPapeletaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5283271972531388531L;

	private String nroPapeleta;
	
	private Date fechaInfraccion;
	
	private String codigo;
	
	private String infractor;
	
	private String tipoDoc;
	
	private String nroDoc;
	
	private String placa;
	
	private String infraccion;
	
	private String estado;
	
	private String nroResolucion;
	
	private Date fechaNotificacion;
	
	private String estadoResolucion;
	
	private BigDecimal montoMulta;
	
	private BigDecimal montoPago;
	
	private Date fechaPago;
	
	private String nroRecibo;
	
	private Integer loteId;
	
	private Integer reciboId;
	
	private String responsable;
	
	private Date fechaActualizacion;
	
	/**
	 *Para consultar deudas descargadas o prescritas en Papeletas: 
	 * */
	private String tipoDescargo;
	private String descripcionDescargo;
	private Date fechaDocumentoDescargo;
	

	public String getNroPapeleta() {
		return nroPapeleta;
	}

	public void setNroPapeleta(String nroPapeleta) {
		this.nroPapeleta = nroPapeleta;
	}

	public Date getFechaInfraccion() {
		return fechaInfraccion;
	}

	public void setFechaInfraccion(Date fechaInfraccion) {
		this.fechaInfraccion = fechaInfraccion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getInfractor() {
		return infractor;
	}

	public void setInfractor(String infractor) {
		this.infractor = infractor;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getNroDoc() {
		return nroDoc;
	}

	public void setNroDoc(String nroDoc) {
		this.nroDoc = nroDoc;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getInfraccion() {
		return infraccion;
	}

	public void setInfraccion(String infraccion) {
		this.infraccion = infraccion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNroResolucion() {
		return nroResolucion;
	}

	public void setNroResolucion(String nroResolucion) {
		this.nroResolucion = nroResolucion;
	}

	public String getEstadoResolucion() {
		return estadoResolucion;
	}

	public void setEstadoResolucion(String estadoResolucion) {
		this.estadoResolucion = estadoResolucion;
	}

	public BigDecimal getMontoMulta() {
		return montoMulta;
	}

	public void setMontoMulta(BigDecimal montoMulta) {
		this.montoMulta = montoMulta;
	}

	public BigDecimal getMontoPago() {
		return montoPago;
	}

	public void setMontoPago(BigDecimal montoPago) {
		this.montoPago = montoPago;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getNroRecibo() {
		return nroRecibo;
	}

	public void setNroRecibo(String nroRecibo) {
		this.nroRecibo = nroRecibo;
	}

	public Integer getLoteId() {
		return loteId;
	}

	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}

	public Integer getReciboId() {
		return reciboId;
	}

	public void setReciboId(Integer reciboId) {
		this.reciboId = reciboId;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public String getTipoDescargo() {
		return tipoDescargo;
	}

	public void setTipoDescargo(String tipoDescargo) {
		this.tipoDescargo = tipoDescargo;
	}

	public String getDescripcionDescargo() {
		return descripcionDescargo;
	}

	public void setDescripcionDescargo(String descripcionDescargo) {
		this.descripcionDescargo = descripcionDescargo;
	}

	public Date getFechaDocumentoDescargo() {
		return fechaDocumentoDescargo;
	}

	public void setFechaDocumentoDescargo(Date fechaDocumentoDescargo) {
		this.fechaDocumentoDescargo = fechaDocumentoDescargo;
	}
	
	
	
	
	
	
}
