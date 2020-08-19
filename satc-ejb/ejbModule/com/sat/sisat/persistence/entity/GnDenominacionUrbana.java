package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the gn_denominacion_urbana database table.
 * 
 */
@Entity
@Table(name="gn_denominacion_urbana")
public class GnDenominacionUrbana implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="deno_id")
	private Integer denoId;

	private String descripcion;
	
	@Transient
	private String descripcionTipoDeno;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	@Column(name="distrito_id")
	private Integer distritoId;

	@Column(name="dpto_id")
	private Integer dptoId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="lote_fin")
	private Integer loteFin;

	@Column(name="lote_inicio")
	private Integer loteInicio;

	private String manzana;

	@Column(name="provincia_id")
	private Integer provinciaId;

	@Column(name="sector_id")
	private Integer sectorId;

	private String terminal;

	@Column(name="tipo_deno_id")
	private Integer tipoDenoId;

	@Column(name="usuario_id")
	private Integer usuarioId;

    public GnDenominacionUrbana() {
    }

	public Integer getDenoId() {
		return this.denoId;
	}

	public void setDenoId(Integer denoId) {
		this.denoId = denoId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionCorta() {
		return this.descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public Integer getDistritoId() {
		return this.distritoId;
	}

	public void setDistritoId(Integer distritoId) {
		this.distritoId = distritoId;
	}

	public Integer getDptoId() {
		return this.dptoId;
	}

	public void setDptoId(Integer dptoId) {
		this.dptoId = dptoId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getLoteFin() {
		return this.loteFin;
	}

	public void setLoteFin(Integer loteFin) {
		this.loteFin = loteFin;
	}

	public Integer getLoteInicio() {
		return this.loteInicio;
	}

	public void setLoteInicio(Integer loteInicio) {
		this.loteInicio = loteInicio;
	}

	public String getManzana() {
		return this.manzana;
	}

	public void setManzana(String manzana) {
		this.manzana = manzana;
	}

	public Integer getProvinciaId() {
		return this.provinciaId;
	}

	public void setProvinciaId(Integer provinciaId) {
		this.provinciaId = provinciaId;
	}

	public Integer getSectorId() {
		return this.sectorId;
	}

	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getTipoDenoId() {
		return this.tipoDenoId;
	}

	public void setTipoDenoId(Integer tipoDenoId) {
		this.tipoDenoId = tipoDenoId;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getDescripcionTipoDeno() {
		return descripcionTipoDeno;
	}

	public void setDescripcionTipoDeno(String descripcionTipoDeno) {
		this.descripcionTipoDeno = descripcionTipoDeno;
	}
}