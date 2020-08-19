package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cc_lote_tipo_orden_impresion database table.
 * 
 */
@Embeddable
public class CcLoteTipoOrdenImpresionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="lote_tipo_orden_impresion_id")
	private int loteTipoOrdenImpresionId;

	@Column(name="lote_id")
	private int loteId;

    public CcLoteTipoOrdenImpresionPK() {
    }
	public int getLoteTipoOrdenImpresionId() {
		return this.loteTipoOrdenImpresionId;
	}
	public void setLoteTipoOrdenImpresionId(int loteTipoOrdenImpresionId) {
		this.loteTipoOrdenImpresionId = loteTipoOrdenImpresionId;
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
		if (!(other instanceof CcLoteTipoOrdenImpresionPK)) {
			return false;
		}
		CcLoteTipoOrdenImpresionPK castOther = (CcLoteTipoOrdenImpresionPK)other;
		return 
			(this.loteTipoOrdenImpresionId == castOther.loteTipoOrdenImpresionId)
			&& (this.loteId == castOther.loteId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.loteTipoOrdenImpresionId;
		hash = hash * prime + this.loteId;
		
		return hash;
    }
}