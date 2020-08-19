package com.sat.sisat.vehicular.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class RvVehiculoDTO implements Serializable{

	private static final long serialVersionUID = -4289428042589446430L;
	
	private String marca;
	private String modelo;
	private int vehiculoId;
	private int annoFabricacion;
	private int categoriaVehiculoId;
	private int condicionVehiculoId;
	private Timestamp fechaActualizacion;
	private Timestamp fechaInsRegistros;
	private Timestamp fechaRegistro;
	private int marcaVehiculoId;
	private int modeloVehiculoId;
	private int numCilindros;
	private String numMotor;
	private int pesoBruto;
	private String placa;
	private String terminal;
	private int tipoMotorId;
	private int tipoTraccionId;
	private int tipoTransmisionId;
	private int usuarioId;
	private int tipoCarroceriaId;
	private int claseVehiculoId;
	private String desCondEspVehic;
	private String desTipoTransmi;
	private String desTipoTracci;
	private String desMarca;
	private String desCatVehic;
	private String desModelo;
	private String desTipoMotor;
	private String desTipoCarroce;
	private String desClaseVehic;
	private Timestamp fechaAdqDescg;	
	
	public RvVehiculoDTO(){
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

	public int getCondicionVehiculoId() {
		return condicionVehiculoId;
	}

	public void setCondicionVehiculoId(int condicionVehiculoId) {
		this.condicionVehiculoId = condicionVehiculoId;
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

	public int getModeloVehiculoId() {
		return modeloVehiculoId;
	}

	public void setModeloVehiculoId(int modeloVehiculoId) {
		this.modeloVehiculoId = modeloVehiculoId;
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

	public int getTipoTraccionId() {
		return tipoTraccionId;
	}

	public void setTipoTraccionId(int tipoTraccionId) {
		this.tipoTraccionId = tipoTraccionId;
	}

	public int getTipoTransmisionId() {
		return tipoTransmisionId;
	}

	public void setTipoTransmisionId(int tipoTransmisionId) {
		this.tipoTransmisionId = tipoTransmisionId;
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

	public int getClaseVehiculoId() {
		return claseVehiculoId;
	}

	public void setClaseVehiculoId(int claseVehiculoId) {
		this.claseVehiculoId = claseVehiculoId;
	}

	public String getDesCondEspVehic() {
		return desCondEspVehic;
	}

	public void setDesCondEspVehic(String desCondEspVehic) {
		this.desCondEspVehic = desCondEspVehic;
	}

	public String getDesTipoTransmi() {
		return desTipoTransmi;
	}

	public void setDesTipoTransmi(String desTipoTransmi) {
		this.desTipoTransmi = desTipoTransmi;
	}

	public String getDesTipoTracci() {
		return desTipoTracci;
	}

	public void setDesTipoTracci(String desTipoTracci) {
		this.desTipoTracci = desTipoTracci;
	}

	public String getDesMarca() {
		return desMarca;
	}

	public void setDesMarca(String desMarca) {
		this.desMarca = desMarca;
	}

	public String getDesCatVehic() {
		return desCatVehic;
	}

	public void setDesCatVehic(String desCatVehic) {
		this.desCatVehic = desCatVehic;
	}

	public String getDesModelo() {
		return desModelo;
	}

	public void setDesModelo(String desModelo) {
		this.desModelo = desModelo;
	}

	public String getDesTipoMotor() {
		return desTipoMotor;
	}

	public void setDesTipoMotor(String desTipoMotor) {
		this.desTipoMotor = desTipoMotor;
	}

	public String getDesTipoCarroce() {
		return desTipoCarroce;
	}

	public void setDesTipoCarroce(String desTipoCarroce) {
		this.desTipoCarroce = desTipoCarroce;
	}

	public String getDesClaseVehic() {
		return desClaseVehic;
	}

	public void setDesClaseVehic(String desClaseVehic) {
		this.desClaseVehic = desClaseVehic;
	}

	public Timestamp getFechaAdqDescg() {
		return fechaAdqDescg;
	}

	public void setFechaAdqDescg(Timestamp fechaAdqDescg) {
		this.fechaAdqDescg = fechaAdqDescg;
	}
}
