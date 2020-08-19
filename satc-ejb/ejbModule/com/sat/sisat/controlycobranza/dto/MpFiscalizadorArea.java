package com.sat.sisat.controlycobranza.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="gn_unidad")
public class MpFiscalizadorArea implements Serializable{

	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(name="unidad_id")	
	private int unidad_id;
	
	

	@Column(name="descripcion")	
	private String descripcion;

	
	public int getUnidad_id() {
		return unidad_id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setUnidad_id(int unidad_id) {
		this.unidad_id = unidad_id;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
