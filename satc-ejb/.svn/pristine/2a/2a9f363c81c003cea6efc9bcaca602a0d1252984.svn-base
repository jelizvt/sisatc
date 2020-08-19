package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mp_sustento_condicion_especial")
public class MpSustentoCondicionEspecial  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="sustento_ce_id")
	private Integer sustentoId;
	
	@Column(name="persona_id")
	private int personaId;
	
	@Column(name="condicion_especial_requisito_id")
	private int requisitoId;
	
	@Column(name="condicion_especial_id")
	private int condicionEspecialId;
	
	@Column(name="descripcion")
	private String descripcion;

	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="estado")
	private Integer estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="terminal")
	private String terminal;

	public Integer getSustentoId() {
		return sustentoId;
	}

	public void setSustentoId(Integer sustentoId) {
		this.sustentoId = sustentoId;
	}

	public int getPersonaId() {
		return personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public int getRequisitoId() {
		return requisitoId;
	}

	public void setRequisitoId(int requisitoId) {
		this.requisitoId = requisitoId;
	}

	public int getCondicionEspecialId() {
		return condicionEspecialId;
	}

	public void setCondicionEspecialId(int condicionEspecialId) {
		this.condicionEspecialId = condicionEspecialId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
}
