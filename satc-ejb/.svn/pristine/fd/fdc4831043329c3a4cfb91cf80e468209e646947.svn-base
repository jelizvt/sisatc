package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the cc_lote_firma database table.
 * 
 */
@Entity
@Table(name="cc_lote_firma")
public class CcLoteFirma implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CcLoteFirmaPK id;

	@Column(name="firmante_id")
	private int firmanteId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="nombre_archivo")
	private String nombreArchivo;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public CcLoteFirma() {
    }

	public CcLoteFirmaPK getId() {
		return this.id;
	}

	public void setId(CcLoteFirmaPK id) {
		this.id = id;
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

	public String getNombreArchivo() {
		return this.nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getFirmanteId() {
		return firmanteId;
	}

	public void setFirmanteId(int firmanteId) {
		this.firmanteId = firmanteId;
	}

}