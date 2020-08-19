package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cc_lote_acto database table.
 * 
 */
@Embeddable
public class CcLoteActoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="lote_id")
	private Integer loteId;

	@Column(name="lote_acto_id")
	private Integer loteActoId;

    public CcLoteActoPK() {
    }
	public Integer getLoteId() {
		return this.loteId;
	}
	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}
	public Integer getLoteActoId() {
		return this.loteActoId;
	}
	public void setLoteActoId(Integer loteActoId) {
		this.loteActoId = loteActoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CcLoteActoPK)) {
			return false;
		}
		CcLoteActoPK castOther = (CcLoteActoPK)other;
		return 
			(this.loteId == castOther.loteId)
			&& (this.loteActoId == castOther.loteActoId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.loteId;
		hash = hash * prime + this.loteActoId;
		
		return hash;
    }
}