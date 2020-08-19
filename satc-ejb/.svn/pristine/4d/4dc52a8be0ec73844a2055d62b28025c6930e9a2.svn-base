package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mp_subtipo_persona database table.
 * 
 */
@Entity
@Table(name="mp_subtipo_persona")
public class MpSubtipoPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MpSubtipoPersonaPK id;

	private String descripcion;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	private String estado;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

	private Integer tipoPersonaId;

	private Integer subtipoPersonaId;
	
    public MpSubtipoPersona() {
    }

	public MpSubtipoPersonaPK getId() {
		return this.id;
	}

	public void setId(MpSubtipoPersonaPK id) {
		this.id = id;
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

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getTipoPersonaId() {
		return tipoPersonaId;
	}

	public void setTipoPersonaId(Integer tipoPersonaId) {
		this.tipoPersonaId = tipoPersonaId;
	}

	public int getSubtipoPersonaId() {
		return subtipoPersonaId;
	}

	public void setSubtipoPersonaId(Integer subtipoPersonaId) {
		this.subtipoPersonaId = subtipoPersonaId;
	}

}