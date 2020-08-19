package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class CjCobranza implements Serializable {
	
	//Forma Pago 
	private int formaPagoId;
	private String formaPagoDes;
	
	//Tipo moneda
	private int tipoMonedaId;
	private String tipoMonedaDes;
	
	private BigDecimal montoSoles;
	private BigDecimal tipoCambio;
	private BigDecimal montoTotal;
	
	private String nroCheque;
	

	//Tipo de Tarjeta
	private int tarjetaId;
	private String tarjetaDes;
	
	//Tipo de Banco
	
	private int bancoId;
    private String bancoDes;
    
    
    //Observacion
    private String observacion;
  
    //Tipo de documento
	//private int tipoDoc;
	//private String tipoDocDes;
    
    //General
   private BigDecimal montoVuelto;
   private int reciboId;
   private int reciboDetalleId;
   private int monedaId;
   
   private int usuarioId;
   private String estado;
   private Timestamp fecha_registro;
   private int terminal;
	

	public CjCobranza(){
	}

	public int getFormaPagoId() {
		return formaPagoId;
	}

	public void setFormaPagoId(int formaPagoId) {
		this.formaPagoId = formaPagoId;
	}

	public String getFormaPagoDes() {
		return formaPagoDes;
	}

	public void setFormaPagoDes(String formaPagoDes) {
		this.formaPagoDes = formaPagoDes;
	}

	public int getTipoMonedaId() {
		return tipoMonedaId;
	}

	public void setTipoMonedaId(int tipoMonedaId) {
		this.tipoMonedaId = tipoMonedaId;
	}

	public String getTipoMonedaDes() {
		return tipoMonedaDes;
	}

	public void setTipoMonedaDes(String tipoMonedaDes) {
		this.tipoMonedaDes = tipoMonedaDes;
	}

	public int getTarjetaId() {
		return tarjetaId;
	}

	public void setTarjetaId(int tarjetaId) {
		this.tarjetaId = tarjetaId;
	}

	public String getTarjetaDes() {
		return tarjetaDes;
	}

	public void setTarjetaDes(String tarjetaDes) {
		this.tarjetaDes = tarjetaDes;
	}

	public int getBancoId() {
		return bancoId;
	}

	public void setBancoId(int bancoId) {
		this.bancoId = bancoId;
	}

	public String getBancoDes() {
		return bancoDes;
	}

	public void setBancoDes(String bancoDes) {
		this.bancoDes = bancoDes;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public BigDecimal getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(BigDecimal tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public String getNroCheque() {
		return nroCheque;
	}

	public void setNroCheque(String nroCheque) {
		this.nroCheque = nroCheque;
	}

	public BigDecimal getMontoSoles() {
		return montoSoles;
	}

	public void setMontoSoles(BigDecimal  montoSoles) {
		this.montoSoles = montoSoles;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(Timestamp fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public int getTerminal() {
		return terminal;
	}

	public void setTerminal(int terminal) {
		this.terminal = terminal;
	}

	public BigDecimal getMontoVuelto() {
		return montoVuelto;
	}

	public void setMontoVuelto(BigDecimal montoVuelto) {
		this.montoVuelto = montoVuelto;
	}

	public int getReciboId() {
		return reciboId;
	}

	public void setReciboId(int reciboId) {
		this.reciboId = reciboId;
	}

	public int getReciboDetalleId() {
		return reciboDetalleId;
	}

	public void setReciboDetalleId(int reciboDetalleId) {
		this.reciboDetalleId = reciboDetalleId;
	}

	public int getMonedaId() {
		return monedaId;
	}

	public void setMonedaId(int monedaId) {
		this.monedaId = monedaId;
	}




	
}
