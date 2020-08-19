package com.sat.sisat.determinacion.vehicular.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DatosInafecDTO implements Serializable {

	private static final long serialVersionUID = 2317415022966684355L;

	private Date fechaInicio;
	private Date fechaFin;
	private String tipo; // 1 = Porcentaje
	private BigDecimal valor;
	private Integer usoPredioId;
	private Integer tipoCondEspecialId;
	
	public DatosInafecDTO() {
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public Integer getUsoPredioId() {
		return usoPredioId;
	}

	public void setUsoPredioId(Integer usoPredioId) {
		this.usoPredioId = usoPredioId;
	}
	public Integer getTipoCondEspecialId() {
		return tipoCondEspecialId;
	}

	public void setTipoCondEspecialId(Integer tipoCondEspecialId) {
		this.tipoCondEspecialId = tipoCondEspecialId;
	}
}
