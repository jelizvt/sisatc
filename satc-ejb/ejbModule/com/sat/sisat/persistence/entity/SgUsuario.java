package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.sat.sisat.exception.SisatException;

import java.sql.Timestamp;


/**
 * The persistent class for the sg_usuario database table.
 * 
 */
@Entity
@Table(name="sg_usuario")
public class SgUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="usuario_id")
	private int usuarioId;

	private String apellidos;

	private String clave;

	private String estado;
	
	
	//Datos para acceder a cosultas RENIEC.
	private String dniUsuario;
	public String getDniUsuario() {
		return dniUsuario;
	}

	public void setDniUsuario(String dniUsuario) {
		this.dniUsuario = dniUsuario;
	}

	public String getClaveREniec() {
		return claveREniec;
	}

	public void setClaveREniec(String claveREniec) {
		this.claveREniec = claveREniec;
	}

	private String claveREniec;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="nombre_usuario")
	private String nombreUsuario;

	private String nombres;

	private String registro;

	private String terminal;

	@Column(name="usuario_actualizacion")
	private int usuarioActualizacion;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
    public SgUsuario() {
    }

	public int getUsuarioId() throws SisatException {
		
		if(this.usuarioId == 0){
			throw new SisatException("Error en la recuperacion de usuario de session");
		}
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
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

	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getRegistro() {
		return this.registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getUsuarioActualizacion() {
		return this.usuarioActualizacion;
	}

	public void setUsuarioActualizacion(int usuarioActualizacion) {
		this.usuarioActualizacion = usuarioActualizacion;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
}