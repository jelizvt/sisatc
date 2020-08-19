/**
 * @author Liliana Aliaga
 * @version 1.0 
 * @since 21/02/2012
 * Entidad Registro Agencia 
 */
package com.sat.sisat.persistence.entity;

import java.io.Serializable;
//import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import javax.persistence.Table;

/**
 * The persistent class for the cj_agencia database table.
 * 
 */
@Entity
@Table(name="cj_agencia")
public class CjAgencia implements Serializable {
	private static final long serialVersionUID = 1L;
	private int agenciaId;
	private String estado;
	private Timestamp fechaRegistro;
	private String nombreAgencia;
	private String nombreCorto;
	private String terminal;
	private int usuarioId;

    public CjAgencia() {
    }


	@Id
	@Column(name="agencia_id")
	public int getAgenciaId() {
		return this.agenciaId;
	}

	public void setAgenciaId(int agenciaId) {
		this.agenciaId = agenciaId;
	}


	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}


	@Column(name="fecha_registro")
	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	@Column(name="nombre_agencia")
	public String getNombreAgencia() {
		return this.nombreAgencia;
	}

	public void setNombreAgencia(String nombreAgencia) {
		this.nombreAgencia = nombreAgencia;
	}


	@Column(name="nombre_corto")
	public String getNombreCorto() {
		return this.nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}


	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}


	@Column(name="usuario_id")
	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}