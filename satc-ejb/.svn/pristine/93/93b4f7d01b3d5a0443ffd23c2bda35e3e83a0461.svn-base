package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the gn_distrito database table.
 * 
 */
@Entity
@Table(name="gn_distrito")
public class GnDistrito implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GnDistritoPK id;

	@Column(name="codigo_postal")
	private String codigoPostal;

	private String descripcion;

    public GnDistrito() {
    }

	public GnDistritoPK getId() {
		return this.id;
	}

	public void setId(GnDistritoPK id) {
		this.id = id;
	}
	
	public String getCodigoPostal() {
		return this.codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}