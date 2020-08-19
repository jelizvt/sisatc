package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rv_fiscalizacion_notificacion")
public class RvFiscalizacionNotificacion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="notificacion_id")
	private int notificacionId;
	
	@Column(name="requerimiento_id")
	private int requerimientoId;
	
	@Column(name="motivo_notificacion_id")
	private int motivoNotificacionId;
	
	@Column(name="notificador_id")
	private int notificadorId;
	
	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="nro_cargo")
	private String nroCargo;
	
	@Column(name="fecha_notificacion")
	private Timestamp fechaNotificacion;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	public RvFiscalizacionNotificacion(){
		
	}
	public int getNotificacionId() {
		return notificacionId;
	}

	public void setNotificacionId(int notificacionId) {
		this.notificacionId = notificacionId;
	}

	public int getRequerimientoId() {
		return requerimientoId;
	}

	public void setRequerimientoId(int requerimientoId) {
		this.requerimientoId = requerimientoId;
	}

	public int getMotivoNotificacionId() {
		return motivoNotificacionId;
	}

	public void setMotivoNotificacionId(int motivoNotificacionId) {
		this.motivoNotificacionId = motivoNotificacionId;
	}

	public int getNotificadorId() {
		return notificadorId;
	}

	public void setNotificadorId(int notificadorId) {
		this.notificadorId = notificadorId;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getNroCargo() {
		return nroCargo;
	}

	public void setNroCargo(String nroCargo) {
		this.nroCargo = nroCargo;
	}

	public Timestamp getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Timestamp fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	@Column(name="estado")
	private String estado;
	
	@Column(name="terminal")
	private String terminal;
	
	@Override
	public String toString() {
		return "RvFiscalizacionNotificacion [notificacionId=" + notificacionId
				+ ", requerimientoId=" + requerimientoId
				+ ", motivoNotificacionId=" + motivoNotificacionId
				+ ", notificadorId=" + notificadorId + ", usuarioId="
				+ usuarioId + ", nroCargo=" + nroCargo + ", fechaNotificacion="
				+ fechaNotificacion + ", fechaRegistro=" + fechaRegistro
				+ ", estado=" + estado + ", terminal=" + terminal + "]";
	}	

}
