package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the rs_multa database table.
 * 
 */
@Entity
@Table(name="rs_multa")
public class RsMulta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="multa_id")
	private Integer multaId;
	
	@Column(name="nro_rs_multa")
	private String nroRsMulta;
	
	@Column(name="unidad_id")
	private Integer unidadId;
	
	@Column(name="persona_id")
	private Integer personaId;
	
	@Column(name="concepto_id_referencia")
	private Integer conceptoIdReferencia;
	
	@Column(name="periodo")
	private Integer periodo;
	
	@Column(name="concepto_id")
	private Integer conceptoId;
	
	@Column(name="subconcepto_id")
	private Integer subConceptoId;
	
	@Column(name="valor_uit")
	private BigDecimal valorUit;
	
	@Column(name="porcentaje_sancion")
	private BigDecimal porcentajeSancion;
	
	@Column(name="monto")
	private BigDecimal monto;
	
	@Column(name="glosa")
	private String glosa;	
	
	@Column(name="djreferencia")
	private Integer djReferencia;

	@Column(name="flag_presento_documentos")
	private Boolean flagPresentoDocumentacion;
	
	@Column(name="nro_requerimiento")
	private String nroRequerimiento;
	
	@Column(name="nro_acta")
	private String nroActa;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="terminal")
	private String terminal;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_acta")
	private Date fechaActa;

	@Column(name="flag_contexto")
	private String flagContexto;
	
	public Integer getMultaId() {
		return multaId;
	}

	public void setMultaId(Integer multaId) {
		this.multaId = multaId;
	}

	public String getNroRsMulta() {
		return nroRsMulta;
	}

	public void setNroRsMulta(String nroRsMulta) {
		this.nroRsMulta = nroRsMulta;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public Integer getConceptoIdReferencia() {
		return conceptoIdReferencia;
	}

	public void setConceptoIdReferencia(Integer conceptoIdReferencia) {
		this.conceptoIdReferencia = conceptoIdReferencia;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public Integer getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}

	public Integer getSubConceptoId() {
		return subConceptoId;
	}

	public void setSubConceptoId(Integer subConceptoId) {
		this.subConceptoId = subConceptoId;
	}

	public BigDecimal getValorUit() {
		return valorUit;
	}

	public void setValorUit(BigDecimal valorUit) {
		this.valorUit = valorUit;
	}

	public BigDecimal getPorcentajeSancion() {
		return porcentajeSancion;
	}

	public void setPorcentajeSancion(BigDecimal porcentajeSancion) {
		this.porcentajeSancion = porcentajeSancion;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
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

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public Integer getDjReferencia() {
		return djReferencia;
	}

	public void setDjReferencia(Integer djReferencia) {
		this.djReferencia = djReferencia;
	}

	public Integer getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(Integer unidadId) {
		this.unidadId = unidadId;
	}

	public Boolean getFlagPresentoDocumentacion() {
		return flagPresentoDocumentacion;
	}

	public void setFlagPresentoDocumentacion(Boolean flagPresentoDocumentacion) {
		this.flagPresentoDocumentacion = flagPresentoDocumentacion;
	}

	public String getNroActa() {
		return nroActa;
	}

	public void setNroActa(String nroActa) {
		this.nroActa = nroActa;
	}

	public String getNroRequerimiento() {
		return nroRequerimiento;
	}

	public void setNroRequerimiento(String nroRequerimiento) {
		this.nroRequerimiento = nroRequerimiento;
	}

	public Date getFechaActa() {
		return fechaActa;
	}

	public void setFechaActa(Date fechaActa) {
		this.fechaActa = fechaActa;
	}

	public String getFlagContexto() {
		return flagContexto;
	}

	public void setFlagContexto(String flagContexto) {
		this.flagContexto = flagContexto;
	}
}