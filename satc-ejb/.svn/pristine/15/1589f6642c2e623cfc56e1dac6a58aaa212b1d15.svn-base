/**
 * @author Liliana Aliaga
 * @version 1.0 
 * @since 21/02/2012
 * Entidad Registro Tupa 
 */
package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * The persistent class for the cj_tupa database table.
 * 
 */
@Entity
@Table(name="cj_tupa")
public class CjTupa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tupa_id")
	private int tupaId;

	@Column(name="anno_tupa")
	private int annoTupa;

	private String descripcion;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="monto_tasa")
	private BigDecimal montoTasa;

	@Column(name="porcentaje_uit")
	private BigDecimal porcentajeUit;

	private String terminal;

	@Column(name="uit_id")
	private int uitId;

	@Column(name="usuario_id")
	private int usuarioId;

    public CjTupa() {
    }

	public int getTupaId() {
		return this.tupaId;
	}

	public void setTupaId(int tupaId) {
		this.tupaId = tupaId;
	}

	public int getAnnoTupa() {
		return this.annoTupa;
	}

	public void setAnnoTupa(int annoTupa) {
		this.annoTupa = annoTupa;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionCorta() {
		return this.descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
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

	public BigDecimal getMontoTasa() {
		return this.montoTasa;
	}

	public void setMontoTasa(BigDecimal montoTasa) {
		this.montoTasa = montoTasa;
	}

	public BigDecimal getPorcentajeUit() {
		return this.porcentajeUit;
	}

	public void setPorcentajeUit(BigDecimal porcentajeUit) {
		this.porcentajeUit = porcentajeUit;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getUitId() {
		return this.uitId;
	}

	public void setUitId(int uitId) {
		this.uitId = uitId;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}