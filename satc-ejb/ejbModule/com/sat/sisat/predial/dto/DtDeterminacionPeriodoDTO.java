package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;

import com.sat.sisat.persistence.entity.DtDeterminacion;

public class DtDeterminacionPeriodoDTO implements Serializable {
	
	private String annoDj;
	private DtDeterminacion determinacion;
	private Integer redetermina; 
	private Integer personaId;
	
	public Integer getPersonaId() {
		return personaId;
	}
	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	public Integer getRedetermina() {
		return redetermina;
	}
	public void setRedetermina(Integer redetermina) {
		this.redetermina = redetermina;
	}
	public DtDeterminacionPeriodoDTO(){
		determinacion=new DtDeterminacion(); 
	}
	public String getAnnoDj() {
		return annoDj;
	}
	public void setAnnoDj(String annoDj) {
		this.annoDj = annoDj;
	}
	public DtDeterminacion getDeterminacion() {
		return determinacion;
	}
	public void setDeterminacion(DtDeterminacion determinacion) {
		this.determinacion = determinacion;
	}

}
