package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the gn_provincia database table.
 * 
 */
@Entity
@Table(name="gn_provincia")
public class GnProvincia implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GnProvinciaPK id;

	private String descripcion;

    public GnProvincia() {
    }

	public GnProvinciaPK getId() {
		return this.id;
	}

	public void setId(GnProvinciaPK id) {
		this.id = id;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}