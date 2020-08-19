package com.sat.sisat.estadocuenta.dto;

import java.io.Serializable;

public class CrGeneralDTO implements Serializable{
	
	private int id;
	private String descripcion;
	private String glosa;
	private boolean seleted;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isSeleted() {
		return seleted;
	}
	public void setSeleted(boolean seleted) {
		this.seleted = seleted;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	
	

}
