package com.sat.sisat.predial.dto;

import java.io.Serializable;

import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.GnTipoVia;
import com.sat.sisat.persistence.entity.GnVia;

public class FindGnVia implements Serializable {
	public GnVia gnVia;
	public GnTipoVia gnTipoVia;
	public GnSector gnSector;
	
	public GnVia getGnVia() {
		return gnVia;
	}
	public void setGnVia(GnVia gnVia) {
		this.gnVia = gnVia;
	}
	public GnTipoVia getGnTipoVia() {
		return gnTipoVia;
	}
	public void setGnTipoVia(GnTipoVia gnTipoVia) {
		this.gnTipoVia = gnTipoVia;
	}
	public GnSector getGnSector() {
		return gnSector;
	}
	public void setGnSector(GnSector gnSector) {
		this.gnSector = gnSector;
	}
	
}
