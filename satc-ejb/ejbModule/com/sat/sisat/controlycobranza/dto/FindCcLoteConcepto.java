package com.sat.sisat.controlycobranza.dto;

import java.io.Serializable;



public class FindCcLoteConcepto implements Serializable {
	private Integer loteId;

	private Integer annoLote;

	private String descripcionLote;

	private Integer conceptoId;
	
	private String concepto;
	
	private Integer subconceptoId;
	
	private String subconcepto;
	
	private Integer loteActoId;

	public FindCcLoteConcepto(){}
	
	public String getSubconcepto() {
		return subconcepto;
	}
	
	@Override
	public String toString(){
		return subconcepto;
	}

	public void setSubconcepto(String subconcepto) {
		this.subconcepto = subconcepto;
	}

	public Integer getSubconceptoId() {
		return subconceptoId;
	}

	public void setSubconceptoId(Integer subconceptoId) {
		this.subconceptoId = subconceptoId;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Integer getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}



	public String getDescripcionLote() {
		return descripcionLote;
	}

	public void setDescripcionLote(String descripcionLote) {
		this.descripcionLote = descripcionLote;
	}

	public Integer getAnnoLote() {
		return annoLote;
	}

	public void setAnnoLote(Integer annoLote) {
		this.annoLote = annoLote;
	}

	public Integer getLoteId() {
		return loteId;
	}

	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}

	public Integer getLoteActoId() {
		return loteActoId;
	}

	public void setLoteActoId(Integer loteActoId) {
		this.loteActoId = loteActoId;
	}
}
