package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the dt_frecuencia_recojo database table.
 * 
 */
@Entity
@Table(name="dt_frecuencia_recojo")
@NamedQuery(name="getDtFrecuenciaRecojoByUbicacionId", query="SELECT m FROM DtFrecuenciaRecojo m WHERE m.estado='1' AND m.ubicacionId=:p_ubicacion_id AND m.periodo=:p_periodo ")
public class DtFrecuenciaRecojo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="frecuencia_recojo_id")
	private Integer frecuenciaRecojoId;
	
	@Column(name="periodo")
	private Integer periodo;
	
	@Column(name="ubicacion_id")
	private Integer ubicacionId;

	@Column(name="frecuencia")
	private Integer frecuencia;
	
	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="estado")
	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="terminal")
	private String terminal;
	
	
    public DtFrecuenciaRecojo() {
    }

	public Integer getFrecuenciaRecojoId() {
		return this.frecuenciaRecojoId;
	}

	public void setFrecuenciaRecojoId(Integer frecuenciaRecojoId) {
		this.frecuenciaRecojoId = frecuenciaRecojoId;
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

	public Integer getFrecuencia() {
		return this.frecuencia;
	}

	public void setFrecuencia(Integer frecuencia) {
		this.frecuencia = frecuencia;
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

	public Integer getUbicacionId() {
		return this.ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

}