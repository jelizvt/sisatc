package com.sat.sisat.cobranzacoactiva.dto;

import java.io.Serializable;

import com.sat.sisat.common.dto.BaseDTO;


public class CoCartera implements Serializable {
	
	private Integer carteraId;
	private Integer ultTipoRecId;
	//cchaucca:inicio 14/07/2016 propiedades para el listado de tipos de rec generados por cartera
	private String fechaGeneracion;
	private String denominacionTipoRec;
	private Integer materiaId;
	//cchaucca:fin 14/07/2016
	
	public Integer getMateriaId() {
		return materiaId;
	}
	public void setMateriaId(Integer materiaId) {
		this.materiaId = materiaId;
	}
	public String getDenominacionTipoRec() {
		return denominacionTipoRec;
	}
	public void setDenominacionTipoRec(String denominacionTipoRec) {
		this.denominacionTipoRec = denominacionTipoRec;
	}
	public String getFechaGeneracion() {
		return fechaGeneracion;
	}
	public void setFechaGeneracion(String fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}
	public Integer getCarteraId() {
		return carteraId;
	}
	public void setCarteraId(Integer carteraId) {
		this.carteraId = carteraId;
	}
	public Integer getUltTipoRecId() {
		return ultTipoRecId;
	}
	public void setUltTipoRecId(Integer ultTipoRecId) {
		this.ultTipoRecId = ultTipoRecId;
	}
	
}
