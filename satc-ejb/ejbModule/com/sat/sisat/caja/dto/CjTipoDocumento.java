package com.sat.sisat.caja.dto;

import java.io.Serializable;

public class CjTipoDocumento implements Serializable {
	


	private int tipoDoc;
	private String descripcioncorta;

    public CjTipoDocumento() {
    }

	public int getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(int tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getDescripcioncorta() {
		return descripcioncorta;
	}

	public void setDescripcioncorta(String descripcioncorta) {
		this.descripcioncorta = descripcioncorta;
	}


}
