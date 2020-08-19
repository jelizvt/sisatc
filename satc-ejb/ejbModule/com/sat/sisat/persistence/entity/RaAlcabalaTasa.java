package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the ra_alcabala_tasa database table.
 * 
 */
@Entity
@Table(name="ra_alcabala_tasa")
@NamedQuery(name="findRaAlcabalaTasaById", query="SELECT a FROM RaAlcabalaTasa a WHERE alcabalaTasaId=:p_alTasaId")
public class RaAlcabalaTasa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="alcabala_tasa_id")
	private int alcabalaTasaId;

	private String estado;

	@Column(name="fecha_fin")
	private Timestamp fechaFin;

	@Column(name="fecha_inicio")
	private Timestamp fechaInicio;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="nro_maximo_uit")
	private int nroMaximoUit;

	private BigDecimal tasa;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public RaAlcabalaTasa() {
    }

	public int getAlcabalaTasaId() {
		return this.alcabalaTasaId;
	}

	public void setAlcabalaTasaId(int alcabalaTasaId) {
		this.alcabalaTasaId = alcabalaTasaId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Timestamp getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getNroMaximoUit() {
		return this.nroMaximoUit;
	}

	public void setNroMaximoUit(int nroMaximoUit) {
		this.nroMaximoUit = nroMaximoUit;
	}

	public BigDecimal getTasa() {
		return this.tasa;
	}

	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
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