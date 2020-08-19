package com.sat.sisat.common.vo;

import java.io.Serializable;

import com.sat.sisat.persistence.entity.Ubigeo;

public class DatosInfractor implements Serializable {

	private String  tipoDocumento ;
	private Integer tipoDocumentoId;
	
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String primerNombre;
	private String segundoNombre;
	
	private String numDocumento;
	private String razonSocial;
	private String claseLicencia;
	private Integer claseLicenciaId;
	private String numLicencia;
	private String categoriaLicencia;
	private Integer categoriaLicenciaId;
	private String domicilio;
	private Integer domicilioId;
	private Ubigeo ubigeo = new Ubigeo();
	private Boolean esPropietario;
	private Integer infractorId;
	
	
	
	
	
	
	

	



	public Integer getInfractorId() {
		return infractorId;
	}



	public void setInfractorId(Integer infractorId) {
		this.infractorId = infractorId;
	}



	public Boolean getEsPropietario() {
		return esPropietario;
	}



	public void setEsPropietario(Boolean esPropietario) {
		this.esPropietario = esPropietario;
	}



	public String getClaseLicencia() {
		return claseLicencia;
	}



	public void setClaseLicencia(String claseLicencia) {
		this.claseLicencia = claseLicencia;
	}



	public Integer getClaseLicenciaId() {
		return claseLicenciaId;
	}



	public void setClaseLicenciaId(Integer claseLicenciaId) {
		this.claseLicenciaId = claseLicenciaId;
	}



	public String getCategoriaLicencia() {
		return categoriaLicencia;
	}



	public void setCategoriaLicencia(String categoriaLicencia) {
		this.categoriaLicencia = categoriaLicencia;
	}



	public Integer getCategoriaLicenciaId() {
		return categoriaLicenciaId;
	}



	public void setCategoriaLicenciaId(Integer categoriaLicenciaId) {
		this.categoriaLicenciaId = categoriaLicenciaId;
	}



	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}



	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}



	public Ubigeo getUbigeo() {
		return ubigeo;
	}



	public void setUbigeo(Ubigeo ubigeo) {
		this.ubigeo = ubigeo;
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




	public String getRazonSocial() {
		return razonSocial;
	}



	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}



	public String getNumDocumento() {
		return numDocumento;
	}



	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}



	public String getTipoDocumento() {
		return tipoDocumento;
	}



	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	
	


	public String getApellidoPaterno() {
		return apellidoPaterno;
	}



	public void setApellidoPaterno(String apellidoPaterno) {
        log(apellidoPaterno);
		this.apellidoPaterno = apellidoPaterno;
	}

	
	 private void log(Object object) {
	        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
	        System.out.println("MyBean " + methodName + ": " + object);
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




}
