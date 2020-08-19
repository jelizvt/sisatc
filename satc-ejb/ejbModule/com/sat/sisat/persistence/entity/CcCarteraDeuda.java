package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * The persistent class for the cc_acto database table.
 * 
 */
@Entity
@Table(name = "cc_cartera_deuda")
public class CcCarteraDeuda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4097586855444997936L;

	@Id
	@Column(name="cartera_deuda_id")
	private Integer carteraDeudaId;

	@Column(name = "monto_minimo", nullable = false)
	@NotNull
	private BigDecimal montoMinimo;

	@Column(name = "monto_maximo", nullable = false)
	@NotNull
	private BigDecimal montoMaximo;

	@Column(name = "referencia", nullable = true)
	private String referencia;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion")
	@NotNull
	private Date fechaCreacion;

	@Column(name = "usuario_id", nullable = false)
	@NotNull
	private int usuarioId;

	@Column(name = "estado", nullable = false)
	@NotNull
	private String estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro")
	@NotNull
	private Date fechaRegistro;

	@Column(name = "terminal", nullable = false)
	@NotNull
	private String terminal;

	public Integer getCarteraDeudaId() {
		return carteraDeudaId;
	}

	public void setCarteraDeudaId(Integer carteraDeudaId) {
		this.carteraDeudaId = carteraDeudaId;
	}

	public BigDecimal getMontoMinimo() {
		return montoMinimo;
	}

	public void setMontoMinimo(BigDecimal montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	public BigDecimal getMontoMaximo() {
		return montoMaximo;
	}

	public void setMontoMaximo(BigDecimal montoMaximo) {
		this.montoMaximo = montoMaximo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
}