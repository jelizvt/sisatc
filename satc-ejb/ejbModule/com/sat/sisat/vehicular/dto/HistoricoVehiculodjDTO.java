package com.sat.sisat.vehicular.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class HistoricoVehiculodjDTO implements Serializable {

	private static final long serialVersionUID = -2174559367338895949L;

	private int djVehicularId;
	private String djvehicularNro;
	private int motivoDeclaId;
	private String motivoDecla;
	private String anioDecla;
	private String anioIniAfec;
	private String anioFinAfec;
	private String anioAfec;
	private Date fechaDecla;
	private Date fechaAdquisicion;
	private String notaria;
	private String estado;
	private String flagDjAnio;
	private boolean determinado;
	private String anioIniAfecContrib;
	private String usuario;
	private Date fechaDescargo;	
	private String glosa;
	private Integer vehiculoId;
	private Integer personaId;
	
	/**
	 * Contiene el anho de descargo si es una DJ de descargo, si no lo es por defecto tiene el valor 0
	 * */
	private String anhoDescargo;
	
	private Date fechaRegistro;
	
	public Boolean esPendiente(){
		return estado.equals("2")?Boolean.TRUE:Boolean.FALSE;
	}
	public Boolean esPendienteDeDescargo(){
		return estado.equals("3")?Boolean.TRUE:Boolean.FALSE;
	}
	public Boolean esPendienteDeActualizacion(){
		return estado.equals("4")?Boolean.TRUE:Boolean.FALSE;
	}
	public Boolean esDescargo(){
		return motivoDeclaId == 4 ? Boolean.TRUE:Boolean.FALSE;
	}
	public Boolean esDjActivo(){
		return flagDjAnio.equals("1")?Boolean.TRUE:Boolean.FALSE;
	}
	public String getStyleSelected() {
        if(esPendiente())
        	return "yellow";
        else if(esDescargo())
        	return "#E6E6E6";       
        else if(esPendienteDeDescargo())
        	return "#F4FA58";    
        else if(esPendienteDeActualizacion())
        	return "#F5FA68"; 
        else if(esDjActivo())
        	return "#99FF99";
        else if(esEliminado())
        	return "#F6CECE";
        else 
        	return "white";
    }
	public Boolean esEliminado(){
		return estado.equals("9")?Boolean.TRUE:Boolean.FALSE;
	}
	
	
	/***
	 * Módulo R.D.VEHICULAR-2015
	 */
	private int requerimientoId;
	private String fiscalizado; 
	private String fiscaAceptada; 
	private String fiscaCerrada; 

	public HistoricoVehiculodjDTO() {
		super();
	}

	public int getDjVehicularId() {
		return djVehicularId;
	}

	public void setDjVehicularId(int djVehicularId) {
		this.djVehicularId = djVehicularId;
	}

	public String getDjvehicularNro() {
		return djvehicularNro;
	}

	public void setDjvehicularNro(String djvehicularNro) {
		this.djvehicularNro = djvehicularNro;
	}

	public int getMotivoDeclaId() {
		return motivoDeclaId;
	}

	public void setMotivoDeclaId(int motivoDeclaId) {
		this.motivoDeclaId = motivoDeclaId;
	}

	public String getMotivoDecla() {
		return motivoDecla;
	}

	public void setMotivoDecla(String motivoDecla) {
		this.motivoDecla = motivoDecla;
	}

	public String getAnioDecla() {
		return anioDecla;
	}

	public void setAnioDecla(String anioDecla) {
		this.anioDecla = anioDecla;
	}

	public String getAnioIniAfec() {
		return anioIniAfec;
	}

	public void setAnioIniAfec(String anioIniAfec) {
		this.anioIniAfec = anioIniAfec;
	}

	public String getAnioFinAfec() {
		return anioFinAfec;
	}

	public void setAnioFinAfec(String anioFinAfec) {
		this.anioFinAfec = anioFinAfec;
	}

	public String getAnioAfec() {
		return anioAfec;
	}

	public void setAnioAfec(String anioAfec) {
		this.anioAfec = anioAfec;
	}

	public Date getFechaDecla() {
		return fechaDecla;
	}

	public void setFechaDecla(Date fechaDecla) {
		this.fechaDecla = fechaDecla;
	}

	public String getNotaria() {
		return notaria;
	}

	public void setNotaria(String notaria) {
		this.notaria = notaria;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFlagDjAnio() {
		return flagDjAnio;
	}

	public void setFlagDjAnio(String flagDjAnio) {
		this.flagDjAnio = flagDjAnio;
	}

	public boolean isDeterminado() {
		return determinado;
	}

	public void setDeterminado(boolean determinado) {
		this.determinado = determinado;
	}

	public String getAnioIniAfecContrib() {
		return anioIniAfecContrib;
	}

	public void setAnioIniAfecContrib(String anioIniAfecContrib) {
		this.anioIniAfecContrib = anioIniAfecContrib;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Date getFechaDescargo() {
		return fechaDescargo;
	}

	public void setFechaDescargo(Date fechaDescargo) {
		this.fechaDescargo = fechaDescargo;
	}
	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}
	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}
	public String getAnhoDescargo() {
		return anhoDescargo;
	}
	public void setAnhoDescargo(String anhoDescargo) {
		this.anhoDescargo = anhoDescargo;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	public Integer getVehiculoId() {
		return vehiculoId;
	}
	public void setVehiculoId(Integer vehiculoId) {
		this.vehiculoId = vehiculoId;
	}
	public Integer getPersonaId() {
		return personaId;
	}
	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	public int getRequerimientoId() {
		return requerimientoId;
	}
	public void setRequerimientoId(int requerimientoId) {
		this.requerimientoId = requerimientoId;
	}
	public String getFiscalizado() {
		return fiscalizado;
	}
	public void setFiscalizado(String fiscalizado) {
		this.fiscalizado = fiscalizado;
	}
	public String getFiscaAceptada() {
		return fiscaAceptada;
	}
	public void setFiscaAceptada(String fiscaAceptada) {
		this.fiscaAceptada = fiscaAceptada;
	}
	public String getFiscaCerrada() {
		return fiscaCerrada;
	}
	public void setFiscaCerrada(String fiscaCerrada) {
		this.fiscaCerrada = fiscaCerrada;
	}
	
	
}
