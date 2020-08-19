package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rp_djdireccion database table.
 * 
 */
@Embeddable
public class RpDjdireccionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="dj_id")
	private int djId;

	@Column(name="direccion_id")
	private int direccionId;

    public RpDjdireccionPK() {
    }
	public int getDjId() {
		return this.djId;
	}
	public void setDjId(int djId) {
		this.djId = djId;
	}
	public int getDireccionId() {
		return this.direccionId;
	}
	public void setDireccionId(int direccionId) {
		this.direccionId = direccionId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RpDjdireccionPK)) {
			return false;
		}
		RpDjdireccionPK castOther = (RpDjdireccionPK)other;
		return 
			(this.djId == castOther.djId)
			&& (this.direccionId == castOther.direccionId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.djId;
		hash = hash * prime + this.direccionId;
		
		return hash;
    }
}