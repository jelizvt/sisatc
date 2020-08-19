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
 * The persistent class for the rp_djuso database table.
 * 
 */
@Entity
@Table(name="rp_djuso_detalle")
public class RpDjusoDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="djuso_detalle_id")
	private Integer djusoDetalleId;
	
	@Column(name="djuso_id")
	private Integer djusoId;

	@Column(name="construccion_id")
	private Integer construccionId;
	
	@Column(name="area_uso")
	private BigDecimal areaUso;//area asignada
	
	@Column(name="mes_fin")
	private String mesFin;

	@Column(name="mes_inicio")
	private String mesInicio;
	
	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="glosa")
	private String glosa;
	
	@Transient
	private String descripcionnivel;
	@Transient
	private String numeronivel;
	@Transient
	private BigDecimal areaConstruccion;
	@Transient
	private BigDecimal areaUsada;
	
	public Integer getDjusoDetalleId() {
		return djusoDetalleId;
	}

	public void setDjusoDetalleId(Integer djusoDetalleId) {
		this.djusoDetalleId = djusoDetalleId;
	}
	
	public BigDecimal getAreaUsada() {
		return areaUsada;
	}

	public void setAreaUsada(BigDecimal areaUsada) {
		this.areaUsada = areaUsada;
	}

	public String getDescripcionnivel() {
		return descripcionnivel;
	}

	public void setDescripcionnivel(String descripcionnivel) {
		this.descripcionnivel = descripcionnivel;
	}

	public String getNumeronivel() {
		return numeronivel;
	}

	public void setNumeronivel(String numeronivel) {
		this.numeronivel = numeronivel;
	}

	public BigDecimal getAreaConstruccion() {
		return areaConstruccion;
	}

	public void setAreaConstruccion(BigDecimal areaConstruccion) {
		this.areaConstruccion = areaConstruccion;
	}

	public Integer getDjusoId() {
		return djusoId;
	}

	public void setDjusoId(Integer djusoId) {
		this.djusoId = djusoId;
	}

	public String getMesFin() {
		return mesFin!=null?mesFin.trim():mesFin;
	}

	public void setMesFin(String mesFin) {
		this.mesFin = mesFin;
	}

	public String getMesInicio() {
		return mesInicio!=null?mesInicio.trim():mesInicio;
	}

	public void setMesInicio(String mesInicio) {
		this.mesInicio = mesInicio;
	}

	public BigDecimal getAreaUso() {
		return areaUso;
	}

	public void setAreaUso(BigDecimal areaUso) {
		this.areaUso = areaUso;
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

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getConstruccionId() {
		return construccionId;
	}

	public void setConstruccionId(Integer construccionId) {
		this.construccionId = construccionId;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	

}