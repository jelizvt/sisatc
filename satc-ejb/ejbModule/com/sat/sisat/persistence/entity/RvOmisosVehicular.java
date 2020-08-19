package com.sat.sisat.persistence.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;

@Entity
@Table(name="rv_omisos_vehicular")
public class RvOmisosVehicular implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="vehicular_omisos_id")
	private int vehicularOmisosId;
	private int codigo;
	private String propietario;
	
	@Column(name="nro_docu_identidad")
	private String nroDocumentoIdentidad;
	
	private String direccion;
	
	@Column(name="fecha_inscripcion")
	private Timestamp fechaInscripcion;
	
	private String placa;
	
	@Column(name="placa_antigua")
	private String placaAntigua;
	
	@Column(name="anno_afectacion")
	private int annoAfectacion;
	
	@Column(name="anno_fabricacion")
	private int annoFabricacion;
	
	@Column(name="numero_cilindros")
	private BigDecimal numeroCilindros;
	
	private String estado;
	
	private String glosa;
	
	@Column(name="flag_vehiculo_declarado")
	private String flagVehiculoDeclarado;
	
	@Column(name="djvehicular_nro")
	private String djvehicularNro;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;
	
	private String terminal;	
	
	
	@Column(name="lote_id")
	private int loteId;
	
	@Column(name="persona_id")
	private int personaId;
	
	@Column(name="tipo_persona_id")
	private int tipoPersonaId;
	
	@Column(name="subtipo_persona_id")
	private int subtipoPersonaId;
	
	@Column(name="tipo_doc_identidad_id")
	private int tipoDocIdentidadId;
	
	@Column(name="distrito_id")
	private int distritoId;
	
	@Column(name="marca_vehiculo_id")
	private int marcaVehiculoId;
	
	
	@Column(name="categoria_vehiculo_id")
	private int categoriaVehiculoId;
	
	@Column(name="modelo_vehiculo_id")
	private int modeloVehiculoId;
	
	@Column(name="clase_vehiculo_id")
	private int claseVehiculoId;
	
	@Column(name="carroceria_id")
	private int carroceriaId;
	
	@Column(name="tipo_motor_id")
	private int tipoMotorId;
	
	@Column(name="usuario_id")
	private int usuarioId;
	
	private String estadoDescripcion;
	private String marcaDescripcion;
	private String modeloDescripcion;

	private String categoriaDescripcion;
	private String carroceriaDescripcion;
	private String tipoMotorDescripcion;
	private String claseDescripcion;
	private String distritoDescripcion;

	public String getDistritoDescripcion() {
		return distritoDescripcion;
	}

	public void setDistritoDescripcion(String distritoDescripcion) {
		this.distritoDescripcion = distritoDescripcion;
	}

	public String getCategoriaDescripcion() {
		return categoriaDescripcion;
	}

	public void setCategoriaDescripcion(String categoriaDescripcion) {
		this.categoriaDescripcion = categoriaDescripcion;
	}

	public String getCarroceriaDescripcion() {
		return carroceriaDescripcion;
	}

	public void setCarroceriaDescripcion(String carroceriaDescripcion) {
		this.carroceriaDescripcion = carroceriaDescripcion;
	}

	public String getTipoMotorDescripcion() {
		return tipoMotorDescripcion;
	}

	public void setTipoMotorDescripcion(String tipoMotorDescripcion) {
		this.tipoMotorDescripcion = tipoMotorDescripcion;
	}

	public String getClaseDescripcion() {
		return claseDescripcion;
	}

	public void setClaseDescripcion(String claseDescripcion) {
		this.claseDescripcion = claseDescripcion;
	}


	public RvOmisosVehicular(){
	
	}

	public int getVehicularOmisosId() {
		return vehicularOmisosId;
	}

	public void setVehicularOmisosId(int vehicularOmisosId) {
		this.vehicularOmisosId = vehicularOmisosId;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getPropietario() {
		return propietario;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}

	public String getNroDocumentoIdentidad() {
		return nroDocumentoIdentidad;
	}

	public void setNroDocumentoIdentidad(String nroDocumentoIdentidad) {
		this.nroDocumentoIdentidad = nroDocumentoIdentidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Timestamp getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(Timestamp fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getPlacaAntigua() {
		return placaAntigua;
	}

	public void setPlacaAntigua(String placaAntigua) {
		this.placaAntigua = placaAntigua;
	}

	public int getAnnoAfectacion() {
		return annoAfectacion;
	}

	public void setAnnoAfectacion(int annoAfectacion) {
		this.annoAfectacion = annoAfectacion;
	}

	public int getAnnoFabricacion() {
		return annoFabricacion;
	}

	public void setAnnoFabricacion(int annoFabricacion) {
		this.annoFabricacion = annoFabricacion;
	}

	public BigDecimal getNumeroCilindros() {
		return numeroCilindros;
	}

	public void setNumeroCilindros(BigDecimal numeroCilindros) {
		this.numeroCilindros = numeroCilindros;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public String getFlagVehiculoDeclarado() {
		return flagVehiculoDeclarado;
	}

	public void setFlagVehiculoDeclarado(String flagVehiculoDeclarado) {
		this.flagVehiculoDeclarado = flagVehiculoDeclarado;
	}

	public String getDjvehicularNro() {
		return djvehicularNro;
	}

	public void setDjvehicularNro(String djvehicularNro) {
		this.djvehicularNro = djvehicularNro;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getLoteId() {
		return loteId;
	}

	public void setLoteId(int loteId) {
		this.loteId = loteId;
	}

	public int getPersonaId() {
		return personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public int getTipoPersonaId() {
		return tipoPersonaId;
	}

	public void setTipoPersonaId(int tipoPersonaId) {
		this.tipoPersonaId = tipoPersonaId;
	}

	public int getSubtipoPersonaId() {
		return subtipoPersonaId;
	}

	public void setSubtipoPersonaId(int subtipoPersonaId) {
		this.subtipoPersonaId = subtipoPersonaId;
	}

	public int getTipoDocIdentidadId() {
		return tipoDocIdentidadId;
	}

	public void setTipoDocIdentidadId(int tipoDocIdentidadId) {
		this.tipoDocIdentidadId = tipoDocIdentidadId;
	}

	public int getDistritoId() {
		return distritoId;
	}

	public void setDistritoId(int distritoId) {
		this.distritoId = distritoId;
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

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	@Override
	public String toString() {
		return "RvOmisosVehicular [vehicularOmisosId=" + vehicularOmisosId
				+ ", codigo=" + codigo + ", propietario=" + propietario
				+ ", nroDocumentoIdentidad=" + nroDocumentoIdentidad
				+ ", direccion=" + direccion + ", fechaInscripcion="
				+ fechaInscripcion + ", placa=" + placa + ", placaAntigua="
				+ placaAntigua + ", annoAfectacion=" + annoAfectacion
				+ ", annoFabricacion=" + annoFabricacion + ", numeroCilindros="
				+ numeroCilindros + ", estado=" + estado + ", glosa=" + glosa
				+ ", flagVehiculoDeclarado=" + flagVehiculoDeclarado
				+ ", djvehicularNro=" + djvehicularNro + ", fechaRegistro="
				+ fechaRegistro + ", fechaActualizacion=" + fechaActualizacion
				+ ", terminal=" + terminal + ", loteId=" + loteId
				+ ", personaId=" + personaId + ", tipoPersonaId="
				+ tipoPersonaId + ", subtipoPersonaId=" + subtipoPersonaId
				+ ", tipoDocIdentidadId=" + tipoDocIdentidadId
				+ ", distritoId=" + distritoId + ", marcaVehiculoId="
				+ marcaVehiculoId + ", categoriaVehiculoId="
				+ categoriaVehiculoId + ", modeloVehiculoId="
				+ modeloVehiculoId + ", claseVehiculoId=" + claseVehiculoId
				+ ", carroceriaId=" + carroceriaId + ", tipoMotorId="
				+ tipoMotorId + ", usuarioId=" + usuarioId + "]";
	}

	public String getEstadoDescripcion() {
		return estadoDescripcion;
	}

	public void setEstadoDescripcion(String estadoDescripcion) {
		this.estadoDescripcion = estadoDescripcion;
	}

	public String getMarcaDescripcion() {
		return marcaDescripcion;
	}

	public void setMarcaDescripcion(String marcaDescripcion) {
		this.marcaDescripcion = marcaDescripcion;
	}

	public String getModeloDescripcion() {
		return modeloDescripcion;
	}

	public void setModeloDescripcion(String modeloDescripcion) {
		this.modeloDescripcion = modeloDescripcion;
	}
	
	
	
	
	
	
	
	

}
