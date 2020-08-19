package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the dt_cercania_parques database table.
 * 
 */
@Entity
@Table(name="dt_cercania_parques")
@NamedQuery(name="getDtCercaniaParqueByUbicacionId", query="SELECT m FROM DtCercaniaParque m WHERE m.estado='1' AND m.ubicacionId=:p_ubicacion_id AND m.periodo=:p_periodo ")
public class DtCercaniaParque implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cercania_parques_id")
	private Integer cercaniaParquesId;
	
	@Column(name="ubicacion_id")
	private Integer ubicacionId;
	
	@Column(name="grupo_cercania_id")
	private Integer grupoCercaniaId;

	@Column(name="periodo")
	private Integer periodo;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="estado")
	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="terminal")
	private String terminal;

	
	public Integer getCercaniaParquesId() {
		return cercaniaParquesId;
	}

	public void setCercaniaParquesId(Integer cercaniaParquesId) {
		this.cercaniaParquesId = cercaniaParquesId;
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

	public Integer getGrupoCercaniaId() {
		return grupoCercaniaId;
	}

	public void setGrupoCercaniaId(Integer grupoCercaniaId) {
		this.grupoCercaniaId = grupoCercaniaId;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getUbicacionId() {
		return ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

}