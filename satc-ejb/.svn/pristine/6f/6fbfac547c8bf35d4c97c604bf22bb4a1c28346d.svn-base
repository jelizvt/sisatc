package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="dt_tasa_impuesto_predial")
@NamedQuery(name="getAllDtTasaImpuestoPredialByPeriodo", query="SELECT m FROM DtTasaImpuestoPredial m WHERE m.estadoId='1' AND m.id.periodo=:p_periodo ORDER BY m.id.tramoId ASC")
public class DtTasaImpuestoPredial implements Serializable {
	private static final long serialVersionUID = 1L;
	

	@EmbeddedId
	private DtTasaImpuestoPredialPK id;
	
	private BigDecimal acumulativo;

	@Column(name="estado")
	private String estadoId;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="monto_fin")
	private BigDecimal montoFin;

	@Column(name="monto_inicio")
	private BigDecimal montoInicio;

	@Column(name="monto_tasa")
	private BigDecimal montoTasa;

	private Integer orden;

	@Column(name="rango_fin")
	private Integer rangoFin;

	@Column(name="rango_inicio")
	private Integer rangoInicio;

	private BigDecimal tasa;

	private String terminal;

	@Column(name="tipo_tasa")
	private String tipoTasa;

	@Column(name="usuario_id")
	private Integer usuarioId;

    public BigDecimal getAcumulativo() {
		return acumulativo;
	}

	public void setAcumulativo(BigDecimal acumulativo) {
		this.acumulativo = acumulativo;
	}

	public String getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public BigDecimal getMontoFin() {
		return montoFin;
	}

	public void setMontoFin(BigDecimal montoFin) {
		this.montoFin = montoFin;
	}

	public BigDecimal getMontoInicio() {
		return montoInicio;
	}

	public void setMontoInicio(BigDecimal montoInicio) {
		this.montoInicio = montoInicio;
	}

	public BigDecimal getMontoTasa() {
		return montoTasa;
	}

	public void setMontoTasa(BigDecimal montoTasa) {
		this.montoTasa = montoTasa;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getRangoFin() {
		return rangoFin;
	}

	public void setRangoFin(Integer rangoFin) {
		this.rangoFin = rangoFin;
	}

	public Integer getRangoInicio() {
		return rangoInicio;
	}

	public void setRangoInicio(Integer rangoInicio) {
		this.rangoInicio = rangoInicio;
	}

	public BigDecimal getTasa() {
		return tasa;
	}

	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public DtTasaImpuestoPredial() {
    }

	public DtTasaImpuestoPredialPK getId() {
		return id;
	}

	public void setId(DtTasaImpuestoPredialPK id) {
		this.id = id;
	}	
}
