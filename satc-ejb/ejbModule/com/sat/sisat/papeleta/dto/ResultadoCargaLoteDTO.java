package com.sat.sisat.papeleta.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ResultadoCargaLoteDTO implements Serializable {
	private String error;
	private String archivoCarga;
	private String urlArchivoError;
	private Integer cantidadSubidos;
	private Integer cantidadError;
	private Integer totalRegistros;
	private Integer cargaLoteId;
	
	public Integer getCargaLoteId() {
		return cargaLoteId;
	}
	public void setCargaLoteId(Integer cargaLoteId) {
		this.cargaLoteId = cargaLoteId;
	}
	public Integer getTotalRegistros() {
		return totalRegistros;
	}
	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getArchivoCarga() {
		return archivoCarga;
	}
	public void setArchivoCarga(String archivoCarga) {
		this.archivoCarga = archivoCarga;
	}
	public String getUrlArchivoError() {
		return urlArchivoError;
	}
	public void setUrlArchivoError(String urlArchivoError) {
		this.urlArchivoError = urlArchivoError;
	}
	public Integer getCantidadSubidos() {
		return cantidadSubidos;
	}
	public void setCantidadSubidos(Integer cantidadSubidos) {
		this.cantidadSubidos = cantidadSubidos;
	}
	public Integer getCantidadError() {
		return cantidadError;
	}
	public void setCantidadError(Integer cantidadError) {
		this.cantidadError = cantidadError;
	}
}
