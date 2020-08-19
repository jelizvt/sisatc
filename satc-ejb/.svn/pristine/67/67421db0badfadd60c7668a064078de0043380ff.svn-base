package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the no_notificacion database table.
 * 
 */
@Entity
@Table(name="cc_reclamo")
public class CcReclamo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="reclamo_id")
	private Integer reclamoId;

	@Column(name="lote_id")
	private Integer loteId;
	
	@Column(name="persona_id")
	private Integer personaId;
	
	@Column(name="acto_id")
	private Integer actoId;

	@Column(name="estado_reclamo_id")
	private Integer estadoReclamoId;
	
	@Column(name="nro_documento")
	private String nroDocumento;
	
	@Column(name="fecha_reclamo")
	private Timestamp fechaReclamo;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	public Integer getReclamoId() {
		return reclamoId;
	}

	public void setReclamoId(Integer reclamoId) {
		this.reclamoId = reclamoId;
	}

	public Integer getLoteId() {
		return loteId;
	}

	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}

	public Integer getActoId() {
		return actoId;
	}

	public void setActoId(Integer actoId) {
		this.actoId = actoId;
	}

	public Integer getEstadoReclamoId() {
		return estadoReclamoId;
	}

	public void setEstadoReclamoId(Integer estadoReclamoId) {
		this.estadoReclamoId = estadoReclamoId;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public Timestamp getFechaReclamo() {
		return fechaReclamo;
	}

	public void setFechaReclamo(Timestamp fechaReclamo) {
		this.fechaReclamo = fechaReclamo;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
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
	
	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	
}