package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "re_estado_resolucion")
@NamedQuery(name = "findEstadosResolucion", query = "SELECT a FROM ReEstadoResolucion a")
public class ReEstadoResolucion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "estado_resolucion_id")
	private Integer estadoResolucionId;
	
	private String descripcion;
	
	@Column(name = "usuario_id")
	private Integer usuarioId;
	
	private String estado;
	
	private String terminal;
	
	@Column(name = "fecha_registro")
	private Timestamp fechaRegistro;

	
	public Integer getEstadoResolucionId() {
		return estadoResolucionId;
	}

	public void setEstadoResolucionId(Integer estadoResolucionId) {
		this.estadoResolucionId = estadoResolucionId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}
