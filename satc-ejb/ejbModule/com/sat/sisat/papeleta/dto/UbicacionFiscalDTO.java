package com.sat.sisat.papeleta.dto;

import java.io.Serializable;

public class UbicacionFiscalDTO implements Serializable {
	private Integer direccionId;
	private String direccionCompleta;
	private Integer viaId;
	private Integer tipoViaId;
	private Integer lugarId;
	private String numero;
	
	public Integer getDireccionId() {
		return direccionId;
	}
	public void setDireccionId(Integer direccionId) {
		this.direccionId = direccionId;
	}
	public String getDireccionCompleta() {
		return direccionCompleta;
	}
	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}
	public Integer getViaId() {
		return viaId;
	}
	public void setViaId(Integer viaId) {
		this.viaId = viaId;
	}
	public Integer getTipoViaId() {
		return tipoViaId;
	}
	public void setTipoViaId(Integer tipoViaId) {
		this.tipoViaId = tipoViaId;
	}
	public Integer getLugarId() {
		return lugarId;
	}
	public void setLugarId(Integer lugarId) {
		this.lugarId = lugarId;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
}
