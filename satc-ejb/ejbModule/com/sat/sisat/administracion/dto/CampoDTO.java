package com.sat.sisat.administracion.dto;

import java.io.Serializable;

/**
 * @author Miguel Macias 
 * @version 0.1
 * @since 24/08/2012
 * Contenedor usado para administrar los datos que seran ingresados a la tabla mt_campo
 *
 */
public class CampoDTO implements Serializable {

	private static final long serialVersionUID = 4568924528113428703L;
	
	private int moduloId;
	
	private int tablaId;
	
	private int tipoCampoId;
	
	private int tipoControlId;
	
	private int tipoDatoId;
	
	private int campoId;
	
	private String nombreCampo;
	
	private String descripcionCorta;
	 
	private String descripcion;
	
	private String alias;
	
	private int tamano;
	
	private int orden;
	
	private String nombreTablaPadre;
	
	private String mascara;
	
	private String filtroDatosPadre;
	
	private boolean flagMostrarControl;
	
	private boolean flagMostrarGrid;
	
	private char estado;
	
	private String valorCampoUpdate;

	public int getModuloId() {
		return moduloId;
	}

	public void setModuloId(int moduloId) {
		this.moduloId = moduloId;
	}

	public int getTablaId() {
		return tablaId;
	}

	public void setTablaId(int tablaId) {
		this.tablaId = tablaId;
	}

	public int getTipoCampoId() {
		return tipoCampoId;
	}

	public void setTipoCampoId(int tipoCampoId) {
		this.tipoCampoId = tipoCampoId;
	}

	public int getTipoControlId() {
		return tipoControlId;
	}

	public void setTipoControlId(int tipoControlId) {
		this.tipoControlId = tipoControlId;
	}

	public int getCampoId() {
		return campoId;
	}

	public void setCampoId(int campoId) {
		this.campoId = campoId;
	}

	public String getNombreCampo() {
		return nombreCampo;
	}

	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getTamano() {
		return tamano;
	}

	public void setTamano(int tamano) {
		this.tamano = tamano;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getNombreTablaPadre() {
		return nombreTablaPadre;
	}

	public void setNombreTablaPadre(String nombreTablaPadre) {
		this.nombreTablaPadre = nombreTablaPadre;
	}

	public boolean isFlagMostrarControl() {
		return flagMostrarControl;
	}

	public void setFlagMostrarControl(boolean flagMostrarControl) {
		this.flagMostrarControl = flagMostrarControl;
	}

	public boolean isFlagMostrarGrid() {
		return flagMostrarGrid;
	}

	public void setFlagMostrarGrid(boolean flagMostrarGrid) {
		this.flagMostrarGrid = flagMostrarGrid;
	}

	public char getEstado() {
		return estado;
	}

	public void setEstado(char estado) {
		this.estado = estado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getValorCampoUpdate() {
		return valorCampoUpdate;
	}

	public void setValorCampoUpdate(String valorCampoUpdate) {
		this.valorCampoUpdate = valorCampoUpdate;
	}

	public String getMascara() {
		return mascara;
	}

	public void setMascara(String mascara) {
		this.mascara = mascara;
	}

	public int getTipoDatoId() {
		return tipoDatoId;
	}

	public void setTipoDatoId(int tipoDatoId) {
		this.tipoDatoId = tipoDatoId;
	}

	public String getFiltroDatosPadre() {
		return filtroDatosPadre;
	}

	public void setFiltroDatosPadre(String filtroDatosPadre) {
		this.filtroDatosPadre = filtroDatosPadre;
	}

	
}
