/**
 * @author Liliana Aliaga
 * @version 1.0 
 * @since 21/02/2012
 * Entidad Registro de Bancos 
 */
package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * The persistent class for the cj_banco database table.
 * 
 */
@Entity
@Table(name="cj_banco")
public class CjBanco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="banco_id")
	private int bancoId;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="nombre_banco")
	private String nombreBanco;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public CjBanco() {
    }

	public int getBancoId() {
		return this.bancoId;
	}

	public void setBancoId(int bancoId) {
		this.bancoId = bancoId;
	}

	public String getDescripcionCorta() {
		return this.descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaRegistro() {
		return (Timestamp) this.fechaRegistro;// 	return  this.fechaRegistro; estaba asi
	}


	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNombreBanco() {
		return this.nombreBanco;
	}

	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}