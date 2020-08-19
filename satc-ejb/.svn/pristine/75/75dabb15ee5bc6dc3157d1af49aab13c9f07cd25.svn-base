package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the gn_tipo_deno_urbana database table.
 * 
 */
@Entity
@Table(name="gn_tipo_deno_urbana")
public class GnTipoDenoUrbana implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_deno_id")
	private int tipoDenoId;

	private String descripcion;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

	public GnTipoDenoUrbana(int tipoDenoId,String descripcion) {
		this.tipoDenoId=tipoDenoId;
		this.descripcion=descripcion;
	}
	
    public GnTipoDenoUrbana() {
    }

	public int getTipoDenoId() {
		return this.tipoDenoId;
	}

	public void setTipoDenoId(int tipoDenoId) {
		this.tipoDenoId = tipoDenoId;
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

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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