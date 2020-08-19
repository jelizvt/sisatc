package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the cc_lote_cuota database table.
 * 
 */
@Entity
@Table(name="cc_lote_cuota")
public class CcLoteCuota implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CcLoteCuotaPK id;

	@Column(name="anno_cuota")
	private int annoCuota;

	private int cuota;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public CcLoteCuota() {
    }

	public CcLoteCuotaPK getId() {
		return this.id;
	}

	public void setId(CcLoteCuotaPK id) {
		this.id = id;
	}
	
	public int getAnnoCuota() {
		return this.annoCuota;
	}

	public void setAnnoCuota(int annoCuota) {
		this.annoCuota = annoCuota;
	}

	public int getCuota() {
		return this.cuota;
	}

	public void setCuota(int cuota) {
		this.cuota = cuota;
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
	
	@Override
	public String toString(){
		return annoCuota + " - "+ cuota;  
	}

}