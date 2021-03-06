package com.sat.sisat.persistence.entity;
// Generated 20/11/2012 10:29:09 AM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TdRequisitoExpedienteId generated by hbm2java
 */
@Embeddable
public class TdRequisitoExpedienteId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4489863467203521393L;
	private int requisitoId;
	private int expedienteId;

	public TdRequisitoExpedienteId() {
	}

	public TdRequisitoExpedienteId(int requisitoId, int expedienteId) {
		this.requisitoId = requisitoId;
		this.expedienteId = expedienteId;
	}

	@Column(name = "requisito_id", nullable = false)
	public int getRequisitoId() {
		return this.requisitoId;
	}

	public void setRequisitoId(int requisitoId) {
		this.requisitoId = requisitoId;
	}

	@Column(name = "expediente_id", nullable = false)
	public int getExpedienteId() {
		return this.expedienteId;
	}

	public void setExpedienteId(int expedienteId) {
		this.expedienteId = expedienteId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TdRequisitoExpedienteId))
			return false;
		TdRequisitoExpedienteId castOther = (TdRequisitoExpedienteId) other;

		return (this.getRequisitoId() == castOther.getRequisitoId())
				&& (this.getExpedienteId() == castOther.getExpedienteId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getRequisitoId();
		result = 37 * result + this.getExpedienteId();
		return result;
	}

}
