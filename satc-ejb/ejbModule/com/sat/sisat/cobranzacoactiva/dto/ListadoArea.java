package com.sat.sisat.cobranzacoactiva.dto;

import java.io.Serializable;

import com.sat.sisat.common.dto.BaseDTO;

public class ListadoArea extends BaseDTO  implements Serializable {
	
	private Integer unidadId;
	private String descripcion;
	
	public Integer getUnidadId() {
		return unidadId;
	}
	public void setUnidadId(Integer unidadId) {
		this.unidadId = unidadId;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
