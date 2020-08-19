package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the gn_tipo_moneda database table.
 * 
 */
@Entity
@Table(name="gn_tipo_moneda")
public class GnTipoMoneda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_moneda_id")
	private int tipoMonedaId;

	private String descripcion;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="tipo_moneda_corto")
	private String tipoMonedaCorto;

	@Column(name="usuario_id")
	private int usuarioId;

    public GnTipoMoneda() {
    }

	public int getTipoMonedaId() {
		return this.tipoMonedaId;
	}

	public void setTipoMonedaId(int tipoMonedaId) {
		this.tipoMonedaId = tipoMonedaId;
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

	public String getTipoMonedaCorto() {
		return this.tipoMonedaCorto;
	}

	public void setTipoMonedaCorto(String tipoMonedaCorto) {
		this.tipoMonedaCorto = tipoMonedaCorto;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}