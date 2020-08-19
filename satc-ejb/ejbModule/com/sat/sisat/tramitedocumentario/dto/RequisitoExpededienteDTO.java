package com.sat.sisat.tramitedocumentario.dto;

import java.util.Date;

public class RequisitoExpededienteDTO {
	
	private Integer requisitoExpedienteId;
	private Integer requisitoId;
	private Integer expedienteId;
	private Boolean flagPresentado;
	private Boolean flagSubsanado;
	private Integer nroDiasPlazo;
	private String glosa;
	private Integer usuarioId;
	private Boolean estado;
	private Date fechaRegistro;
	private String terminal;
	private Integer dj_id;
	public Integer getRequisitoExpedienteId() {
		return requisitoExpedienteId;
	}
	public void setRequisitoExpedienteId(Integer requisitoExpedienteId) {
		this.requisitoExpedienteId = requisitoExpedienteId;
	}
	public Integer getRequisitoId() {
		return requisitoId;
	}
	public void setRequisitoId(Integer requisitoId) {
		this.requisitoId = requisitoId;
	}
	public Integer getExpedienteId() {
		return expedienteId;
	}
	public void setExpedienteId(Integer expedienteId) {
		this.expedienteId = expedienteId;
	}
	public Boolean getFlagPresentado() {
		return flagPresentado;
	}
	public void setFlagPresentado(Boolean flagPresentado) {
		this.flagPresentado = flagPresentado;
	}
	public Boolean getFlagSubsanado() {
		return flagSubsanado;
	}
	public void setFlagSubsanado(Boolean flagSubsanado) {
		this.flagSubsanado = flagSubsanado;
	}
	public Integer getNroDiasPlazo() {
		return nroDiasPlazo;
	}
	public void setNroDiasPlazo(Integer nroDiasPlazo) {
		this.nroDiasPlazo = nroDiasPlazo;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
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
	public Integer getDj_id() {
		return dj_id;
	}
	public void setDj_id(Integer dj_id) {
		this.dj_id = dj_id;
	}
	
	

}
