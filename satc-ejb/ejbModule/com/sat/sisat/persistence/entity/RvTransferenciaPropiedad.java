package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rv_transferencia_propiedad database table.
 * 
 */
@Entity
@Table(name="rv_transferencia_propiedad")
public class RvTransferenciaPropiedad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="transferencia_id")
	private int transferenciaId;

	@Column(name="djvehicular_id")
	private int djvehicularId;

	private String estado;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="persona_id")
	private int personaId;

	private String terminal;

	private String tipo;

	@Column(name="usuario_id")
	private int usuarioId;

    public RvTransferenciaPropiedad() {
    }

	public int getTransferenciaId() {
		return this.transferenciaId;
	}

	public void setTransferenciaId(int transferenciaId) {
		this.transferenciaId = transferenciaId;
	}

	public int getDjvehicularId() {
		return this.djvehicularId;
	}

	public void setDjvehicularId(int djvehicularId) {
		this.djvehicularId = djvehicularId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}