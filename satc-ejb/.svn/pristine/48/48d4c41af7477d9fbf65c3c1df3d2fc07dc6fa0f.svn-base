package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rv_sustento_vehicular database table.
 * 
 */
@Embeddable
public class RvSustentoVehicularPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="djvehicular_id")
	private int djvehicularId;

	@Column(name="sustento_id")
	private int sustentoId;

    public RvSustentoVehicularPK() {
    }
	public int getDjvehicularId() {
		return this.djvehicularId;
	}
	public void setDjvehicularId(int djvehicularId) {
		this.djvehicularId = djvehicularId;
	}
	public int getSustentoId() {
		return this.sustentoId;
	}
	public void setSustentoId(int sustentoId) {
		this.sustentoId = sustentoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RvSustentoVehicularPK)) {
			return false;
		}
		RvSustentoVehicularPK castOther = (RvSustentoVehicularPK)other;
		return 
			(this.djvehicularId == castOther.djvehicularId)
			&& (this.sustentoId == castOther.sustentoId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.djvehicularId;
		hash = hash * prime + this.sustentoId;
		
		return hash;
    }
}