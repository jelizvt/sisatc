package com.sat.sisat.predial.dto;

import java.io.Serializable;

import com.sat.sisat.persistence.entity.GnDenominacionUrbana;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.GnTipoDenoUrbana;


public class FindGnDenUrbana implements Serializable {
	public GnDenominacionUrbana gnDenUrbana;
	public GnTipoDenoUrbana gnTipoDenoUrbana;
	public GnSector gnSector;
	
	public GnSector getGnSector() {
		return gnSector;
	}
	public void setGnSector(GnSector gnSector) {
		this.gnSector = gnSector;
	}

	public GnDenominacionUrbana getGnDenUrbana() {
		return gnDenUrbana;
	}
	public void setGnDenUrbana(GnDenominacionUrbana gnDenUrbana) {
		this.gnDenUrbana = gnDenUrbana;
	}
	
	public GnTipoDenoUrbana getGnTipoDenoUrbana() {
		return gnTipoDenoUrbana;
	}
	public void setGnTipoDenoUrbana(GnTipoDenoUrbana gnTipoDenoUrbana) {
		this.gnTipoDenoUrbana = gnTipoDenoUrbana;
	}	
}
