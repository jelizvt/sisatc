/**
 * @author Liliana Aliaga
 * @version 1.0 
 * @since 21/02/2012
 * Entidad Registro ReciboDetalle
 */
package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * The persistent class for the cj_recibo_detalle database table.
 * 
 */
@Entity
@Table(name="cj_recibo_detalle")
public class CjReciboDetalle implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="recibo_id")
	private int reciboId;

	@Id
	@Column(name="recibo_detalle_id")
	private int reciboDetalleId;

	@Column(name="banco_id")
	private Integer bancoId;
	
	@Column(name="forma_pago_id")
	private int formaPagoId;

	@Column(name="tipo_cambio")
	private BigDecimal tipoCambio;
	
	@Column(name="monto")
	private BigDecimal monto;
	
	@Column(name="monto_total_soles")
	private BigDecimal montoTotalSoles;
	
	@Column(name="nro_cheque")
	private String nroCheque;
	
	@Column(name="tarjeta_id")
	private Integer tarjetaId;
	
	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="observacion")
	private String observacion;
	
	@Column(name="estado")
	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="terminal")
	private String terminal;
	
	@Column(name="tipo_moneda_id")
	private int tipoMonedaId;
	
	@Transient
	private String banco;
	@Transient
	private String formaPago;
	@Transient
	private String tipoTarjeta;
	@Transient
	private String tipoMoneda;
	
	public CjReciboDetalle() {
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

	public Integer getBancoId() {
		return bancoId;
	}

	public void setBancoId(Integer bancoId) {
		this.bancoId = bancoId;
	}

	public int getFormaPagoId() {
		return formaPagoId;
	}

	public void setFormaPagoId(int formaPagoId) {
		this.formaPagoId = formaPagoId;
	}

	public BigDecimal getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(BigDecimal tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public BigDecimal getMontoTotalSoles() {
		return montoTotalSoles;
	}

	public void setMontoTotalSoles(BigDecimal montoTotalSoles) {
		this.montoTotalSoles = montoTotalSoles;
	}

	public String getNroCheque() {
		return nroCheque;
	}

	public void setNroCheque(String nroCheque) {
		this.nroCheque = nroCheque;
	}

	public Integer getTarjetaId() {
		return tarjetaId;
	}

	public void setTarjetaId(Integer tarjetaId) {
		this.tarjetaId = tarjetaId;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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

	public int getTipoMonedaId() {
		return tipoMonedaId;
	}

	public void setTipoMonedaId(int tipoMonedaId) {
		this.tipoMonedaId = tipoMonedaId;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public String getTipoMoneda() {
		return tipoMoneda;
	}

	public void setTipoMoneda(String tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}
}