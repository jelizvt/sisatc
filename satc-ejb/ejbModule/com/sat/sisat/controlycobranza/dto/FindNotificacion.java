package com.sat.sisat.controlycobranza.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;

public class FindNotificacion   implements Serializable {

	
	private Integer notificacionId;

	private Integer actoId;

	private Integer annoDocumento;

	private String estado;

	private String fechaNotificacion;

	private Timestamp fechaRegistro;

	private Integer loteId;

	private Integer motivoNotificacionId;

	private String nroCargo;

	private String nroDocumento;

	private String terminal;

	private Integer usuarioId;
	
	private Integer recId;
	
	
	
	public Integer getNotificacionId() {
		return notificacionId;
	}

	public void setNotificacionId(Integer notificacionId) {
		this.notificacionId = notificacionId;
	}

	public Integer getActoId() {
		return actoId;
	}

	public void setActoId(Integer actoId) {
		this.actoId = actoId;
	}

	public Integer getAnnoDocumento() {
		return annoDocumento;
	}

	public void setAnnoDocumento(Integer annoDocumento) {
		this.annoDocumento = annoDocumento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(String fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getLoteId() {
		return loteId;
	}

	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}

	public Integer getMotivoNotificacionId() {
		return motivoNotificacionId;
	}

	public void setMotivoNotificacionId(Integer motivoNotificacionId) {
		this.motivoNotificacionId = motivoNotificacionId;
	}

	public String getNroCargo() {
		return nroCargo;
	}

	public void setNroCargo(String nroCargo) {
		this.nroCargo = nroCargo;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getUsuarioId() {
		return usuarioId;
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

	public Integer getDetalleMasivaNotificacionId() {
		return detalleMasivaNotificacionId;
	}

	public void setDetalleMasivaNotificacionId(Integer detalleMasivaNotificacionId) {
		this.detalleMasivaNotificacionId = detalleMasivaNotificacionId;
	}

	public Integer getCorrelativo() {
		return correlativo;
	}

	public void setCorrelativo(Integer correlativo) {
		this.correlativo = correlativo;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getMasivaNotificacionId() {
		return masivaNotificacionId;
	}

	public void setMasivaNotificacionId(Integer masivaNotificacionId) {
		this.masivaNotificacionId = masivaNotificacionId;
	}

	public String getNroActo() {
		return nroActo;
	}

	public void setNroActo(String nroActo) {
		this.nroActo = nroActo;
	}

	public String getNroRec() {
		return nroRec;
	}

	public void setNroRec(String nroRec) {
		this.nroRec = nroRec;
	}

	public Integer getCodigoNotificador() {
		return codigoNotificador;
	}

	public void setCodigoNotificador(Integer codigoNotificador) {
		this.codigoNotificador = codigoNotificador;
	}

	public String getMotivoNotificacion() {
		return motivoNotificacion;
	}

	public void setMotivoNotificacion(String motivoNotificacion) {
		this.motivoNotificacion = motivoNotificacion;
	}

	public String getNotificador() {
		return notificador;
	}

	public void setNotificador(String notificador) {
		this.notificador = notificador;
	}

	private Integer detalleMasivaNotificacionId;

	private Integer correlativo;

	private String errorCode;

	private String errorMessage;

	private Integer masivaNotificacionId;

	private String nroActo;

	private String nroRec;

	private Integer codigoNotificador;
	
	private String motivoNotificacion;
	
	private String notificador;
	
}
