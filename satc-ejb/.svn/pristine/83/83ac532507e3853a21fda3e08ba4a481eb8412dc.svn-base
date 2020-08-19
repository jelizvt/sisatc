package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cc_lote_schedule database table.
 * 
 */
@Embeddable
public class CcLoteSchedulePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="lote_id")
	private int loteId;

	@Column(name="schedule_id")
	private int scheduleId;

    public CcLoteSchedulePK() {
    }
	public int getLoteId() {
		return this.loteId;
	}
	public void setLoteId(int loteId) {
		this.loteId = loteId;
	}
	public int getScheduleId() {
		return this.scheduleId;
	}
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CcLoteSchedulePK)) {
			return false;
		}
		CcLoteSchedulePK castOther = (CcLoteSchedulePK)other;
		return 
			(this.loteId == castOther.loteId)
			&& (this.scheduleId == castOther.scheduleId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.loteId;
		hash = hash * prime + this.scheduleId;
		
		return hash;
    }
}