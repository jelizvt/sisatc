package com.sat.sisat.predial.dto;

import java.io.Serializable;

public class FotoPredioInspeccionDTO  implements Serializable{
	private int fotoInspeccionId;
	private int personaId;
	private String apellidosNombres;
	private String direccionCompleta;
	private int predioId;
	private String direccionPredio;
	private int diasBaneja;
	private int diasAsignado;
	private int estado;
	private String nombreUsuarioRegistro;
	private String fechaRegistro;
	private String  nombreUsuarioAsigna;
	private String fechaAsigna;
	private String nombreUsuarioCierra;
	private String fechaCierra;
	private String nombreInspector;
	
	private Boolean flagActualizado;
	
	public int getFotoInspeccionId() {
		return fotoInspeccionId;
	}
	public void setFotoInspeccionId(int fotoInspeccionId) {
		this.fotoInspeccionId = fotoInspeccionId;
	}
	public int getPersonaId() {
		return personaId;
	}
	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}
	public String getApellidosNombres() {
		return apellidosNombres;
	}
	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}
	public String getDireccionCompleta() {
		return direccionCompleta;
	}
	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}
	public int getPredioId() {
		return predioId;
	}
	public void setPredioId(int predioId) {
		this.predioId = predioId;
	}
	public String getDireccionPredio() {
		return direccionPredio;
	}
	public void setDireccionPredio(String direccionPredio) {
		this.direccionPredio = direccionPredio;
	}
	public int getDiasBaneja() {
		return diasBaneja;
	}
	public void setDiasBaneja(int diasBaneja) {
		this.diasBaneja = diasBaneja;
	}
	public int getDiasAsignado() {
		return diasAsignado;
	}
	public void setDiasAsignado(int diasAsignado) {
		this.diasAsignado = diasAsignado;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getNombreUsuarioRegistro() {
		return nombreUsuarioRegistro;
	}
	public void setNombreUsuarioRegistro(String nombreUsuarioRegistro) {
		this.nombreUsuarioRegistro = nombreUsuarioRegistro;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getNombreUsuarioAsigna() {
		return nombreUsuarioAsigna;
	}
	public void setNombreUsuarioAsigna(String nombreUsuarioAsigna) {
		this.nombreUsuarioAsigna = nombreUsuarioAsigna;
	}
	public String getFechaAsigna() {
		return fechaAsigna;
	}
	public void setFechaAsigna(String fechaAsigna) {
		this.fechaAsigna = fechaAsigna;
	}
	public String getNombreUsuarioCierra() {
		return nombreUsuarioCierra;
	}
	public void setNombreUsuarioCierra(String nombreUsuarioCierra) {
		this.nombreUsuarioCierra = nombreUsuarioCierra;
	}
	public String getFechaCierra() {
		return fechaCierra;
	}
	public void setFechaCierra(String fechaCierra) {
		this.fechaCierra = fechaCierra;
	}
	public Boolean getFlagActualizado() {
		return flagActualizado;
	}
	public void setFlagActualizado(Boolean flagActualizado) {
		this.flagActualizado = flagActualizado;
	}
	public String getNombreInspector() {
		return nombreInspector;
	}
	public void setNombreInspector(String nombreInspector) {
		this.nombreInspector = nombreInspector;
	}
	
	

}
