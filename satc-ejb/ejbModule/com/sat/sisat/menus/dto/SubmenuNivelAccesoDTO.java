package com.sat.sisat.menus.dto;
//-=CRAMIREZ=-
import java.io.Serializable;

public class SubmenuNivelAccesoDTO implements Serializable{
	private static final long serialVersionUID = 4636686895544895555L;
	
	private int nivelAccesoId;
	private String descripcion;
	private boolean habilitado;
	public int getNivelAccesoId() {
		return nivelAccesoId;
	}
	public void setNivelAccesoId(int nivelAccesoId) {
		this.nivelAccesoId = nivelAccesoId;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID; 
	}
	public boolean isHabilitado() {
		return habilitado;
	}
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
	
	
}
