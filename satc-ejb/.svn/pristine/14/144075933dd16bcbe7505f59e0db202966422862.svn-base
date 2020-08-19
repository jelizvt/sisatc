package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The persistent class for the cc_lote_concepto database table.
 * 
 */
@Entity
@Table(name="cc_lote_concepto")
public class CcLoteConcepto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CcLoteConceptoPK id;

	@Column(name="agrupado_bien")
	private String agrupadoBien;

	@Column(name="agrupado_cuota")
	private String agrupadoCuota;

	@Column(name="concepto_id")
	private int conceptoId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="subconcepto_id")
	private int subconceptoId;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public CcLoteConcepto() {
    }

	public CcLoteConceptoPK getId() {
		return this.id;
	}

	public void setId(CcLoteConceptoPK id) {
		this.id = id;
	}
	
	public String getAgrupadoBien() {
		return this.agrupadoBien;
	}

	public void setAgrupadoBien(String agrupadoBien) {
		this.agrupadoBien = agrupadoBien;
	}

	public String getAgrupadoCuota() {
		return this.agrupadoCuota;
	}

	public void setAgrupadoCuota(String agrupadoCuota) {
		this.agrupadoCuota = agrupadoCuota;
	}

	public int getConceptoId() {
		return this.conceptoId;
	}

	public void setConceptoId(int conceptoId) {
		this.conceptoId = conceptoId;
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

	public int getSubconceptoId() {
		return this.subconceptoId;
	}

	public void setSubconceptoId(int subconceptoId) {
		this.subconceptoId = subconceptoId;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}