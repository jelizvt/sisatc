package com.sat.sisat.persistence.entity;

// Generated 20/11/2012 10:29:09 AM by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * TdRequisito generated by hbm2java
 */
@Entity
@Table(name = "td_procedimiento")
public class TdProcedimiento implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4659269148690918456L;

	@Id
	@Column(name = "procedimiento_id")
	private Integer procedimientoId;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "desc_corta")
	private String descCorta;

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

	public Integer getProcedimientoId() {
		return procedimientoId;
	}

	public void setProcedimientoId(Integer procedimientoId) {
		this.procedimientoId = procedimientoId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescCorta() {
		return descCorta;
	}

	public void setDescCorta(String descCorta) {
		this.descCorta = descCorta;
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