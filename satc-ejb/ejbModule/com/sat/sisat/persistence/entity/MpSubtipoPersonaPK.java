package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the mp_subtipo_persona database table.
 * 
 */
@Embeddable
public class MpSubtipoPersonaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="tipo_persona_id")
	private Integer tipoPersonaId;

	@Column(name="subtipo_persona_id")
	private Integer subtipoPersonaId;

    public MpSubtipoPersonaPK() {
    }
	public Integer getTipoPersonaId() {
		return this.tipoPersonaId;
	}
	public void setTipoPersonaId(Integer tipoPersonaId) {
		this.tipoPersonaId = tipoPersonaId;
	}
	public Integer getSubtipoPersonaId() {
		return this.subtipoPersonaId;
	}
	public void setSubtipoPersonaId(Integer subtipoPersonaId) {
		this.subtipoPersonaId = subtipoPersonaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MpSubtipoPersonaPK)) {
			return false;
		}
		MpSubtipoPersonaPK castOther = (MpSubtipoPersonaPK)other;
		return 
			(this.tipoPersonaId == castOther.tipoPersonaId)
			&& (this.subtipoPersonaId == castOther.subtipoPersonaId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.tipoPersonaId;
		hash = hash * prime + this.subtipoPersonaId;
		
		return hash;
    }
}