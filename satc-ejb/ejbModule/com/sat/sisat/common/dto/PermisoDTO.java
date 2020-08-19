package com.sat.sisat.common.dto;

import java.io.Serializable;

public class PermisoDTO implements Serializable {

	private static final long serialVersionUID = -5520497778534206104L;

	private int moduloId;
	private boolean nuevo;
	private boolean modificacion;
	private boolean consulta;
	private boolean cambioEstado;
	private boolean flagMenu;

	public PermisoDTO() {
	}

	public int getModuloId() {
		return moduloId;
	}

	public void setModuloId(int moduloId) {
		this.moduloId = moduloId;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public boolean isModificacion() {
		return modificacion;
	}

	public void setModificacion(boolean modificacion) {
		this.modificacion = modificacion;
	}

	public boolean isConsulta() {
		return consulta;
	}

	public void setConsulta(boolean consulta) {
		this.consulta = consulta;
	}

	public boolean isCambioEstado() {
		return cambioEstado;
	}

	public void setCambioEstado(boolean cambioEstado) {
		this.cambioEstado = cambioEstado;
	}

	public boolean isFlagMenu() {
		return flagMenu;
	}

	public void setFlagMenu(boolean flagMenu) {
		this.flagMenu = flagMenu;
	}
}