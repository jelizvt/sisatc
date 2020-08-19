package com.sat.sisat.common.vo;

import java.io.Serializable;

public class DatosExpediente implements Serializable {
	
	private String numeroExpediente; 
	private String estado;
	private String tipo;
	
	
	

	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getNumeroExpediente() {
		return numeroExpediente;
	}


	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}


	public DatosExpediente() {
		// TODO Auto-generated constructor stub
	}

}
