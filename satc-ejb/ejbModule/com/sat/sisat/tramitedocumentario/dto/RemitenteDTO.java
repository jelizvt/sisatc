package com.sat.sisat.tramitedocumentario.dto;

import java.io.Serializable;
import java.util.Date;

public class RemitenteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2749196933226484691L;
	
	private String priNombre;
	private String segNombre;
	private String apePaterno;
	private String apeMaterno;
	private String razonSocial;
	private String nroDoc;
	private String dirCompleta;
	private String terminal;	
	private Integer usarioId;	
	private Integer tipoDoc;
	private String nroDNI;
	private String nroRUC;
	//para validar tipo
	private String tipo;
	
	public String getPriNombre() {
		return priNombre;
	}
	public void setPriNombre(String priNombre) {
		this.priNombre = priNombre;
	}
	public String getSegNombre() {
		return segNombre;
	}
	public void setSegNombre(String segNombre) {
		this.segNombre = segNombre;
	}
	public String getApePaterno() {
		return apePaterno;
	}
	public void setApePaterno(String apePaterno) {
		this.apePaterno = apePaterno;
	}
	public String getApeMaterno() {
		return apeMaterno;
	}
	public void setApeMaterno(String apeMaterno) {
		this.apeMaterno = apeMaterno;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getNroDoc() {
		return nroDoc;
	}
	public void setNroDoc(String nroDoc) {
		this.nroDoc = nroDoc;
	}
	public String getDirCompleta() {
		return dirCompleta;
	}
	public void setDirCompleta(String dirCompleta) {
		this.dirCompleta = dirCompleta;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public Integer getUsarioId() {
		return usarioId;
	}
	public void setUsarioId(Integer usarioId) {
		this.usarioId = usarioId;
	}
	public Integer getTipoDoc() {
		return tipoDoc;
	}
	public void setTipoDoc(Integer tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	public String getNroDNI() {
		return nroDNI;
	}
	public void setNroDNI(String nroDNI) {
		this.nroDNI = nroDNI;
	}
	public String getNroRUC() {
		return nroRUC;
	}
	public void setNroRUC(String nroRUC) {
		this.nroRUC = nroRUC;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
