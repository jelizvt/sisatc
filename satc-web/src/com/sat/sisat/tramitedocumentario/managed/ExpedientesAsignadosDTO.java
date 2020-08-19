package com.sat.sisat.tramitedocumentario.managed;

import java.io.Serializable;

public class ExpedientesAsignadosDTO implements Serializable {

	private static final long serialVersionUID = -2186266253005900898L;
	
	
	private int nroExpedienteAsignados;
	
	private String nombresResolutor;

	public int getNroExpedienteAsignados() {
		return nroExpedienteAsignados;
	}

	public void setNroExpedienteAsignados(int nroExpedienteAsignado) {
		this.nroExpedienteAsignados = nroExpedienteAsignado;
	}

	public String getNombresResolutor() {
		return nombresResolutor;
	}

	public void setNombresResolutor(String nombresResolutor) {
		this.nombresResolutor = nombresResolutor;
	}
}
