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
@Table(name = "dt_determinacion")
@NamedQueries({
@NamedQuery(name="getDtDeterminacionByPersonaId", query="SELECT m FROM DtDeterminacion m WHERE m.estado=:p_estado AND m.personaId=:p_persona_id AND m.annoDeterminacion=:p_periodo AND m.conceptoId=:p_concepto_id"),
@NamedQuery(name="getDtDeterminacionAntByPersonaId", query="SELECT m FROM DtDeterminacion m WHERE m.estado=:p_estado AND m.personaId=:p_persona_id AND m.annoDeterminacion=:p_periodo AND m.conceptoId=:p_concepto_id AND m.determinacionId!=:p_determinacion_id"),//Obtiene las determinaciones activas anteriores a una determinacion 
@NamedQuery(name="getDtDeterminacionArbitrioByPersonaId", query="SELECT m FROM DtDeterminacion m WHERE m.estado=:p_estado AND m.personaId=:p_persona_id AND m.djreferenciaId=:p_djid AND m.annoDeterminacion=:p_periodo AND m.conceptoId=:p_concepto_id"),
@NamedQuery(name="getDtDeterminacionByDjReferenciaId", query="SELECT m FROM DtDeterminacion m WHERE m.estado='1' AND m.djreferenciaId=:p_djreferencia_id AND m.conceptoId=:p_concepto_id AND m.annoDeterminacion=:p_periodo AND m.personaId=:p_persona_id"),
//@NamedQuery(name="getDtDeterminacionByPapeletaId", query="SELECT m FROM DtDeterminacion m WHERE m.estado='1' AND m.djreferenciaId=:p_djreferencia_id AND m.conceptoId=:p_concepto_id AND m.personaId=:p_persona_id"),
@NamedQuery(name="getDtDeterminacionByPapeletaId", query="SELECT m FROM DtDeterminacion m WHERE m.estado='1' AND m.djreferenciaId=:p_djreferencia_id AND m.conceptoId=:p_concepto_id "),//Correccion de calculo de deuda de la papeleta
@NamedQuery(name="getAllDtDeterminacionByPersonaId", query="SELECT m FROM DtDeterminacion m WHERE m.estado='1' AND m.personaId=:p_persona_id AND m.conceptoId=:p_concepto_id ORDER BY m.annoDeterminacion ASC"),
@NamedQuery(name="getAllDtDeterminacionByPeriodo", query="SELECT m FROM DtDeterminacion m WHERE m.personaId=:p_persona_id AND m.annoDeterminacion=:p_periodo AND m.conceptoId=:p_concepto_id ORDER BY determinacionId DESC")
})

public class DtDeterminacion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient
	private int cantRusticos;
	
	@Transient
	private int cantUrbanos;
	
	@Id
	@Column(name = "determinacion_id")
	private Integer determinacionId;

	@Column(name = "anno_determinacion")
	private Integer annoDeterminacion;

	@Column(name = "base_afecta")
	private BigDecimal baseAfecta;

	@Column(name = "base_afecta_anterior")
	private BigDecimal baseAfectaAnterior;

	@Column(name = "base_exonerada")
	private BigDecimal baseExonerada;

	@Column(name = "base_exonerada_anterior")
	private BigDecimal baseExoneradaAnterior;

	@Column(name = "base_imponible")
	private BigDecimal baseImponible;

	@Column(name = "base_imponible_anterior")
	private BigDecimal baseImponibleAnterior;

	@Column(name = "concepto_id")
	private Integer conceptoId;

	@Column(name = "djreferencia_id")
	private Integer djreferenciaId;

	private String estado;

	@Column(name = "fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name = "fecha_registro")
	private Timestamp fechaRegistro;

	private BigDecimal impuesto;

	@Column(name = "impuesto_anterior")
	private BigDecimal impuestoAnterior;

	@Column(name = "impuesto_diferencia")
	private BigDecimal impuestoDiferencia;

	@Column(name = "nro_cuotas")
	private Integer nroCuotas;

	@Column(name = "nro_documento")
	private String nroDocumento;

	@Column(name = "persona_id")
	private Integer personaId;

	@Column(name = "porc_propiedad")
	private BigDecimal porcPropiedad;

	private String terminal;

	@Column(name = "usuario_id")
	private Integer usuarioId;

	@Column(name="subconcepto_id")
	private Integer subconceptoId;
	
	//--
	@Column(name = "fiscalizado")
	private String fiscalizado;
	
	@Column(name = "fisca_aceptada")
	private String fiscaAceptada;
	
	@Column(name = "fisca_cerrada")
	private String fiscaCerrada;
	//--
	@Column(name = "id_anterior")
	private Integer IdAnterior;
	
	@Column(name = "tipo_uso_id")
	private Integer tipoUsoId;
	
	@Column(name = "tipo_deuda_id",nullable= true)
	private Integer tipoDeudaId;
	
	//Previo a la subvencion de arbitrios
	@Column(name = "base_imponible_presubvencion")
	private BigDecimal baseImponiblePreSub;
	
	@Column(name = "djuso_id")
	private Integer djusoId;

	@Column(name = "flag_variacion_valor")
	private String flagVariacionValor;
	
	public BigDecimal getBaseImponiblePreSub() {
		return baseImponiblePreSub;
	}

	public void setBaseImponiblePreSub(BigDecimal baseImponiblePreSub) {
		this.baseImponiblePreSub = baseImponiblePreSub;
	}

	public Integer getTipoUsoId() {
		return tipoUsoId;
	}

	public void setTipoUsoId(Integer tipoUsoId) {
		this.tipoUsoId = tipoUsoId;
	}

	public DtDeterminacion() {
		
	}

	public Integer getIdAnterior() {
		return IdAnterior;
	}

	public void setIdAnterior(Integer idAnterior) {
		IdAnterior = idAnterior;
	}
	
	public String getFiscalizado() {
		return fiscalizado;
	}

	public void setFiscalizado(String fiscalizado) {
		this.fiscalizado = fiscalizado;
	}

	public String getFiscaAceptada() {
		return fiscaAceptada;
	}

	public void setFiscaAceptada(String fiscaAceptada) {
		this.fiscaAceptada = fiscaAceptada;
	}

	public String getFiscaCerrada() {
		return fiscaCerrada;
	}

	public void setFiscaCerrada(String fiscaCerrada) {
		this.fiscaCerrada = fiscaCerrada;
	}

	public Integer getDeterminacionId() {
		return this.determinacionId;
	}

	public void setDeterminacionId(Integer determinacionId) {
		this.determinacionId = determinacionId;
	}

	public Integer getAnnoDeterminacion() {
		return this.annoDeterminacion;
	}

	public void setAnnoDeterminacion(Integer annoDeterminacion) {
		this.annoDeterminacion = annoDeterminacion;
	}

	public BigDecimal getBaseAfecta() {
		return this.baseAfecta;
	}

	public void setBaseAfecta(BigDecimal baseAfecta) {
		this.baseAfecta = baseAfecta;
	}

	public BigDecimal getBaseAfectaAnterior() {
		return this.baseAfectaAnterior;
	}

	public void setBaseAfectaAnterior(BigDecimal baseAfectaAnterior) {
		this.baseAfectaAnterior = baseAfectaAnterior;
	}

	public BigDecimal getBaseExonerada() {
		return this.baseExonerada;
	}

	public void setBaseExonerada(BigDecimal baseExonerada) {
		this.baseExonerada = baseExonerada;
	}

	public BigDecimal getBaseExoneradaAnterior() {
		return this.baseExoneradaAnterior;
	}

	public void setBaseExoneradaAnterior(BigDecimal baseExoneradaAnterior) {
		this.baseExoneradaAnterior = baseExoneradaAnterior;
	}

	public BigDecimal getBaseImponible() {
		return this.baseImponible;
	}

	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}

	public BigDecimal getBaseImponibleAnterior() {
		return this.baseImponibleAnterior;
	}

	public void setBaseImponibleAnterior(BigDecimal baseImponibleAnterior) {
		this.baseImponibleAnterior = baseImponibleAnterior;
	}

	public Integer getConceptoId() {
		return this.conceptoId;
	}

	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}

	public Integer getDjreferenciaId() {
		return this.djreferenciaId;
	}

	public void setDjreferenciaId(Integer djreferenciaId) {
		this.djreferenciaId = djreferenciaId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public BigDecimal getImpuesto() {
		return this.impuesto;
	}

	public void setImpuesto(BigDecimal impuesto) {
		this.impuesto = impuesto;
	}

	public BigDecimal getImpuestoAnterior() {
		return this.impuestoAnterior;
	}

	public void setImpuestoAnterior(BigDecimal impuestoAnterior) {
		this.impuestoAnterior = impuestoAnterior;
	}

	public BigDecimal getImpuestoDiferencia() {
		return this.impuestoDiferencia;
	}

	public void setImpuestoDiferencia(BigDecimal impuestoDiferencia) {
		this.impuestoDiferencia = impuestoDiferencia;
	}

	public Integer getNroCuotas() {
		return this.nroCuotas;
	}

	public void setNroCuotas(Integer nroCuotas) {
		this.nroCuotas = nroCuotas;
	}

	public String getNroDocumento() {
		return this.nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public Integer getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public BigDecimal getPorcPropiedad() {
		return this.porcPropiedad;
	}

	public void setPorcPropiedad(BigDecimal porcPropiedad) {
		this.porcPropiedad = porcPropiedad;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getSubconceptoId() {
		return subconceptoId;
	}

	public void setSubconceptoId(Integer subconceptoId) {
		this.subconceptoId = subconceptoId;
	}

	public int getCantRusticos() {
		return cantRusticos;
	}

	public void setCantRusticos(int cantRusticos) {
		this.cantRusticos = cantRusticos;
	}

	public int getCantUrbanos() {
		return cantUrbanos;
	}

	public void setCantUrbanos(int cantUrbanos) {
		this.cantUrbanos = cantUrbanos;
	}

	public Integer getTipoDeudaId() {
		return tipoDeudaId;
	}

	public void setTipoDeudaId(Integer tipoDeudaId) {
		this.tipoDeudaId = tipoDeudaId;
	}

	public Integer getDjusoId() {
		return djusoId;
	}

	public void setDjusoId(Integer djusoId) {
		this.djusoId = djusoId;
	}

	public String getFlagVariacionValor() {
		return flagVariacionValor;
	}

	public void setFlagVariacionValor(String flagVariacionValor) {
		this.flagVariacionValor = flagVariacionValor;
	}
}