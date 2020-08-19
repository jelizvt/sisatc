package com.sat.sisat.vehicular.dto;

import java.io.Serializable;
import java.util.Date;

public class PreliminarDTO implements Serializable{
	
	private static final long serialVersionUID = -1856527724520632378L;
	
	/**
	 * Anho de la DJ la cual es valida (antes anno_declaracion)*/
	private String anhoAfectacion;
	private String numeroDecla;
	private String motivoDecla;
	private Date fechaDecla;
	private String placaVehic;
	private String nroMotorVehic;
	private Date fechaPrimeraInsReg;
	private String anioFabric;
	private String categoriaVehic;
	private String marcaVehic;
	private String modeloVehic;
	private String claseVehic;
	private Date fechaAdqui;
	private String tipoAdqui;
	private String tarjetaProp;
	private String condicionProp;
	private String porcentaje;
	private String tipoMoneda;
	private String valorAdqui;
	private String usuario;
	private String glosa;
	private Date fechaDescargo;

	public PreliminarDTO() {
	}

	public PreliminarDTO(String numeroDecla, String motivoDecla,
			Date fechaDecla, String placaVehic, String nroMotorVehic,
			Date fechaPrimeraInsReg, String anioFabric, String categoriaVehic,
			String marcaVehic, String modeloVehic, String claseVehic,
			Date fechaAdqui, String tipoAdqui, String tarjetaProp,
			String condicionProp, String porcentaje, String tipoMoneda,
			String valorAdqui) {
		super();
		this.numeroDecla = numeroDecla;
		this.motivoDecla = motivoDecla;
		this.fechaDecla = fechaDecla;
		this.placaVehic = placaVehic;
		this.nroMotorVehic = nroMotorVehic;
		this.fechaPrimeraInsReg = fechaPrimeraInsReg;
		this.anioFabric = anioFabric;
		this.categoriaVehic = categoriaVehic;
		this.marcaVehic = marcaVehic;
		this.modeloVehic = modeloVehic;
		this.claseVehic = claseVehic;
		this.fechaAdqui = fechaAdqui;
		this.tipoAdqui = tipoAdqui;
		this.tarjetaProp = tarjetaProp;
		this.condicionProp = condicionProp;
		this.porcentaje = porcentaje;
		this.tipoMoneda = tipoMoneda;
		this.valorAdqui = valorAdqui;
	}

	public String getNumeroDecla() {
		return numeroDecla;
	}

	public void setNumeroDecla(String numeroDecla) {
		this.numeroDecla = numeroDecla;
	}

	public String getMotivoDecla() {
		return motivoDecla;
	}

	public void setMotivoDecla(String motivoDecla) {
		this.motivoDecla = motivoDecla;
	}

	public Date getFechaDecla() {
		return fechaDecla;
	}

	public void setFechaDecla(Date fechaDecla) {
		this.fechaDecla = fechaDecla;
	}

	public String getPlacaVehic() {
		return placaVehic;
	}

	public void setPlacaVehic(String placaVehic) {
		this.placaVehic = placaVehic;
	}

	public String getNroMotorVehic() {
		return nroMotorVehic;
	}

	public void setNroMotorVehic(String nroMotorVehic) {
		this.nroMotorVehic = nroMotorVehic;
	}

	public Date getFechaPrimeraInsReg() {
		return fechaPrimeraInsReg;
	}

	public void setFechaPrimeraInsReg(Date fechaPrimeraInsReg) {
		this.fechaPrimeraInsReg = fechaPrimeraInsReg;
	}

	public String getAnioFabric() {
		return anioFabric;
	}

	public void setAnioFabric(String anioFabric) {
		this.anioFabric = anioFabric;
	}

	public String getCategoriaVehic() {
		return categoriaVehic;
	}

	public void setCategoriaVehic(String categoriaVehic) {
		this.categoriaVehic = categoriaVehic;
	}

	public String getMarcaVehic() {
		return marcaVehic;
	}

	public void setMarcaVehic(String marcaVehic) {
		this.marcaVehic = marcaVehic;
	}

	public String getModeloVehic() {
		return modeloVehic;
	}

	public void setModeloVehic(String modeloVehic) {
		this.modeloVehic = modeloVehic;
	}

	public String getClaseVehic() {
		return claseVehic;
	}

	public void setClaseVehic(String claseVehic) {
		this.claseVehic = claseVehic;
	}

	public Date getFechaAdqui() {
		return fechaAdqui;
	}

	public void setFechaAdqui(Date fechaAdqui) {
		this.fechaAdqui = fechaAdqui;
	}

	public String getTipoAdqui() {
		return tipoAdqui;
	}

	public void setTipoAdqui(String tipoAdqui) {
		this.tipoAdqui = tipoAdqui;
	}

	public String getTarjetaProp() {
		return tarjetaProp;
	}

	public void setTarjetaProp(String tarjetaProp) {
		this.tarjetaProp = tarjetaProp;
	}

	public String getCondicionProp() {
		return condicionProp;
	}

	public void setCondicionProp(String condicionProp) {
		this.condicionProp = condicionProp;
	}

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getTipoMoneda() {
		return tipoMoneda;
	}

	public void setTipoMoneda(String tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}

	public String getValorAdqui() {
		return valorAdqui;
	}

	public void setValorAdqui(String valorAdqui) {
		this.valorAdqui = valorAdqui;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public Date getFechaDescargo() {
		return fechaDescargo;
	}

	public void setFechaDescargo(Date fechaDescargo) {
		this.fechaDescargo = fechaDescargo;
	}

	public String getAnhoAfectacion() {
		return anhoAfectacion;
	}

	public void setAnhoAfectacion(String anhoAfectacion) {
		this.anhoAfectacion = anhoAfectacion;
	}
}
