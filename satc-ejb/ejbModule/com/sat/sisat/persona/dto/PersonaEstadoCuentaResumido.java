package com.sat.sisat.persona.dto;

import java.io.Serializable;

public class PersonaEstadoCuentaResumido implements Serializable{
	
	private int personaId;
	private String annoDeuda;
	private int conceptoId;
	private String conceptoDescripcion;
	private int subconceptoId;
	private String subconceptoDescripcion;
	private String cuotas;
	public int getPersonaId() {
		return personaId;
	}
	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}
	public String getAnnoDeuda() {
		return annoDeuda;
	}
	public void setAnnoDeuda(String annoDeuda) {
		this.annoDeuda = annoDeuda;
	}
	public int getConceptoId() {
		return conceptoId;
	}
	public void setConceptoId(int conceptoId) {
		this.conceptoId = conceptoId;
	}
	public String getConceptoDescripcion() {
		return conceptoDescripcion;
	}
	public void setConceptoDescripcion(String conceptoDescripcion) {
		this.conceptoDescripcion = conceptoDescripcion;
	}
	public int getSubconceptoId() {
		return subconceptoId;
	}
	public void setSubconceptoId(int subconceptoId) {
		this.subconceptoId = subconceptoId;
	}
	public String getSubconceptoDescripcion() {
		return subconceptoDescripcion;
	}
	public void setSubconceptoDescripcion(String subconceptoDescripcion) {
		this.subconceptoDescripcion = subconceptoDescripcion;
	}
	public String getCuotas() {
		return cuotas;
	}
	public void setCuotas(String cuotas) {
		this.cuotas = cuotas;
	}

}
