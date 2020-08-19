package com.sat.sisat.persistence.entity;

// Generated 20/11/2012 10:29:09 AM by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * TdEstadoExpediente generated by hbm2java
 */
@Entity
@Table(name = "td_estado_expediente")
public class TdEstadoExpediente implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6225067248901126143L;

	@Id
	@Column(name = "estado_expediente_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer estadoExpedienteId ;
	
	@Column(name="descripcion", nullable=false)
	private String descripcion;
	
	@Column(name = "usuario_id", nullable = false)
	@NotNull
	private int usuarioId;

	@Column(name = "estado", nullable = false)
	@NotNull
	private String estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro")
	@NotNull
	private Date fechaRegistro;

	@Column(name = "terminal", nullable = false)
	@NotNull
	private String terminal;

	public Integer getEstadoExpedienteId() {
		return estadoExpedienteId;
	}

	public void setEstadoExpedienteId(Integer estadoExpedienteId) {
		this.estadoExpedienteId = estadoExpedienteId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	
}