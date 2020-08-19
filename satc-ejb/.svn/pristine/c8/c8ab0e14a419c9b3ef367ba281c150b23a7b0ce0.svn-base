package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the dt_tasa_impuesto_predial database table.
 * 
 */
@Embeddable
public class DtTasaImpuestoPredialPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int periodo;

	@Column(name="tramo_id")
	private int tramoId;

    public DtTasaImpuestoPredialPK() {
    }
	public int getPeriodo() {
		return this.periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public int getTramoId() {
		return this.tramoId;
	}
	public void setTramoId(int tramoId) {
		this.tramoId = tramoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DtTasaImpuestoPredialPK)) {
			return false;
		}
		DtTasaImpuestoPredialPK castOther = (DtTasaImpuestoPredialPK)other;
		return 
			(this.periodo == castOther.periodo)
			&& (this.tramoId == castOther.tramoId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.periodo;
		hash = hash * prime + this.tramoId;
		
		return hash;
    }
}