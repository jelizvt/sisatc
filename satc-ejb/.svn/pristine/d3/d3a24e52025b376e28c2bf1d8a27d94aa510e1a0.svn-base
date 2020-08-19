package com.sat.sisat.administracion.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Miguel Macias 
 * @version 0.1
 * @since 31/07/2012
 * 
 * Contenedor usado para representar una fila en la grilla que sera mostrada en la interfaz, como la grilla varia
 * en dimenciones m x n se vio por conveniente que en su estructura interna se maneje con una lista de string donde 
 * cada registro de esta lista es una columna de una fila, adicionalmente se tiene un campo reservado para la llave primaria
 * para poder asi ubicar sobre que campo se hace una operacion CRUD
 *  
 */
public class RowLstDataDTO implements Serializable{
	
	private static final long serialVersionUID = 8042652295404296468L;
	
	
	private List<String> rowData;
	
	private int key;

	private List<Integer> listPFK = new ArrayList<Integer>();
	
	public List<String> getRowData() {
		return rowData;
	}

	public void setRowData(List<String> rowData) {
		this.rowData = rowData;
	}

	public List<Integer> getListPFK() {
		return listPFK;
	}

	public void setListPFK(List<Integer> listPFK) {
		this.listPFK = listPFK;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
	
}
