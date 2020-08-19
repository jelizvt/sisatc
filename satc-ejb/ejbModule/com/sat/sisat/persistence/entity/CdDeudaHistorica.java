package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the cd_deuda_historica database table.
 * 
 */
@Entity
@Table(name="cd_deuda_historica")
public class CdDeudaHistorica implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CdDeudaHistoricaPK id;

	@Column(name="cuenta_referencia")
	private String cuentaReferencia;

	@Column(name="determinacion_id")
	private Integer determinacionId;

	private String estado;

	@Column(name="fecha_movimiento")
	private Timestamp fechaMovimiento;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="fecha_vencimiento")
	private Timestamp fechaVencimiento;

	private BigDecimal insoluto;

	@Column(name="interes_anual")
	private BigDecimal interesAnual;

	@Column(name="interes_capitalizado")
	private BigDecimal interesCapitalizado;

	@Column(name="interes_mensual")
	private BigDecimal interesMensual;

	@Column(name="nro_docu_sustento")
	private String nroDocuSustento;

	@Column(name="persona_id")
	private Integer personaId;

	private String terminal;

	@Column(name="tipo_deuda")
	private Integer tipoDeuda;

	@Column(name="tipo_movimiento_id")
	private Integer tipoMovimientoId;

	private BigDecimal total;

	@Column(name="usuario_id")
	private Integer usuarioId;

    public CdDeudaHistorica() {
    }

	public CdDeudaHistoricaPK getId() {
		return this.id;
	}

	public void setId(CdDeudaHistoricaPK id) {
		this.id = id;
	}
	
	public String getCuentaReferencia() {
		return this.cuentaReferencia;
	}

	public void setCuentaReferencia(String cuentaReferencia) {
		this.cuentaReferencia = cuentaReferencia;
	}

	public Integer getDeterminacionId() {
		return this.determinacionId;
	}

	public void setDeterminacionId(Integer determinacionId) {
		this.determinacionId = determinacionId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaMovimiento() {
		return this.fechaMovimiento;
	}

	public void setFechaMovimiento(Timestamp fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
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

	public String getNroDocuSustento() {
		return this.nroDocuSustento;
	}

	public void setNroDocuSustento(String nroDocuSustento) {
		this.nroDocuSustento = nroDocuSustento;
	}

	public Integer getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getTipoDeuda() {
		return this.tipoDeuda;
	}

	public void setTipoDeuda(Integer tipoDeuda) {
		this.tipoDeuda = tipoDeuda;
	}

	public Integer getTipoMovimientoId() {
		return this.tipoMovimientoId;
	}

	public void setTipoMovimientoId(Integer tipoMovimientoId) {
		this.tipoMovimientoId = tipoMovimientoId;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

}