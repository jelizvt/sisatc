package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the rp_otros_frentes database table.
 * 
 */
@Entity
@Table(name="rp_otros_frentes")
@NamedQuery(name="getAllRpOtrosFrenteByDjId", query="SELECT f FROM RpOtrosFrente f WHERE f.estado='1' AND  f.djId=:p_dj_id ")
public class RpOtrosFrente implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Column(name="dj_id")
	private int djId;

	@Id	
	@Column(name="otro_frente_id")
	private int otroFrenteId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="nro_puerta_1")
	private String nroPuerta1;

	@Column(name="nro_puerta_2")
	private String nroPuerta2;

	@Column(name="nro_puerta_3")
	private String nroPuerta3;

	@Column(name="nro_puerta_4")
	private String nroPuerta4;

	private String referencia;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="ubicacion_id")
	private Integer ubicacionId;
	
	private String numero1;
	
	@Column(name="frente")
	private BigDecimal frente;
	
	@Transient
	private String tipoVia;
	@Transient
	private String nombreVia;
	@Transient
	private int item;
	
	public RpOtrosFrente() {
    }
	
    public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
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

	public String getNroPuerta1() {
		return this.nroPuerta1;
	}

	public void setNroPuerta1(String nroPuerta1) {
		this.nroPuerta1 = nroPuerta1;
	}

	public String getNroPuerta2() {
		return this.nroPuerta2;
	}

	public void setNroPuerta2(String nroPuerta2) {
		this.nroPuerta2 = nroPuerta2;
	}

	public String getNroPuerta3() {
		return this.nroPuerta3;
	}

	public void setNroPuerta3(String nroPuerta3) {
		this.nroPuerta3 = nroPuerta3;
	}

	public String getNroPuerta4() {
		return this.nroPuerta4;
	}

	public void setNroPuerta4(String nroPuerta4) {
		this.nroPuerta4 = nroPuerta4;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getDjId() {
		return this.djId;
	}
	public void setDjId(int djId) {
		this.djId = djId;
	}
	public int getOtroFrenteId() {
		return this.otroFrenteId;
	}
	public void setOtroFrenteId(int otroFrenteId) {
		this.otroFrenteId = otroFrenteId;
	}
	public String getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public String getNumero1() {
		return numero1;
	}

	public void setNumero1(String numero1) {
		this.numero1 = numero1;
	}

	public Integer getUbicacionId() {
		return ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public BigDecimal getFrente() {
		return frente;
	}

	public void setFrente(BigDecimal frente) {
		this.frente = frente;
	}
}