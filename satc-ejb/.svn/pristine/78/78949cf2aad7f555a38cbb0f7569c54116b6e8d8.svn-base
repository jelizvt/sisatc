package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rv_modelo_vehiculo database table.
 * 
 */
@Embeddable
public class RvModeloVehiculoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="marca_vehiculo_id")
	private int marcaVehiculoId;

	@Column(name="categoria_vehiculo_id")
	private int categoriaVehiculoId;

	@Column(name="modelo_vehiculo_id")
	private int modeloVehiculoId;

    public RvModeloVehiculoPK() {
    }
	public int getMarcaVehiculoId() {
		return this.marcaVehiculoId;
	}
	public void setMarcaVehiculoId(int marcaVehiculoId) {
		this.marcaVehiculoId = marcaVehiculoId;
	}
	public int getCategoriaVehiculoId() {
		return this.categoriaVehiculoId;
	}
	public void setCategoriaVehiculoId(int categoriaVehiculoId) {
		this.categoriaVehiculoId = categoriaVehiculoId;
	}
	public int getModeloVehiculoId() {
		return this.modeloVehiculoId;
	}
	public void setModeloVehiculoId(int modeloVehiculoId) {
		this.modeloVehiculoId = modeloVehiculoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RvModeloVehiculoPK)) {
			return false;
		}
		RvModeloVehiculoPK castOther = (RvModeloVehiculoPK)other;
		return 
			(this.marcaVehiculoId == castOther.marcaVehiculoId)
			&& (this.categoriaVehiculoId == castOther.categoriaVehiculoId)
			&& (this.modeloVehiculoId == castOther.modeloVehiculoId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.marcaVehiculoId;
		hash = hash * prime + this.categoriaVehiculoId;
		hash = hash * prime + this.modeloVehiculoId;
		
		return hash;
    }
}