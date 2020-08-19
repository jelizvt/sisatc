package com.sat.sisat.vehicular.dto;

import java.io.Serializable;

public class DistritoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int dpto_id;
	private int provincia_id;
	private int distrito_id;
	private String descripcion;
	private String codigo_postal;
	
	public int getDpto_id() {
		return dpto_id;
	}
	public void setDpto_id(int dpto_id) {
		this.dpto_id = dpto_id;
	}
	public int getProvincia_id() {
		return provincia_id;
	}
	public void setProvincia_id(int provincia_id) {
		this.provincia_id = provincia_id;
	}
	public int getDistrito_id() {
		return distrito_id;
	}
	public void setDistrito_id(int distrito_id) {
		this.distrito_id = distrito_id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodigo_postal() {
		return codigo_postal;
	}
	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}
	
	

}
