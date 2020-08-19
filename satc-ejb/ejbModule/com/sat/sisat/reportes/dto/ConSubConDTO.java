package com.sat.sisat.reportes.dto;

import java.io.Serializable;

public class ConSubConDTO implements Serializable {

private static final long serialVersionUID = 1L;
	
	private Integer conceptoId;
	private Integer subConceptoId;
	private String descSubcon;
	private String descCon;

	public Integer getConceptoId() {
		return conceptoId;
	}
	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}
	public Integer getSubConceptoId() {
		return subConceptoId;
	}
	public void setSubConceptoId(Integer subConceptoId) {
		this.subConceptoId = subConceptoId;
	}
	public String getDescSubcon() {
		return descSubcon;
	}
	public void setDescSubcon(String descSubcon) {
		this.descSubcon = descSubcon;
	}
	public String getDescCon() {
		return descCon;
	}
	public void setDescCon(String descCon) {
		this.descCon = descCon;
	}
	
}
