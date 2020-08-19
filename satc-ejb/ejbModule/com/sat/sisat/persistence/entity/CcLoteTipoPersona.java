package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the cc_lote_tipo_persona database table.
 * 
 */
@Entity
@Table(name="cc_lote_tipo_persona")
public class CcLoteTipoPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CcLoteTipoPersonaPK id;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public CcLoteTipoPersona() {
    }

	public CcLoteTipoPersonaPK getId() {
		return this.id;
	}

	public void setId(CcLoteTipoPersonaPK id) {
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

}