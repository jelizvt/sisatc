package com.sat.sisat.common.vo;

import java.io.Serializable;

public class DatosPersonalPolicial implements Serializable {
	
	private String nroCIP;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String primerNombre;
	private String segundoNombre;
	
	
	

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}



	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}



	public String getApellidoMaterno() {
		return apellidoMaterno;
	}



	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}



	public String getPrimerNombre() {
		return primerNombre;
	}



	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}



	public String getSegundoNombre() {
		return segundoNombre;
	}



	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}



	public String getNroCIP() {
		return nroCIP;
	}



	public void setNroCIP(String nroCIP) {
		this.nroCIP = nroCIP;
	}



	public DatosPersonalPolicial() {
		// TODO Auto-generated constructor stub
	}

}
