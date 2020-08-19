package com.sat.sisat.predial.dto;

import java.io.Serializable;


public class AfectacionDTO implements Serializable {
	
	public String usoPredio;
	public String tipoAfectacion;
	public Double valorAfectacion;

	public AfectacionDTO(){
		this.setUsoPredio("0");
		this.setTipoAfectacion("0");
		this.setValorAfectacion(Double.valueOf(0));
	}
	
	public String getTipoAfectacion() {
		return tipoAfectacion;
	}
	public void setTipoAfectacion(String tipoAfectacion) {
		this.tipoAfectacion = tipoAfectacion;
	}
	public Double getValorAfectacion() {
		return valorAfectacion;
	}
	public void setValorAfectacion(Double valorAfectacion) {
		this.valorAfectacion = valorAfectacion;
	}
	public String getUsoPredio() {
		return usoPredio;
	}
	public void setUsoPredio(String usoPredio) {
		this.usoPredio = usoPredio;
	}
}
