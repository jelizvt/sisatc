package com.sat.sisat.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the gn_departamento database table.
 * 
 */
@Entity
@Table(name="gn_departamento")
public class GnDepartamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="dpto_id")
	private int dptoId;

	private String descripcion;

    public GnDepartamento() {
    }

	public int getDptoId() {
		return this.dptoId;
	}

	public void setDptoId(int dptoId) {
		this.dptoId = dptoId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}