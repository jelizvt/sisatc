package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the sg_rol_modulo database table.
 * 
 */
@Embeddable
public class SgRolModuloPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="rol_id")
	private int rolId;

	@Column(name="modulo_id")
	private int moduloId;

    public SgRolModuloPK() {
    }
	public int getRolId() {
		return this.rolId;
	}
	public void setRolId(int rolId) {
		this.rolId = rolId;
	}
	public int getModuloId() {
		return this.moduloId;
	}
	public void setModuloId(int moduloId) {
		this.moduloId = moduloId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SgRolModuloPK)) {
			return false;
		}
		SgRolModuloPK castOther = (SgRolModuloPK)other;
		return 
			(this.rolId == castOther.rolId)
			&& (this.moduloId == castOther.moduloId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.rolId;
		hash = hash * prime + this.moduloId;
		
		return hash;
    }
}