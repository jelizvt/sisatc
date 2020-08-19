package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="pa_resolucion")
@NamedQuery(name="findPaResolucion", query="SELECT a FROM PaResolucion a WHERE a.estado='1' ORDER BY a.resolucionId ASC ")
public class PaResolucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="resolucion_id")
	private Integer resolucionId;

	private String estado;

	@Column(name="estado_resolucion_id")
	private int estadoResolucionId;

	@Column(name="fecha_emision")
	private Timestamp fechaEmision;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String observaciones;

	@Column(name="terminal")
	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="cantidad")
	private Integer cantidad;
	
    public PaResolucion() {
    	
    }

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
    	
	public Integer getResolucionId() {
		return this.resolucionId;
	}

	public void setResolucionId(Integer resolucionId) {
		this.resolucionId = resolucionId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getEstadoResolucionId() {
		return this.estadoResolucionId;
	}

	public void setEstadoResolucionId(int estadoResolucionId) {
		this.estadoResolucionId = estadoResolucionId;
	}

	public Timestamp getFechaEmision() {
		return this.fechaEmision;
	}

	public void setFechaEmision(Timestamp fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal= terminal;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}