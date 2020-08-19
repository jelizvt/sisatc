package com.sat.sisat.persistence.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * The persistent class for the dt_determinacion database table.
 * 
 */
@Entity
@Table(name = "dt_determinacion_valor")

public class DtDeterminacionValor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(name = "determinacion_valor_id")
	private Integer determinacionValorId;

	@Column(name = "determinacion_id")
	private Integer determinacionId;
	
	@Column(name = "persona_id")
	private Integer personaId;
	
	@Column(name = "concepto_id")
	private Integer conceptoId;
	
	@Column(name="subconcepto_id")
	private Integer subconceptoId;
	
	@Column(name = "base_imponible")
	private BigDecimal baseImponible;

	@Column(name = "impuesto")
	private BigDecimal impuesto;
	
	@Column(name = "anno_determinacion")
	private Integer annoDeterminacion;
	
	//variaciones
	@Column(name = "determinacion_id_var")
	private Integer determinacionIdVariacion;
	
	@Column(name = "persona_id_var")
	private Integer personaIdVariacion;
	
	@Column(name = "concepto_id_var")
	private Integer conceptoIdVariacion;
	
	@Column(name="subconcepto_id_var")
	private Integer subconceptoIdVariacion;
	
	@Column(name = "base_imponible_var")
	private BigDecimal baseImponibleVariacion;

	@Column(name = "impuesto_var")
	private BigDecimal impuestoVariacion;
	
	@Column(name = "anno_determinacion_var")
	private Integer annoDeterminacionVariacion;

	@Column(name = "diff_impuesto")
	private BigDecimal diffImpuesto;

	@Column(name="flag_aumento")
	private String flagAumento;
	
	private String estado;
	
	@Column(name = "fecha_registro")
	private Timestamp fechaRegistro;

	
	private String terminal;

	@Column(name = "usuario_id")
	private Integer usuarioId;

	
	@Column(name = "fecha_registro_determinacion")
	private Timestamp fechaRegistroDt;
	
	@Column(name = "fecha_registro_determinacion_var")
	private Timestamp fechaRegistroDtVariacion;

	public DtDeterminacionValor() {
		
	}


	public Integer getDeterminacionValorId() {
		return determinacionValorId;
	}


	public void setDeterminacionValorId(Integer determinacionValorId) {
		this.determinacionValorId = determinacionValorId;
	}


	public Integer getDeterminacionId() {
		return determinacionId;
	}


	public void setDeterminacionId(Integer determinacionId) {
		this.determinacionId = determinacionId;
	}


	public Integer getPersonaId() {
		return personaId;
	}


	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}


	public Integer getConceptoId() {
		return conceptoId;
	}


	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}


	public Integer getSubconceptoId() {
		return subconceptoId;
	}


	public void setSubconceptoId(Integer subconceptoId) {
		this.subconceptoId = subconceptoId;
	}


	public BigDecimal getBaseImponible() {
		return baseImponible;
	}


	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}


	public BigDecimal getImpuesto() {
		return impuesto;
	}


	public void setImpuesto(BigDecimal impuesto) {
		this.impuesto = impuesto;
	}


	public Integer getAnnoDeterminacion() {
		return annoDeterminacion;
	}


	public void setAnnoDeterminacion(Integer annoDeterminacion) {
		this.annoDeterminacion = annoDeterminacion;
	}


	public Integer getDeterminacionIdVariacion() {
		return determinacionIdVariacion;
	}


	public void setDeterminacionIdVariacion(Integer determinacionIdVariacion) {
		this.determinacionIdVariacion = determinacionIdVariacion;
	}


	public Integer getPersonaIdVariacion() {
		return personaIdVariacion;
	}


	public void setPersonaIdVariacion(Integer personaIdVariacion) {
		this.personaIdVariacion = personaIdVariacion;
	}


	public Integer getConceptoIdVariacion() {
		return conceptoIdVariacion;
	}


	public void setConceptoIdVariacion(Integer conceptoIdVariacion) {
		this.conceptoIdVariacion = conceptoIdVariacion;
	}


	public Integer getSubconceptoIdVariacion() {
		return subconceptoIdVariacion;
	}


	public void setSubconceptoIdVariacion(Integer subconceptoIdVariacion) {
		this.subconceptoIdVariacion = subconceptoIdVariacion;
	}


	public BigDecimal getBaseImponibleVariacion() {
		return baseImponibleVariacion;
	}


	public void setBaseImponibleVariacion(BigDecimal baseImponibleVariacion) {
		this.baseImponibleVariacion = baseImponibleVariacion;
	}


	public BigDecimal getImpuestoVariacion() {
		return impuestoVariacion;
	}


	public void setImpuestoVariacion(BigDecimal impuestoVariacion) {
		this.impuestoVariacion = impuestoVariacion;
	}


	public Integer getAnnoDeterminacionVariacion() {
		return annoDeterminacionVariacion;
	}


	public void setAnnoDeterminacionVariacion(Integer annoDeterminacionVariacion) {
		this.annoDeterminacionVariacion = annoDeterminacionVariacion;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
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


	public Integer getUsuarioId() {
		return usuarioId;
	}


	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}


	public Timestamp getFechaRegistroDt() {
		return fechaRegistroDt;
	}


	public void setFechaRegistroDt(Timestamp fechaRegistroDt) {
		this.fechaRegistroDt = fechaRegistroDt;
	}


	public Timestamp getFechaRegistroDtVariacion() {
		return fechaRegistroDtVariacion;
	}


	public void setFechaRegistroDtVariacion(Timestamp fechaRegistroDtVariacion) {
		this.fechaRegistroDtVariacion = fechaRegistroDtVariacion;
	}


	public String getFlagAumento() {
		return flagAumento;
	}


	public void setFlagAumento(String flagAumento) {
		this.flagAumento = flagAumento;
	}


	public BigDecimal getDiffImpuesto() {
		return diffImpuesto;
	}


	public void setDiffImpuesto(BigDecimal diffImpuesto) {
		this.diffImpuesto = diffImpuesto;
	}

}