package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the rp_categoria_uso database table.
 * 
 */
@Entity
@Table(name="rp_categoria_uso_seguridad")
@NamedQuery(name="getAllRpCategoriaUsoSeguridadByPeriodo", query="SELECT m FROM RpCategoriaUsoSeguridad m WHERE m.estado='1' AND m.periodo=:p_periodo ")
public class RpCategoriaUsoSeguridad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="categoria_uso_seguridad_id")
	private Integer categoriaUsoSeguridadId;

	@Column(name="descipcion_corta")
	private String descripcionCorta;

	private String descripcion;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private Integer periodo;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

    public RpCategoriaUsoSeguridad() {
    }

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Integer getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public Integer getCategoriaUsoSeguridadId() {
		return categoriaUsoSeguridadId;
	}

	public void setCategoriaUsoSeguridadId(Integer categoriaUsoSeguridadId) {
		this.categoriaUsoSeguridadId = categoriaUsoSeguridadId;
	}
}