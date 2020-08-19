package com.sat.sisat.controlycobranza.dto;

import java.io.Serializable;



public class FindCcLoteSector implements Serializable{

	private Integer sectorId;

	private Integer loteId;

	private Integer loteSectorId;

	private String sector;
	
	public FindCcLoteSector(){
		
	}

	public Integer getSectorId() {
		return sectorId;
	}

	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}

	public Integer getLoteId() {
		return loteId;
	}

	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}

	public Integer getLoteSectorId() {
		return loteSectorId;
	}

	public void setLoteSectorId(Integer loteSectorId) {
		this.loteSectorId = loteSectorId;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}
	
	@Override
	public String toString(){
		return sector;
	}
}


