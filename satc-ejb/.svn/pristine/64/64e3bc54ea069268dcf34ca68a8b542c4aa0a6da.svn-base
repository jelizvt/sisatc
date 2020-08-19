package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the cc_firmantes_tipo_acto database table.
 * 
 */
@Entity
@Table(name="cc_firmantes_tipo_acto")
public class CcFirmantesTipoActo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="firmantes_tipo_acto_id")
	private int firmantesTipoActoId;

	private int defecto;

	private String estado;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;
	
	@Column(name="concepto_id")
	private int ConceptoId;

	public CcFirmantesTipoActo() {
    }

	public int getFirmantesTipoActoId() {
		return this.firmantesTipoActoId;
	}

	public void setFirmantesTipoActoId(int firmantesTipoActoId) {
		this.firmantesTipoActoId = firmantesTipoActoId;
	}

	public int getDefecto() {
		return this.defecto;
	}

	public void setDefecto(int defecto) {
		this.defecto = defecto;
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

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getConceptoId() {
		return ConceptoId;
	}

	public void setConceptoId(int conceptoId) {
		ConceptoId = conceptoId;
	}

	
	
}