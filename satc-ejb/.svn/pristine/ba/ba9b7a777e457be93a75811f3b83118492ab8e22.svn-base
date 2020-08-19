package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="gn_uit")
public class GnUit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5546434802438190129L;

	@Id
	@Column(name = "uit_id")
	private Integer uitId;

	@Column(name = "anno_uit")
	private Integer annoUit;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_inicio")
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_fin")
	private Date fechaFin;

	@Column(name = "uit")
	private BigDecimal uit;

	@Column(name = "usuario_id")
	private Integer usuarioId;

	@Column(name = "estado")
	private String estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro")
	private Date fechaRegistro;

	@Column(name = "terminal")
	private String terminal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_actualizacion")
	private Date fechaActualizacion;

	public Integer getUitId() {
		return uitId;
	}

	public void setUitId(Integer uitId) {
		this.uitId = uitId;
	}

	public Integer getAnnoUit() {
		return annoUit;
	}

	public void setAnnoUit(Integer annoUit) {
		this.annoUit = annoUit;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public BigDecimal getUit() {
		return uit;
	}

	public void setUit(BigDecimal uit) {
		this.uit = uit;
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

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

}
