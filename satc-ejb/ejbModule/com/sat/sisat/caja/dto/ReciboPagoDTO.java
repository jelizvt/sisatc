package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReciboPagoDTO implements Serializable{

	private static final long serialVersionUID = 3277516297398697518L;
	
	private int reciboId;
	private Integer personaId;
	private String nroRecibo;
	private String personaDes;
	private Date fechaRecibo;
	private BigDecimal montoACobrar;
	private BigDecimal montoDescuento = new BigDecimal(0);
	private BigDecimal montoVuelto = new BigDecimal(0);
	private BigDecimal montoCobrado;
	private BigDecimal montoSubTotal = new BigDecimal("0.00");
	private String direccion;
	private String cajeroDes;
	private String pagadoPor;
	private String montoLetras;
	
	// datos para recibos extornados
	private Boolean estaExtornado = Boolean.FALSE;
	private Integer usuarioIdExtorno;	
	private String usuarioAsStringExtorno;	
	private Date fechaExtorno;	
	private String observacionExtorno;
	private String mensaje;
	
	public ReciboPagoDTO(){
	}

	public ReciboPagoDTO(int reciboId, Integer personaId, String nroRecibo,
			String personaDes, Date fechaRecibo, BigDecimal montoACobrar,
			BigDecimal montoDescuento, BigDecimal montoVuelto,
			BigDecimal montoCobrado, String direccion, String cajeroDes,
			String pagadoPor, String montoLetras) {
		super();
		this.reciboId = reciboId;
		this.personaId = personaId;
		this.nroRecibo = nroRecibo;
		this.personaDes = personaDes;
		this.fechaRecibo = fechaRecibo;
		this.montoACobrar = montoACobrar;
		this.montoDescuento = montoDescuento;
		this.montoVuelto = montoVuelto;
		this.montoCobrado = montoCobrado;
		this.direccion = direccion;
		this.cajeroDes = cajeroDes;
		this.pagadoPor = pagadoPor;
		this.montoLetras = montoLetras;
	}

	public int getReciboId() {
		return reciboId;
	}

	public void setReciboId(int reciboId) {
		this.reciboId = reciboId;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public String getNroRecibo() {
		return nroRecibo;
	}

	public void setNroRecibo(String nroRecibo) {
		this.nroRecibo = nroRecibo;
	}

	public String getPersonaDes() {
		return personaDes;
	}

	public void setPersonaDes(String personaDes) {
		this.personaDes = personaDes;
	}

	public Date getFechaRecibo() {
		return fechaRecibo;
	}

	public void setFechaRecibo(Date fechaRecibo) {
		this.fechaRecibo = fechaRecibo;
	}

	public BigDecimal getMontoACobrar() {
		return montoACobrar;
	}

	public void setMontoACobrar(BigDecimal montoACobrar) {
		this.montoACobrar = montoACobrar;
	}

	public BigDecimal getMontoDescuento() {
		return montoDescuento;
	}

	public void setMontoDescuento(BigDecimal montoDescuento) {
		this.montoDescuento = montoDescuento;
	}

	public BigDecimal getMontoVuelto() {
		return montoVuelto;
	}

	public void setMontoVuelto(BigDecimal montoVuelto) {
		this.montoVuelto = montoVuelto;
	}

	public BigDecimal getMontoCobrado() {
		return montoCobrado;
	}

	public void setMontoCobrado(BigDecimal montoCobrado) {
		this.montoCobrado = montoCobrado;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCajeroDes() {
		return cajeroDes;
	}

	public void setCajeroDes(String cajeroDes) {
		this.cajeroDes = cajeroDes;
	}

	public String getPagadoPor() {
		return pagadoPor;
	}

	public void setPagadoPor(String pagadoPor) {
		this.pagadoPor = pagadoPor;
	}

	public String getMontoLetras() {
		return montoLetras;
	}

	public void setMontoLetras(String montoLetras) {
		this.montoLetras = montoLetras;
	}

	public BigDecimal getMontoSubTotal() {
		return montoSubTotal;
	}

	public void setMontoSubTotal(BigDecimal montoSubTotal) {
		this.montoSubTotal = montoSubTotal;
	}

	public Integer getUsuarioIdExtorno() {
		return usuarioIdExtorno;
	}

	public void setUsuarioIdExtorno(Integer usuarioIdExtorno) {
		this.usuarioIdExtorno = usuarioIdExtorno;
	}

	public String getUsuarioAsStringExtorno() {
		return usuarioAsStringExtorno;
	}

	public void setUsuarioAsStringExtorno(String usuarioAsStringExtorno) {
		this.usuarioAsStringExtorno = usuarioAsStringExtorno;
	}

	public Date getFechaExtorno() {
		return fechaExtorno;
	}

	public void setFechaExtorno(Date fechaExtorno) {
		this.fechaExtorno = fechaExtorno;
	}

	public String getObservacionExtorno() {
		return observacionExtorno;
	}

	public void setObservacionExtorno(String observacionExtorno) {
		this.observacionExtorno = observacionExtorno;
	}

	public Boolean getEstaExtornado() {
		return estaExtornado;
	}

	public void setEstaExtornado(Boolean estaExtornado) {
		this.estaExtornado = estaExtornado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
