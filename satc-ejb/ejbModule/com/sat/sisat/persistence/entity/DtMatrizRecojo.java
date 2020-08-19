package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the dt_matriz_recojo database table.
 * 
 */
@Entity
@Table(name="dt_matriz_recojo")
@NamedQueries({
	@NamedQuery(name="getDtMatrizRecojoByFrecuencia", query="SELECT m FROM DtMatrizRecojo m WHERE m.estado='1' AND m.frecuencia=:p_frecuencia AND m.periodo=:p_periodo "),
	@NamedQuery(name="getDtMatrizRecojoByPeriodo", query="SELECT m FROM DtMatrizRecojo m WHERE m.estado='1' AND m.periodo=:p_periodo ")
})

public class DtMatrizRecojo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="matriz_recojo_id")
	private Integer matrizRecojoId;

	@Column(name="categoria_uso_id")
	private Integer categoriaUsoId;

	@Column(name="costo_m2_anual")
	private BigDecimal costoM2Anual;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private Integer frecuencia;

	private Integer periodo;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="zona_recoleccion_id")
	private Integer zonaRecoleccionId;

    public DtMatrizRecojo() {
    }

	public Integer getMatrizRecojoId() {
		return this.matrizRecojoId;
	}

	public void setMatrizRecojoId(Integer matrizRecojoId) {
		this.matrizRecojoId = matrizRecojoId;
	}

	public Integer getCategoriaUsoId() {
		return this.categoriaUsoId;
	}

	public void setCategoriaUsoId(Integer categoriaUsoId) {
		this.categoriaUsoId = categoriaUsoId;
	}

	public BigDecimal getCostoM2Anual() {
		return this.costoM2Anual;
	}

	public void setCostoM2Anual(BigDecimal costoM2Anual) {
		this.costoM2Anual = costoM2Anual;
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

	public Integer getFrecuencia() {
		return this.frecuencia;
	}

	public void setFrecuencia(Integer frecuencia) {
		this.frecuencia = frecuencia;
	}

	public Integer getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
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

	public Integer getZonaRecoleccionId() {
		return this.zonaRecoleccionId;
	}

	public void setZonaRecoleccionId(Integer zonaRecoleccionId) {
		this.zonaRecoleccionId = zonaRecoleccionId;
	}

}