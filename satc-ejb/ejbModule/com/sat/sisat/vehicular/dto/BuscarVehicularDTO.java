package com.sat.sisat.vehicular.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BuscarVehicularDTO implements Serializable {
	
	private static final long serialVersionUID = -1033220662267751984L;
	
	private String placa;
	private String nroMotor;
	private String categoria;
	private String marca;
	private String modelo;
	private int djVehicularId;
	private String motivoDecla;
	private Date fecha;
	private String estado;
	private Date fechaInsReg;
	private String anioFabric;
	private int vehiculoId;
	private int motivoDeclaId;
	private String contribuyente;
	private String djvehicularNro;
	private BigDecimal porcentajeDecla;
	private Date fechaAdquisicion;
	private int djVehicularPrevioId;
	private Date fechaDescargo;
	
	
	//Inicio ITANTAMANGO
	private int codPersona;
	private String nomPropietario;
	
	private int requerimientoId;
	
	public Boolean esPendiente(){
		return estado.equals("2")?Boolean.TRUE:Boolean.FALSE;
	}
	public Boolean esPendienteDeDescargo(){
		return estado.equals("3")?Boolean.TRUE:Boolean.FALSE;
	}
	public Boolean esPendienteDeActualizacion(){
		return estado.equals("4")?Boolean.TRUE:Boolean.FALSE;
	}
	public Boolean esDescargo(){
		return motivoDeclaId == 4 ? Boolean.TRUE:Boolean.FALSE;
	}
	public String getStyleSelected() {
        if(esPendiente())
        	return "yellow";
        else if(esDescargo())
        	return "#E6E6E6";       
        else if(esPendienteDeDescargo())
        	return "#F4FA58";     
        else if(esPendienteDeActualizacion())
        	return "#F5FA68"; 
        else if(!esPendiente())
        	return "#99FF99";
        else 
        	return "white";
    }

	public int getCodPersona() {
		return codPersona;
	}

	public int getDjVehicularPrevioId() {
		return djVehicularPrevioId;
	}

	public void setDjVehicularPrevioId(int djVehicularPrevioId) {
		this.djVehicularPrevioId = djVehicularPrevioId;
	}

	public void setCodPersona(int codPersona) {
		this.codPersona = codPersona;
	}

	public String getNomPropietario() {
		return nomPropietario;
	}

	public void setNomPropietario(String nomPropietario) {
		this.nomPropietario = nomPropietario;
	}
	//Fin ITANTAMANGO

	public BuscarVehicularDTO() {
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getNroMotor() {
		return nroMotor;
	}

	public void setNroMotor(String nroMotor) {
		this.nroMotor = nroMotor;
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

	public int getDjVehicularId() {
		return djVehicularId;
	}

	public void setDjVehicularId(int djVehicularId) {
		this.djVehicularId = djVehicularId;
	}

	public String getMotivoDecla() {
		return motivoDecla;
	}

	public void setMotivoDecla(String motivoDecla) {
		this.motivoDecla = motivoDecla;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaInsReg() {
		return fechaInsReg;
	}

	public void setFechaInsReg(Date fechaInsReg) {
		this.fechaInsReg = fechaInsReg;
	}

	public String getAnioFabric() {
		return anioFabric;
	}

	public void setAnioFabric(String anioFabric) {
		this.anioFabric = anioFabric;
	}

	public int getVehiculoId() {
		return vehiculoId;
	}

	public void setVehiculoId(int vehiculoId) {
		this.vehiculoId = vehiculoId;
	}

	public int getMotivoDeclaId() {
		return motivoDeclaId;
	}

	public void setMotivoDeclaId(int motivoDeclaId) {
		this.motivoDeclaId = motivoDeclaId;
	}

	public String getContribuyente() {
		return contribuyente;
	}

	public void setContribuyente(String contribuyente) {
		this.contribuyente = contribuyente;
	}

	public String getDjvehicularNro() {
		return djvehicularNro;
	}

	public void setDjvehicularNro(String djvehicularNro) {
		this.djvehicularNro = djvehicularNro;
	}

	public BigDecimal getPorcentajeDecla() {
		return porcentajeDecla;
	}

	public void setPorcentajeDecla(BigDecimal porcentajeDecla) {
		this.porcentajeDecla = porcentajeDecla;
	}

	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}
	public Date getFechaDescargo() {
		return fechaDescargo;
	}
	public void setFechaDescargo(Date fechaDescargo) {
		this.fechaDescargo = fechaDescargo;
	}
	public int getRequerimientoId() {
		return requerimientoId;
	}
	public void setRequerimientoId(int requerimientoId) {
		this.requerimientoId = requerimientoId;
	}
}
