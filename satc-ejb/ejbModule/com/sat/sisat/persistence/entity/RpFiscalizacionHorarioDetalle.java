package com.sat.sisat.persistence.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * The persistent class for the rp_fiscalizacion_programa database table.
 * 
 */
@Entity
@Table(name="rp_fiscalizacion_detalle_horario")

public class RpFiscalizacionHorarioDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(name="horario_detalle_id")	
	private int horarioDetalleId;
	
	@Column(name="horario_id")	
	private int horarioId;
	
	@Column(name="inspeccion_id")	
	private int inspeccionId;

	@Column(name="estado")
	private String  estado;
	
	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="fecha_registro")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fechaRegistro;
	
	@Column(name="terminal")
	private String terminal;	
	
	@Column(name="fecha_actualizacion")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fechaActualizacion;

	
    public RpFiscalizacionHorarioDetalle() {
    }


   

	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public int getUsuarioId() {
		return usuarioId;
	}


	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}


	public java.util.Date getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(java.util.Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	public String getTerminal() {
		return terminal;
	}


	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}


	public java.util.Date getFechaActualizacion() {
		return fechaActualizacion;
	}


	public void setFechaActualizacion(java.util.Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}




	public int getHorarioDetalleId() {
		return horarioDetalleId;
	}




	public void setHorarioDetalleId(int horarioDetalleId) {
		this.horarioDetalleId = horarioDetalleId;
	}




	public int getHorarioId() {
		return horarioId;
	}




	public void setHorarioId(int horarioId) {
		this.horarioId = horarioId;
	}




	public int getInspeccionId() {
		return inspeccionId;
	}




	public void setInspeccionId(int inspeccionId) {
		this.inspeccionId = inspeccionId;
	}




	



	

	

}