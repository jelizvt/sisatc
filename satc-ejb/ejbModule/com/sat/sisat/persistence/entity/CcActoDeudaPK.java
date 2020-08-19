package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cc_acto_deuda database table.
 * 
 */
@Embeddable
public class CcActoDeudaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="lote_id")
	private int loteId;

	@Column(name="acto_id")
	private int actoId;
	
	
	

    public CcActoDeudaPK() {
    }
	public int getLoteId() {
		return this.loteId;
	}
	public void setLoteId(int loteId) {
		this.loteId = loteId;
	}
	public int getActoId() {
		return this.actoId;
	}
	public void setActoId(int actoId) {
		this.actoId = actoId;
	}
//	public int getActoDeudaId() {
//		return this.actoDeudaId;
//	}
//	public void setActoDeudaId(int actoDeudaId) {
//		this.actoDeudaId = actoDeudaId;
//	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CcActoDeudaPK)) {
			return false;
		}
		CcActoDeudaPK castOther = (CcActoDeudaPK)other;
		return 
			(this.loteId == castOther.loteId)
			&& (this.actoId == castOther.actoId)
			;

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.loteId;
		hash = hash * prime + this.actoId;
		
		
		return hash;
    }
}