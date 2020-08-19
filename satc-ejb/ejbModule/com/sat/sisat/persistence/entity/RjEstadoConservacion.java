package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rj_estado_conservacion database table.
 * 
 */
@Entity
@Table(name="rj_estado_conservacion")
public class RjEstadoConservacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="conservacion_id")
	private int conservacionId;

	private String descripcion;

	@Column(name="estado")
	private String estadoId;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="usuario_id")
	private int usuarioId;

    public RjEstadoConservacion() {
    }

	public int getConservacionId() {
		return this.conservacionId;
	}

	public void setConservacionId(int conservacionId) {
		this.conservacionId = conservacionId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstadoId() {
		return this.estadoId;
	}

	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}