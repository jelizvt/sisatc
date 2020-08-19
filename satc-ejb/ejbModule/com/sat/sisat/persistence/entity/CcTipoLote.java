package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the cc_tipo_lote database table.
 * 
 */
@Entity
@Table(name="cc_tipo_lote")
public class CcTipoLote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_lote_id")
	private int tipoLoteId;

	private String descripcion;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="tipo_cobranza")
	private int tipoCobranza;

    public CcTipoLote() {
    }

	public int getTipoLoteId() {
		return this.tipoLoteId;
	}

	public void setTipoLoteId(int tipoLoteId) {
		this.tipoLoteId = tipoLoteId;
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

	public int getTipoCobranza() {
		return tipoCobranza;
	}

	public void setTipoCobranza(int tipoCobranza) {
		this.tipoCobranza = tipoCobranza;
	}

}