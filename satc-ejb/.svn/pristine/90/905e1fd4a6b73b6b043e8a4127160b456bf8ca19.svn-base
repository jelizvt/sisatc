package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the pa_papeleta database table.
 * 
 */
@Entity
@Table(name="pa_incidencia")
@NamedQuery(name="getPaIncidenciaByInfraccionId", query="SELECT a FROM PaIncidencia a WHERE a.personaId=:p_infractor_id AND a.infraccionId=:p_infraccion_id AND papeletaId=:p_papeleta_id AND a.estado='1' ")
public class PaIncidencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="incidencia_id")
	private Integer incidenciaId;
	
	@Column(name="persona_id")
	private Integer personaId;
	
	@Column(name="infraccion_id")
	private Integer infraccionId;
	
	@Column(name="fecha_infraccion")
	private Timestamp fechaInfraccion;
	
	@Column(name="papeleta_id")
	private Integer papeletaId;
	
	transient
	private Integer puntosAcum;
	
	@Column(name="monto_multa")
	private Double montoMulta;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
	@Column(name="terminal")
	private String terminal;
	
	@Column(name="reincidente")
	private Integer reincidente;

	//--
	@Column(name="puntos")
	private Integer puntos;
	
	@Column(name="puntos_firmes")
	private Integer puntosFirmes = new Integer(0);
	
	@Column(name="monto_infraccion")
	private Double montoInfraccion;
	//--
	// 1 Incidencia firme proveniente de una papeleta firme, 0 papeleta en proceso o puntos en proceso
	@Column(name="flag_firme")
	private Character flagFirme = new Character('0');
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_firme")	
	private Date fechaFirme;
	
	
	public Integer getPuntos() {
		return puntos;
	}

	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}

	public Double getMontoInfraccion() {
		return montoInfraccion;
	}

	public void setMontoInfraccion(Double montoInfraccion) {
		this.montoInfraccion = montoInfraccion;
	}

	public Integer getReincidente() {
		return reincidente;
	}

	public void setReincidente(Integer reincidente) {
		this.reincidente = reincidente;
	}

	public Integer getIncidenciaId() {
		return incidenciaId;
	}

	public void setIncidenciaId(Integer incidenciaId) {
		this.incidenciaId = incidenciaId;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public Integer getInfraccionId() {
		return infraccionId;
	}

	public void setInfraccionId(Integer infraccionId) {
		this.infraccionId = infraccionId;
	}

	public Timestamp getFechaInfraccion() {
		return fechaInfraccion;
	}

	public void setFechaInfraccion(Timestamp fechaInfraccion) {
		this.fechaInfraccion = fechaInfraccion;
	}

	public Integer getPapeletaId() {
		return papeletaId;
	}

	public void setPapeletaId(Integer papeletaId) {
		this.papeletaId = papeletaId;
	}

	public Integer getPuntosAcum() {
		return puntosAcum;
	}

	public void setPuntosAcum(Integer puntosAcum) {
		this.puntosAcum= puntosAcum;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	
	public Double getMontoMulta() {
		return montoMulta;
	}

	public void setMontoMulta(Double montoMulta) {
		this.montoMulta = montoMulta;
	}

	public Integer getPuntosFirmes() {
		return puntosFirmes;
	}

	public void setPuntosFirmes(Integer puntosFirmes) {
		this.puntosFirmes = puntosFirmes;
	}

	public Character getFlagFirme() {
		return flagFirme;
	}

	public void setFlagFirme(Character flagFirme) {
		this.flagFirme = flagFirme;
	}

	public Date getFechaFirme() {
		return fechaFirme;
	}

	public void setFechaFirme(Date fechaFirme) {
		this.fechaFirme = fechaFirme;
	}
}