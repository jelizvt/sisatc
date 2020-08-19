package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.util.Date;

public class CajaAperturaDTO implements Serializable {

	private static final long serialVersionUID = -4572296116105361533L;

	private int aperturaId;
	private int agenciaId;
	private int agenciaOperacionId;
	private Date fechaApertura;
	private Date fechaCierre;
	private int usuarioId;
	private String estado;
	private Date fechaRegistro;
	private String terminal;
	private String usuarioDes;
	private String agenciaDes;
	private String estadoDes;
	private String flagOper;
	
	public CajaAperturaDTO(){
	}

	public int getAperturaId() {
		return aperturaId;
	}

	public void setAperturaId(int aperturaId) {
		this.aperturaId = aperturaId;
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

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
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

	public String getUsuarioDes() {
		return usuarioDes;
	}

	public void setUsuarioDes(String usuarioDes) {
		this.usuarioDes = usuarioDes;
	}

	public String getAgenciaDes() {
		return agenciaDes;
	}

	public void setAgenciaDes(String agenciaDes) {
		this.agenciaDes = agenciaDes;
	}

	public String getEstadoDes() {
		return estadoDes;
	}

	public void setEstadoDes(String estadoDes) {
		this.estadoDes = estadoDes;
	}

	public String getFlagOper() {
		return flagOper;
	}

	public void setFlagOper(String flagOper) {
		this.flagOper = flagOper;
	}
}
