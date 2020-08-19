package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the rp_tipo_uso database table.
 * 
 */
@Entity
@Table(name="rp_tipo_uso_predio_rustico")
@NamedQuery(name="getAllRpTipoUsoPredioRustico", query="SELECT m FROM RpTipoUsoPredioRustico m WHERE m.estado='1' ORDER BY m.descripcion ")
public class RpTipoUsoPredioRustico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_uso_rustico_id")
	private Integer tipoUsoRusticoId;

	private String descripcion;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

	public Integer getTipoUsoRusticoId() {
		return tipoUsoRusticoId;
	}

	public void setTipoUsoRusticoId(Integer tipoUsoRusticoId) {
		this.tipoUsoRusticoId = tipoUsoRusticoId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

}