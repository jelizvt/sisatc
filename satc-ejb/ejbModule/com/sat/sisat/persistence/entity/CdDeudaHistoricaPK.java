package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cd_deuda_historica database table.
 * 
 */
@Embeddable
public class CdDeudaHistoricaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="deuda_id")
	private int deudaId;

	@Column(name="historica_id")
	private int historicaId;

    public CdDeudaHistoricaPK() {
    }
	public int getDeudaId() {
		return this.deudaId;
	}
	public void setDeudaId(int deudaId) {
		this.deudaId = deudaId;
	}
	public int getHistoricaId() {
		return this.historicaId;
	}
	public void setHistoricaId(int historicaId) {
		this.historicaId = historicaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CdDeudaHistoricaPK)) {
			return false;
		}
		CdDeudaHistoricaPK castOther = (CdDeudaHistoricaPK)other;
		return 
			(this.deudaId == castOther.deudaId)
			&& (this.historicaId == castOther.historicaId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.deudaId;
		hash = hash * prime + this.historicaId;
		
		return hash;
    }
}