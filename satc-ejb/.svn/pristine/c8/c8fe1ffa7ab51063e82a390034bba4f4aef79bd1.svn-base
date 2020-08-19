package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the gn_provincia database table.
 * 
 */
@Embeddable
public class GnProvinciaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="dpto_id")
	private int dptoId;

	@Column(name="provincia_id")
	private int provinciaId;

    public GnProvinciaPK() {
    }
	public int getDptoId() {
		return this.dptoId;
	}
	public void setDptoId(int dptoId) {
		this.dptoId = dptoId;
	}
	public int getProvinciaId() {
		return this.provinciaId;
	}
	public void setProvinciaId(int provinciaId) {
		this.provinciaId = provinciaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GnProvinciaPK)) {
			return false;
		}
		GnProvinciaPK castOther = (GnProvinciaPK)other;
		return 
			(this.dptoId == castOther.dptoId)
			&& (this.provinciaId == castOther.provinciaId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.dptoId;
		hash = hash * prime + this.provinciaId;
		
		return hash;
    }
}