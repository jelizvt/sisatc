package com.sat.sisat.common.vo;

import java.io.Serializable;
import java.util.HashMap;

public class EstadoTipoTierra implements Serializable {
	private Integer tipoTierra;
	private HashMap<Integer,Boolean> categoria;
	private HashMap<Integer,Boolean> altitud;
	
	public EstadoTipoTierra(Integer[] aCategoria,Integer[] aAltitud){
		categoria=new HashMap<Integer,Boolean>();
		
		for(int i=0;i<aCategoria.length;i++){
			categoria.put(aCategoria[i],Boolean.TRUE);
		}
		
		altitud=new HashMap<Integer,Boolean>();
		for(int i=0;i<aAltitud.length;i++){
			altitud.put(aAltitud[i],Boolean.TRUE);
		}
	}
	
	public Integer getTipoTierra() {
		return tipoTierra;
	}
	public void setTipoTierra(Integer tipoTierra) {
		this.tipoTierra = tipoTierra;
	}
	public HashMap<Integer, Boolean> getCategoria() {
		return categoria;
	}
	public void setCategoria(HashMap<Integer, Boolean> categoria) {
		this.categoria = categoria;
	}
	public HashMap<Integer, Boolean> getAltitud() {
		return altitud;
	}
	public void setAltitud(HashMap<Integer, Boolean> altitud) {
		this.altitud = altitud;
	}
	
}
