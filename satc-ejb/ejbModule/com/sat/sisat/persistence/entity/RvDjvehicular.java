package com.sat.sisat.persistence.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * The persistent class for the rv_djvehicular database table.
 * 
 */
@Entity
@Table(name = "rv_djvehicular")
public class RvDjvehicular implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "djvehicular_id")
	private int djvehicularId;

	@Column(name = "anno_afectacion")
	private int annoAfectacion;

	@Column(name = "anno_declaracion")
	private String annoDeclaracion;

	@Column(name = "anno_fin_afectacion")
	private int annoFinAfectacion;

	@Column(name = "anno_ini_afectacion")
	private int annoIniAfectacion;

	@Column(name = "categoria_vehiculo_id")
	private int categoriaVehiculoId;

	@Column(name = "condicion_vehiculo_id")
	private int condicionVehiculoId;

	@Column(name = "djvehicular_nro")
	private String djvehicularNro;

	@Column(name = "djvehicular_previo_id")
	private int djvehicularPrevioId;

	private String estado;

	@Column(name = "fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name = "fecha_adquisicion")
	private Timestamp fechaAdquisicion;

	@Column(name = "fecha_declaracion")
	private Timestamp fechaDeclaracion;

	@Column(name = "fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name = "flag_dj_anno")
	private String flagDjAnno;

	@Column(name = "marca_vehiculo_id")
	private int marcaVehiculoId;

	@Column(name = "modelo_vehiculo_id")
	private int modeloVehiculoId;

	@Column(name = "notaria_id")
	private int notariaId;

	@Column(name = "num_tarjeta_propiedad")
	private String numTarjetaPropiedad;

	@Column(name = "persona_id")
	private int personaId;

	@Column(name = "porc_propiedad")
	private BigDecimal porcPropiedad;

	@Column(name = "rv_motivo_declaracion_id")
	private int rvMotivoDeclaracionId;

	private String terminal;

	@Column(name = "tipo_adquisicion_id")
	private int tipoAdquisicionId;

	@Column(name = "tipo_cambio_id")
	private int tipoCambioId;

	@Column(name = "tipo_moneda_id")
	private int tipoMonedaId;

	@Column(name = "tipo_motor_id")
	private int tipoMotorId;

	@Column(name = "tipo_propiedad_id")
	private int tipoPropiedadId;

	@Column(name = "tipo_traccion_id")
	private int tipoTraccionId;

	@Column(name = "tipo_transmision_id")
	private int tipoTransmisionId;

	@Column(name = "usuario_id")
	private int usuarioId;

	@Column(name = "val_adq_otra_moneda")
	private BigDecimal valAdqOtraMoneda;

	@Column(name = "val_adq_soles")
	private BigDecimal valAdqSoles;

	@Column(name = "vehiculo_id")
	private int vehiculoId;

	// bi-directional many-to-one association to RvMotivoDescargo
	@ManyToOne
	@JoinColumn(name = "motivo_descargo_id")
	private RvMotivoDescargo rvMotivoDescargo;
	
	@Column(name = "num_motor")
	private String numMotor;
	
	@Column(name = "anno_fabricacion")
	private int annoFabricacion;
	
	@Column(name = "fecha_ins_registros")
	private Timestamp fechaInsRegistros;
	
	@Column(name = "num_cilindros")
	private int numCilindros;
	
	@Column(name = "peso_bruto")
	private int pesoBruto;
	
	@Column(name = "tipo_carroceria_id")
	private int tipoCarroceriaId;

	@Column(name = "clase_vehiculo_id")
	private int claseVehiculoId;
		
	@Column(name = "glosa")
	private String glosa;

	@Column(name = "fecha_descargo", nullable = true)
	private Timestamp fechaDescargo;
	
	/***
	 * Módulo R.D.VEHICULAR-2015
	 */
	@Column(name = "requerimiento_id")
	private int requerimientoId;
	
	@Column(name="fiscalizado")
	private String fiscalizado; 
	
	@Column(name="fisca_aceptada")
	private String fiscaAceptada; 
	
	@Column(name="fisca_cerrada")
	private String fiscaCerrada; 

	public RvDjvehicular() {
	}

	public int getDjvehicularId() {
		return this.djvehicularId;
	}

	public void setDjvehicularId(int djvehicularId) {
		this.djvehicularId = djvehicularId;
	}

	public int getAnnoAfectacion() {
		return this.annoAfectacion;
	}

	public void setAnnoAfectacion(int annoAfectacion) {
		this.annoAfectacion = annoAfectacion;
	}

	public String getAnnoDeclaracion() {
		return this.annoDeclaracion;
	}

	public void setAnnoDeclaracion(String annoDeclaracion) {
		this.annoDeclaracion = annoDeclaracion;
	}

	public int getAnnoFinAfectacion() {
		return this.annoFinAfectacion;
	}

	public void setAnnoFinAfectacion(int annoFinAfectacion) {
		this.annoFinAfectacion = annoFinAfectacion;
	}

	public int getAnnoIniAfectacion() {
		return this.annoIniAfectacion;
	}

	public void setAnnoIniAfectacion(int annoIniAfectacion) {
		this.annoIniAfectacion = annoIniAfectacion;
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

	public String getDjvehicularNro() {
		return this.djvehicularNro;
	}

	public void setDjvehicularNro(String djvehicularNro) {
		this.djvehicularNro = djvehicularNro;
	}

	public int getDjvehicularPrevioId() {
		return this.djvehicularPrevioId;
	}

	public void setDjvehicularPrevioId(int djvehicularPrevioId) {
		this.djvehicularPrevioId = djvehicularPrevioId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaAdquisicion() {
		return this.fechaAdquisicion;
	}

	public void setFechaAdquisicion(Timestamp fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	public Timestamp getFechaDeclaracion() {
		return this.fechaDeclaracion;
	}

	public void setFechaDeclaracion(Timestamp fechaDeclaracion) {
		this.fechaDeclaracion = fechaDeclaracion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFlagDjAnno() {
		return this.flagDjAnno;
	}

	public void setFlagDjAnno(String flagDjAnno) {
		this.flagDjAnno = flagDjAnno;
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

	public int getNotariaId() {
		return this.notariaId;
	}

	public void setNotariaId(int notariaId) {
		this.notariaId = notariaId;
	}

	public String getNumTarjetaPropiedad() {
		return this.numTarjetaPropiedad;
	}

	public void setNumTarjetaPropiedad(String numTarjetaPropiedad) {
		this.numTarjetaPropiedad = numTarjetaPropiedad;
	}

	public int getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public BigDecimal getPorcPropiedad() {
		return this.porcPropiedad;
	}

	public void setPorcPropiedad(BigDecimal porcPropiedad) {
		this.porcPropiedad = porcPropiedad;
	}

	public int getRvMotivoDeclaracionId() {
		return this.rvMotivoDeclaracionId;
	}

	public void setRvMotivoDeclaracionId(int rvMotivoDeclaracionId) {
		this.rvMotivoDeclaracionId = rvMotivoDeclaracionId;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getTipoAdquisicionId() {
		return this.tipoAdquisicionId;
	}

	public void setTipoAdquisicionId(int tipoAdquisicionId) {
		this.tipoAdquisicionId = tipoAdquisicionId;
	}

	public int getTipoCambioId() {
		return this.tipoCambioId;
	}

	public void setTipoCambioId(int tipoCambioId) {
		this.tipoCambioId = tipoCambioId;
	}

	public int getTipoMonedaId() {
		return this.tipoMonedaId;
	}

	public void setTipoMonedaId(int tipoMonedaId) {
		this.tipoMonedaId = tipoMonedaId;
	}

	public int getTipoMotorId() {
		return this.tipoMotorId;
	}

	public void setTipoMotorId(int tipoMotorId) {
		this.tipoMotorId = tipoMotorId;
	}

	public int getTipoPropiedadId() {
		return this.tipoPropiedadId;
	}

	public void setTipoPropiedadId(int tipoPropiedadId) {
		this.tipoPropiedadId = tipoPropiedadId;
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

	public BigDecimal getValAdqOtraMoneda() {
		return this.valAdqOtraMoneda;
	}

	public void setValAdqOtraMoneda(BigDecimal valAdqOtraMoneda) {
		this.valAdqOtraMoneda = valAdqOtraMoneda;
	}

	public BigDecimal getValAdqSoles() {
		return this.valAdqSoles;
	}

	public void setValAdqSoles(BigDecimal valAdqSoles) {
		this.valAdqSoles = valAdqSoles;
	}

	public int getVehiculoId() {
		return this.vehiculoId;
	}

	public void setVehiculoId(int vehiculoId) {
		this.vehiculoId = vehiculoId;
	}

	public RvMotivoDescargo getRvMotivoDescargo() {
		return this.rvMotivoDescargo;
	}

	public void setRvMotivoDescargo(RvMotivoDescargo rvMotivoDescargo) {
		this.rvMotivoDescargo = rvMotivoDescargo;
	}

	public String getNumMotor() {
		return numMotor;
	}

	public void setNumMotor(String numMotor) {
		this.numMotor = numMotor;
	}

	public int getAnnoFabricacion() {
		return annoFabricacion;
	}

	public void setAnnoFabricacion(int annoFabricacion) {
		this.annoFabricacion = annoFabricacion;
	}

	public Timestamp getFechaInsRegistros() {
		return fechaInsRegistros;
	}

	public void setFechaInsRegistros(Timestamp fechaInsRegistros) {
		this.fechaInsRegistros = fechaInsRegistros;
	}

	public int getNumCilindros() {
		return numCilindros;
	}

	public void setNumCilindros(int numCilindros) {
		this.numCilindros = numCilindros;
	}

	public int getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(int pesoBruto) {
		this.pesoBruto = pesoBruto;
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

	public Timestamp getFechaDescargo() {
		return fechaDescargo;
	}

	public void setFechaDescargo(Timestamp fechaDescargo) {
		this.fechaDescargo = fechaDescargo;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public int getRequerimientoId() {
		return requerimientoId;
	}

	public void setRequerimientoId(int requerimientoId) {
		this.requerimientoId = requerimientoId;
	}

	public String getFiscalizado() {
		return fiscalizado;
	}

	public void setFiscalizado(String fiscalizado) {
		this.fiscalizado = fiscalizado;
	}

	public String getFiscaAceptada() {
		return fiscaAceptada;
	}

	public void setFiscaAceptada(String fiscaAceptada) {
		this.fiscaAceptada = fiscaAceptada;
	}

	public String getFiscaCerrada() {
		return fiscaCerrada;
	}

	public void setFiscaCerrada(String fiscaCerrada) {
		this.fiscaCerrada = fiscaCerrada;
	}
}