package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cj_pago_tupa")
public class CjPagoTupa implements Serializable{

	private static final long serialVersionUID = 1304200496557169059L;

	@Id
	@Column(name="pago_tupa_id")
	private int pagoTupaId;
	
	@Column(name="apertura_id")
	private int aperturaId;
	
	@Column(name="tupa_id")
	private int tupaId;
	
	@Column(name="recibo_id")
	private int reciboId;
	
	@Column(name="fecha_pago")
	private Timestamp fechaPago;
	
	@Column(name="monto_pago")
	private BigDecimal montoPago;
	
	@Column(name="descuento")
	private BigDecimal descuento;
	
	@Column(name="sub_total")
	private BigDecimal subTotal;
	
	@Column(name="ordenanza_id")
	private Integer ordenanzaId;
	
	@Column(name="cantidad")
	private Integer cantidad;
	
	@Column(name="flag_extorno")
	private String flagExtorno;
	
	@Column(name="fecha_extorno")
	private Timestamp fechaExtorno;
	
	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
	@Column(name="terminal")
	private String terminal;
	
	@Column(name="fecha_upd")
	private Timestamp fechaUpd;
	
	@Column(name="terminal_upd")
	private String terminalUpd;
	
	@Column(name="usuario_upd_id")
	private Integer usuarioUpdId;

	public CjPagoTupa(){
	}

	public int getPagoTupaId() {
		return pagoTupaId;
	}

	public void setPagoTupaId(int pagoTupaId) {
		this.pagoTupaId = pagoTupaId;
	}

	public int getAperturaId() {
		return aperturaId;
	}

	public void setAperturaId(int aperturaId) {
		this.aperturaId = aperturaId;
	}

	public int getTupaId() {
		return tupaId;
	}

	public void setTupaId(int tupaId) {
		this.tupaId = tupaId;
	}

	public int getReciboId() {
		return reciboId;
	}

	public void setReciboId(int reciboId) {
		this.reciboId = reciboId;
	}

	public Timestamp getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Timestamp fechaPago) {
		this.fechaPago = fechaPago;
	}

	public BigDecimal getMontoPago() {
		return montoPago;
	}

	public void setMontoPago(BigDecimal montoPago) {
		this.montoPago = montoPago;
	}

	public String getFlagExtorno() {
		return flagExtorno;
	}

	public void setFlagExtorno(String flagExtorno) {
		this.flagExtorno = flagExtorno;
	}

	public Timestamp getFechaExtorno() {
		return fechaExtorno;
	}

	public void setFechaExtorno(Timestamp fechaExtorno) {
		this.fechaExtorno = fechaExtorno;
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

	public Timestamp getFechaUpd() {
		return fechaUpd;
	}

	public void setFechaUpd(Timestamp fechaUpd) {
		this.fechaUpd = fechaUpd;
	}

	public String getTerminalUpd() {
		return terminalUpd;
	}

	public void setTerminalUpd(String terminalUpd) {
		this.terminalUpd = terminalUpd;
	}

	public Integer getUsuarioUpdId() {
		return usuarioUpdId;
	}

	public void setUsuarioUpdId(Integer usuarioUpdId) {
		this.usuarioUpdId = usuarioUpdId;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public Integer getOrdenanzaId() {
		return ordenanzaId;
	}

	public void setOrdenanzaId(Integer ordenanzaId) {
		this.ordenanzaId = ordenanzaId;
	}
}
