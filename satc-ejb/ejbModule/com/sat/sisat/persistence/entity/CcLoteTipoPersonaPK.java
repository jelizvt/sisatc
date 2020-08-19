package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cc_lote_tipo_persona database table.
 * 
 */
@Embeddable
public class CcLoteTipoPersonaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="lote_id")
	private int loteId;

	@Column(name="tipo_persona_id")
	private int tipoPersonaId;

	@Column(name="lote_tipo_persona_id")
	private int loteTipoPersonaId;

    public CcLoteTipoPersonaPK() {
    }
	public int getLoteId() {
		return this.loteId;
	}
	public void setLoteId(int loteId) {
		this.loteId = loteId;
	}
	public int getTipoPersonaId() {
		return this.tipoPersonaId;
	}
	public void setTipoPersonaId(int tipoPersonaId) {
		this.tipoPersonaId = tipoPersonaId;
	}
	public int getLoteTipoPersonaId() {
		return this.loteTipoPersonaId;
	}
	public void setLoteTipoPersonaId(int loteTipoPersonaId) {
		this.loteTipoPersonaId = loteTipoPersonaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CcLoteTipoPersonaPK)) {
			return false;
		}
		CcLoteTipoPersonaPK castOther = (CcLoteTipoPersonaPK)other;
		return 
			(this.loteId == castOther.loteId)
			&& (this.tipoPersonaId == castOther.tipoPersonaId)
			&& (this.loteTipoPersonaId == castOther.loteTipoPersonaId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.loteId;
		hash = hash * prime + this.tipoPersonaId;
		hash = hash * prime + this.loteTipoPersonaId;
		
		return hash;
    }
}