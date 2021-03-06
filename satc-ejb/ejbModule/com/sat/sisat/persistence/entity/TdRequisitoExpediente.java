package com.sat.sisat.persistence.entity;

// Generated 20/11/2012 10:29:09 AM by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * TdRequisitoExpediente generated by hbm2java
 */
@Entity
@Table(name = "td_requisito_expediente")
public class TdRequisitoExpediente implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8131859562792253268L;

	@Id
	@Column(name = "requisito_expediente_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer requisitoExpedienteId;

	@Column(name = "requisito_id")
	private Integer requisitoId;

	@Column(name = "expediente_id")
	private Integer expedienteId;

	@Column(name = "flag_presentado")
	private Boolean flagPresentado = Boolean.FALSE;

	@Column(name = "flag_subsanado")
	private String flagSubsanado;

	@Column(name = "glosa")
	private String glosa;
	
	@Column(name = "nro_dias_plazo")
	private Integer nroDiasPlazo;

	@Column(name = "usuario_id", nullable = false)
	@NotNull
	private int usuarioId;

	@Column(name = "estado", nullable = false)
	@NotNull
	private String estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro")
	@NotNull
	private Date fechaRegistro;

	@Column(name = "terminal", nullable = false)
	@NotNull
	private String terminal;
	
	@Column(name = "dj_id")
	@Null
	private Integer djId;

	@Transient
	private String descripcionRequisito;
	
	
	
	

	public Integer getDjId() {
		return djId;
	}

	public void setDjId(Integer djId) {
		this.djId = djId;
	}

	public Integer getRequisitoExpedienteId() {
		return requisitoExpedienteId;
	}

	public void setRequisitoExpedienteId(Integer requisitoExpedienteId) {
		this.requisitoExpedienteId = requisitoExpedienteId;
	}

	public Integer getRequisitoId() {
		return requisitoId;
	}

	public void setRequisitoId(Integer requisitoId) {
		this.requisitoId = requisitoId;
	}

	public Integer getExpedienteId() {
		return expedienteId;
	}

	public void setExpedienteId(Integer expedienteId) {
		this.expedienteId = expedienteId;
	}

	public Boolean getFlagPresentado() {
		return flagPresentado;
	}

	public void setFlagPresentado(Boolean flagPresentado) {
		this.flagPresentado = flagPresentado;
	}

	public String getFlagSubsanado() {
		return flagSubsanado;
	}

	public void setFlagSubsanado(String flagSubsanado) {
		this.flagSubsanado = flagSubsanado;
	}

	public Integer getNroDiasPlazo() {
		return nroDiasPlazo;
	}

	public void setNroDiasPlazo(Integer nroDiasPlazo) {
		this.nroDiasPlazo = nroDiasPlazo;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getDescripcionRequisito() {
		return descripcionRequisito;
	}

	public void setDescripcionRequisito(String descripcionRequisito) {
		this.descripcionRequisito = descripcionRequisito;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
}
