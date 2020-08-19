package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The persistent class for the cc_acto_deuda database table.
 * 
 */
@Entity
@Table(name="cc_acto_deuda")
public class CcActoDeuda implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CcActoDeudaPK id;

	@Column(name="anno_deuda")
	private int annoDeuda;

	@Column(name="anno_documento")
	private int annoDocumento;

	@Column(name="area_uso")
	private BigDecimal areaUso;

	@Column(name="concepto_id")
	private int conceptoId;

	@Column(name="estado")
	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="fecha_vencimiento")
	private Timestamp fechaVencimiento;
	
	@Column(name="insoluto")
	private BigDecimal insoluto;

	@Column(name="interes_anual")
	private BigDecimal interesAnual;

	@Column(name="interes_capitalizado")
	private BigDecimal interesCapitalizado;

	@Column(name="interes_mensual")
	private BigDecimal interesMensual;

	@Column(name="nro_cuenta_banco")
	private String nroCuentaBanco;

	@Column(name="nro_documento")
	private int nroDocumento;

	@Column(name="nro_referencia")
	private int nroReferencia;

	private BigDecimal reajuste;

	@Column(name="subconcepto_id")
	private int subconceptoId;

	@Column(name="deuda_id")
	private Integer deudaId;	
	
	@Column(name="terminal")
	private String terminal;

	@Column(name="tipo_uso_id", nullable = true)
	private Integer tipoUsoId;

	@Column(name="total_deuda")
	private BigDecimal totalDeuda;

	@Column(name="usuario_id")
	private int usuarioId;

    public CcActoDeuda() {
    }

	public CcActoDeudaPK getId() {
		return this.id;
	}

	public void setId(CcActoDeudaPK id) {
		this.id = id;
	}
	
	public int getAnnoDeuda() {
		return this.annoDeuda;
	}

	public void setAnnoDeuda(int annoDeuda) {
		this.annoDeuda = annoDeuda;
	}

	public int getAnnoDocumento() {
		return this.annoDocumento;
	}

	public void setAnnoDocumento(int annoDocumento) {
		this.annoDocumento = annoDocumento;
	}

	public BigDecimal getAreaUso() {
		return this.areaUso;
	}

	public void setAreaUso(BigDecimal areaUso) {
		this.areaUso = areaUso;
	}

	public int getConceptoId() {
		return this.conceptoId;
	}

	public void setConceptoId(int conceptoId) {
		this.conceptoId = conceptoId;
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

	public Timestamp getFechaVencimiento() {
		return this.fechaVencimiento;
	}

	public void setFechaVencimiento(Timestamp fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public BigDecimal getInsoluto() {
		return this.insoluto;
	}

	public void setInsoluto(BigDecimal insoluto) {
		this.insoluto = insoluto;
	}

	public BigDecimal getInteresAnual() {
		return this.interesAnual;
	}

	public void setInteresAnual(BigDecimal interesAnual) {
		this.interesAnual = interesAnual;
	}

	public BigDecimal getInteresCapitalizado() {
		return this.interesCapitalizado;
	}

	public void setInteresCapitalizado(BigDecimal interesCapitalizado) {
		this.interesCapitalizado = interesCapitalizado;
	}

	public BigDecimal getInteresMensual() {
		return this.interesMensual;
	}

	public void setInteresMensual(BigDecimal interesMensual) {
		this.interesMensual = interesMensual;
	}

	public String getNroCuentaBanco() {
		return this.nroCuentaBanco;
	}

	public void setNroCuentaBanco(String nroCuentaBanco) {
		this.nroCuentaBanco = nroCuentaBanco;
	}

	public int getNroDocumento() {
		return this.nroDocumento;
	}

	public void setNroDocumento(int nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public int getNroReferencia() {
		return this.nroReferencia;
	}

	public void setNroReferencia(int nroReferencia) {
		this.nroReferencia = nroReferencia;
	}

	public BigDecimal getReajuste() {
		return this.reajuste;
	}

	public void setReajuste(BigDecimal reajuste) {
		this.reajuste = reajuste;
	}

	public int getSubconceptoId() {
		return this.subconceptoId;
	}

	public void setSubconceptoId(int subconceptoId) {
		this.subconceptoId = subconceptoId;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getTipoUsoId() {
		return this.tipoUsoId;
	}

	public void setTipoUsoId(Integer tipoUsoId) {
		this.tipoUsoId = tipoUsoId;
	}

	public BigDecimal getTotalDeuda() {
		return this.totalDeuda;
	}

	public void setTotalDeuda(BigDecimal totalDeuda) {
		this.totalDeuda = totalDeuda;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getDeudaId() {
		return deudaId;
	}

	public void setDeudaId(Integer deudaId) {
		this.deudaId = deudaId;
	}

}