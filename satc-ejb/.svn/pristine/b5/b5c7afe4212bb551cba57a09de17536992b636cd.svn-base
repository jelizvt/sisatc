package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="gn_tipo_documento")
public class GnTipoDocumento implements Serializable{

	private static final long serialVersionUID = -3602916853230377435L;

	@Id
	@Column(name="tipo_documento_id")
	private int tipoDocumentoId;
	
	@Column(name="descripcion", length=250)
	private String descripcion;
	
	@Column(name="descripcion_corta", length=10)
	private String descripcionCorta;

	@Column(name="codigo_documento", length=5)
	private String codigoDocumento;
	
	@Column(name="flag_descargo" , length=1)
	private String flag_descargo;

	@Column(name="estado" , length=1)
	private String estado;
	
	@Column(name="usuario_id")
	private int usuarioId;	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;
		
	@Column(name="terminal", length=20)
	private String terminal;

	public int getTipoDocumentoId() {
		return tipoDocumentoId;
	}

	public void setTipoDocumentoId(int tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public String getCodigoDocumento() {
		return codigoDocumento;
	}

	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getFlag_descargo() {
		return flag_descargo;
	}

	public void setFlag_descargo(String flag_descargo) {
		this.flag_descargo = flag_descargo;
	}
	
}
