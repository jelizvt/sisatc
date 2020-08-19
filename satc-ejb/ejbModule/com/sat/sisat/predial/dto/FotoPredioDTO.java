package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class FotoPredioDTO implements Serializable{
	
	private int fotoPredioId;
	private int predioId;
	private int predioWebId;
	private String url;
	private int fuente;
	private int estado;
	
	public int getFotoPredioId() {
		return fotoPredioId;
	}
	public void setFotoPredioId(int fotoPredioId) {
		this.fotoPredioId = fotoPredioId;
	}
	public int getPredioId() {
		return predioId;
	}
	public void setPredioId(int predioId) {
		this.predioId = predioId;
	}
	public int getPredioWebId() {
		return predioWebId;
	}
	public void setPredioWebId(int predioWebId) {
		this.predioWebId = predioWebId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getFuente() {
		return fuente;
	}
	public void setFuente(int fuente) {
		this.fuente = fuente;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	
}
