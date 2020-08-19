package com.sat.sisat.fiscalizacion.dto;

import java.io.Serializable;
import java.util.Date;

public class FindInpscDocTipo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer tipoDocumentoId;
	private String descripcionTipoDocumento;
	private String descripcionCortaTipoDocumento;
	private String tabla;
	private String columna;
	private Integer valorCorrelativoDocumento;
	private String  valorCorrelativo;
	
	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}
	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}
	public String getDescripcionTipoDocumento() {
		return descripcionTipoDocumento;
	}
	public void setDescripcionTipoDocumento(String descripcionTipoDocumento) {
		this.descripcionTipoDocumento = descripcionTipoDocumento;
	}
	public String getDescripcionCortaTipoDocumento() {
		return descripcionCortaTipoDocumento;
	}
	public void setDescripcionCortaTipoDocumento(
			String descripcionCortaTipoDocumento) {
		this.descripcionCortaTipoDocumento = descripcionCortaTipoDocumento;
	}
	public String getTabla() {
		return tabla;
	}
	public void setTabla(String tabla) {
		this.tabla = tabla;
	}
	public String getColumna() {
		return columna;
	}
	public void setColumna(String columna) {
		this.columna = columna;
	}
	public Integer getValorCorrelativoDocumento() {
		return valorCorrelativoDocumento;
	}
	public void setValorCorrelativoDocumento(Integer valorCorrelativoDocumento) {
		this.valorCorrelativoDocumento = valorCorrelativoDocumento;
	}
	public String getValorCorrelativo() {
		return valorCorrelativo;
	}
	public void setValorCorrelativo(String valorCorrelativo) {
		this.valorCorrelativo = valorCorrelativo;
	}

}
