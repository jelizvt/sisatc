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
 * The persistent class for the gn_tipo_cambio database table.
 * 
 */
@Entity
@Table(name="gn_tipo_cambio")
public class GnTipoCambio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_cambio_id")
	private int tipoCambioId;

	@Transient
	private Object fecha;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="tipo_moneda_id")
	private int tipoMonedaId;

	@Column(name="usuario_id")
	private int usuarioId;

	@Column(name="valor_compra")
	private BigDecimal valorCompra;

	@Column(name="valor_venta")
	private BigDecimal valorVenta;

    public GnTipoCambio() {
    }

	public int getTipoCambioId() {
		return this.tipoCambioId;
	}

	public void setTipoCambioId(int tipoCambioId) {
		this.tipoCambioId = tipoCambioId;
	}

	public Object getFecha() {
		return this.fecha;
	}

	public void setFecha(Object fecha) {
		this.fecha = fecha;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getTipoMonedaId() {
		return this.tipoMonedaId;
	}

	public void setTipoMonedaId(int tipoMonedaId) {
		this.tipoMonedaId = tipoMonedaId;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public BigDecimal getValorCompra() {
		return this.valorCompra;
	}

	public void setValorCompra(BigDecimal valorCompra) {
		this.valorCompra = valorCompra;
	}

	public BigDecimal getValorVenta() {
		return this.valorVenta;
	}

	public void setValorVenta(BigDecimal valorVenta) {
		this.valorVenta = valorVenta;
	}

}