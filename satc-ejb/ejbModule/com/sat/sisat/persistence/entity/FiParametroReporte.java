package com.sat.sisat.persistence.entity;

public class FiParametroReporte {

	 private int parametroId;
     private String parametroDescripcion;
     
     public FiParametroReporte(int id, String desc){
    	         this.parametroId = id;
    	         this.parametroDescripcion = desc;
     }
  
	public int getParametroId() {
		return parametroId;
	}
	public void setParametroId(int parametroId) {
		this.parametroId = parametroId;
	}
	public String getParametroDescripcion() {
		return parametroDescripcion;
	}
	public void setParametroDescripcion(String parametroDescripcion) {
		this.parametroDescripcion = parametroDescripcion;
	}
}
