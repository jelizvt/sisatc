package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the pa_carga_lotes database table.
 * 
 */
@Entity
@Table(name="pa_carga_lotes")
@NamedQuery(name="findPaCargaLoteByNumeroOficio", query="SELECT a FROM PaCargaLote a WHERE a.numOficio=:p_num_oficio AND a.cargaLotesId!=:p_carga_lotes_id AND a.estado='1'")
public class PaCargaLote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="carga_lotes_id")
	private Integer cargaLotesId;

	@Column(name="num_oficio")
	private String numOficio;

	@Column(name="fec_oficio")
	private Timestamp fecOficio;

	@Column(name="fec_recepcion")
	private Timestamp fecRecepcion;

	@Column(name="num_expediente")
	private String numExpediente;
	
	@Column(name="num_lote")
	private String numLote;
	
	@Column(name="observaciones")
	private String observaciones;
	
	@Column(name="archivo")
	private String archivo;
	
	@Column(name="origen")
	private String origen;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
	@Column(name="terminal")
	private String terminal;

	@Column(name="cantidad")
	private Integer cantidad;
	
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getCargaLotesId() {
		return cargaLotesId;
	}

	public void setCargaLotesId(Integer cargaLotesId) {
		this.cargaLotesId = cargaLotesId;
	}

	public String getNumOficio() {
		return numOficio;
	}

	public void setNumOficio(String numOficio) {
		this.numOficio = numOficio;
	}

	public Timestamp getFecOficio() {
		return fecOficio;
	}

	public void setFecOficio(Timestamp fecOficio) {
		this.fecOficio = fecOficio;
	}

	public Timestamp getFecRecepcion() {
		return fecRecepcion;
	}

	public void setFecRecepcion(Timestamp fecRecepcion) {
		this.fecRecepcion = fecRecepcion;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNumLote() {
		return numLote;
	}

	public void setNumLote(String numLote) {
		this.numLote = numLote;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
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
		this.terminal= terminal;
	}
	
}