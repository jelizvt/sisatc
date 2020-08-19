package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cj_agencia_operacion database table.
 * 
 */
@Embeddable
public class CjAgenciaOperacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="agencia_id")
	private int agenciaId;

	@Column(name="agencia_operacion_id")
	private int agenciaOperacionId;

    public CjAgenciaOperacionPK() {
    }
	public int getAgenciaId() {
		return this.agenciaId;
	}
	public void setAgenciaId(int agenciaId) {
		this.agenciaId = agenciaId;
	}
	public int getAgenciaOperacionId() {
		return this.agenciaOperacionId;
	}
	public void setAgenciaOperacionId(int agenciaOperacionId) {
		this.agenciaOperacionId = agenciaOperacionId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CjAgenciaOperacionPK)) {
			return false;
		}
		CjAgenciaOperacionPK castOther = (CjAgenciaOperacionPK)other;
		return 
			(this.agenciaId == castOther.agenciaId)
			&& (this.agenciaOperacionId == castOther.agenciaOperacionId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.agenciaId;
		hash = hash * prime + this.agenciaOperacionId;
		
		return hash;
    }
}