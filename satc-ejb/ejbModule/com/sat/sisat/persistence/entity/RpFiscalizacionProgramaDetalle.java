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
@Table(name="rp_fiscalizacion_detalle_programa")

public class RpFiscalizacionProgramaDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(name="programa_detalle_id")	
	private int programaDetalleId;
	
	@Column(name="programa_id")	
	private int programaId;
	
	@Column(name="anio_fiscalizacion")	
	private String anioFiscalizacion;

	@Column(name="estado")
	private String  estado;
	
	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="fecha_registro")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fechaRegistro;
	
	@Column(name="terminal")
	private String termninal;	
	
	@Column(name="fecha_actualizacion")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fechaActualizacion;

	
    public RpFiscalizacionProgramaDetalle() {
    }

	public int getProgramaDetalleId() {
		return programaDetalleId;
	}


	public void setProgramaDetalleId(int programaDetalleId) {
		this.programaDetalleId = programaDetalleId;
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


	public String getTermninal() {
		return termninal;
	}


	public void setTermninal(String termninal) {
		this.termninal = termninal;
	}


	public java.util.Date getFechaActualizacion() {
		return fechaActualizacion;
	}


	public void setFechaActualizacion(java.util.Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}




	public String getAnioFiscalizacion() {
		return anioFiscalizacion;
	}




	public void setAnioFiscalizacion(String anioFiscalizacion) {
		this.anioFiscalizacion = anioFiscalizacion;
	}

	public int getProgramaId() {
		return programaId;
	}

	public void setProgramaId(int programaId) {
		this.programaId = programaId;
	}



}