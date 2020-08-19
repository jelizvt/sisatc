package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the cc_lote_categoria database table.
 * 
 */
@Entity
@Table(name="cc_lote_categoria")
public class CcLoteCategoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CcLoteCategoriaPK id;

	@Column(name="categoria_persona_id")
	private int categoriaPersonaId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public CcLoteCategoria() {
    }

	public CcLoteCategoriaPK getId() {
		return this.id;
	}

	public void setId(CcLoteCategoriaPK id) {
		this.id = id;
	}
	
	public int getCategoriaPersonaId() {
		return this.categoriaPersonaId;
	}

	public void setCategoriaPersonaId(int categoriaPersonaId) {
		this.categoriaPersonaId = categoriaPersonaId;
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