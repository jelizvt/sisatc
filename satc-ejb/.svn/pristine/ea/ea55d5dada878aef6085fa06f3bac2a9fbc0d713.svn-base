package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ra_transferente_alcabala database table.
 * 
 */
//@Embeddable
public class RaTransferenteAlcabalaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private int djalcabalaId;
	private int transferenteId;

    public RaTransferenteAlcabalaPK() {
    }

	@Column(name="djalcabala_id")
	public int getDjalcabalaId() {
		return this.djalcabalaId;
	}
	public void setDjalcabalaId(int djalcabalaId) {
		this.djalcabalaId = djalcabalaId;
	}

	@Column(name="transferente_id")
	public int getTransferenteId() {
		return this.transferenteId;
	}
	public void setTransferenteId(int transferenteId) {
		this.transferenteId = transferenteId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RaTransferenteAlcabalaPK)) {
			return false;
		}
		RaTransferenteAlcabalaPK castOther = (RaTransferenteAlcabalaPK)other;
		return 
			(this.djalcabalaId == castOther.djalcabalaId)
			&& (this.transferenteId == castOther.transferenteId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.djalcabalaId;
		hash = hash * prime + this.transferenteId;
		
		return hash;
    }
}