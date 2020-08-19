package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * The persistent class for the rv_motivo_descargo database table.
 * 
 */
@Entity
@Table(name = "rv_motivo_descargo")
public class RvMotivoDescargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "motivo_descargo_id")
	private int motivoDescargoId;

	private String descripcion;

	private String estado;

	@Column(name = "fecha_registro")
	private Timestamp fechaRegistro;

	public RvMotivoDescargo() {
	}

	public int getMotivoDescargoId() {
		return this.motivoDescargoId;
	}

	public void setMotivoDescargoId(int motivoDescargoId) {
		this.motivoDescargoId = motivoDescargoId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}