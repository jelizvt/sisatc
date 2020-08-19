package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "gn_remate")
@NamedQuery(name = "FindAllRemate", query = "select r from GnRemate r order by fecha_registro desc")
public class GnRemate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "remate_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int remateId;
	@Column(name = "propietario_id")
	private int propietarioId;
	// private String propietario;
	private String placa;
	@Column(name = "monto_adjudicado")
	private BigDecimal montoAdjudicado;
	@Column(name = "fecha_remate")
	private Timestamp fechaRemate;
	@Column(name = "fecha_registro")
	private Timestamp fechaRegistro;
	@Column(name = "usuario_id", nullable = false)
	private Integer usuarioId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "propietario_id", referencedColumnName = "persona_id", nullable = false, insertable = false, updatable = false)
	private GnPersona gnPersona;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id", nullable = false, insertable = false, updatable = false)
	private SgUsuario sgUsuario;
	private String terminal;
	private String sustento;

	public int getRemateId() {
		return remateId;
	}

	public void setRemateId(int remateId) {
		this.remateId = remateId;
	}

	public int getPropietarioId() {
		return propietarioId;
	}

	public void setPropietarioId(int propietarioId) {
		this.propietarioId = propietarioId;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public BigDecimal getMontoAdjudicado() {
		return montoAdjudicado;
	}

	public void setMontoAdjudicado(BigDecimal montoAdjudicado) {
		this.montoAdjudicado = montoAdjudicado;
	}

	public Timestamp getFechaRemate() {
		return fechaRemate;
	}

	public void setFechaRemate(Timestamp fechaRemate) {
		this.fechaRemate = fechaRemate;
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

	public String getSustento() {
		return sustento;
	}

	public void setSustento(String sustento) {
		this.sustento = sustento;
	}

	public SgUsuario getSgUsuario() {
		return sgUsuario;
	}

	public void setSgUsuario(SgUsuario sgUsuario) {
		this.sgUsuario = sgUsuario;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public GnPersona getGnPersona() {
		return gnPersona;
	}

	public void setGnPersona(GnPersona gnPersona) {
		this.gnPersona = gnPersona;
	}

}
