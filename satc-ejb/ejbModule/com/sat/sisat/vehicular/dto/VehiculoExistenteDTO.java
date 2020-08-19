package com.sat.sisat.vehicular.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class VehiculoExistenteDTO implements Serializable{

	private int vehiculoId;
	private int annoFabricacion;
	private int categoriaVehiculoId;
	private String categoria;
	private int condicionVehiculoId;
	private String condicionVehiculo;
	private Timestamp fechaActualizacion;
	private Timestamp fechaInsRegistros;
	private Timestamp fechaRegistro;
	private int marcaVehiculoId;
	private String marca;
	private int modeloVehiculoId;
	private String modelo;
	private int numCilindros;
	private String numMotor;
	private int pesoBruto;
	private String placa;
	private String terminal;
	private int tipoMotorId;
	private String tipoMotor;
	private int tipoTraccionId;
	private String tipoTraccion;
	private int tipoTransmisionId;
	private String tipoTransmision;
	private int usuarioId;
	private int tipoCarroceriaId;
	private String tipoCarroceria;
	private int claseVehiculoId;
	private String claseVehiculo;

	public VehiculoExistenteDTO() {
		super();
	}

	public VehiculoExistenteDTO(int vehiculoId, int annoFabricacion,
			int categoriaVehiculoId, String categoria, int condicionVehiculoId,
			String condicionVehiculo, Timestamp fechaActualizacion,
			Timestamp fechaInsRegistros, Timestamp fechaRegistro,
			int marcaVehiculoId, String marca, int modeloVehiculoId,
			String modelo, int numCilindros, String numMotor, int pesoBruto,
			String placa, String terminal, int tipoMotorId, String tipoMotor,
			int tipoTraccionId, String tipoTraccion, int tipoTransmisionId,
			String tipoTransmision, int usuarioId, int tipoCarroceriaId,
			String tipoCarroceria, int claseVehiculoId, String claseVehiculo) {
		super();
		this.vehiculoId = vehiculoId;
		this.annoFabricacion = annoFabricacion;
		this.categoriaVehiculoId = categoriaVehiculoId;
		this.categoria = categoria;
		this.condicionVehiculoId = condicionVehiculoId;
		this.condicionVehiculo = condicionVehiculo;
		this.fechaActualizacion = fechaActualizacion;
		this.fechaInsRegistros = fechaInsRegistros;
		this.fechaRegistro = fechaRegistro;
		this.marcaVehiculoId = marcaVehiculoId;
		this.marca = marca;
		this.modeloVehiculoId = modeloVehiculoId;
		this.modelo = modelo;
		this.numCilindros = numCilindros;
		this.numMotor = numMotor;
		this.pesoBruto = pesoBruto;
		this.placa = placa;
		this.terminal = terminal;
		this.tipoMotorId = tipoMotorId;
		this.tipoMotor = tipoMotor;
		this.tipoTraccionId = tipoTraccionId;
		this.tipoTraccion = tipoTraccion;
		this.tipoTransmisionId = tipoTransmisionId;
		this.tipoTransmision = tipoTransmision;
		this.usuarioId = usuarioId;
		this.tipoCarroceriaId = tipoCarroceriaId;
		this.tipoCarroceria = tipoCarroceria;
		this.claseVehiculoId = claseVehiculoId;
		this.claseVehiculo = claseVehiculo;
	}

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

	public int getCategoriaVehiculoId() {
		return categoriaVehiculoId;
	}

	public void setCategoriaVehiculoId(int categoriaVehiculoId) {
		this.categoriaVehiculoId = categoriaVehiculoId;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getCondicionVehiculoId() {
		return condicionVehiculoId;
	}

	public void setCondicionVehiculoId(int condicionVehiculoId) {
		this.condicionVehiculoId = condicionVehiculoId;
	}

	public String getCondicionVehiculo() {
		return condicionVehiculo;
	}

	public void setCondicionVehiculo(String condicionVehiculo) {
		this.condicionVehiculo = condicionVehiculo;
	}

	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaInsRegistros() {
		return fechaInsRegistros;
	}

	public void setFechaInsRegistros(Timestamp fechaInsRegistros) {
		this.fechaInsRegistros = fechaInsRegistros;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getMarcaVehiculoId() {
		return marcaVehiculoId;
	}

	public void setMarcaVehiculoId(int marcaVehiculoId) {
		this.marcaVehiculoId = marcaVehiculoId;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getModeloVehiculoId() {
		return modeloVehiculoId;
	}

	public void setModeloVehiculoId(int modeloVehiculoId) {
		this.modeloVehiculoId = modeloVehiculoId;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getNumCilindros() {
		return numCilindros;
	}

	public void setNumCilindros(int numCilindros) {
		this.numCilindros = numCilindros;
	}

	public String getNumMotor() {
		return numMotor;
	}

	public void setNumMotor(String numMotor) {
		this.numMotor = numMotor;
	}

	public int getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(int pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getTipoMotorId() {
		return tipoMotorId;
	}

	public void setTipoMotorId(int tipoMotorId) {
		this.tipoMotorId = tipoMotorId;
	}

	public String getTipoMotor() {
		return tipoMotor;
	}

	public void setTipoMotor(String tipoMotor) {
		this.tipoMotor = tipoMotor;
	}

	public int getTipoTraccionId() {
		return tipoTraccionId;
	}

	public void setTipoTraccionId(int tipoTraccionId) {
		this.tipoTraccionId = tipoTraccionId;
	}

	public String getTipoTraccion() {
		return tipoTraccion;
	}

	public void setTipoTraccion(String tipoTraccion) {
		this.tipoTraccion = tipoTraccion;
	}

	public int getTipoTransmisionId() {
		return tipoTransmisionId;
	}

	public void setTipoTransmisionId(int tipoTransmisionId) {
		this.tipoTransmisionId = tipoTransmisionId;
	}

	public String getTipoTransmision() {
		return tipoTransmision;
	}

	public void setTipoTransmision(String tipoTransmision) {
		this.tipoTransmision = tipoTransmision;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getTipoCarroceriaId() {
		return tipoCarroceriaId;
	}

	public void setTipoCarroceriaId(int tipoCarroceriaId) {
		this.tipoCarroceriaId = tipoCarroceriaId;
	}

	public String getTipoCarroceria() {
		return tipoCarroceria;
	}

	public void setTipoCarroceria(String tipoCarroceria) {
		this.tipoCarroceria = tipoCarroceria;
	}

	public int getClaseVehiculoId() {
		return claseVehiculoId;
	}

	public void setClaseVehiculoId(int claseVehiculoId) {
		this.claseVehiculoId = claseVehiculoId;
	}

	public String getClaseVehiculo() {
		return claseVehiculo;
	}

	public void setClaseVehiculo(String claseVehiculo) {
		this.claseVehiculo = claseVehiculo;
	}
}
