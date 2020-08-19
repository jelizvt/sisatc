package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity 
@Table(name="cj_benef_bonocajam")
@NamedQuery(name="findCjBenefBonoById", query="SELECT a FROM CjBenefBono a WHERE a.persona_id=:p_persona_id AND a.estado=1 AND a.periodo=:p_periodo")
public class CjBenefBono implements Serializable{
	/// benef_bonocajam_id, persona_id, periodo, estado, usuario_id, terminal, fecha_registro
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(name="benef_bonocajam_id")	
	private int benef_bonocajam_id;
	
	@Column(name="persona_id")	
	private int persona_id;
	
	@Column(name="periodo")	
	private int periodo;

	@Column(name="estado")
	private String  estado;
	
	@Column(name="usuario_id")
	private int usuario_id;
	
	@Column(name="terminal")
	private String terminal;
	
	@Column(name="fecha_registro")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fecha_registro;
	

	public int getBenef_bonocajam_id() {
		return benef_bonocajam_id;
	}

	public int getPersona_id() {
		return persona_id;
	}

	public int getPeriodo() {
		return periodo;
	}

	public String getEstado() {
		return estado;
	}

	public int getUsuario_id() {
		return usuario_id;
	}

	public java.util.Date getFecha_registro() {
		return fecha_registro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setBenef_bonocajam_id(int benef_bonocajam_id) {
		this.benef_bonocajam_id = benef_bonocajam_id;
	}

	public void setPersona_id(int persona_id) {
		this.persona_id = persona_id;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setUsuario_id(int usuario_id) {
		this.usuario_id = usuario_id;
	}

	public void setFecha_registro(java.util.Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}	
}
