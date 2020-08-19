package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="dt_condi_especial_contri")
@NamedQuery(name="getDtCondiEspecialContriByPeriodo", query="SELECT m FROM DtCondiEspecialContri m WHERE m.periodo=:p_periodo AND m.conceptoId=:p_concepto_id AND m.subconceptoId=:p_subconcepto_id AND m.tipoCondEspecialId=:p_tipo_cond_especial_id AND m.usoPredioId=:p_uso_predio_id")
public class DtCondiEspecialContri implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="condi_especial_contri_id")
	private int condiEspecialContriId;

	@Column(name="concepto_id")
	private int conceptoId;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private int periodo;

	@Column(name="subconcepto_id")
	private int subconceptoId;

	private String terminal;

	private String tipo;

	@Column(name="tipo_cond_especial_id")
	private int tipoCondEspecialId;

	@Column(name="uso_predio_id")
	private int usoPredioId;

	private BigDecimal valor;

    public DtCondiEspecialContri() {
    }

	public int getCondiEspecialContriId() {
		return this.condiEspecialContriId;
	}

	public void setCondiEspecialContriId(int condiEspecialContriId) {
		this.condiEspecialContriId = condiEspecialContriId;
	}

	public int getConceptoId() {
		return this.conceptoId;
	}

	public void setConceptoId(int conceptoId) {
		this.conceptoId = conceptoId;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
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

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getTipoCondEspecialId() {
		return this.tipoCondEspecialId;
	}

	public void setTipoCondEspecialId(int tipoCondEspecialId) {
		this.tipoCondEspecialId = tipoCondEspecialId;
	}

	public int getUsoPredioId() {
		return this.usoPredioId;
	}

	public void setUsoPredioId(int usoPredioId) {
		this.usoPredioId = usoPredioId;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
