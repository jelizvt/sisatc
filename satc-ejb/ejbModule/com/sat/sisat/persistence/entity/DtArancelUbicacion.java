package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the dt_tasa_vehicular database table.
 * 
 */
@Entity
@Table(name="dt_arancel_ubicacion")
@NamedQuery(name="getDtArancelUbicacionByPeriodo", query="SELECT m FROM DtArancelUbicacion m WHERE m.estado='1' AND m.ubicacionId=:p_ubicacion_id AND m.periodo=:p_periodo")
public class DtArancelUbicacion implements Serializable {
	@Id
	@Column(name="arancel_ubicacion_id")
	private Integer arancelUbicacionId;
	
	@Column(name="periodo")
	private Integer periodo;
	
	@Column(name="ubicacion_id")
	private Integer ubicacionId;
	
	@Column(name="valor_arancel")
	private Double valorArancel;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
	@Column(name="terminal")
	private String terminal;

	public Integer getArancelUbicacionId() {
		return arancelUbicacionId;
	}

	public void setArancelUbicacionId(Integer arancelUbicacionId) {
		this.arancelUbicacionId = arancelUbicacionId;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public Integer getUbicacionId() {
		return ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public Double getValorArancel() {
		return valorArancel;
	}

	public void setValorArancel(Double valorArancel) {
		this.valorArancel = valorArancel;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}