package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the mp_tipo_docu_identidad database table.
 * 
 */
@Entity
@Table(name="mp_tipo_docu_identidad")
@NamedQuery(name="getAllMpTipoDocuIdentidad", query="SELECT a FROM MpTipoDocuIdentidad a WHERE a.estado='1' ORDER BY descripcion")
public class MpTipoDocuIdentidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_doc_identidad_id")
	private Integer tipoDocIdentidadId;

	private String descripcion;

	@Column(name="descrpcion_corta")
	private String descrpcionCorta;

	private String estado;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	private String formato;

	@Column(name="longitud_maxima")
	private Integer longitudMaxima;

	@Column(name="longitud_minimo")
	private Integer longitudMinimo;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

    public MpTipoDocuIdentidad() {
    }

	public Integer getTipoDocIdentidadId() {
		return this.tipoDocIdentidadId;
	}

	public void setTipoDocIdentidadId(Integer tipoDocIdentidadId) {
		this.tipoDocIdentidadId = tipoDocIdentidadId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescrpcionCorta() {
		return this.descrpcionCorta;
	}

	public void setDescrpcionCorta(String descrpcionCorta) {
		this.descrpcionCorta = descrpcionCorta;
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

	public String getFormato() {
		return this.formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public int getLongitudMaxima() {
		return this.longitudMaxima;
	}

	public void setLongitudMaxima(Integer longitudMaxima) {
		this.longitudMaxima = longitudMaxima;
	}

	public Integer getLongitudMinimo() {
		return this.longitudMinimo;
	}

	public void setLongitudMinimo(Integer longitudMinimo) {
		this.longitudMinimo = longitudMinimo;
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

}