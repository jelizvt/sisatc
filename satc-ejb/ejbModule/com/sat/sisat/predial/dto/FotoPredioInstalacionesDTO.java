package com.sat.sisat.predial.dto;

import java.io.Serializable;

public class FotoPredioInstalacionesDTO implements Serializable{
	
	private int annoInstalacion;
	private int mesInstalacion;
	private int tipoObra_id;
	private String valorInstalacion;
	private String mesdescripcion;
	private String tipoinstalaciondescripcion;
	private String nroNivel;
	private String areaTerreno;
	
	
	
	public int getAnnoInstalacion() {
		return annoInstalacion;
	}
	public void setAnnoInstalacion(int annoInstalacion) {
		this.annoInstalacion = annoInstalacion;
	}
	public int getMesInstalacion() {
		return mesInstalacion;
	}
	public void setMesInstalacion(int mesInstalacion) {
		this.mesInstalacion = mesInstalacion;
	}
	public int getTipoObra_id() {
		return tipoObra_id;
	}
	public void setTipoObra_id(int tipoObra_id) {
		this.tipoObra_id = tipoObra_id;
	}
	public String getValorInstalacion() {
		return valorInstalacion;
	}
	public void setValorInstalacion(String valorInstalacion) {
		this.valorInstalacion = valorInstalacion;
	}
	public String getMesdescripcion() {
		return mesdescripcion;
	}
	public void setMesdescripcion(String mesdescripcion) {
		this.mesdescripcion = mesdescripcion;
	}
	public String getTipoinstalaciondescripcion() {
		return tipoinstalaciondescripcion;
	}
	public void setTipoinstalaciondescripcion(String tipoinstalaciondescripcion) {
		this.tipoinstalaciondescripcion = tipoinstalaciondescripcion;
	}
	public String getNroNivel() {
		return nroNivel;
	}
	public void setNroNivel(String nroNivel) {
		this.nroNivel = nroNivel;
	}
	public String getAreaTerreno() {
		return areaTerreno;
	}
	public void setAreaTerreno(String areaTerreno) {
		this.areaTerreno = areaTerreno;
	}
	
	
	
}
