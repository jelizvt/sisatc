package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.util.Date;

public class AgenciaOperacionDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private int agenciaId;
	private int agenciaOperacionId;
	private Date fechaApertura;
	private Date fechaCierre;
	private String estado;
	private int usuarioId;
	private Date fechaRegistro;
	private String terminal;
	private String nombreAgencia;
	private String estadoDes;
	private String flagOperacion;
	private String usuarioDes;
	
	public AgenciaOperacionDTO(){
	}

	public int getAgenciaId() {
		return agenciaId;
	}

	public void setAgenciaId(int agenciaId) {
		this.agenciaId = agenciaId;
	}

	public int getAgenciaOperacionId() {
		return agenciaOperacionId;
	}

	public void setAgenciaOperacionId(int agenciaOperacionId) {
		this.agenciaOperacionId = agenciaOperacionId;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
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

	public String getNombreAgencia() {
		return nombreAgencia;
	}

	public void setNombreAgencia(String nombreAgencia) {
		this.nombreAgencia = nombreAgencia;
	}

	public String getEstadoDes() {
		return estadoDes;
	}

	public void setEstadoDes(String estadoDes) {
		this.estadoDes = estadoDes;
	}

	public String getFlagOperacion() {
		return flagOperacion;
	}

	public void setFlagOperacion(String flagOperacion) {
		this.flagOperacion = flagOperacion;
	}

	public String getUsuarioDes() {
		return usuarioDes;
	}

	public void setUsuarioDes(String usuarioDes) {
		this.usuarioDes = usuarioDes;
	}
}
