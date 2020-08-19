package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the cc_lote_schedule database table.
 * 
 */
@Entity
@Table(name="cc_lote_schedule")
public class CcLoteSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CcLoteSchedulePK id;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="fecha_schedule")
	private Timestamp fechaSchedule;

	@Column(name="hora_schedule")
	private String horaSchedule;

	private String terminal;

	@Column(name="tipo_schedule")
	private String tipoSchedule;

	@Column(name="usuario_id")
	private int usuarioId;
	@Transient
	private Date fechaProgramacion;

    public CcLoteSchedule() {
    }

	public CcLoteSchedulePK getId() {
		return this.id;
	}

	public void setId(CcLoteSchedulePK id) {
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

	public Timestamp getFechaSchedule() {
		return this.fechaSchedule;
	}

	public void setFechaSchedule(Timestamp fechaSchedule) {
		this.fechaSchedule = fechaSchedule;
	}

	public String getHoraSchedule() {
		return this.horaSchedule;
	}

	public void setHoraSchedule(String horaSchedule) {
		this.horaSchedule = horaSchedule;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTipoSchedule() {
		return this.tipoSchedule;
	}

	public void setTipoSchedule(String tipoSchedule) {
		this.tipoSchedule = tipoSchedule;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Date getFechaProgramacion() {
		return fechaProgramacion;
	}

	public void setFechaProgramacion(Date fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}

}