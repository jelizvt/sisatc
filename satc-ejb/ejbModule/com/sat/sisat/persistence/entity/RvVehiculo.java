package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;

/**
 * The persistent class for the rv_vehiculo database table.
 * 
 */
@Entity
@Table(name = "rv_vehiculo")
@NamedQueries({
	@NamedQuery(name="findRvVehiculoByPlaca", query="SELECT a FROM RvVehiculo a WHERE a.placa=:p_placa"),
	@NamedQuery(name="findRvVehiculoByPlacas", query="SELECT a FROM RvVehiculo a WHERE a.placa=:p_placa or a.placa=:p_placa_anterior")
})
public class RvVehiculo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String marca;
	
	private String modelo;
	

	@Id
	@Column(name = "vehiculo_id")
	private int vehiculoId;

	@Column(name = "anno_fabricacion")
	private int annoFabricacion;

	@Column(name = "categoria_vehiculo_id")
	private int categoriaVehiculoId;

	@Column(name = "condicion_vehiculo_id")
	private int condicionVehiculoId;

	@Column(name = "fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name = "fecha_ins_registros")
	private Timestamp fechaInsRegistros;

	@Column(name = "fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name = "marca_vehiculo_id")
	private int marcaVehiculoId;

	@Column(name = "modelo_vehiculo_id")
	private int modeloVehiculoId;

	@Column(name = "num_cilindros")
	private int numCilindros;

	@Column(name = "num_motor")
	private String numMotor;

	@Column(name = "peso_bruto")
	private int pesoBruto;

	private String placa;

	private String terminal;

	@Column(name = "tipo_motor_id")
	private int tipoMotorId;

	@Column(name = "tipo_traccion_id")
	private int tipoTraccionId;

	@Column(name = "tipo_transmision_id")
	private int tipoTransmisionId;

	@Column(name = "usuario_id")
	private int usuarioId;

	@Column(name = "tipo_carroceria_id")
	private int tipoCarroceriaId;

	@Column(name = "clase_vehiculo_id")
	private int claseVehiculoId;
	
	@Column(name = "placa_anterior")
	private String placaAnterior;

	public RvVehiculo() {
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
		return this.vehiculoId;
	}

	public void setVehiculoId(int vehiculoId) {
		this.vehiculoId = vehiculoId;
	}

	public int getAnnoFabricacion() {
		return this.annoFabricacion;
	}

	public void setAnnoFabricacion(int annoFabricacion) {
		this.annoFabricacion = annoFabricacion;
	}

	public int getCategoriaVehiculoId() {
		return this.categoriaVehiculoId;
	}

	public void setCategoriaVehiculoId(int categoriaVehiculoId) {
		this.categoriaVehiculoId = categoriaVehiculoId;
	}

	public int getCondicionVehiculoId() {
		return this.condicionVehiculoId;
	}

	public void setCondicionVehiculoId(int condicionVehiculoId) {
		this.condicionVehiculoId = condicionVehiculoId;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaInsRegistros() {
		return this.fechaInsRegistros;
	}

	public void setFechaInsRegistros(Timestamp fechaInsRegistros) {
		this.fechaInsRegistros = fechaInsRegistros;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getMarcaVehiculoId() {
		return this.marcaVehiculoId;
	}

	public void setMarcaVehiculoId(int marcaVehiculoId) {
		this.marcaVehiculoId = marcaVehiculoId;
	}

	public int getModeloVehiculoId() {
		return this.modeloVehiculoId;
	}

	public void setModeloVehiculoId(int modeloVehiculoId) {
		this.modeloVehiculoId = modeloVehiculoId;
	}

	public int getNumCilindros() {
		return this.numCilindros;
	}

	public void setNumCilindros(int numCilindros) {
		this.numCilindros = numCilindros;
	}

	public String getNumMotor() {
		return this.numMotor;
	}

	public void setNumMotor(String numMotor) {
		this.numMotor = numMotor;
	}

	public int getPesoBruto() {
		return this.pesoBruto;
	}

	public void setPesoBruto(int pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getTipoMotorId() {
		return this.tipoMotorId;
	}

	public void setTipoMotorId(int tipoMotorId) {
		this.tipoMotorId = tipoMotorId;
	}

	public int getTipoTraccionId() {
		return this.tipoTraccionId;
	}

	public void setTipoTraccionId(int tipoTraccionId) {
		this.tipoTraccionId = tipoTraccionId;
	}

	public int getTipoTransmisionId() {
		return this.tipoTransmisionId;
	}

	public void setTipoTransmisionId(int tipoTransmisionId) {
		this.tipoTransmisionId = tipoTransmisionId;
	}

	public int getUsuarioId() {
		return this.usuarioId;
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
		return this.claseVehiculoId;
	}

	public void setClaseVehiculoId(int claseVehiculoId) {
		this.claseVehiculoId = claseVehiculoId;
	}

	public String getPlacaAnterior() {
		return placaAnterior;
	}

	public void setPlacaAnterior(String placaAnterior) {
		this.placaAnterior = placaAnterior;
	}
}