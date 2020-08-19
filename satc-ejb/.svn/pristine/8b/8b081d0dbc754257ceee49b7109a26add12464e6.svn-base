package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the no_notificacion database table.
 * 
 */
@Entity
@Table(name="no_notificacion")
public class NoNotificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="notificacion_id")
	private Integer notificacionId;

	@Column(name="acto_id")
	private Integer actoId;

	@Column(name="anno_documento")
	private Integer annoDocumento;


	private String estado;

	@Column(name="fecha_notificacion")
	private Timestamp fechaNotificacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="lote_id")
	private Integer loteId;

	@Column(name="motivo_notificacion_id")
	private Integer motivoNotificacionId;


	@Column(name="nro_cargo")
	private String nroCargo;

	@Column(name="nro_documento")
	private String nroDocumento;

	private String terminal;


	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="rec_id")
	private Integer recId;
	
//	@Column(name="ubicacion_id")
//	private String ubicacionId;

	public Integer getNotificadorId() {
		return notificadorId;
	}

	public void setNotificadorId(Integer notificadorId) {
		this.notificadorId = notificadorId;
	}

	@Column(name="notificador_id")
	private Integer notificadorId;

	
    public NoNotificacion() {
    }

	public Integer getNotificacionId() {
		return this.notificacionId;
	}

	public void setNotificacionId(Integer notificacionId) {
		this.notificacionId = notificacionId;
	}

	public Integer getActoId() {
		return this.actoId;
	}

	public void setActoId(Integer actoId) {
		this.actoId = actoId;
	}

	public Integer getAnnoDocumento() {
		return this.annoDocumento;
	}

	public void setAnnoDocumento(Integer annoDocumento) {
		this.annoDocumento = annoDocumento;
	}


	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaNotificacion() {
		return this.fechaNotificacion;
	}

	public void setFechaNotificacion(Timestamp fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getLoteId() {
		return this.loteId;
	}

	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}

	public Integer getMotivoNotificacionId() {
		return this.motivoNotificacionId;
	}

	public void setMotivoNotificacionId(Integer motivoNotificacionId) {
		this.motivoNotificacionId = motivoNotificacionId;
	}


	public String getNroCargo() {
		return this.nroCargo;
	}

	public void setNroCargo(String nroCargo) {
		this.nroCargo = nroCargo;
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


	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getRecId() {
		return recId;
	}

	public void setRecId(Integer recId) {
		this.recId = recId;
	}

//	public String getUbicacionId() {
//		return ubicacionId;
//	}
//
//	public void setUbicacionId(String ubicacionId) {
//		this.ubicacionId = ubicacionId;
//	}

}