package com.sat.sisat.caja.dto;

import java.io.Serializable;

import com.sat.sisat.persistence.entity.CjReciboDetalle;

public class CjReciboDetalleEntity extends CjReciboDetalle implements Serializable{
	
	
	private String moneda_des;

	public String getMoneda_des() {
		return moneda_des;
	}
	public void setMoneda_des(String moneda_des) {
		this.moneda_des = moneda_des;
	}
	

	

}
