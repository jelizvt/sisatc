/**
 * @author Liliana Aliaga
 * @version 1.0 
 * @since 21/02/2012
 * Entidad Registro de Apertura 
 */

package com.sat.sisat.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * The persistent class for the cj_caja_apertura database table.
 * 
 */
@Entity
@Table(name="cj_caja_apertura")
public class CjCajaApertura implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="apertura_id")
	private int aperturaId;
	
	@Id
	@Column(name="agencia_id")
	private int agenciaId;
	
	@Id
	@Column(name="agencia_operacion_id")
	private int agenciaOperacionId;

	@Column(name="fecha_apertura")
	private Timestamp fechaApertura;

	@Column(name="fecha_cierre")
	private Timestamp fechaCierre;

	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="terminal")
	private String terminal;

    public CjCajaApertura() {
    }

	public int getAperturaId() {
		return aperturaId;
	}

	public void setAperturaId(int aperturaId) {
		this.aperturaId = aperturaId;
	}

	public int getAgenciaId() {
		return agenciaId;
	}

	public void setAgenciaId(int agenciaId) {
		this.agenciaId = agenciaId;
	}

	public int getAgenciaOperacionId() {
		return agenciaOperacionId;
	}

	public void setAgenciaOperacionId(int agenciaOperacionId) {
		this.agenciaOperacionId = agenciaOperacionId;
	}

	public Timestamp getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Timestamp fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Timestamp getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Timestamp fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
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