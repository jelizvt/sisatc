package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the rj_docu_anexo database table.
 * 
 */
@Entity
@Table(name="pa_docu_anexo")
@NamedQuery(name="getAllPaDocuAnexo", query="SELECT a FROM PaDocuAnexo a WHERE a.papeletaId=:p_papeleta_id")
public class PaDocuAnexo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="papeleta_id")
	private Integer papeletaId;

	@Id
	@Column(name="docu_anexo_id")
	private Integer docuAnexoId;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="numero_documento")
	private String numeroDocumento;

	private String referencia;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="tipo_documento_id")
	private Integer tipoDocumentoId;
	
	@Column(name="content_id")
	private BigDecimal contentId;
	
	@Column(name="ruta")
	private String ruta;
	
	
	@Transient
	private String tipoDocumento;
	
	private String estado;
	
	public PaDocuAnexo() {
    }

	public Integer getDocuAnexoId() {
		return this.docuAnexoId;
	}

	public void setDocuAnexoId(Integer docuAnexoId) {
		this.docuAnexoId = docuAnexoId;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
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

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}

	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public Integer getPapeletaId() {
		return papeletaId;
	}

	public void setPapeletaId(Integer papeletaId) {
		this.papeletaId = papeletaId;
	}
	
	public BigDecimal getContentId() {
		return contentId;
	}

	public void setContentId(BigDecimal contentId) {
		this.contentId = contentId;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
}