package com.sat.sisat.usuario.dto;

import java.io.Serializable;

public class CajaPermisosDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private boolean isCajero;
	private boolean isSupervisorCaja;
	private boolean isNotario;
	
	public boolean isCajero() {
		return isCajero;
	}
	public void setCajero(boolean isCajero) {
		this.isCajero = isCajero;
	}
	public boolean isSupervisorCaja() {
		return isSupervisorCaja;
	}
	public void setSupervisorCaja(boolean isSupervisorCaja) {
		this.isSupervisorCaja = isSupervisorCaja;
	}
	public boolean isNotario() {
		return isNotario;
	}
	public void setNotario(boolean isNotario) {
		this.isNotario = isNotario;
	}
	
	

}
