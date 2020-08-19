package com.sat.sisat.caja.dto;

import java.io.Serializable;

public class CjMotivos implements Serializable {
	
	private int Motivo_extorno_id;
	private String descripcionExtorno;
	private int Motivo_cuadre_id;  
	
	private String descripcionCierreCaja;

	public int getMotivo_extorno_id() {
		return Motivo_extorno_id;
	}

	public void setMotivo_extorno_id(int motivo_extorno_id) {
		Motivo_extorno_id = motivo_extorno_id;
	}

	public String getDescripcionExtorno() {
		return descripcionExtorno;
	}

	public void setDescripcionExtorno(String descripcionExtorno) {
		this.descripcionExtorno = descripcionExtorno;
	}

	public String getDescripcionCierreCaja() {
		return descripcionCierreCaja;
	}

	public void setDescripcionCierreCaja(String descripcionCierreCaja) {
		this.descripcionCierreCaja = descripcionCierreCaja;
	}

	public int getMotivo_cuadre_id() {
		return Motivo_cuadre_id;
	}

	public void setMotivo_cuadre_id(int motivo_cuadre_id) {
		Motivo_cuadre_id = motivo_cuadre_id;
	}
	
	
	

}
