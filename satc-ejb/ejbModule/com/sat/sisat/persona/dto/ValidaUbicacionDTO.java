package com.sat.sisat.persona.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ValidaUbicacionDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private Integer persona_id;
	private Integer estado;
	private Integer is_ubicable_control;
	private Integer usuario_registro_id;
	private String terminal_registro;
	private java.sql.Timestamp fecha_registro;
	private String nombre_usuario_registro;
	private Integer usuario_valida_id;
	private String terminal_valida;
	private java.sql.Timestamp fecha_valida;
	private String nombre_usuario_valido;
	private String apellidos_nombres;
	private String glosa;
	private Integer dias;
	
	public Integer getPersona_id() {
		return persona_id;
	}
	public void setPersona_id(Integer persona_id) {
		this.persona_id = persona_id;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public Integer getIs_ubicable_control() {
		return is_ubicable_control;
	}
	public void setIs_ubicable_control(Integer is_ubicable_control) {
		this.is_ubicable_control = is_ubicable_control;
	}
	public Integer getUsuario_registro_id() {
		return usuario_registro_id;
	}
	public void setUsuario_registro_id(Integer usuario_registro_id) {
		this.usuario_registro_id = usuario_registro_id;
	}
	public String getTerminal_registro() {
		return terminal_registro;
	}
	public void setTerminal_registro(String terminal_registro) {
		this.terminal_registro = terminal_registro;
	}
	public java.sql.Timestamp getFecha_registro() {
		return fecha_registro;
	}
	public void setFecha_registro(java.sql.Timestamp fecha_registro) {
		this.fecha_registro = fecha_registro;
	}
	public String getNombre_usuario_registro() {
		return nombre_usuario_registro;
	}
	public void setNombre_usuario_registro(String nombre_usuario_registro) {
		this.nombre_usuario_registro = nombre_usuario_registro;
	}
	public Integer getUsuario_valida_id() {
		return usuario_valida_id;
	}
	public void setUsuario_valida_id(Integer usuario_valida_id) {
		this.usuario_valida_id = usuario_valida_id;
	}
	public String getTerminal_valida() {
		return terminal_valida;
	}
	public void setTerminal_valida(String terminal_valida) {
		this.terminal_valida = terminal_valida;
	}
	public java.sql.Timestamp getFecha_valida() {
		return fecha_valida;
	}
	public void setFecha_valida(java.sql.Timestamp fecha_valida) {
		this.fecha_valida = fecha_valida;
	}
	public String getNombre_usuario_valido() {
		return nombre_usuario_valido;
	}
	public void setNombre_usuario_valido(String nombre_usuario_valido) {
		this.nombre_usuario_valido = nombre_usuario_valido;
	}
	public String getApellidos_nombres() {
		return apellidos_nombres;
	}
	public void setApellidos_nombres(String apellidos_nombres) {
		this.apellidos_nombres = apellidos_nombres;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getDias() {
		return dias;
	}
	public void setDias(Integer dias) {
		this.dias = dias;
	}
	
	
	
	
}
