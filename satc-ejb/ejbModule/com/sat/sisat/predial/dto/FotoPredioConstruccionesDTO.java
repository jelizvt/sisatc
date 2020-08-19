package com.sat.sisat.predial.dto;

import java.io.Serializable;

public class FotoPredioConstruccionesDTO implements Serializable{
	private int orden;
	private int nivel;
	private String seccion;
	private int annoConstruccion;
	private String categorias;
	private String areaConstruccion;
	private String valorConstruccion;
	
	private String area_terreno;
	private String base_afecta;
	private String arancel;
	
	private Boolean inspeccion;
	
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public String getSeccion() {
		return seccion;
	}
	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
	public int getAnnoConstruccion() {
		return annoConstruccion;
	}
	public void setAnnoConstruccion(int annoConstruccion) {
		this.annoConstruccion = annoConstruccion;
	}
	public String getCategorias() {
		return categorias;
	}
	public void setCategorias(String categorias) {
		this.categorias = categorias;
	}
	public String getAreaConstruccion() {
		return areaConstruccion;
	}
	public void setAreaConstruccion(String areaConstruccion) {
		this.areaConstruccion = areaConstruccion;
	}
	public String getValorConstruccion() {
		return valorConstruccion;
	}
	public void setValorConstruccion(String valorConstruccion) {
		this.valorConstruccion = valorConstruccion;
	}
	public String getArea_terreno() {
		return area_terreno;
	}
	public void setArea_terreno(String area_terreno) {
		this.area_terreno = area_terreno;
	}
	public String getBase_afecta() {
		return base_afecta;
	}
	public void setBase_afecta(String base_afecta) {
		this.base_afecta = base_afecta;
	}
	public String getArancel() {
		return arancel;
	}
	public void setArancel(String arancel) {
		this.arancel = arancel;
	}
	public Boolean getInspeccion() {
		return inspeccion;
	}
	public void setInspeccion(Boolean inspeccion) {
		this.inspeccion = inspeccion;
	}
	
	
	

}
