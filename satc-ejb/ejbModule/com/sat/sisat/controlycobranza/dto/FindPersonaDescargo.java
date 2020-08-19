package com.sat.sisat.controlycobranza.dto;
/**
 * @author Cristobal Ramires -=CRAMIREZ=-
 * @version 0.1
 * @since 28/02/2019 FindDetalleLoteDescargo
 */

import java.io.Serializable;

public class FindPersonaDescargo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	private String contribuyente;
	private java.sql.Timestamp fecha_registro;
	private String nombre_usuario;	
	private String descripcionEstado;	
	private Integer estado;
	private Integer usuario_id;
	private Integer dias;
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getContribuyente() {
		return contribuyente;
	}
	public void setContribuyente(String contribuyente) {
		this.contribuyente = contribuyente;
	}
	public java.sql.Timestamp getFecha_registro() {
		return fecha_registro;
	}
	public void setFecha_registro(java.sql.Timestamp fecha_registro) {
		this.fecha_registro = fecha_registro;
	}
	public String getNombre_usuario() {
		return nombre_usuario;
	}
	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}
	public String getDescripcionEstado() {
		return descripcionEstado;
	}
	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public Integer getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(Integer usuario_id) {
		this.usuario_id = usuario_id;
	}
	public Integer getDias() {
		return dias;
	}
	public void setDias(Integer dias) {
		this.dias = dias;
	}
	
	
}
