package com.sat.sisat.common.vo;

import java.io.Serializable;

public class DatosPropietario implements Serializable {
	
	private Integer propietarioId;
	private String esPropietario;
	private String tipoDocumento;
	private Integer tipoDocumentoId;
	private String numDocumento;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String primerNombre;
	private String segundoNombre;
	private String claseLicencia;
	private Integer claseLicenciaId;
	private String categoriaLicencia;
	private Integer categoriaLicenciaId;
	private String numLicencia;
	private String domicilio; 
	private Integer domicilioId;
	private String razonSocial;
	
	

	public Integer getPropietarioId() {
		return propietarioId;
	}



	public void setPropietarioId(Integer propietarioId) {
		this.propietarioId = propietarioId;
	}



	public Integer getClaseLicenciaId() {
		return claseLicenciaId;
	}



	public void setClaseLicenciaId(Integer claseLicenciaId) {

		this.claseLicenciaId = claseLicenciaId;
	}





	public Integer getCategoriaLicenciaId() {
		return categoriaLicenciaId;
	}



	public void setCategoriaLicenciaId(Integer categoriaLicenciaId) {
		this.categoriaLicenciaId = categoriaLicenciaId;
	}



	public String getRazonSocial() {
		return razonSocial;
	}



	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}



	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}



	public void setTipoDocumentoId(Integer tipoDocumentoId) {		
		this.tipoDocumentoId = tipoDocumentoId;
	}



	public String getTipoDocumento() {
		return tipoDocumento;
	}



	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}



	public String getClaseLicencia() {
		return claseLicencia;
	}



	public void setClaseLicencia(String claseLicencia) {		
		this.claseLicencia = claseLicencia;
	}



	public String getCategoriaLicencia() {
		return categoriaLicencia;
	}



	public void setCategoriaLicencia(String categoriaLicencia) {		
		this.categoriaLicencia = categoriaLicencia;
	}



	public String getDomicilio() {
		return domicilio;
	}



	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	


	public Integer getDomicilioId() {
		return domicilioId;
	}



	public void setDomicilioId(Integer domicilioId) {
		this.domicilioId = domicilioId;
	}



	public String getNumLicencia() {
		return numLicencia;
	}



	public void setNumLicencia(String numLicencia) {		
		this.numLicencia = numLicencia;
	}





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



	public String getNumDocumento() {
		return numDocumento;
	}



	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}

	
	 private void log(Object object) {
	        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
	        System.out.println("MyBean " + methodName + ": " + object);
	    }




	public String getEsPropietario() {
		return esPropietario;
	}



	public void setEsPropietario(String esPropietario) {
		this.esPropietario = esPropietario;
	}



	public DatosPropietario() {
		// TODO Auto-generated constructor stub
	}

}
