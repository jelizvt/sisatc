package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the no_masiva_digi_notif database table.
 * 
 */
@Entity
@Table(name="no_masiva_digi_notif")
public class NoMasivaDigiNotif implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="masiva_digi_notif_id")
	private int masivaDigiNotifId;

	@Column(name="cant_correctos")
	private int cantCorrectos;

	@Column(name="cant_errores")
	private int cantErrores;

	@Column(name="cant_duplicado")
	private int cantDuplicado;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public NoMasivaDigiNotif() {
    }

	public int getMasivaDigiNotifId() {
		return this.masivaDigiNotifId;
	}

	public void setMasivaDigiNotifId(int masivaDigiNotifId) {
		this.masivaDigiNotifId = masivaDigiNotifId;
	}

	public int getCantCorrectos() {
		return this.cantCorrectos;
	}

	public void setCantCorrectos(int cantCorrectos) {
		this.cantCorrectos = cantCorrectos;
	}

	public int getCantErrores() {
		return this.cantErrores;
	}

	public void setCantErrores(int cantErrores) {
		this.cantErrores = cantErrores;
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

	public int getCantDuplicado() {
		return cantDuplicado;
	}

	public void setCantDuplicado(int cantDuplicado) {
		this.cantDuplicado = cantDuplicado;
	}

}