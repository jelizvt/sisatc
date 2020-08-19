package com.sat.sisat.vehicular.dto;

import java.io.Serializable;
import java.util.Date;

public class CarteraVehiculosDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int vehicularOmisosId;
	private int sectorId;
	private int personaId;
	private int requerimientoId;
	private int tipoDocumentoId;
	private String placa;
	private String nroRequerimiento;
	private String anioInspeccion;
	private int distritoId;
	private String distritoDescripcion;
	private String propietario;
	private String direccionCompleta;
	private int programaId;
	private String nombrePrograma;
	private String fechaInscripcion;
	private String fechaPropiedad;
	private String nroPartida;
	private String sector;
	private Date fechaDescargo;
	
	
	private int marcaVehiculoId;
	private int categoriaVehiculoId;
	private int modeloVehiculoId;
	private int claseVehiculoId;
	private int carroceriaId;
	private int tipoMotorId;
	private Double numeroCilindros;
	
	private int annoFabricacion;
	private int annoAfectacion;
	
	
	private String marcaDescripcion;
	private String categoriaDescripcion;
	private String modeloDescripcion;
	private String claseDescripcion;
	private String tipoCarroceriaDescripcion;
	
	private String marcaDescripcionTemporal;
	private String modeloDescripcionTemporal;
	

	private boolean selected;
	
	public int getVehicularOmisosId() {
		return vehicularOmisosId;
	}
	public void setVehicularOmisosId(int vehicularOmisosId) {
		this.vehicularOmisosId = vehicularOmisosId;
	}
	public int getSectorId() {
		return sectorId;
	}
	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}
	public int getPersonaId() {
		return personaId;
	}
	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}
	public int getRequerimientoId() {
		return requerimientoId;
	}
	public void setRequerimientoId(int requerimientoId) {
		this.requerimientoId = requerimientoId;
	}
	public int getTipoDocumentoId() {
		return tipoDocumentoId;
	}
	public void setTipoDocumentoId(int tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getNroRequerimiento() {
		return nroRequerimiento;
	}
	public void setNroRequerimiento(String nroRequerimiento) {
		this.nroRequerimiento = nroRequerimiento;
	}
	public String getAnioInspeccion() {
		return anioInspeccion;
	}
	public void setAnioInspeccion(String anioInspeccion) {
		this.anioInspeccion = anioInspeccion;
	}
	public int getDistritoId() {
		return distritoId;
	}
	public void setDistritoId(int distritoId) {
		this.distritoId = distritoId;
	}
	public String getDistritoDescripcion() {
		return distritoDescripcion;
	}
	public void setDistritoDescripcion(String distritoDescripcion) {
		this.distritoDescripcion = distritoDescripcion;
	}
	public String getPropietario() {
		return propietario;
	}
	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}
	public String getDireccionCompleta() {
		return direccionCompleta;
	}
	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}
	public int getProgramaId() {
		return programaId;
	}
	public void setProgramaId(int programaId) {
		this.programaId = programaId;
	}
	public String getNombrePrograma() {
		return nombrePrograma;
	}
	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}
	public String getFechaInscripcion() {
		return fechaInscripcion;
	}
	public void setFechaInscripcion(String fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}
	public String getFechaPropiedad() {
		return fechaPropiedad;
	}
	public void setFechaPropiedad(String fechaPropiedad) {
		this.fechaPropiedad = fechaPropiedad;
	}
	public String getNroPartida() {
		return nroPartida;
	}
	public void setNroPartida(String nroPartida) {
		this.nroPartida = nroPartida;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public Date getFechaDescargo() {
		return fechaDescargo;
	}
	public void setFechaDescargo(Date fechaDescargo) {
		this.fechaDescargo = fechaDescargo;
	}
	public int getMarcaVehiculoId() {
		return marcaVehiculoId;
	}
	public void setMarcaVehiculoId(int marcaVehiculoId) {
		this.marcaVehiculoId = marcaVehiculoId;
	}
	public int getCategoriaVehiculoId() {
		return categoriaVehiculoId;
	}
	public void setCategoriaVehiculoId(int categoriaVehiculoId) {
		this.categoriaVehiculoId = categoriaVehiculoId;
	}
	public int getModeloVehiculoId() {
		return modeloVehiculoId;
	}
	public void setModeloVehiculoId(int modeloVehiculoId) {
		this.modeloVehiculoId = modeloVehiculoId;
	}
	public int getClaseVehiculoId() {
		return claseVehiculoId;
	}
	public void setClaseVehiculoId(int claseVehiculoId) {
		this.claseVehiculoId = claseVehiculoId;
	}
	public int getCarroceriaId() {
		return carroceriaId;
	}
	public void setCarroceriaId(int carroceriaId) {
		this.carroceriaId = carroceriaId;
	}
	public int getTipoMotorId() {
		return tipoMotorId;
	}
	public void setTipoMotorId(int tipoMotorId) {
		this.tipoMotorId = tipoMotorId;
	}
	public Double getNumeroCilindros() {
		return numeroCilindros;
	}
	public void setNumeroCilindros(Double numeroCilindros) {
		this.numeroCilindros = numeroCilindros;
	}
	
	private int vehiculoId;
	public int getVehiculoId() {
		return vehiculoId;
	}
	public void setVehiculoId(int vehiculoId) {
		this.vehiculoId = vehiculoId;
	}
	public int getAnnoFabricacion() {
		return annoFabricacion;
	}
	public void setAnnoFabricacion(int annoFabricacion) {
		this.annoFabricacion = annoFabricacion;
	}
	public int getAnnoAfectacion() {
		return annoAfectacion;
	}
	public void setAnnoAfectacion(int annoAfectacion) {
		this.annoAfectacion = annoAfectacion;
	}
	public String getMarcaDescripcion() {
		return marcaDescripcion;
	}
	public void setMarcaDescripcion(String marcaDescripcion) {
		this.marcaDescripcion = marcaDescripcion;
	}
	public String getCategoriaDescripcion() {
		return categoriaDescripcion;
	}
	public void setCategoriaDescripcion(String categoriaDescripcion) {
		this.categoriaDescripcion = categoriaDescripcion;
	}
	public String getModeloDescripcion() {
		return modeloDescripcion;
	}
	public void setModeloDescripcion(String modeloDescripcion) {
		this.modeloDescripcion = modeloDescripcion;
	}
	public String getClaseDescripcion() {
		return claseDescripcion;
	}
	public void setClaseDescripcion(String claseDescripcion) {
		this.claseDescripcion = claseDescripcion;
	}
	public String getTipoCarroceriaDescripcion() {
		return tipoCarroceriaDescripcion;
	}
	public void setTipoCarroceriaDescripcion(String tipoCarroceriaDescripcion) {
		this.tipoCarroceriaDescripcion = tipoCarroceriaDescripcion;
	}
	public String getMarcaDescripcionTemporal() {
		return marcaDescripcionTemporal;
	}
	public void setMarcaDescripcionTemporal(String marcaDescripcionTemporal) {
		this.marcaDescripcionTemporal = marcaDescripcionTemporal;
	}
	public String getModeloDescripcionTemporal() {
		return modeloDescripcionTemporal;
	}
	public void setModeloDescripcionTemporal(String modeloDescripcionTemporal) {
		this.modeloDescripcionTemporal = modeloDescripcionTemporal;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
}
