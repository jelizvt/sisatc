package com.sat.sisat.determinacion.vehicular.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DatosNecesariosDeterDTO implements Serializable {

	private static final long serialVersionUID = -2515008064513562130L;

	private int categVehicId;
	private int marcaVehicId;
	private int modeloVehicId;
	private int anioIniAfec;
	private int anioFinAfec;
	private int anioAfec;
	private int anioFabric;
	private BigDecimal valorAdquiSoles;
	private BigDecimal valorAdquiOtraMoneda;
	private BigDecimal porcentajePropiedad;
	private int personaId;
	private int tipoMonedaId;
	private Integer djPrevioId;
	private int anioIniAfecContrib;
	private Integer vehiculoId;
	private Integer anhoAdquision;
	
	private Integer motivoDeclaracionId;
	
	private String fiscalizado;
	private String fiscalizadoAceptado;
	private String fiscalizadoCerrado;
	
	public DatosNecesariosDeterDTO() {
	}

	public int getCategVehicId() {
		return categVehicId;
	}

	public void setCategVehicId(int categVehicId) {
		this.categVehicId = categVehicId;
	}

	public int getMarcaVehicId() {
		return marcaVehicId;
	}

	public void setMarcaVehicId(int marcaVehicId) {
		this.marcaVehicId = marcaVehicId;
	}

	public int getModeloVehicId() {
		return modeloVehicId;
	}

	public void setModeloVehicId(int modeloVehicId) {
		this.modeloVehicId = modeloVehicId;
	}

	public int getAnioIniAfec() {
		return anioIniAfec;
	}

	public void setAnioIniAfec(int anioIniAfec) {
		this.anioIniAfec = anioIniAfec;
	}

	public int getAnioFinAfec() {
		return anioFinAfec;
	}

	public void setAnioFinAfec(int anioFinAfec) {
		this.anioFinAfec = anioFinAfec;
	}

	public int getAnioAfec() {
		return anioAfec;
	}

	public void setAnioAfec(int anioAfec) {
		this.anioAfec = anioAfec;
	}

	public int getAnioFabric() {
		return anioFabric;
	}

	public void setAnioFabric(int anioFabric) {
		this.anioFabric = anioFabric;
	}

	public BigDecimal getValorAdquiSoles() {
		return valorAdquiSoles;
	}

	public void setValorAdquiSoles(BigDecimal valorAdquiSoles) {
		this.valorAdquiSoles = valorAdquiSoles;
	}

	public BigDecimal getValorAdquiOtraMoneda() {
		return valorAdquiOtraMoneda;
	}

	public void setValorAdquiOtraMoneda(BigDecimal valorAdquiOtraMoneda) {
		this.valorAdquiOtraMoneda = valorAdquiOtraMoneda;
	}

	public BigDecimal getPorcentajePropiedad() {
		return porcentajePropiedad;
	}

	public void setPorcentajePropiedad(BigDecimal porcentajePropiedad) {
		this.porcentajePropiedad = porcentajePropiedad;
	}

	public int getPersonaId() {
		return personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public int getTipoMonedaId() {
		return tipoMonedaId;
	}

	public void setTipoMonedaId(int tipoMonedaId) {
		this.tipoMonedaId = tipoMonedaId;
	}

	public Integer getDjPrevioId() {
		return djPrevioId;
	}

	public void setDjPrevioId(Integer djPrevioId) {
		this.djPrevioId = djPrevioId;
	}

	public int getAnioIniAfecContrib() {
		return anioIniAfecContrib;
	}

	public void setAnioIniAfecContrib(int anioIniAfecContrib) {
		this.anioIniAfecContrib = anioIniAfecContrib;
	}

	public Integer getMotivoDeclaracionId() {
		return motivoDeclaracionId;
	}

	public void setMotivoDeclaracionId(Integer motivoDeclaracionId) {
		this.motivoDeclaracionId = motivoDeclaracionId;
	}

	public Integer getVehiculoId() {
		return vehiculoId;
	}

	public void setVehiculoId(Integer vehiculoId) {
		this.vehiculoId = vehiculoId;
	}

	/**
	 * @return the anhoAdquision
	 */
	public Integer getAnhoAdquision() {
		return anhoAdquision;
	}

	/**
	 * @param anhoAdquision the anhoAdquision to set
	 */
	public void setAnhoAdquision(Integer anhoAdquision) {
		this.anhoAdquision = anhoAdquision;
	}

	public String getFiscalizado() {
		return fiscalizado;
	}

	public void setFiscalizado(String fiscalizado) {
		this.fiscalizado = fiscalizado;
	}

	public String getFiscalizadoAceptado() {
		return fiscalizadoAceptado;
	}

	public void setFiscalizadoAceptado(String fiscalizadoAceptado) {
		this.fiscalizadoAceptado = fiscalizadoAceptado;
	}

	public String getFiscalizadoCerrado() {
		return fiscalizadoCerrado;
	}

	public void setFiscalizadoCerrado(String fiscalizadoCerrado) {
		this.fiscalizadoCerrado = fiscalizadoCerrado;
	}

	
}
