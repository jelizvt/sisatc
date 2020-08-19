package com.sat.sisat.obligacion.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class SubConceptoDTO implements Serializable {	

	private static final long serialVersionUID = -2616686220555292763L;

	private int conceptoId;
	@Id
	private int subconceptoId;
	
	private String descripcion;
	
	private String descripcionCorta;
	
	private BigDecimal porcentajeUit;
	
	private BigDecimal valor;
	
	private Integer periodo;		

	public int getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(int conceptoId) {
		this.conceptoId = conceptoId;
	}

	public int getSubconceptoId() {
		return subconceptoId;
	}

	public void setSubconceptoId(int subconceptoId) {
		this.subconceptoId = subconceptoId;
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

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getPorcentajeUit() {
		return porcentajeUit;
	}

	public void setPorcentajeUit(BigDecimal porcentajeUit) {
		this.porcentajeUit = porcentajeUit;
	}		

}
