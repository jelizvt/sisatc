package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the dt_zona_seguridad_ubicacion database table.
 * 
 */
@Entity
@Table(name="dt_zona_seguridad_ubicacion")
@NamedQuery(name="getDtZonaSeguridadByUbicacionId", query="SELECT m FROM DtZonaSeguridadUbicacion m WHERE m.estado='1' AND m.ubicacionId=:p_ubicacion_id AND m.periodo=:p_periodo")
public class DtZonaSeguridadUbicacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="zona_seguridad_ubica_id")
	private Integer zonaSeguridadUbicaId;
	
	@Column(name="ubicacion_id")
	private Integer ubicacionId;

	@Column(name="zona_seguridad_id")
	private Integer zonaSeguridadId;

	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="estado")
	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="terminal")
	private String terminal;

	@Column(name="periodo")
	private Integer periodo;
	

    public DtZonaSeguridadUbicacion() {
    }

	public Integer getZonaSeguridadUbicaId() {
		return this.zonaSeguridadUbicaId;
	}

	public void setZonaSeguridadUbicaId(Integer zonaSeguridadUbicaId) {
		this.zonaSeguridadUbicaId = zonaSeguridadUbicaId;
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

	public Integer getUbicacionId() {
		return this.ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getZonaSeguridadId() {
		return this.zonaSeguridadId;
	}

	public void setZonaSeguridadId(Integer zonaSeguridadId) {
		this.zonaSeguridadId = zonaSeguridadId;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
}