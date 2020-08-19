package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cc_lote_firma database table.
 * 
 */
@Embeddable
public class CcLoteFirmaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="lote_firma_id")
	private int loteFirmaId;

	@Column(name="lote_id")
	private int loteId;

    public CcLoteFirmaPK() {
    }
	public int getLoteFirmaId() {
		return this.loteFirmaId;
	}
	public void setLoteFirmaId(int loteFirmaId) {
		this.loteFirmaId = loteFirmaId;
	}
	public int getLoteId() {
		return this.loteId;
	}
	public void setLoteId(int loteId) {
		this.loteId = loteId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CcLoteFirmaPK)) {
			return false;
		}
		CcLoteFirmaPK castOther = (CcLoteFirmaPK)other;
		return 
			(this.loteFirmaId == castOther.loteFirmaId)
			&& (this.loteId == castOther.loteId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.loteFirmaId;
		hash = hash * prime + this.loteId;
		
		return hash;
    }
}