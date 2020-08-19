package com.sat.sisat.persistence.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;

@Entity
@Table(name="rv_omisos_detalle_vehicular")
public class RvOmisosDetalleVehicular implements Serializable {
	private static final long serialVersionUID = 4899861305313077682L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="vehicular_omisos_detalle_id")
	private int vehicularOmisosDetalleId;
	
	@Column(name="requerimiento_id")
	private int requerimientoId;
	
	@Column(name="anno_determinacion")
	private Integer annoDeterminacion;
	
	@Column(name="tasa")
	private BigDecimal tasa;
	
	@Column(name="base_imponible")
	private BigDecimal baseImponible;
	
	@Column(name="base_exonerada")
	private BigDecimal baseExonerada;
	
	@Column(name="base_afecta")
	private BigDecimal baseAfecta;
	
	@Column(name="impuesto")
	private BigDecimal impuesto;
	
	@Column(name="marca_vehiculo_id")
	private int marcaId;
	
	@Column(name="categoria_vehiculo_id")
	private int categoriaId;
	
	@Column(name="modelo_vehiculo_id")
	private int modeloId;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
	private String terminal;
	
	@Column(name="descripcion_marca")
	private String descripcionMarca;
	
	@Column(name="descripcion_modelo")
	private String descripcionModelo;
	
	@Column(name="descripcion_categoria")
	private String descripcionCategoria;	
	
	public RvOmisosDetalleVehicular(){		
	}


	public int getVehicularOmisosDetalleId() {
		return vehicularOmisosDetalleId;
	}


	public void setVehicularOmisosDetalleId(int vehicularOmisosDetalleId) {
		this.vehicularOmisosDetalleId = vehicularOmisosDetalleId;
	}

	


	public BigDecimal getBaseImponible() {
		return baseImponible;
	}


	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}


	public BigDecimal getBaseExonerada() {
		return baseExonerada;
	}


	public void setBaseExonerada(BigDecimal baseExonerada) {
		this.baseExonerada = baseExonerada;
	}


	public BigDecimal getBaseAfecta() {
		return baseAfecta;
	}


	public void setBaseAfecta(BigDecimal baseAfecta) {
		this.baseAfecta = baseAfecta;
	}


	public BigDecimal getImpuesto() {
		return impuesto;
	}


	public void setImpuesto(BigDecimal impuesto) {
		this.impuesto = impuesto;
	}

	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public int getUsuarioId() {
		return usuarioId;
	}


	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}


	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	public String getTerminal() {
		return terminal;
	}


	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getRequerimientoId() {
		return requerimientoId;
	}


	public void setRequerimientoId(int requerimientoId) {
		this.requerimientoId = requerimientoId;
	}
	
	
	public BigDecimal getTasa() {
		return tasa;
	}


	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}


	public int getMarcaId() {
		return marcaId;
	}


	public void setMarcaId(int marcaId) {
		this.marcaId = marcaId;
	}


	public int getCategoriaId() {
		return categoriaId;
	}


	public void setCategoriaId(int categoriaId) {
		this.categoriaId = categoriaId;
	}


	public int getModeloId() {
		return modeloId;
	}


	public void setModeloId(int modeloId) {
		this.modeloId = modeloId;
	}


	@Override
	public String toString() {
		return "RvOmisosDetalleVehicular [vehicularOmisosDetalleId="
				+ vehicularOmisosDetalleId + ", requerimientoId="
				+ requerimientoId + ", annoDeterminacion="
				+ annoDeterminacion + ", baseImponible=" + baseImponible
				+ ", baseExonerada=" + baseExonerada + ", baseAfecta="
				+ baseAfecta + ", impuesto=" + impuesto + ", estado=" + estado
				+ ", usuarioId=" + usuarioId + ", fechaRegistro="
				+ fechaRegistro + ", terminal=" + terminal + "]";
	}


	public String getDescripcionMarca() {
		return descripcionMarca;
	}


	public void setDescripcionMarca(String descripcionMarca) {
		this.descripcionMarca = descripcionMarca;
	}


	public String getDescripcionModelo() {
		return descripcionModelo;
	}


	public void setDescripcionModelo(String descripcionModelo) {
		this.descripcionModelo = descripcionModelo;
	}


	public String getDescripcionCategoria() {
		return descripcionCategoria;
	}


	public void setDescripcionCategoria(String descripcionCategoria) {
		this.descripcionCategoria = descripcionCategoria;
	}


	public Integer getAnnoDeterminacion() {
		return annoDeterminacion;
	}


	public void setAnnoDeterminacion(Integer annoDeterminacion) {
		this.annoDeterminacion = annoDeterminacion;
	}


	

}
