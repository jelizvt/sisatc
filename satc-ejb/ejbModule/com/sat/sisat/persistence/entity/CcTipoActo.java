package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the cc_tipo_acto database table.
 * 
 */
@Entity
@Table(name="cc_tipo_acto")
public class CcTipoActo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_acto_id")
	private int tipoActoId;

	private String descripcion;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="flag_informativo")
	private String flagInformativo;

	private String terminal;

	@Column(name="tipo_documento")
	private Integer tipoDocumento;

	@Column(name="tipo_lote_id")
	private Integer tipoLoteId;

	@Column(name="usuario_id")
	private Integer usuarioId;

    public CcTipoActo() {
    }

	public int getTipoActoId() {
		return this.tipoActoId;
	}

	public void setTipoActoId(Integer tipoActoId) {
		this.tipoActoId = tipoActoId;
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

	public String getFlagInformativo() {
		return this.flagInformativo;
	}

	public void setFlagInformativo(String flagInformativo) {
		this.flagInformativo = flagInformativo;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Integer getTipoLoteId() {
		return this.tipoLoteId;
	}

	public void setTipoLoteId(Integer tipoLoteId) {
		this.tipoLoteId = tipoLoteId;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

}