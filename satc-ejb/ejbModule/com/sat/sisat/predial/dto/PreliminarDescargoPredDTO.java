package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PreliminarDescargoPredDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1094438554782232094L;
	//Nro de Declaracion Jurada
	private int djId;
	
	//Datos del Contribuyente
	private Integer personaId;
	private String nombreRazonSocial;
	private String tipoDocumento;
	private String numeroDocumento;
		
	//Datos de la declaración
	private String fechaDeclaracion;
	private String fechaDescargo;
	private String motivoDeclaracion;
	private String motivoDescargo;
	private String numeroDeclaracion;
	private Double porcentajePropiedad;
	private Double area;
	private String glosa;
	
	//Datos del Predio
	private String tipoPredio;
	private String numeroPredio;
	private String direccion;
	private String condicionPropiedad;
	
	//DATOS DE ADQUIRIENTE
	private String selectedNotaria;
	
	//DOCUMENTOS ANEXOS
	
	//Areas y Porcentajes
	private BigDecimal areaMatriz;
	private BigDecimal areaTransferida;
	private BigDecimal porcentajeMatriz;
	private BigDecimal porcentajeTransferido;
	private String formaAdquisicion;
	
	//Usuario
	private String usuario;
	

	public PreliminarDescargoPredDTO() {
		
	}
	
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}

	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getFechaDeclaracion() {
		return fechaDeclaracion;
	}

	public void setFechaDeclaracion(String fechaDeclaracion) {
		this.fechaDeclaracion = fechaDeclaracion;
	}

	public String getMotivoDeclaracion() {
		return motivoDeclaracion;
	}

	public void setMotivoDeclaracion(String motivoDeclaracion) {
		this.motivoDeclaracion = motivoDeclaracion;
	}

	public String getMotivoDescargo() {
		return motivoDescargo;
	}

	public void setMotivoDescargo(String motivoDescargo) {
		this.motivoDescargo = motivoDescargo;
	}

	public String getNumeroDeclaracion() {
		return numeroDeclaracion;
	}

	public void setNumeroDeclaracion(String numeroDeclaracion) {
		this.numeroDeclaracion = numeroDeclaracion;
	}

	public Double getPorcentajePropiedad() {
		return porcentajePropiedad;
	}

	public void setPorcentajePropiedad(Double porcentajePropiedad) {
		this.porcentajePropiedad = porcentajePropiedad;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public String getTipoPredio() {
		return tipoPredio;
	}

	public void setTipoPredio(String tipoPredio) {
		this.tipoPredio = tipoPredio;
	}

	public String getNumeroPredio() {
		return numeroPredio;
	}

	public void setNumeroPredio(String numeroPredio) {
		this.numeroPredio = numeroPredio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCondicionPropiedad() {
		return condicionPropiedad;
	}

	public void setCondicionPropiedad(String condicionPropiedad) {
		this.condicionPropiedad = condicionPropiedad;
	}

	public String getSelectedNotaria() {
		return selectedNotaria;
	}

	public void setSelectedNotaria(String selectedNotaria) {
		this.selectedNotaria = selectedNotaria;
	}
	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public String getFechaDescargo() {
		return fechaDescargo;
	}

	public void setFechaDescargo(String fechaDescargo) {
		this.fechaDescargo = fechaDescargo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
		
	}

	public int getDjId() {
		return djId;
	}

	public void setDjId(int djId) {
		this.djId = djId;
	}

	public BigDecimal getAreaMatriz() {
		return areaMatriz;
	}

	public void setAreaMatriz(BigDecimal areaMatriz) {
		this.areaMatriz = areaMatriz;
	}

	public BigDecimal getPorcentajeMatriz() {
		return porcentajeMatriz;
	}

	public void setPorcentajeMatriz(BigDecimal porcentajeMatriz) {
		this.porcentajeMatriz = porcentajeMatriz;
	}

	public BigDecimal getAreaTransferida() {
		return areaTransferida;
	}

	public void setAreaTransferida(BigDecimal areaTransferida) {
		this.areaTransferida = areaTransferida;
	}

	public BigDecimal getPorcentajeTransferido() {
		return porcentajeTransferido;
	}

	public void setPorcentajeTransferido(BigDecimal porcentajeTransferido) {
		this.porcentajeTransferido = porcentajeTransferido;
	}

	public String getFormaAdquisicion() {
		return formaAdquisicion;
	}

	public void setFormaAdquisicion(String formaAdquisicion) {
		this.formaAdquisicion = formaAdquisicion;
	}

}
