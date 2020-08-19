package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the mp_situacion_empresarial database table.
 * 
 */
@Entity
@Table(name="mp_situacion_empresarial")
public class MpSituacionEmpresarial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="situacion_empresarial_id")
	private Integer situacionEmpresarialId;

	private String descripcion;

	@Column(name="estado")
	private String estado;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="usuario_id")
	private Integer usuarioId;

    public MpSituacionEmpresarial() {
    }

	public Integer getSituacionEmpresarialId() {
		return this.situacionEmpresarialId;
	}

	public void setSituacionEmpresarialId(Integer situacionEmpresarialId) {
		this.situacionEmpresarialId = situacionEmpresarialId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}