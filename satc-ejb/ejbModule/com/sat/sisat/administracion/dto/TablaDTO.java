package com.sat.sisat.administracion.dto;

import java.io.Serializable;

/**
 * @author Miguel Macias 
 * @version 0.1
 * @since 31/07/2012
 * 
 *  Contenedor usado para la obtencion y actualizacion de data en la tabla mt_tabla
 *  
 */
public class TablaDTO implements Serializable{

	private static final long serialVersionUID = -6007389033979386990L;
	
	
	private int codModulo;
	private int codTabla;
	private int codTipoTabla;
	private String descripcion;
	private String descripcionCorta;

	public int getCodModulo() {
		return codModulo;
	}

	public void setCodModulo(int codModulo) {
		this.codModulo = codModulo;
	}

	public int getCodTabla() {
		return codTabla;
	}

	public void setCodTabla(int codTabla) {
		this.codTabla = codTabla;
	}

	public int getCodTipoTabla() {
		return codTipoTabla;
	}

	public void setCodTipoTabla(int codTipoTabla) {
		this.codTipoTabla = codTipoTabla;
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
}
