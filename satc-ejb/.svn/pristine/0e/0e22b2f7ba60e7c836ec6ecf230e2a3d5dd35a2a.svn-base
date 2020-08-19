package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cc_lote_concepto database table.
 * 
 */
@Embeddable
public class CcLoteConceptoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="lote_id")
	private Integer loteId;

	@Column(name="lote_acto_id")
	private Integer loteActoId;

	@Column(name="lote_concepto_id")
	private Integer loteConceptoId;

    public CcLoteConceptoPK() {
    }
	public int getLoteId() {
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
	public Integer getLoteConceptoId() {
		return this.loteConceptoId;
	}
	public void setLoteConceptoId(Integer loteConceptoId) {
		this.loteConceptoId = loteConceptoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CcLoteConceptoPK)) {
			return false;
		}
		CcLoteConceptoPK castOther = (CcLoteConceptoPK)other;
		return 
			(this.loteId == castOther.loteId)
			&& (this.loteActoId == castOther.loteActoId)
			&& (this.loteConceptoId == castOther.loteConceptoId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.loteId;
		hash = hash * prime + this.loteActoId;
		hash = hash * prime + this.loteConceptoId;
		
		return hash;
    }
}