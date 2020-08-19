package com.sat.sisat.reportes.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ReporteNotificacionDTO implements Serializable{
	
	private String notificador;
	private String tipoNotificacion;
	private String tipoValor;
	private String nroValor;
	private Timestamp fechaNotificacion;
	private Timestamp fechaRegistro;
	private String usuarioRegistro;
	
	public String getNotificador() {
		return notificador;
	}
	public void setNotificador(String notificador) {
		this.notificador = notificador;
	}
	public String getTipoNotificacion() {
		return tipoNotificacion;
	}
	public void setTipoNotificacion(String tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}
	public String getTipoValor() {
		return tipoValor;
	}
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}
	public String getNroValor() {
		return nroValor;
	}
	public void setNroValor(String nroValor) {
		this.nroValor = nroValor;
	}
	public Timestamp getFechaNotificacion() {
		return fechaNotificacion;
	}
	public void setFechaNotificacion(Timestamp fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}
	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}
	
	
}
