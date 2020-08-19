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

@Entity
@Table(name="dt_matriz_seguridad")
@NamedQueries(
	{
		@NamedQuery(name="getAllDtZonaSeguridadUso2016ByPeriodo", query="SELECT m FROM DtMatrizSeguridad m WHERE m.estado='1' AND m.zonaSeguridadId=:p_zona_seguridad_id AND m.periodo=:p_periodo "),
		@NamedQuery(name="getAllDtZonaSeguridadUso2016All", query="SELECT m FROM DtMatrizSeguridad m WHERE m.estado='1' AND m.periodo=:p_periodo "),
	}
)
public class DtMatrizSeguridad implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="zona_seguridad_uso_id")
	private Integer zonaSeguridadUsoId;

	private String estado;

	@Column(name="tasa_mensual")
	private BigDecimal tasaMensual;
	
	@Column(name="tasa_anual")
	private BigDecimal tasaAnual;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private Integer periodo;

	private String terminal;

	@Column(name="categoria_uso_seguridad_id")
	private Integer categoriaUsoSeguridadId;

	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="zona_seguridad_id")
	private Integer zonaSeguridadId;
	
	public DtMatrizSeguridad (){
    }

	public Integer getZonaSeguridadUsoId() {
		return zonaSeguridadUsoId;
	}

	public void setZonaSeguridadUsoId(Integer zonaSeguridadUsoId) {
		this.zonaSeguridadUsoId = zonaSeguridadUsoId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getTasaMensual() {
		return tasaMensual;
	}

	public void setTasaMensual(BigDecimal tasaMensual) {
		this.tasaMensual = tasaMensual;
	}

	public BigDecimal getTasaAnual() {
		return tasaAnual;
	}

	public void setTasaAnual(BigDecimal tasaAnual) {
		this.tasaAnual = tasaAnual;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getCategoriaUsoSeguridadId() {
		return categoriaUsoSeguridadId;
	}

	public void setCategoriaUsoSeguridadId(Integer categoriaUsoSeguridadId) {
		this.categoriaUsoSeguridadId = categoriaUsoSeguridadId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getZonaSeguridadId() {
		return zonaSeguridadId;
	}

	public void setZonaSeguridadId(Integer zonaSeguridadId) {
		this.zonaSeguridadId = zonaSeguridadId;
	}
	

}
