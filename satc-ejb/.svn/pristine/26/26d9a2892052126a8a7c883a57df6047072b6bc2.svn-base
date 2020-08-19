package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="rp_sustento_predial")
public class RpSustentoPredial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="dj_id")
	private int djId;
	
	@Id
	@Access(AccessType.PROPERTY)
	@Column(name="sustento_id")
	private int sustentoId;
	
	@Column(name="doc_sustentatorio_id")
	private int docSustentatorioId;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="nro_documento")
	private String nroDocumento;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public RpSustentoPredial() {
    }

	public int getDocSustentatorioId() {
		return this.docSustentatorioId;
	}

	public int getSustentoId() {
		return sustentoId;
	}

	public void setSustentoId(int sustentoId) {
		this.sustentoId = sustentoId;
	}

	public void setDocSustentatorioId(int docSustentatorioId) {
		this.docSustentatorioId = docSustentatorioId;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNroDocumento() {
		return this.nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
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
	
	public int getDjId() {
		return djId;
	}

	public void setDjId(int djId) {
		this.djId = djId;
	}
}