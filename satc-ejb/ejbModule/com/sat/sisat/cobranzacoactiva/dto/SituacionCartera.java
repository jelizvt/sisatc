package com.sat.sisat.cobranzacoactiva.dto;

import java.io.Serializable;

import com.sat.sisat.common.dto.BaseDTO;


public class SituacionCartera extends BaseDTO implements Serializable {
	private Integer carteraId;
	private Integer loteId;
	private String tipoValor;
	private String nroValor;
	private Double deudaRecibida;
	private String estadoGestion;
	private Double deudaPagadaPreCoactiva;
	private Double deudaPagadaCoactiva;
	private Integer periodo;
	private String coactivo;
	private Double costas;
	private Double costasPagada;
	private String estadoDeuda;
	private Double totalDeudaValor;
	private Double totalCanceladoValor;
	private String concepto;
	private String nroPapeleta;
	private String nroPlaca;
	private String codigoInfractor;
	private String apellidosNombresInfractor;
	
	public String getNroPapeleta() {
		return nroPapeleta;
	}
	public void setNroPapeleta(String nroPapeleta) {
		this.nroPapeleta = nroPapeleta;
	}
	public String getNroPlaca() {
		return nroPlaca;
	}
	public void setNroPlaca(String nroPlaca) {
		this.nroPlaca = nroPlaca;
	}
	public String getCodigoInfractor() {
		return codigoInfractor;
	}
	public void setCodigoInfractor(String codigoInfractor) {
		this.codigoInfractor = codigoInfractor;
	}
	public String getApellidosNombresInfractor() {
		return apellidosNombresInfractor;
	}
	public void setApellidosNombresInfractor(String apellidosNombresInfractor) {
		this.apellidosNombresInfractor = apellidosNombresInfractor;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public Double getTotalDeudaValor() {
		return totalDeudaValor;
	}
	public void setTotalDeudaValor(Double totalDeudaValor) {
		this.totalDeudaValor = totalDeudaValor;
	}
	public Double getTotalCanceladoValor() {
		return totalCanceladoValor;
	}
	public void setTotalCanceladoValor(Double totalCanceladoValor) {
		this.totalCanceladoValor = totalCanceladoValor;
	}
	public Integer getCarteraId() {
		return carteraId;
	}
	public void setCarteraId(Integer carteraId) {
		this.carteraId = carteraId;
	}
	public Integer getLoteId() {
		return loteId;
	}
	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}
	public String getTipoValor() {
		return tipoValor;
	}
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}
	public String getNroValor() {
		return nroValor;
	}
	public void setNroValor(String nroValor) {
		this.nroValor = nroValor;
	}
	public Double getDeudaRecibida() {
		return deudaRecibida;
	}
	public void setDeudaRecibida(Double deudaRecibida) {
		this.deudaRecibida = deudaRecibida;
	}
	public String getEstadoGestion() {
		return estadoGestion;
	}
	public void setEstadoGestion(String estadoGestion) {
		this.estadoGestion = estadoGestion;
	}
	public Double getDeudaPagadaCoactiva() {
		return deudaPagadaCoactiva;
	}
	public void setDeudaPagadaCoactiva(Double deudaPagadaCoactiva) {
		this.deudaPagadaCoactiva = deudaPagadaCoactiva;
	}
	public Integer getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	public String getCoactivo() {
		return coactivo;
	}
	public void setCoactivo(String coactivo) {
		this.coactivo = coactivo;
	}
	public Double getCostas() {
		return costas;
	}
	public void setCostas(Double costas) {
		this.costas = costas;
	}
	public Double getCostasPagada() {
		return costasPagada;
	}
	public void setCostasPagada(Double costasPagada) {
		this.costasPagada = costasPagada;
	}
	public String getEstadoDeuda() {
		return estadoDeuda;
	}
	public void setEstadoDeuda(String estadoDeuda) {
		this.estadoDeuda = estadoDeuda;
	}
	public Double getDeudaPagadaPreCoactiva() {
		return deudaPagadaPreCoactiva;
	}
	public void setDeudaPagadaPreCoactiva(Double deudaPagadaPreCoactiva) {
		this.deudaPagadaPreCoactiva = deudaPagadaPreCoactiva;
	}
}
