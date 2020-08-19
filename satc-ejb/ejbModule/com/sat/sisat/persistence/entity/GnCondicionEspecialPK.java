package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the gn_condicion_especial database table.
 * 
 */
@Embeddable
public class GnCondicionEspecialPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="persona_id")
	private Integer personaId;

	@Column(name="condicion_especial_id")
	private Integer condicionEspecialId;

    public GnCondicionEspecialPK() {
    }
	public Integer getPersonaId() {
		return this.personaId;
	}
	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	public Integer getCondicionEspecialId() {
		return this.condicionEspecialId;
	}
	public void setCondicionEspecialId(Integer condicionEspecialId) {
		this.condicionEspecialId = condicionEspecialId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GnCondicionEspecialPK)) {
			return false;
		}
		GnCondicionEspecialPK castOther = (GnCondicionEspecialPK)other;
		return 
			(this.personaId == castOther.personaId)
			&& (this.condicionEspecialId == castOther.condicionEspecialId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.personaId;
		hash = hash * prime + this.condicionEspecialId;
		
		return hash;
    }
}