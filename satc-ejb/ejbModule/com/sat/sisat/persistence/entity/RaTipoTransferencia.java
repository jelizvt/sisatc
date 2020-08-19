package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the ra_tipo_transferencia database table.
 * 
 */
@Entity
@Table(name="ra_tipo_transferencia")
public class RaTipoTransferencia implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@Column(name="tipo_transferencia_id")
	private int tipoTransferenciaId;

	@Column(name="desc_corta")
	private String descCorta;

	private String descripcion;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="flag_afectacion")
	private String flagAfectacion;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

	//bi-directional many-to-one association to RaDjalcabala
//	@OneToMany(mappedBy="raTipoTransferencia")
//	private List<RaDjalcabala> raDjalcabalas;

	//bi-directional many-to-one association to RaDjalcabalaHistorico
//	@OneToMany(mappedBy="raTipoTransferencia")
//	private List<RaDjalcabalaHistorico> raDjalcabalaHistoricos;

    public RaTipoTransferencia() {
    }

	public int getTipoTransferenciaId() {
		return this.tipoTransferenciaId;
	}

	public void setTipoTransferenciaId(int tipoTransferenciaId) {
		this.tipoTransferenciaId = tipoTransferenciaId;
	}

	public String getDescCorta() {
		return this.descCorta;
	}

	public void setDescCorta(String descCorta) {
		this.descCorta = descCorta;
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

	public String getFlagAfectacion() {
		return this.flagAfectacion;
	}

	public void setFlagAfectacion(String flagAfectacion) {
		this.flagAfectacion = flagAfectacion;
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

//	public List<RaDjalcabala> getRaDjalcabalas() {
//		return this.raDjalcabalas;
//	}
//
//	public void setRaDjalcabalas(List<RaDjalcabala> raDjalcabalas) {
//		this.raDjalcabalas = raDjalcabalas;
//	}
	
//	public List<RaDjalcabalaHistorico> getRaDjalcabalaHistoricos() {
//		return this.raDjalcabalaHistoricos;
//	}
//
//	public void setRaDjalcabalaHistoricos(List<RaDjalcabalaHistorico> raDjalcabalaHistoricos) {
//		this.raDjalcabalaHistoricos = raDjalcabalaHistoricos;
//	}
	
}