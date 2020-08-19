package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the gn_sector database table.
 * 
 */
@Entity
@Table(name="gn_sector_lugar")
public class GnSectorLugar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="sector_lugar_id")
	private Integer sectorLugarId;

	@Column(name="sector_id")
	private Integer sectorId;
	
	@Column(name="lugar_id")
	private Integer lugarId;

	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="estado")
	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="terminal")
	private String terminal;
	
	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;


	public GnSectorLugar() {
    }
    
    
	public Integer getSectorId() {
		return this.sectorId;
	}

	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
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
	
	public Integer getSectorLugarId() {
		return sectorLugarId;
	}

	public void setSectorLugarId(Integer sectorLugarId) {
		this.sectorLugarId = sectorLugarId;
	}

	public Integer getLugarId() {
		return lugarId;
	}

	public void setLugarId(Integer lugarId) {
		this.lugarId = lugarId;
	}


	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}


	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

}