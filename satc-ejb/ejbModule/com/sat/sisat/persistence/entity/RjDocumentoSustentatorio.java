package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rj_documento_sustentatorio database table.
 * 
 */
@Entity
@Table(name="rj_documento_sustentatorio")
public class RjDocumentoSustentatorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="doc_sustentatorio_id")
	private int docSustentatorioId;

	private String item;
	
	private String numerodocumento;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="usuario_id")
	private int usuarioId;

	@Column(name="fecha_inicio")
	private Timestamp fechaInicio;
	
	@Column(name="fecha_fin")
	private Timestamp fechaFin;
	
	@Column(name="dj_id")
	private int djId;
	
	@Column(name="tipo_docu_sustento_id")
	private int tipoDocuSustentoId;
	
	private String referencia;
	
	public RjDocumentoSustentatorio() {
    	
    }

	public int getDocSustentatorioId() {
		return this.docSustentatorioId;
	}

	public void setDocSustentatorioId(int docSustentatorioId) {
		this.docSustentatorioId = docSustentatorioId;
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

	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getDjId() {
		return djId;
	}

	public void setDjId(int djId) {
		this.djId = djId;
	}

	public int getTipoDocuSustentoId() {
		return tipoDocuSustentoId;
	}

	public void setTipoDocuSustentoId(int tipoDocuSustentoId) {
		this.tipoDocuSustentoId = tipoDocuSustentoId;
	}
	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	public String getNumerodocumento() {
		return numerodocumento;
	}

	public void setNumerodocumento(String numerodocumento) {
		this.numerodocumento = numerodocumento;
	}
}