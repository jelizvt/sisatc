package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cc_lote_cuota database table.
 * 
 */
@Embeddable
public class CcLoteCuotaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="lote_id")
	private int loteId;

	@Column(name="lote_acto_id")
	private int loteActoId;

	@Column(name="lote_cuota_id")
	private int loteCuotaId;

    public CcLoteCuotaPK() {
    }
	public int getLoteId() {
		return this.loteId;
	}
	public void setLoteId(int loteId) {
		this.loteId = loteId;
	}
	public int getLoteActoId() {
		return this.loteActoId;
	}
	public void setLoteActoId(int loteActoId) {
		this.loteActoId = loteActoId;
	}
	public int getLoteCuotaId() {
		return this.loteCuotaId;
	}
	public void setLoteCuotaId(int loteCuotaId) {
		this.loteCuotaId = loteCuotaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CcLoteCuotaPK)) {
			return false;
		}
		CcLoteCuotaPK castOther = (CcLoteCuotaPK)other;
		return 
			(this.loteId == castOther.loteId)
			&& (this.loteActoId == castOther.loteActoId)
			&& (this.loteCuotaId == castOther.loteCuotaId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.loteId;
		hash = hash * prime + this.loteActoId;
		hash = hash * prime + this.loteCuotaId;
		
		return hash;
    }
}