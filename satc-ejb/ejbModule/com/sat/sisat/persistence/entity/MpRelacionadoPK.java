package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the mp_relacionado database table.
 * 
 */
@Embeddable
public class MpRelacionadoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="persona_id")
	private Integer personaId;

	@Column(name="relacionado_id")
	private Integer relacionadoId;

    public MpRelacionadoPK() {
    }
    
    public MpRelacionadoPK(Integer personaId,Integer relacionadoId){
    	this.personaId=personaId;
    	this.relacionadoId=relacionadoId;
    	
    }
	public int getPersonaId() {
		return this.personaId;
	}
	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	public int getRelacionadoId() {
		return this.relacionadoId;
	}
	public void setRelacionadoId(Integer relacionadoId) {
		this.relacionadoId = relacionadoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MpRelacionadoPK)) {
			return false;
		}
		MpRelacionadoPK castOther = (MpRelacionadoPK)other;
		return 
			(this.personaId == castOther.personaId)
			&& (this.relacionadoId == castOther.relacionadoId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.personaId;
		hash = hash * prime + this.relacionadoId;
		
		return hash;
    }
}