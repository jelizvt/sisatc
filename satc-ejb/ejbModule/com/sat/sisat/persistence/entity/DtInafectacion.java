package com.sat.sisat.persistence.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the dt_inafectacion database table.
 * 
 */
@Entity
@Table(name="dt_inafectacion")
@NamedQuery(name="getDtInafectacionByPredioId", query="SELECT m FROM DtInafectacion m WHERE m.estado='1' AND m.conceptoId=:p_concepto_id AND m.predioId=:p_predio_id")
public class DtInafectacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="inafectacion_id")
	private int inafectacionId;

	@Column(name="anno_fin")
	private int annoFin;

	@Column(name="anno_inicio")
	private int annoInicio;

	@Column(name="concepto_id")
	private int conceptoId;

	@Column(name="cond_espe_predio_id")
	private int condEspePredioId;

	@Column(name="condicion_vehiculo_id")
	private int condicionVehiculoId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="fecha_resolucion")
	private Timestamp fechaResolucion;

	private String glosa;

	@Column(name="mes_fin")
	private String mesFin;

	@Column(name="mes_inicio")
	private String mesInicio;

	@Column(name="nro_resolucion")
	private String nroResolucion;

	@Column(name="predio_id")
	private int predioId;

	private String terminal;

	@Column(name="tipo_afectacion")
	private String tipoAfectacion;

	@Column(name="usuario_id")
	private int usuarioId;

	@Column(name="valor_afectacion")
	private BigDecimal valorAfectacion;

	@Column(name="vehiculo_id")
	private int vehiculoId;

    public DtInafectacion() {
    }

	public int getInafectacionId() {
		return this.inafectacionId;
	}

	public void setInafectacionId(int inafectacionId) {
		this.inafectacionId = inafectacionId;
	}

	public int getAnnoFin() {
		return this.annoFin;
	}

	public void setAnnoFin(int annoFin) {
		this.annoFin = annoFin;
	}

	public int getAnnoInicio() {
		return this.annoInicio;
	}

	public void setAnnoInicio(int annoInicio) {
		this.annoInicio = annoInicio;
	}

	public int getConceptoId() {
		return this.conceptoId;
	}

	public void setConceptoId(int conceptoId) {
		this.conceptoId = conceptoId;
	}

	public int getCondEspePredioId() {
		return this.condEspePredioId;
	}

	public void setCondEspePredioId(int condEspePredioId) {
		this.condEspePredioId = condEspePredioId;
	}

	public int getCondicionVehiculoId() {
		return this.condicionVehiculoId;
	}

	public void setCondicionVehiculoId(int condicionVehiculoId) {
		this.condicionVehiculoId = condicionVehiculoId;
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

	public Timestamp getFechaResolucion() {
		return this.fechaResolucion;
	}

	public void setFechaResolucion(Timestamp fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	public String getGlosa() {
		return this.glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public String getMesFin() {
		return this.mesFin;
	}

	public void setMesFin(String mesFin) {
		this.mesFin = mesFin;
	}

	public String getMesInicio() {
		return this.mesInicio;
	}

	public void setMesInicio(String mesInicio) {
		this.mesInicio = mesInicio;
	}

	public String getNroResolucion() {
		return this.nroResolucion;
	}

	public void setNroResolucion(String nroResolucion) {
		this.nroResolucion = nroResolucion;
	}

	public int getPredioId() {
		return this.predioId;
	}

	public void setPredioId(int predioId) {
		this.predioId = predioId;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTipoAfectacion() {
		return this.tipoAfectacion;
	}

	public void setTipoAfectacion(String tipoAfectacion) {
		this.tipoAfectacion = tipoAfectacion;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public BigDecimal getValorAfectacion() {
		return this.valorAfectacion;
	}

	public void setValorAfectacion(BigDecimal valorAfectacion) {
		this.valorAfectacion = valorAfectacion;
	}

	public int getVehiculoId() {
		return this.vehiculoId;
	}

	public void setVehiculoId(int vehiculoId) {
		this.vehiculoId = vehiculoId;
	}

}