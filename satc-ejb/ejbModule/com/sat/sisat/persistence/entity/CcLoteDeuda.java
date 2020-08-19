package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the cc_acto database table.
 * 
 */
@Entity
@Table(name = "cc_lote_deuda")
public class CcLoteDeuda implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = -5203219485400102101L;
	
	@Id
	@Column(name = "deuda_id")
	private Integer deudaId;
	@Column(name = "lote_id")
	private Integer loteId;
	

	public CcLoteDeuda() {
	}

	public Integer getDeudaId() {
		return deudaId;
	}

	public void setDeudaId(Integer deudaId) {
		this.deudaId = deudaId;
	}

	public Integer getLoteId() {
		return loteId;
	}

	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}

}