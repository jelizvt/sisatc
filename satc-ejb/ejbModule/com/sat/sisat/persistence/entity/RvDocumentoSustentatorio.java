package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rv_documento_sustentatorio database table.
 * 
 */
@Entity
@Table(name="rv_documento_sustentatorio")
public class RvDocumentoSustentatorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="doc_sustentatorio_id")
	private int docSustentatorioId;

	private String descripcion;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="usuario_id")
	private int usuarioId;

    public RvDocumentoSustentatorio() {
    }

	public int getDocSustentatorioId() {
		return this.docSustentatorioId;
	}

	public void setDocSustentatorioId(int docSustentatorioId) {
		this.docSustentatorioId = docSustentatorioId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}