package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the cc_lote_acto database table.
 * 
 */
@Entity
@Table(name="cc_lote_acto")
public class CcLoteActo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CcLoteActoPK id;

	@Column(name="agrupado_acto")
	private String agrupadoActo;

	@Column(name="agrupado_bien")
	private String agrupadoBien;

	@Column(name="agrupado_cuota")
	private String agrupadoCuota;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="fecha_vencimiento")
	private Timestamp fechaVencimiento;

	private String terminal;

	@Column(name="tipo_acto_id")
	private int tipoActoId;

	@Column(name="usuario_id")
	private int usuarioId;

    public CcLoteActo() {
    }

	public CcLoteActoPK getId() {
		return this.id;
	}

	public void setId(CcLoteActoPK id) {
		this.id = id;
	}
	
	public String getAgrupadoActo() {
		return this.agrupadoActo;
	}

	public void setAgrupadoActo(String agrupadoActo) {
		this.agrupadoActo = agrupadoActo;
	}

	public String getAgrupadoBien() {
		return this.agrupadoBien;
	}

	public void setAgrupadoBien(String agrupadoBien) {
		this.agrupadoBien = agrupadoBien;
	}

	public String getAgrupadoCuota() {
		return this.agrupadoCuota;
	}

	public void setAgrupadoCuota(String agrupadoCuota) {
		this.agrupadoCuota = agrupadoCuota;
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

	public Timestamp getFechaVencimiento() {
		return this.fechaVencimiento;
	}

	public void setFechaVencimiento(Timestamp fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getTipoActoId() {
		return this.tipoActoId;
	}

	public void setTipoActoId(int tipoActoId) {
		this.tipoActoId = tipoActoId;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}