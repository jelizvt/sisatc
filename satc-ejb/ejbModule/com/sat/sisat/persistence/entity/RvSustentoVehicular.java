package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rv_sustento_vehicular database table.
 * 
 */
@Entity
@Table(name="rv_sustento_vehicular")
public class RvSustentoVehicular implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RvSustentoVehicularPK id;

	@Column(name="doc_sustentatorio_id")
	private int docSustentatorioId;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="nro_documento")
	private String nroDocumento;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;
	
	//INICIO ITANTAMANGO
	@Column(name="referencia")
	private String referencia;
	
	@Column(name="content_id")
	private Integer contentId;

    public Integer getContentId() {
		return contentId;
	}

	public void setContent_id(Integer contentId) {
		this.contentId = contentId;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	//FIN ITANTAMANGO

	public RvSustentoVehicular() {
    }

	public RvSustentoVehicularPK getId() {
		return this.id;
	}

	public void setId(RvSustentoVehicularPK id) {
		this.id = id;
	}
	
	public int getDocSustentatorioId() {
		return this.docSustentatorioId;
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

}