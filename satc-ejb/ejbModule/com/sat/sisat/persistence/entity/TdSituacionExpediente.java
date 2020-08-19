package com.sat.sisat.persistence.entity;


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
 * TdSituacionExpediente
 */
@Entity
@Table(name = "td_situacion_expediente")
public class TdSituacionExpediente implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6225067248901126144L;

	@Id
	@Column(name = "situacion_expediente_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer situacionExpedienteId ;
	
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

	public Integer getSituacionExpedienteId() {
		return situacionExpedienteId;
	}

	public void setSituacionExpedienteId(Integer situacionExpedienteId) {
		this.situacionExpedienteId = situacionExpedienteId;
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
