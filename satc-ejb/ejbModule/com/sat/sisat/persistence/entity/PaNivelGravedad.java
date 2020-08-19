package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the pa_nivel_gravedad database table.
 * 
 */
/*@Entity
@Table(name="pa_nivel_gravedad")*/
public class PaNivelGravedad implements Serializable {
	private static final long serialVersionUID = 1L;

	private String descripcion;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	private String estado;

	private Timestamp fecha;

	@Id
	@Column(name="nivel_gravedad_id")
	private int nivelGravedadId;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public PaNivelGravedad() {
    }

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionCorta() {
		return this.descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public int getNivelGravedadId() {
		return this.nivelGravedadId;
	}

	public void setNivelGravedadId(int nivelGravedadId) {
		this.nivelGravedadId = nivelGravedadId;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}