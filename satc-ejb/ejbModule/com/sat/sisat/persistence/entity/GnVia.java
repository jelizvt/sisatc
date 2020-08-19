package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the gn_via database table.
 * 
 */
@Entity
@Table(name="gn_via")
@NamedQuery(name="getAllGnViaByTipoViaId", query="SELECT a FROM GnVia a WHERE a.tipoViaId=:p_tipo_via_id AND a.estado='1'")
public class GnVia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="via_id")
	private Integer viaId;

	@Column(name="tipo_via_id")
	private Integer tipoViaId;
	
	@Column(name="dpto_id")
	private Integer dptoId;
	
	@Column(name="provincia_id")
	private Integer provinciaId;
	
	@Column(name="distrito_id")
	private Integer distritoId;
	
	private String descripcion;
	
	@Column(name="descripcion_alterna")
	private String descripcionAlterna;

	@Transient
	private String descripcionTipoVia;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

	public GnVia() {
    
	}
	
	public Integer getViaId() {
		return viaId;
	}

	public void setViaId(Integer viaId) {
		this.viaId = viaId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionTipoVia() {
		return descripcionTipoVia;
	}

	public void setDescripcionTipoVia(String descripcionTipoVia) {
		this.descripcionTipoVia = descripcionTipoVia;
	}

	public String getDescripcionAlterna() {
		return descripcionAlterna;
	}

	public void setDescripcionAlterna(String descripcionAlterna) {
		this.descripcionAlterna = descripcionAlterna;
	}

	public Integer getDistritoId() {
		return distritoId;
	}

	public void setDistritoId(Integer distritoId) {
		this.distritoId = distritoId;
	}

	public Integer getDptoId() {
		return dptoId;
	}

	public void setDptoId(Integer dptoId) {
		this.dptoId = dptoId;
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

	public Integer getProvinciaId() {
		return provinciaId;
	}

	public void setProvinciaId(Integer provinciaId) {
		this.provinciaId = provinciaId;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getTipoViaId() {
		return tipoViaId;
	}

	public void setTipoViaId(Integer tipoViaId) {
		this.tipoViaId = tipoViaId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

}