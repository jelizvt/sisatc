package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the dt_zona_seguridad_uso database table.
 * 
 */
@Entity
@Table(name="dt_zona_seguridad_uso")
@NamedQuery(name="getAllDtZonaSeguridadUsoByPeriodo", query="SELECT m FROM DtZonaSeguridadUso m WHERE m.estado='1' AND m.zonaSeguridadId=:p_zona_seguridad_id AND m.periodo=:p_periodo ")
public class DtZonaSeguridadUso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="zona_seguridad_uso_id")
	private Integer zonaSeguridadUsoId;

	private String estado;

	@Column(name="tasa_mensual")
	private BigDecimal tasaMensual;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private Integer periodo;

	private String terminal;

	@Column(name="tipo_uso_id")
	private Integer tipoUsoId;

	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="zona_seguridad_id")
	private Integer zonaSeguridadId;

    public DtZonaSeguridadUso() {
    }

	public Integer getZonaSeguridadUsoId() {
		return this.zonaSeguridadUsoId;
	}

	public void setZonaSeguridadUsoId(Integer zonaSeguridadUsoId) {
		this.zonaSeguridadUsoId = zonaSeguridadUsoId;
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

	public Integer getTipoUsoId() {
		return this.tipoUsoId;
	}

	public void setTipoUsoId(Integer tipoUsoId) {
		this.tipoUsoId = tipoUsoId;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getZonaSeguridadId() {
		return this.zonaSeguridadId;
	}

	public void setZonaSeguridadId(Integer zonaSeguridadId) {
		this.zonaSeguridadId = zonaSeguridadId;
	}
	
	public BigDecimal getTasaMensual() {
		return tasaMensual;
	}

	public void setTasaMensual(BigDecimal tasaMensual) {
		this.tasaMensual = tasaMensual;
	}

}