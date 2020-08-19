package com.sat.sisat.estadocuenta.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;

public class ReciboPago implements Serializable{
	
	private int reciboId;
	private Integer personaId;
	private Timestamp fechaRecibo;
	private String nroRecibo;
	private BigDecimal montoACobrar;
	private BigDecimal montoDescuento;
	private BigDecimal montoCobrado;
	private BigDecimal montoVuelto;
	private String nroReferencia;
	private int usuarioId;
	private String estado;
	private Timestamp fechaRegistro;
	private String terminal;
	private String montoLetra;
	private String flagFuente;
	private String referencia;
	private Timestamp fechaUpd;
	private Timestamp terminalUpd;
	private Integer usuarioUpdId;
	private String obsExtorno;
	private String tipoRecibo;
	private BigDecimal montoDescargo;
	
	private Boolean estadoConstancia;
	private Integer estadoRecord;
	
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
	public Timestamp getFechaRecibo() {
		return fechaRecibo;
	}
	public void setFechaRecibo(Timestamp fechaRecibo) {
		this.fechaRecibo = fechaRecibo;
	}
	public String getNroRecibo() {
		return nroRecibo;
	}
	public void setNroRecibo(String nroRecibo) {
		this.nroRecibo = nroRecibo;
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
	public BigDecimal getMontoCobrado() {
		return montoCobrado;
	}
	public void setMontoCobrado(BigDecimal montoCobrado) {
		this.montoCobrado = montoCobrado;
	}
	public BigDecimal getMontoVuelto() {
		return montoVuelto;
	}
	public void setMontoVuelto(BigDecimal montoVuelto) {
		this.montoVuelto = montoVuelto;
	}
	public String getNroReferencia() {
		return nroReferencia;
	}
	public void setNroReferencia(String nroReferencia) {
		this.nroReferencia = nroReferencia;
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
	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getMontoLetra() {
		return montoLetra;
	}
	public void setMontoLetra(String montoLetra) {
		this.montoLetra = montoLetra;
	}
	public String getFlagFuente() {
		return flagFuente;
	}
	public void setFlagFuente(String flagFuente) {
		this.flagFuente = flagFuente;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public Timestamp getFechaUpd() {
		return fechaUpd;
	}
	public void setFechaUpd(Timestamp fechaUpd) {
		this.fechaUpd = fechaUpd;
	}
	public Timestamp getTerminalUpd() {
		return terminalUpd;
	}
	public void setTerminalUpd(Timestamp terminalUpd) {
		this.terminalUpd = terminalUpd;
	}
	public Integer getUsuarioUpdId() {
		return usuarioUpdId;
	}
	public void setUsuarioUpdId(Integer usuarioUpdId) {
		this.usuarioUpdId = usuarioUpdId;
	}
	public String getObsExtorno() {
		return obsExtorno;
	}
	public void setObsExtorno(String obsExtorno) {
		this.obsExtorno = obsExtorno;
	}
	public BigDecimal getMontoDescargo() {
		return montoDescargo;
	}
	public void setMontoDescargo(BigDecimal montoDescargo) {
		this.montoDescargo = montoDescargo;
	}
	public String getTipoRecibo() {
		return tipoRecibo;
	}
	public void setTipoRecibo(String tipoRecibo) {
		this.tipoRecibo = tipoRecibo;
	}
	public Boolean getEstadoConstancia() {
		return estadoConstancia;
	}
	public void setEstadoConstancia(Boolean estadoConstancia) {
		this.estadoConstancia = estadoConstancia;
	}
	public Integer getEstadoRecord() {
		return estadoRecord;
	}
	public void setEstadoRecord(Integer estadoRecord) {
		this.estadoRecord = estadoRecord;
	}
	
}
