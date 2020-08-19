package com.sat.sisat.descargo.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class DescargoGenerico implements Serializable {

	private int codigo;
	private String descripcion;

	private int personaId;
	private int djId;
	private int predioId;
	private int djVehicular;
	private int vehiculoId;
	
	private int subconceptoId;
	private String subconceptoDes;
	private int conceptoId;
	private String conceptoDes;

	private String DireccionCompleta;
	private String ListaPredio;
	private String ListaVehiculo;
	private String ListaConcepto;
	private String ListaCuota;
	private String ListaAnyo;

	private String Observacion;
	private BigDecimal Insoluto;
	private BigDecimal DerechoEmision;
	private String Periodo;


	private int PapeletaId;

	// papeletas
	private int papeleta_id;
	private Timestamp FechaIntraccion;
	private String nroPapeleta;
	private String placa;
	private String infractorDes;
	private BigDecimal monto7;
	private BigDecimal monto7a15;
	private BigDecimal montoMayora15;
	private String observacionDes;

	// impuesto vehicular

	private int codigoVehiculo;
	private String categoria;
	private String marca;
	private String modelo;

	// impuesto predial

	private int codigoPredial;
	private String direccion;

	private boolean select;
	
	// estado cuenta
	private BigDecimal descuento;
	private BigDecimal totalCancelado;
	private String flagControlyCobranza;
	private String flagFraccionamiento;
	private String flagTramiteDocumetario;
	private String flagResolucion;
	private String storeProcedure;
	private String uso;
	private String fisca;
	private Integer estadoDeudaId;
	private String estadoDeudaDescripcion;
    private Integer annoDocumento;
    private Integer nroDocumentoId;

	public DescargoGenerico() {

	}

	public String getEstadoDeudaDescripcion() {
		return estadoDeudaDescripcion;
	}

	public void setEstadoDeudaDescripcion(String estadoDeudaDescripcion) {
		this.estadoDeudaDescripcion = estadoDeudaDescripcion;
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPersonaId() {
		return personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public String getListaPredio() {
		return ListaPredio;
	}

	public void setListaPredio(String listaPredio) {
		ListaPredio = listaPredio;
	}

	public String getListaVehiculo() {
		return ListaVehiculo;
	}

	public void setListaVehiculo(String listaVehiculo) {
		ListaVehiculo = listaVehiculo;
	}

	public String getListaConcepto() {
		return ListaConcepto;
	}

	public void setListaConcepto(String listaConcepto) {
		ListaConcepto = listaConcepto;
	}

	public String getListaCuota() {
		return ListaCuota;
	}

	public void setListaCuota(String listaCuota) {
		ListaCuota = listaCuota;
	}

	public String getListaAnyo() {
		return ListaAnyo;
	}

	public void setListaAnyo(String listaAnyo) {
		ListaAnyo = listaAnyo;
	}

	public String getObservacion() {
		return Observacion;
	}

	public void setObservacion(String observacion) {
		Observacion = observacion;
	}

	public BigDecimal getInsoluto() {
		return Insoluto;
	}

	public void setInsoluto(BigDecimal insoluto) {
		Insoluto = insoluto;
	}

	public BigDecimal getDerechoEmision() {
		return DerechoEmision;
	}

	public void setDerechoEmision(BigDecimal derechoEmision) {
		DerechoEmision = derechoEmision;
	}

	public String getPeriodo() {
		return Periodo;
	}

	public void setPeriodo(String periodo) {
		this.Periodo = periodo;
	}

	public int getDjId() {
		return djId;
	}

	public void setDjId(int djId) {
		this.djId = djId;
	}

	public int getPredioId() {
		return predioId;
	}

	public void setPredioId(int predioId) {
		this.predioId = predioId;
	}

	public String getDireccionCompleta() {
		return DireccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		DireccionCompleta = direccionCompleta;
	}

	public int getDjVehicular() {
		return djVehicular;
	}

	public void setDjVehicular(int djVehicular) {
		this.djVehicular = djVehicular;
	}

	public int getVehiculoId() {
		return vehiculoId;
	}

	public void setVehiculoId(int vehiculoId) {
		this.vehiculoId = vehiculoId;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public int getSubconceptoId() {
		return subconceptoId;
	}

	public void setSubconceptoId(int subconceptoId) {
		this.subconceptoId = subconceptoId;
	}

	public String getSubconceptoDes() {
		return subconceptoDes;
	}

	public void setSubconceptoDes(String subconceptoDes) {
		this.subconceptoDes = subconceptoDes;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public int getCodigoVehiculo() {
		return codigoVehiculo;
	}

	public void setCodigoVehiculo(int codigoVehiculo) {
		this.codigoVehiculo = codigoVehiculo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getCodigoPredial() {
		return codigoPredial;
	}

	public void setCodigoPredial(int codigoPredial) {
		this.codigoPredial = codigoPredial;
	}

	public int getPapeletaId() {
		return PapeletaId;
	}

	public void setPapeletaId(int papeletaId) {
		PapeletaId = papeletaId;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public BigDecimal getMonto7() {
		return monto7;
	}

	public void setMonto7(BigDecimal monto7) {
		this.monto7 = monto7;
	}

	public BigDecimal getMonto7a15() {
		return monto7a15;
	}

	public void setMonto7a15(BigDecimal monto7a15) {
		this.monto7a15 = monto7a15;
	}

	public Timestamp getFechaIntraccion() {
		return FechaIntraccion;
	}

	public void setFechaIntraccion(Timestamp fechaIntraccion) {
		FechaIntraccion = fechaIntraccion;
	}

	public int getPapeleta_id() {
		return papeleta_id;
	}

	public void setPapeleta_id(int papeleta_id) {
		this.papeleta_id = papeleta_id;
	}

	public BigDecimal getMontoMayora15() {
		return montoMayora15;
	}

	public void setMontoMayora15(BigDecimal montoMayora15) {
		this.montoMayora15 = montoMayora15;
	}

	public String getNroPapeleta() {
		return nroPapeleta;
	}

	public void setNroPapeleta(String nroPapeleta) {
		this.nroPapeleta = nroPapeleta;
	}

	public String getInfractorDes() {
		return infractorDes;
	}

	public void setInfractorDes(String infractorDes) {
		this.infractorDes = infractorDes;
	}

	public String getObservacionDes() {
		return observacionDes;
	}

	public void setObservacionDes(String observacionDes) {
		this.observacionDes = observacionDes;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getTotalCancelado() {
		return totalCancelado;
	}

	public void setTotalCancelado(BigDecimal totalCancelado) {
		this.totalCancelado = totalCancelado;
	}

	public String getFlagControlyCobranza() {
		return flagControlyCobranza;
	}

	public void setFlagControlyCobranza(String flagControlyCobranza) {
		this.flagControlyCobranza = flagControlyCobranza;
	}

	public String getFlagFraccionamiento() {
		return flagFraccionamiento;
	}

	public void setFlagFraccionamiento(String flagFraccionamiento) {
		this.flagFraccionamiento = flagFraccionamiento;
	}

	public String getFlagTramiteDocumetario() {
		return flagTramiteDocumetario;
	}

	public void setFlagTramiteDocumetario(String flagTramiteDocumetario) {
		this.flagTramiteDocumetario = flagTramiteDocumetario;
	}

	public String getFlagResolucion() {
		return flagResolucion;
	}

	public void setFlagResolucion(String flagResolucion) {
		this.flagResolucion = flagResolucion;
	}

	public String getStoreProcedure() {
		return storeProcedure;
	}

	public void setStoreProcedure(String storeProcedure) {
		this.storeProcedure = storeProcedure;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public String getFisca() {
		return fisca;
	}

	public void setFisca(String fisca) {
		this.fisca = fisca;
	}

	public Integer getEstadoDeudaId() {
		return estadoDeudaId;
	}

	public void setEstadoDeudaId(Integer estadoDeudaId) {
		this.estadoDeudaId = estadoDeudaId;
	}

	public Integer getAnnoDocumento() {
		return annoDocumento;
	}

	public void setAnnoDocumento(Integer annoDocumento) {
		this.annoDocumento = annoDocumento;
	}

	public Integer getNroDocumentoId() {
		return nroDocumentoId;
	}

	public void setNroDocumentoId(Integer nroDocumentoId) {
		this.nroDocumentoId = nroDocumentoId;
	}

	
}
