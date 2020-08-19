package com.sat.sisat.cobranzacoactiva.dto;

import java.io.Serializable;

import com.sat.sisat.common.dto.BaseDTO;

public class ListadoEstadoTransferencia extends BaseDTO implements Serializable {
	
	private Integer estadoTransferenciaId;
	private String descripcion;
	
	public Integer getEstadoTransferenciaId() {
		return estadoTransferenciaId;
	}
	public void setEstadoTransferenciaId(Integer estadoTransferenciaId) {
		this.estadoTransferenciaId = estadoTransferenciaId;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
