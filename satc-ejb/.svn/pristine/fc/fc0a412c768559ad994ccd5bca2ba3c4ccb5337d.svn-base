package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the sg_rol_modulo database table.
 * 
 */
@Entity
@Table(name="sg_rol_modulo")
public class SgRolModulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SgRolModuloPK id;

	private String accion;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="flag_menu")
	private String flagMenu;

	private String terminal;

	private int usuario;

    public SgRolModulo() {
    }

	public SgRolModuloPK getId() {
		return this.id;
	}

	public void setId(SgRolModuloPK id) {
		this.id = id;
	}
	
	public String getAccion() {
		return this.accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFlagMenu() {
		return this.flagMenu;
	}

	public void setFlagMenu(String flagMenu) {
		this.flagMenu = flagMenu;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getUsuario() {
		return this.usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

}