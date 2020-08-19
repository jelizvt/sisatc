package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the cc_lote database table.
 * 
 */
@Entity
@Table(name="cc_lote")
public class CcLote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="lote_id")
	private int loteId;

	@Column(name="anno_lote")
	private int annoLote;

	@Column(name="descripcion_lote")
	private String descripcionLote;

	private String estado;

	@Column(name="fecha_emision")
	private Timestamp fechaEmision;

	@Column(name="fecha_interes")
	private Timestamp fechaInteres;

	@Column(name="fecha_lote")
	private Timestamp fechaLote;

	@Column(name="fecha_notificacion")
	private Timestamp fechaNotificacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="monto_fin")
	private BigDecimal montoFin;

	@Column(name="monto_inicio")
	private BigDecimal montoInicio;

	@Column(name="nro_documentos")
	private int nroDocumentos;

	@Column(name="nro_personas")
	private int nroPersonas;

	private String terminal;

	@Column(name="tipo_impresion")
	private String tipoImpresion;

	@Column(name="tipo_lote")
	private String tipoLote;

	@Column(name="tipo_lote_id")
	private int tipoLoteId;

	@Column(name="total_costas")
	private BigDecimal totalCostas;

	@Column(name="total_lote")
	private BigDecimal totalLote;

	@Column(name="usuario_id")
	private int usuarioId;

	@Column(name="flag_generacion")
	private String flagGeneracion;
	
	@Column(name="tipo_lote_generacion")
	private String tipoLoteGeneracion;
	
	@Column(name="descripcion_ordenamiento")
	private String descripcionOrdenamiento;
	
	@Column(name="flag_ubicables")
	private String flagUbicables;
	
	@Column(name="impresion")
	private int impresion;
	
    public CcLote() {
    }

	public int getLoteId() {
		return this.loteId;
	}

	public void setLoteId(int loteId) {
		this.loteId = loteId;
	}

	public int getAnnoLote() {
		return this.annoLote;
	}

	public void setAnnoLote(int annoLote) {
		this.annoLote = annoLote;
	}

	public String getDescripcionLote() {
		return this.descripcionLote;
	}

	public void setDescripcionLote(String descripcionLote) {
		this.descripcionLote = descripcionLote;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaEmision() {
		return this.fechaEmision;
	}

	public void setFechaEmision(Timestamp fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Timestamp getFechaInteres() {
		return this.fechaInteres;
	}

	public void setFechaInteres(Timestamp fechaInteres) {
		this.fechaInteres = fechaInteres;
	}

	public Timestamp getFechaLote() {
		return this.fechaLote;
	}

	public void setFechaLote(Timestamp fechaLote) {
		this.fechaLote = fechaLote;
	}

	public Timestamp getFechaNotificacion() {
		return this.fechaNotificacion;
	}

	public void setFechaNotificacion(Timestamp fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public BigDecimal getMontoFin() {
		return this.montoFin;
	}

	public void setMontoFin(BigDecimal montoFin) {
		this.montoFin = montoFin;
	}

	public BigDecimal getMontoInicio() {
		return this.montoInicio;
	}

	public void setMontoInicio(BigDecimal montoInicio) {
		this.montoInicio = montoInicio;
	}

	public int getNroDocumentos() {
		return this.nroDocumentos;
	}

	public void setNroDocumentos(int nroDocumentos) {
		this.nroDocumentos = nroDocumentos;
	}

	public int getNroPersonas() {
		return this.nroPersonas;
	}

	public void setNroPersonas(int nroPersonas) {
		this.nroPersonas = nroPersonas;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTipoImpresion() {
		return this.tipoImpresion;
	}

	public void setTipoImpresion(String tipoImpresion) {
		this.tipoImpresion = tipoImpresion;
	}

	public String getTipoLote() {
		return this.tipoLote;
	}

	public void setTipoLote(String tipoLote) {
		this.tipoLote = tipoLote;
	}

	public int getTipoLoteId() {
		return this.tipoLoteId;
	}

	public void setTipoLoteId(int tipoLoteId) {
		this.tipoLoteId = tipoLoteId;
	}

	public BigDecimal getTotalCostas() {
		return this.totalCostas;
	}

	public void setTotalCostas(BigDecimal totalCostas) {
		this.totalCostas = totalCostas;
	}

	public BigDecimal getTotalLote() {
		return this.totalLote;
	}

	public void setTotalLote(BigDecimal totalLote) {
		this.totalLote = totalLote;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}



	public String getFlagGeneracion() {
		return flagGeneracion;
	}

	public void setFlagGeneracion(String flagGeneracion) {
		this.flagGeneracion = flagGeneracion;
	}

	public String getTipoLoteGeneracion() {
		return tipoLoteGeneracion;
	}

	public void setTipoLoteGeneracion(String tipoLoteGeneracion) {
		this.tipoLoteGeneracion = tipoLoteGeneracion;
	}

	public String getDescripcionOrdenamiento() {
		return descripcionOrdenamiento;
	}

	public void setDescripcionOrdenamiento(String descripcionOrdenamiento) {
		this.descripcionOrdenamiento = descripcionOrdenamiento;
	}

	public String getFlagUbicables() {
		return flagUbicables;
	}

	public void setFlagUbicables(String flagUbicables) {
		this.flagUbicables = flagUbicables;
	}

	public int getImpresion() {
		return impresion;
	}

	public void setImpresion(int impresion) {
		this.impresion = impresion;
	}

}