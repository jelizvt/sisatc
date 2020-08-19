package com.sat.sisat.persistence.entity;

import java.io.Serializable;
//import javax.persistence.*;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the ra_alcabala_sustento database table.
 * 
 */
@Entity
@Table(name="ra_alcabala_sustento")
public class RaAlcabalaSustento implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name="ra_alcabala_sustento_id")
	private int raAlcabalaSustentoId;

	@Column(name="djalcabala_id")
	private int djalcabalaId;
	
	@Column(name="doc_sustentatorio_id")
	private int docSustentatorioId;
	
	@Column(name="referencia")
	private String referencia;

	@Column(name="numero_documento")
	private String numeroDocumento;
	
	@Column(name="content_id")
	private Integer contentId;

	@Column(name="terminal")
	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

	@Column(name = "fecha_registro")
	private Timestamp fechaRegistro;
	
	@Column(name="estado")
	private String estado;
	
	
	public String getNumeroDocumento() {
		return numeroDocumento;
	}


	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}



	public Integer getContentId() {
		return contentId;
	}


	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	
    public RaAlcabalaSustento() {
    }



	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
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

	public int getRaAlcabalaSustentoId() {
		return raAlcabalaSustentoId;
	}

	public void setRaAlcabalaSustentoId(int raAlcabalaSustentoId) {
		this.raAlcabalaSustentoId = raAlcabalaSustentoId;
	}

	public int getDjalcabalaId() {
		return djalcabalaId;
	}

	public void setDjalcabalaId(int djalcabalaId) {
		this.djalcabalaId = djalcabalaId;
	}

	public int getDocSustentatorioId() {
		return docSustentatorioId;
	}

	public void setDocSustentatorioId(int docSustentatorioId) {
		this.docSustentatorioId = docSustentatorioId;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}


}