package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the rp_djuso database table.
 * 
 */
@Entity
@Table(name="rp_djuso")
@NamedQuery(name="getAllRpDjusoByArbitrioId", query="SELECT m FROM RpDjuso m WHERE m.estado='1' AND m.djarbitrioId=:p_arbitrio_id ")
public class RpDjuso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="djarbitrio_id")
	private int djarbitrioId;

	@Id
	@Column(name="djuso_id")
	private int djusoId;

	@Column(name="anno_afectacion")
	private int annoAfectacion;

	@Column(name="area_uso")
	private BigDecimal areaUso;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Transient
	private String mesFin;

	@Transient
	private String mesInicio;

	@Column(name="mes_fin")
	private String mesFinId;

	@Column(name="mes_inicio")
	private String mesInicioId;
	
	private String terminal;

	@Column(name="tipo_uso_id")
	private int tipoUsoId;

	@Column(name="usuario_id")
	private int usuarioId;

	@Transient
	private String tipoUsoDescripcion;
	
	@Transient
	private int item;
	
	public RpDjuso() {
    }

	public int getAnnoAfectacion() {
		return this.annoAfectacion;
	}

	public void setAnnoAfectacion(int annoAfectacion) {
		this.annoAfectacion = annoAfectacion;
	}

	public BigDecimal getAreaUso() {
		return this.areaUso;
	}

	public void setAreaUso(BigDecimal areaUso) {
		this.areaUso = areaUso;
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

	public String getMesFin() {
		return this.mesFin;
	}

	public void setMesFin(String mesFin) {
		this.mesFin = mesFin;
	}

	public String getMesInicio() {
		return this.mesInicio;
	}

	public void setMesInicio(String mesInicio) {
		this.mesInicio = mesInicio;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getTipoUsoId() {
		return this.tipoUsoId;
	}

	public void setTipoUsoId(int tipoUsoId) {
		this.tipoUsoId = tipoUsoId;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	public int getDjarbitrioId() {
		return djarbitrioId;
	}

	public void setDjarbitrioId(int djarbitrioId) {
		this.djarbitrioId = djarbitrioId;
	}

	public int getDjusoId() {
		return djusoId;
	}

	public void setDjusoId(int djusoId) {
		this.djusoId = djusoId;
	}
	public String getTipoUsoDescripcion() {
		return tipoUsoDescripcion;
	}

	public void setTipoUsoDescripcion(String tipoUsoDescripcion) {
		this.tipoUsoDescripcion = tipoUsoDescripcion;
	}
	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}
	public String getMesFinId() {
		return mesFinId;
	}

	public void setMesFinId(String mesFinId) {
		this.mesFinId = mesFinId;
	}

	public String getMesInicioId() {
		return mesInicioId;
	}

	public void setMesInicioId(String mesInicioId) {
		this.mesInicioId = mesInicioId;
	}
}