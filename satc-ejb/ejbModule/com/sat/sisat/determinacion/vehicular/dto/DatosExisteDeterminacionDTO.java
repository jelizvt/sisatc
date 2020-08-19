package com.sat.sisat.determinacion.vehicular.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class DatosExisteDeterminacionDTO implements Serializable {

	private static final long serialVersionUID = 4157750215325392173L;

	private int determinacionId;
	private BigDecimal baseImponible;
	private BigDecimal baseExonerada;
	private BigDecimal baseAfecta;
	private BigDecimal impuesto;
	private String estado;
	private Integer usuario;
	private Timestamp fechaActualizacion;

	public DatosExisteDeterminacionDTO() {
	}

	public DatosExisteDeterminacionDTO(int determinacionId,
			BigDecimal baseImponible, BigDecimal baseExonerada,
			BigDecimal baseAfecta, BigDecimal impuesto, String estado) {
		super();
		this.determinacionId = determinacionId;
		this.baseImponible = baseImponible;
		this.baseExonerada = baseExonerada;
		this.baseAfecta = baseAfecta;
		this.impuesto = impuesto;
		this.estado = estado;
	}

	public int getDeterminacionId() {
		return determinacionId;
	}

	public void setDeterminacionId(int determinacionId) {
		this.determinacionId = determinacionId;
	}

	public BigDecimal getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}

	public BigDecimal getBaseExonerada() {
		return baseExonerada;
	}

	public void setBaseExonerada(BigDecimal baseExonerada) {
		this.baseExonerada = baseExonerada;
	}

	public BigDecimal getBaseAfecta() {
		return baseAfecta;
	}

	public void setBaseAfecta(BigDecimal baseAfecta) {
		this.baseAfecta = baseAfecta;
	}

	public BigDecimal getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(BigDecimal impuesto) {
		this.impuesto = impuesto;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
}
