package com.sat.sisat.estadocuenta.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;

import com.sat.sisat.persistence.entity.GnSubconceptoPK;

public class CrSubConceptoDTO  implements Serializable{
	
	private int id;
	private String abreviacion;
	private String descripcion;
	private String descripcionCorta;
	private String estado;
	private Timestamp fechaRegistro;
	private String terminal;
	private Integer usuarioId;
	private boolean seleted;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAbreviacion() {
		return abreviacion;
	}
	public void setAbreviacion(String abreviacion) {
		this.abreviacion = abreviacion;
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
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	public boolean isSeleted() {
		return seleted;
	}
	public void setSeleted(boolean seleted) {
		this.seleted = seleted;
	}
	
	

}
