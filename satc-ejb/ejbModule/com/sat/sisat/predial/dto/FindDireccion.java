package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class FindDireccion  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1483373443178849565L;
	private Integer viaId;
	private String numero;
	private String letra;
	private String numero2;
	private String letra2;
	private String codigoPredio;
	private String predioId;
	private String descDomicilio;
	private Timestamp fechaDeclaracion;
	private String personaId;
	private String codigoAnterior;
	
	public Integer getViaId() {
		return viaId;
	}
	public void setViaId(Integer viaId) {
		this.viaId = viaId;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getLetra() {
		return letra;
	}
	public void setLetra(String letra) {
		this.letra = letra;
	}
	public String getNumero2() {
		return numero2;
	}
	public void setNumero2(String numero2) {
		this.numero2 = numero2;
	}
	public String getLetra2() {
		return letra2;
	}
	public void setLetra2(String letra2) {
		this.letra2 = letra2;
	}
	public String getCodigoPredio() {
		return codigoPredio;
	}
	public void setCodigoPredio(String codigoPredio) {
		this.codigoPredio = codigoPredio;
	}
	public String getDescDomicilio() {
		return descDomicilio;
	}
	public void setDescDomicilio(String descDomicilio) {
		this.descDomicilio = descDomicilio;
	}
	public Timestamp getFechaDeclaracion() {
		return fechaDeclaracion;
	}
	public void setFechaDeclaracion(Timestamp fechaDeclaracion) {
		this.fechaDeclaracion = fechaDeclaracion;
	}
	public String getPredioId() {
		return predioId;
	}
	public void setPredioId(String predioId) {
		this.predioId = predioId;
	}
	public String getPersonaId() {
		return personaId;
	}
	public void setPersonaId(String personaId) {
		this.personaId = personaId;
	}
	public String getCodigoAnterior() {
		return codigoAnterior;
	}
	public void setCodigoAnterior(String codigoAnterior) {
		this.codigoAnterior = codigoAnterior;
	}
	
}
