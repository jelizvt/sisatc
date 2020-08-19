package com.sat.sisat.persistence.entity; 

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the pa_estado_resol database table.
 * 
 */
@Entity
@Table(name="pa_estado_resol")
public class PaEstadoResol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="estado_resol_id")
	private int estadoResolId;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	private String estado;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
	@Column(name="terminal")
	private String terminal;
	
    public int getEstadoResolId() {
		return estadoResolId;
	}

	public void setEstadoResolId(int estadoResolId) {
		this.estadoResolId = estadoResolId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
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

	public PaEstadoResol() {
    }

	

}