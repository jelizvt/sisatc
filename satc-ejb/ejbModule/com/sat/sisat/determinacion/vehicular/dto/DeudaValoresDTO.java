package com.sat.sisat.determinacion.vehicular.dto;

import java.io.Serializable;

public class DeudaValoresDTO implements Serializable {

	private static final long serialVersionUID = 5888986617778315194L;

	private int deudaId;
	private String flagCoactiva;
	private String flagOp;

	public DeudaValoresDTO() {
	}

	public int getDeudaId() {
		return deudaId;
	}

	public void setDeudaId(int deudaId) {
		this.deudaId = deudaId;
	}

	public String getFlagCoactiva() {
		return flagCoactiva;
	}

	public void setFlagCoactiva(String flagCoactiva) {
		this.flagCoactiva = flagCoactiva;
	}

	public String getFlagOp() {
		return flagOp;
	}

	public void setFlagOp(String flagOp) {
		this.flagOp = flagOp;
	}
}
