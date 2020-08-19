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
@Table(name="dt_determinacion_predio")
@NamedQuery(name="getAllDtDeterminacionPredioByDeterminacionId", query="SELECT m FROM DtDeterminacionPredio m WHERE m.estado='1' AND m.determinacionId=:p_determinacion_id")
public class DtDeterminacionPredio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="deter_predio_id")
	private Integer deterPredioId;
	
	@Column(name="determinacion_id")
	private Integer determinacionId;

	private BigDecimal arancel;

	@Column(name="area_terreno")
	private BigDecimal areaTerreno;

	@Column(name="base_afecta")
	private BigDecimal baseAfecta;

	@Column(name="base_exonerada")
	private BigDecimal baseExonerada;

	@Column(name="base_imponible")
	private BigDecimal baseImponible;

	@Column(name="dj_id")
	private Integer djId;

	private String estado;

	@Column(name="fecha_adquisicion")
	private Timestamp fechaAdquisicion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="flag_inafectacion")
	private String flagInafectacion;

	@Column(name="porc_propiedad")
	private BigDecimal porcPropiedad;

	@Column(name="predio_id")
	private Integer predioId;

	private String terminal;

	@Column(name="tipo_inafectacion")
	private String tipoInafectacion;

	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="valor_construccion")
	private BigDecimal valorConstruccion;

	@Column(name="valor_impuesto")
	private BigDecimal valorImpuesto;

	@Column(name="valor_inafectacion")
	private BigDecimal valorInafectacion;

	@Column(name="valor_instalacion")
	private BigDecimal valorInstalacion;

	@Column(name="valor_predio")
	private BigDecimal valorPredio;

	@Column(name="valor_terreno")
	private BigDecimal valorTerreno;
	
	@Column(name="valor_autovaluo_alcabala")
	private BigDecimal valorAutovaluoAlcabala;

    public Integer getDeterPredioId() {
		return deterPredioId;
	}

	public void setDeterPredioId(Integer deterPredioId) {
		this.deterPredioId = deterPredioId;
	}

	public Integer getDeterminacionId() {
		return determinacionId;
	}

	public void setDeterminacionId(Integer determinacionId) {
		this.determinacionId = determinacionId;
	}

	public BigDecimal getArancel() {
		return arancel;
	}

	public void setArancel(BigDecimal arancel) {
		this.arancel = arancel;
	}

	public BigDecimal getAreaTerreno() {
		return areaTerreno;
	}

	public void setAreaTerreno(BigDecimal areaTerreno) {
		this.areaTerreno = areaTerreno;
	}

	public BigDecimal getBaseAfecta() {
		return baseAfecta;
	}

	public void setBaseAfecta(BigDecimal baseAfecta) {
		this.baseAfecta = baseAfecta;
	}

	public BigDecimal getBaseExonerada() {
		return baseExonerada;
	}

	public void setBaseExonerada(BigDecimal baseExonerada) {
		this.baseExonerada = baseExonerada;
	}

	public BigDecimal getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}

	public Integer getDjId() {
		return djId;
	}

	public void setDjId(Integer djId) {
		this.djId = djId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	public void setFechaAdquisicion(Timestamp fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFlagInafectacion() {
		return flagInafectacion;
	}

	public void setFlagInafectacion(String flagInafectacion) {
		this.flagInafectacion = flagInafectacion;
	}

	public BigDecimal getPorcPropiedad() {
		return porcPropiedad;
	}

	public void setPorcPropiedad(BigDecimal porcPropiedad) {
		this.porcPropiedad = porcPropiedad;
	}

	public Integer getPredioId() {
		return predioId;
	}

	public void setPredioId(Integer predioId) {
		this.predioId = predioId;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTipoInafectacion() {
		return tipoInafectacion;
	}

	public void setTipoInafectacion(String tipoInafectacion) {
		this.tipoInafectacion = tipoInafectacion;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public BigDecimal getValorConstruccion() {
		return valorConstruccion;
	}

	public void setValorConstruccion(BigDecimal valorConstruccion) {
		this.valorConstruccion = valorConstruccion;
	}

	public BigDecimal getValorImpuesto() {
		return valorImpuesto;
	}

	public void setValorImpuesto(BigDecimal valorImpuesto) {
		this.valorImpuesto = valorImpuesto;
	}

	public BigDecimal getValorInafectacion() {
		return valorInafectacion;
	}

	public void setValorInafectacion(BigDecimal valorInafectacion) {
		this.valorInafectacion = valorInafectacion;
	}

	public BigDecimal getValorInstalacion() {
		return valorInstalacion;
	}

	public void setValorInstalacion(BigDecimal valorInstalacion) {
		this.valorInstalacion = valorInstalacion;
	}

	public BigDecimal getValorPredio() {
		return valorPredio;
	}

	public void setValorPredio(BigDecimal valorPredio) {
		this.valorPredio = valorPredio;
	}

	public BigDecimal getValorTerreno() {
		return valorTerreno;
	}

	public void setValorTerreno(BigDecimal valorTerreno) {
		this.valorTerreno = valorTerreno;
	}

	public DtDeterminacionPredio() {
    }

	public BigDecimal getValorAutovaluoAlcabala() {
		return valorAutovaluoAlcabala;
	}

	public void setValorAutovaluoAlcabala(BigDecimal valorAutovaluoAlcabala) {
		this.valorAutovaluoAlcabala = valorAutovaluoAlcabala;
	}

	

}
