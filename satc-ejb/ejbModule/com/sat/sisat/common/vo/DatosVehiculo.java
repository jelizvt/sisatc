package com.sat.sisat.common.vo;

import java.io.Serializable;

public class DatosVehiculo implements Serializable {
	
	private String placa;
	private String nroTarjetaPropiedad;
	private String marca;
	private Integer marcaId;
	private String modelo;
	private Integer modeloId;
	private String anoFabricacion;
	private Integer vehiculoId;
	
	
	
	
	




	public Integer getVehiculoId() {
		return vehiculoId;
	}



	public void setVehiculoId(Integer vehiculoId) {
		this.vehiculoId = vehiculoId;
	}



	public String getAnoFabricacion() {
		return anoFabricacion;
	}



	public void setAnoFabricacion(String anoFabricacion) {
		this.anoFabricacion = anoFabricacion;
	}



	public String getMarca() {
		return marca;
	}



	public void setMarca(String marca) {
		this.marca = marca;
	}



	public Integer getMarcaId() {
		return marcaId;
	}



	public void setMarcaId(Integer marcaId) {
		this.marcaId = marcaId;
	}



	public String getModelo() {
		return modelo;
	}



	public void setModelo(String modelo) {
		this.modelo = modelo;
	}



	public Integer getModeloId() {
		return modeloId;
	}



	public void setModeloId(Integer modeloId) {
		this.modeloId = modeloId;
	}



	public String getNroTarjetaPropiedad() {
		return nroTarjetaPropiedad;
	}



	public void setNroTarjetaPropiedad(String nroTarjetaPropiedad) {
		this.nroTarjetaPropiedad = nroTarjetaPropiedad;
	}



	public String getPlaca() {
		return placa;
	}



	public void setPlaca(String placa) {
		this.placa = placa;
	}



	public DatosVehiculo() {
		// TODO Auto-generated constructor stub
	}

}
