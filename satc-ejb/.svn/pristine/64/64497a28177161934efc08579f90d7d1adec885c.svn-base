package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rj_docu_anexo database table.
 * 
 */
@Entity
@Table(name="rj_docu_anexo")
public class RjDocuAnexo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="dj_id")
	private int djId;

	@Id
	@Column(name="docu_anexo_id")
	private int docuAnexoId;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="numero_documento")
	private String numeroDocumento;

	private String referencia;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

	@Column(name="tipo_documento_id")
	private int tipoDocumentoId;
	
	//INICIO ITANTAMANGO
	@Column(name="content_id")
	private Integer contentId;
	//FIN ITANTAMANGO
	
	@Transient
	private String tipoDocumento;
	
	private String estado;
	
	@Transient
	private int item;
	
	public RjDocuAnexo() {
    }

	public int getDjId() {
		return this.djId;
	}

	public void setDjId(int djId) {
		this.djId = djId;
	}

	public int getDocuAnexoId() {
		return this.docuAnexoId;
	}

	public void setDocuAnexoId(int docuAnexoId) {
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

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	public int getTipoDocumentoId() {
		return tipoDocumentoId;
	}

	public void setTipoDocumentoId(int tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}


}