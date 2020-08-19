package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rj_mes database table.
 * 
 */
@Entity
@Table(name="rj_mes")
public class RjMes implements Serializable {
	private static final long serialVersionUID = 1L;

	private String descripcion;

	private String estado;

	@Id	
	@Column(name="mes_id")
	private String mesId;

    public RjMes() {
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

	public String getMesId() {
		return this.mesId;
	}

	public void setMesId(String mesId) {
		this.mesId = mesId;
	}

}