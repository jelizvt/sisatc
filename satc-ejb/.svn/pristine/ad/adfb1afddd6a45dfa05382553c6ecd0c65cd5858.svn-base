package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the gn_distrito database table.
 * 
 */
@Embeddable
public class GnDistritoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="dpto_id")
	private int dptoId;

	@Column(name="provincia_id")
	private int provinciaId;

	@Column(name="distrito_id")
	private int distritoId;

    public GnDistritoPK() {
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
	public int getDistritoId() {
		return this.distritoId;
	}
	public void setDistritoId(int distritoId) {
		this.distritoId = distritoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GnDistritoPK)) {
			return false;
		}
		GnDistritoPK castOther = (GnDistritoPK)other;
		return 
			(this.dptoId == castOther.dptoId)
			&& (this.provinciaId == castOther.provinciaId)
			&& (this.distritoId == castOther.distritoId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.dptoId;
		hash = hash * prime + this.provinciaId;
		hash = hash * prime + this.distritoId;
		
		return hash;
    }
}