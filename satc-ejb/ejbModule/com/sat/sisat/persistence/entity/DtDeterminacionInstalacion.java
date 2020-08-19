package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dt_determinacion_instalacion")
public class DtDeterminacionInstalacion implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="determinacion_instalacion_id")
	private Integer determinacionInstalacionId;
	
	@Column(name="determinacion_id")
	private Integer determinacionId;

	@Column(name="deter_predio_id")
	private Integer deterPredioId;

	@Column(name="dj_id")
	private Integer djId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="instalacion_id")
	private Integer instalacionId;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="valor_instalacion")
	private BigDecimal valorInstalacion;
	
    public Integer getDeterminacionInstalacionId() {
		return determinacionInstalacionId;
	}

	public void setDeterminacionInstalacionId(Integer determinacionInstalacionId) {
		this.determinacionInstalacionId = determinacionInstalacionId;
	}

	public Integer getDeterminacionId() {
		return determinacionId;
	}

	public void setDeterminacionId(Integer determinacionId) {
		this.determinacionId = determinacionId;
	}

	public Integer getDeterPredioId() {
		return deterPredioId;
	}

	public void setDeterPredioId(Integer deterPredioId) {
		this.deterPredioId = deterPredioId;
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

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getInstalacionId() {
		return instalacionId;
	}

	public void setInstalacionId(Integer instalacionId) {
		this.instalacionId = instalacionId;
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

	public BigDecimal getValorInstalacion() {
		return valorInstalacion;
	}

	public void setValorInstalacion(BigDecimal valorInstalacion) {
		this.valorInstalacion = valorInstalacion;
	}

	public DtDeterminacionInstalacion() {
    	
    }

	

}
