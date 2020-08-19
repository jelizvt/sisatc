package com.sat.sisat.controlycobranza.dto;

import java.io.Serializable;

public class FindCcLoteTipoPersona implements Serializable {

	private Integer tipoPersonaId;

	private Integer loteId;

	private Integer loteTipoPersonaId;

	private String tipoPersona;
	
	public FindCcLoteTipoPersona(){
		
	}

	public Integer getTipoPersonaId() {
		return tipoPersonaId;
	}

	public void setTipoPersonaId(Integer tipoPersonaId) {
		this.tipoPersonaId = tipoPersonaId;
	}

	public Integer getLoteId() {
		return loteId;
	}

	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}

	public Integer getLoteTipoPersonaId() {
		return loteTipoPersonaId;
	}

	public void setLoteTipoPersonaId(Integer loteTipoPersonaId) {
		this.loteTipoPersonaId = loteTipoPersonaId;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	@Override
	public String toString(){
		return tipoPersona;
	}
}
