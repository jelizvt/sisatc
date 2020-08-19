package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rp_categoria_rustico database table.
 * 
 */
@Entity
@Table(name="rp_categoria_rustico")
public class RpCategoriaRustico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="categoria_rustico_id")
	private int categoriaRusticoId;

	private String descripcion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public RpCategoriaRustico() {
    }

	public int getCategoriaRusticoId() {
		return this.categoriaRusticoId;
	}

	public void setCategoriaRusticoId(int categoriaRusticoId) {
		this.categoriaRusticoId = categoriaRusticoId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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