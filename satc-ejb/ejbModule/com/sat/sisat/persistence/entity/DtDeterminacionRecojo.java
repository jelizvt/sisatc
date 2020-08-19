package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the cc_acto database table.
 * 
 */
@Entity
@Table(name="dt_determinacion_recojo")
public class DtDeterminacionRecojo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="determinacion_recojo_id")
	private Integer determinacionRecojoId;

	@Column(name="area_m2")
	private BigDecimal areaM2;

	@Column(name="categoria_uso_id")
	private Integer categoriaUsoId;

	@Column(name="costo_m2_anual")
	private BigDecimal costoM2Anual;

	@Column(name="determinacion_arbitrios_id")
	private Integer determinacionArbitriosId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

	public Integer getDeterminacionRecojoId() {
		return determinacionRecojoId;
	}

	public void setDeterminacionRecojoId(Integer determinacionRecojoId) {
		this.determinacionRecojoId = determinacionRecojoId;
	}

	public BigDecimal getAreaM2() {
		return areaM2;
	}

	public void setAreaM2(BigDecimal areaM2) {
		this.areaM2 = areaM2;
	}

	public Integer getCategoriaUsoId() {
		return categoriaUsoId;
	}

	public void setCategoriaUsoId(Integer categoriaUsoId) {
		this.categoriaUsoId = categoriaUsoId;
	}

	public BigDecimal getCostoM2Anual() {
		return costoM2Anual;
	}

	public void setCostoM2Anual(BigDecimal costoM2Anual) {
		this.costoM2Anual = costoM2Anual;
	}

	public Integer getDeterminacionArbitriosId() {
		return determinacionArbitriosId;
	}

	public void setDeterminacionArbitriosId(Integer determinacionArbitriosId) {
		this.determinacionArbitriosId = determinacionArbitriosId;
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

}