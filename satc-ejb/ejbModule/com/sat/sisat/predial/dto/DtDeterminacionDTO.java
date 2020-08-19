package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class DtDeterminacionDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -486337916480648197L;

	private int cantRusticos;	

	private int cantUrbanos;
	
	private Integer determinacionId;

	private Integer annoDeterminacion;

	private BigDecimal baseAfecta;

	private BigDecimal baseAfectaAnterior;

	private BigDecimal baseExonerada;

	private BigDecimal baseExoneradaAnterior;

	private BigDecimal baseImponible;

	private BigDecimal baseImponibleAnterior;

	private Integer conceptoId;

	private Integer djreferenciaId;

	private String estado;

	private Timestamp fechaActualizacion;

	private Timestamp fechaRegistro;

	private BigDecimal impuesto;

	private BigDecimal impuestoAnterior;

	private BigDecimal impuestoDiferencia;

	private Integer nroCuotas;

	private String nroDocumento;

	private Integer personaId;

	private BigDecimal porcPropiedad;

	private String terminal;

	private Integer usuarioId;

	private Integer subconceptoId;
	
	private String fiscalizado;
	
	private String fiscaAceptada;
	
	private String fiscaCerrada;

	private Integer IdAnterior;
	
	private Integer tipoUsoId;
	
	private String nombreUsuario;
	
	private Integer predioId;

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

	public Integer getDeterminacionId() {
		return determinacionId;
	}

	public void setDeterminacionId(Integer determinacionId) {
		this.determinacionId = determinacionId;
	}

	public Integer getAnnoDeterminacion() {
		return annoDeterminacion;
	}

	public void setAnnoDeterminacion(Integer annoDeterminacion) {
		this.annoDeterminacion = annoDeterminacion;
	}

	public BigDecimal getBaseAfecta() {
		return baseAfecta;
	}

	public void setBaseAfecta(BigDecimal baseAfecta) {
		this.baseAfecta = baseAfecta;
	}

	public BigDecimal getBaseAfectaAnterior() {
		return baseAfectaAnterior;
	}

	public void setBaseAfectaAnterior(BigDecimal baseAfectaAnterior) {
		this.baseAfectaAnterior = baseAfectaAnterior;
	}

	public BigDecimal getBaseExonerada() {
		return baseExonerada;
	}

	public void setBaseExonerada(BigDecimal baseExonerada) {
		this.baseExonerada = baseExonerada;
	}

	public BigDecimal getBaseExoneradaAnterior() {
		return baseExoneradaAnterior;
	}

	public void setBaseExoneradaAnterior(BigDecimal baseExoneradaAnterior) {
		this.baseExoneradaAnterior = baseExoneradaAnterior;
	}

	public BigDecimal getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}

	public BigDecimal getBaseImponibleAnterior() {
		return baseImponibleAnterior;
	}

	public void setBaseImponibleAnterior(BigDecimal baseImponibleAnterior) {
		this.baseImponibleAnterior = baseImponibleAnterior;
	}

	public Integer getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}

	public Integer getDjreferenciaId() {
		return djreferenciaId;
	}

	public void setDjreferenciaId(Integer djreferenciaId) {
		this.djreferenciaId = djreferenciaId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public BigDecimal getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(BigDecimal impuesto) {
		this.impuesto = impuesto;
	}

	public BigDecimal getImpuestoAnterior() {
		return impuestoAnterior;
	}

	public void setImpuestoAnterior(BigDecimal impuestoAnterior) {
		this.impuestoAnterior = impuestoAnterior;
	}

	public BigDecimal getImpuestoDiferencia() {
		return impuestoDiferencia;
	}

	public void setImpuestoDiferencia(BigDecimal impuestoDiferencia) {
		this.impuestoDiferencia = impuestoDiferencia;
	}

	public Integer getNroCuotas() {
		return nroCuotas;
	}

	public void setNroCuotas(Integer nroCuotas) {
		this.nroCuotas = nroCuotas;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public BigDecimal getPorcPropiedad() {
		return porcPropiedad;
	}

	public void setPorcPropiedad(BigDecimal porcPropiedad) {
		this.porcPropiedad = porcPropiedad;
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

	public Integer getSubconceptoId() {
		return subconceptoId;
	}

	public void setSubconceptoId(Integer subconceptoId) {
		this.subconceptoId = subconceptoId;
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

	public Integer getIdAnterior() {
		return IdAnterior;
	}

	public void setIdAnterior(Integer idAnterior) {
		IdAnterior = idAnterior;
	}

	public Integer getTipoUsoId() {
		return tipoUsoId;
	}

	public void setTipoUsoId(Integer tipoUsoId) {
		this.tipoUsoId = tipoUsoId;
	}

	public String getNombreUsuario() {		
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Integer getPredioId() {
		return predioId;
	}

	public void setPredioId(Integer predioId) {
		this.predioId = predioId;
	}
}
