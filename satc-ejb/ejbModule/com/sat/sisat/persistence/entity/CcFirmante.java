package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the cc_firmantes database table.
 * 
 */
@Entity
@Table(name="cc_firmantes")
public class CcFirmante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="firmante_id")
	private int firmanteId;

	@Column(name="apellidos_nombres")
	private String apellidosNombres;

	private String cargo;

	private String estado;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String firma;

	@Column(name="responsable_id")
	private int responsableId;

	private String terminal;
	
	 public CcFirmante() {
    }

	public int getFirmanteId() {
		return this.firmanteId;
	}

	public void setFirmanteId(int firmanteId) {
		this.firmanteId = firmanteId;
	}

	public String getApellidosNombres() {
		return this.apellidosNombres;
	}

	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFirma() {
		return this.firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public int getResponsableId() {
		return this.responsableId;
	}

	public void setResponsableId(int responsableId) {
		this.responsableId = responsableId;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	@Override
	public String toString(){
		return apellidosNombres +" - "+ cargo;
	}
	
}