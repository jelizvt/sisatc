package com.sat.sisat.common.dto;

import java.io.Serializable;

public class GenericDTO extends BaseDTO implements Serializable{

	private static final long serialVersionUID = 8037914828486455472L;

	private int id;
	private String descripcion;
	private Boolean estado; 
	
	public GenericDTO(){
	}

	public GenericDTO(int id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}
