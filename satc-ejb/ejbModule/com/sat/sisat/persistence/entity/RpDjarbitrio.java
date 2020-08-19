package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the rp_djarbitrios database table.
 * 
 */
@Entity
@Table(name="rp_djarbitrios")
@NamedQuery(name="getRpDjarbitrioByDjId", query="SELECT m FROM RpDjarbitrio m WHERE m.estado='1' AND m.djId=:p_dj_id ")
public class RpDjarbitrio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="djarbitrio_id")
	private int djarbitrioId;

	@Column(name="dj_id")
	private int djId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public RpDjarbitrio() {
    	
    }

	public int getDjarbitrioId() {
		return this.djarbitrioId;
	}

	public void setDjarbitrioId(int djarbitrioId) {
		this.djarbitrioId = djarbitrioId;
	}

	public int getDjId() {
		return this.djId;
	}

	public void setDjId(int djId) {
		this.djId = djId;
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