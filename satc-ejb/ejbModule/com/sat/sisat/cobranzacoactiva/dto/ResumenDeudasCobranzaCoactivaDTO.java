package com.sat.sisat.cobranzacoactiva.dto;

import java.io.Serializable;

public class ResumenDeudasCobranzaCoactivaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -335556318396652190L;

	private String conceptoDescripcion;

	private Integer anho;

	private Integer nroCuotas;
	
	private String nroPapeleta;

	public String getConceptoDescripcion() {
		return conceptoDescripcion;
	}

	public void setConceptoDescripcion(String conceptoDescripcion) {
		this.conceptoDescripcion = conceptoDescripcion;
	}

	public Integer getAnho() {
		return anho;
	}

	public void setAnho(Integer anho) {
		this.anho = anho;
	}

	public Integer getNroCuotas() {
		return nroCuotas;
	}

	public void setNroCuotas(Integer nroCuotas) {
		this.nroCuotas = nroCuotas;
	}

	public String getNroPapeleta() {
		return nroPapeleta;
	}

	public void setNroPapeleta(String nroPapeleta) {
		this.nroPapeleta = nroPapeleta;
	}

}
