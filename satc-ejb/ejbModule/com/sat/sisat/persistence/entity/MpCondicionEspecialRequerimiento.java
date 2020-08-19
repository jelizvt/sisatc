package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mp_requerimiento_condicion_especial")
public class MpCondicionEspecialRequerimiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="requerimiento_ce_id")
	private int CondEspecialRequerimientoId;

	@Column(name="persona_id")
	private int personaId;
	
	@Column(name="tipo_asunto_id")
	private int tipoAsuntoId;
	
	@Column(name="asunto_id")
	private int asuntoId;
	
	@Column(name="nro_expediente")
	private String nroExpediente;
	
	@Column(name="nro_requerimiento")
	private String nroRequerimiento;
	
	@Column(name="fecha_inicio_recepcion")
	private Timestamp fechaRecepcion;
	
	@Column(name="fecha_fin_recepcion")
	private Timestamp fechaFin;
	
	@Column(name="estado_resolucion_id")
	private int estadoResolucionId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

	public int getCondEspecialRequerimientoId() {
		return CondEspecialRequerimientoId;
	}

	public void setCondEspecialRequerimientoId(int condEspecialRequerimientoId) {
		CondEspecialRequerimientoId = condEspecialRequerimientoId;
	}

	public int getPersonaId() {
		return personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public int getTipoAsuntoId() {
		return tipoAsuntoId;
	}

	public void setTipoAsuntoId(int tipoAsuntoId) {
		this.tipoAsuntoId = tipoAsuntoId;
	}

	public int getAsuntoId() {
		return asuntoId;
	}

	public void setAsuntoId(int asuntoId) {
		this.asuntoId = asuntoId;
	}

	public String getNroExpediente() {
		return nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}

	public String getNroRequerimiento() {
		return nroRequerimiento;
	}

	public void setNroRequerimiento(String nroRequerimiento) {
		this.nroRequerimiento = nroRequerimiento;
	}

	public Timestamp getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Timestamp fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public Timestamp getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getEstadoResolucionId() {
		return estadoResolucionId;
	}

	public void setEstadoResolucionId(int estadoResolucionId) {
		this.estadoResolucionId = estadoResolucionId;
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

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}
}
