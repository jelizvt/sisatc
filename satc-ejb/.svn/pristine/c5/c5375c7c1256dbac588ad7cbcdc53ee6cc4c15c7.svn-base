package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the mp_predio database table.
 * 
 */
@Entity
@Table(name="mp_predio")
public class MpPredio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="predio_id")
	private Integer predioId;

	@Column(name="area_terreno")
	private BigDecimal areaTerreno;

	@Column(name="area_terreno_has")
	private BigDecimal areaTerrenoHas;
	
	@Column(name="area_terreno_comun")
	private BigDecimal areaTerrenoComun;

	@Column(name="codigo_anterior")
	private String codigoAnterior;

	@Column(name="codigo_predio")
	private String codigoPredio;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="fecha_adquisicion")
	private Date fechaAdquisicion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private BigDecimal frente;

	@Column(name="id_anterior")
	private String idAnterior;
	
	@Column(name="tipo_predio")
	private String tipoPredio;

	@Column(name="terminal")
	private String terminal;
	
	@Column(name="usuario_id")
	private Integer usuarioId;

	private String estado;
	
	@Column(name="area_terreno_comun_has")
	private BigDecimal areaTerrenoComunHas;
	
	public MpPredio() {
    }

	public Integer getPredioId() {
		return this.predioId;
	}

	public void setPredioId(Integer predioId) {
		this.predioId = predioId;
	}

	public BigDecimal getAreaTerreno() {
		return this.areaTerreno;
	}

	public void setAreaTerreno(BigDecimal areaTerreno) {
		this.areaTerreno = areaTerreno;
	}

	public BigDecimal getAreaTerrenoComun() {
		return this.areaTerrenoComun;
	}

	public void setAreaTerrenoComun(BigDecimal areaTerrenoComun) {
		this.areaTerrenoComun = areaTerrenoComun;
	}

	public String getCodigoAnterior() {
		return this.codigoAnterior;
	}

	public void setCodigoAnterior(String codigoAnterior) {
		this.codigoAnterior = codigoAnterior;
	}

	public String getCodigoPredio() {
		return this.codigoPredio;
	}

	public void setCodigoPredio(String codigoPredio) {
		this.codigoPredio = codigoPredio;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaAdquisicion() {
		return this.fechaAdquisicion;
	}

	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public BigDecimal getFrente() {
		return this.frente;
	}

	public void setFrente(BigDecimal frente) {
		this.frente = frente;
	}

	public String getIdAnterior() {
		return this.idAnterior;
	}

	public void setIdAnterior(String idAnterior) {
		this.idAnterior = idAnterior;
	}

	public String getTipoPredio() {
		return this.tipoPredio;
	}

	public void setTipoPredio(String tipoPredio) {
		this.tipoPredio = tipoPredio;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public BigDecimal getAreaTerrenoHas() {
		return areaTerrenoHas;
	}

	public void setAreaTerrenoHas(BigDecimal areaTerrenoHas) {
		this.areaTerrenoHas = areaTerrenoHas;
	}
	
	public BigDecimal getAreaTerrenoComunHas() {
		return areaTerrenoComunHas;
	}

	public void setAreaTerrenoComunHas(BigDecimal areaTerrenoComunHas) {
		this.areaTerrenoComunHas = areaTerrenoComunHas;
	}
}