package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cc_lote_categoria database table.
 * 
 */
@Embeddable
public class CcLoteCategoriaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="lote_id")
	private int loteId;

	@Column(name="lote_categoria_id")
	private int loteCategoriaId;

    public CcLoteCategoriaPK() {
    }
	public int getLoteId() {
		return this.loteId;
	}
	public void setLoteId(int loteId) {
		this.loteId = loteId;
	}
	public int getLoteCategoriaId() {
		return this.loteCategoriaId;
	}
	public void setLoteCategoriaId(int loteCategoriaId) {
		this.loteCategoriaId = loteCategoriaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CcLoteCategoriaPK)) {
			return false;
		}
		CcLoteCategoriaPK castOther = (CcLoteCategoriaPK)other;
		return 
			(this.loteId == castOther.loteId)
			&& (this.loteCategoriaId == castOther.loteCategoriaId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.loteId;
		hash = hash * prime + this.loteCategoriaId;
		
		return hash;
    }
}