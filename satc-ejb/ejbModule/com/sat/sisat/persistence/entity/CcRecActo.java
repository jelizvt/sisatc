package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the cc_rec_acto database table.
 * 
 */
@Entity
@Table(name="cc_rec_acto")
public class CcRecActo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="rec_acto_id")
	private int recActoId;

	@Column(name="deuda_id")
	private int deudaId;

	@Column(name="deuda_total")
	private BigDecimal deudaTotal;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="persona_id")
	private int personaId;

	@Column(name="rec_id")
	private int recId;

	private String terminal;

	@Column(name="tipo_acto_id")
	private int tipoActoId;

	@Column(name="usuario_id")
	private int usuarioId;

    public CcRecActo() {
    }

	public int getRecActoId() {
		return this.recActoId;
	}

	public void setRecActoId(int recActoId) {
		this.recActoId = recActoId;
	}

	public int getDeudaId() {
		return this.deudaId;
	}

	public void setDeudaId(int deudaId) {
		this.deudaId = deudaId;
	}

	public BigDecimal getDeudaTotal() {
		return this.deudaTotal;
	}

	public void setDeudaTotal(BigDecimal deudaTotal) {
		this.deudaTotal = deudaTotal;
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

	public int getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public int getRecId() {
		return this.recId;
	}

	public void setRecId(int recId) {
		this.recId = recId;
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