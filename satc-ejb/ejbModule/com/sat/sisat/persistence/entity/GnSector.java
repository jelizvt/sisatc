package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the gn_sector database table.
 * 
 */
@Entity
@Table(name="gn_sector")
public class GnSector implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="sector_id")
	private Integer sectorId;

	private String descripcion;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	@Column(name="usuario_id")
	private Integer usuarioId;
	
	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	public GnSector() {
    }
    
    public GnSector(Integer sectorId,String descripcion) {
    	this.sectorId=sectorId;
    	this.descripcion=descripcion;
    }

	public Integer getSectorId() {
		return this.sectorId;
	}

	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	@Override
	public String toString(){
		return this.descripcion;
	}

}