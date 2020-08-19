package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the cj_agencia_usuario database table.
 * 
 */
@Entity
@Table(name="cj_agencia_usuario")
public class CjAgenciaUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="agencia_usuario_id")
	private int agenciaUsuarioId;

	private String estado;

	@Column(name="fecha_fin")
	private Timestamp fechaFin;

	@Column(name="fecha_inicio")
	private Timestamp fechaInicio;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="ip_asignada")
	private String ipAsignada;

	private String terminal;

	@Column(name="tipo_rol")
	private String tipoRol;

	//bi-directional many-to-one association to CjAgencia
    @ManyToOne
	@JoinColumn(name="agencia_id")
	private CjAgencia cjAgencia;

	//bi-directional many-to-one association to SgUsuario
    @ManyToOne
	@JoinColumn(name="usuario_id")
	private SgUsuario sgUsuario;

    public CjAgenciaUsuario() {
    }

	public int getAgenciaUsuarioId() {
		return this.agenciaUsuarioId;
	}

	public void setAgenciaUsuarioId(int agenciaUsuarioId) {
		this.agenciaUsuarioId = agenciaUsuarioId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Timestamp getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getIpAsignada() {
		return this.ipAsignada;
	}

	public void setIpAsignada(String ipAsignada) {
		this.ipAsignada = ipAsignada;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTipoRol() {
		return this.tipoRol;
	}

	public void setTipoRol(String tipoRol) {
		this.tipoRol = tipoRol;
	}

	public CjAgencia getCjAgencia() {
		return this.cjAgencia;
	}

	public void setCjAgencia(CjAgencia cjAgencia) {
		this.cjAgencia = cjAgencia;
	}
	
	public SgUsuario getSgUsuario() {
		return this.sgUsuario;
	}

	public void setSgUsuario(SgUsuario sgUsuario) {
		this.sgUsuario = sgUsuario;
	}
	
}