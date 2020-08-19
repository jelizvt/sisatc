package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cc_lote_sector database table.
 * 
 */
@Embeddable
public class CcLoteSectorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="sector_id")
	private int sectorId;

	@Column(name="lote_id")
	private int loteId;

	@Column(name="lote_sector_id")
	private int loteSectorId;

    public CcLoteSectorPK() {
    }
	public int getSectorId() {
		return this.sectorId;
	}
	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}
	public int getLoteId() {
		return this.loteId;
	}
	public void setLoteId(int loteId) {
		this.loteId = loteId;
	}
	public int getLoteSectorId() {
		return this.loteSectorId;
	}
	public void setLoteSectorId(int loteSectorId) {
		this.loteSectorId = loteSectorId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CcLoteSectorPK)) {
			return false;
		}
		CcLoteSectorPK castOther = (CcLoteSectorPK)other;
		return 
			(this.sectorId == castOther.sectorId)
			&& (this.loteId == castOther.loteId)
			&& (this.loteSectorId == castOther.loteSectorId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.sectorId;
		hash = hash * prime + this.loteId;
		hash = hash * prime + this.loteSectorId;
		
		return hash;
    }
}