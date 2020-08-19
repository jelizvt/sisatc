package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the gn_subconcepto database table.
 * 
 */
@Embeddable
public class GnSubconceptoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="concepto_id")
	private Integer conceptoId;

	@Column(name="subconcepto_id")
	private Integer subconceptoId;

    public GnSubconceptoPK() {
    }
	public Integer getConceptoId() {
		return this.conceptoId;
	}
	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}
	public Integer getSubconceptoId() {
		return this.subconceptoId;
	}
	public void setSubconceptoId(Integer subconceptoId) {
		this.subconceptoId = subconceptoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GnSubconceptoPK)) {
			return false;
		}
		GnSubconceptoPK castOther = (GnSubconceptoPK)other;
		return 
			(this.conceptoId == castOther.conceptoId)
			&& (this.subconceptoId == castOther.subconceptoId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.conceptoId;
		hash = hash * prime + this.subconceptoId;
		
		return hash;
    }
}