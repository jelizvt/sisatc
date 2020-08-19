package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="gn_ubicacion")
public class GnUbicacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ubicacion_id")
	private int ubicacionId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="lado_cuadra")
	private int ladoCuadra;

	@Column(name="numero_cuadra")
	private int numeroCuadra;

	@Column(name="numero_manzana")
	private int numeroManzana;

	@Column(name="sector_lugar_id")
	private int sectorLugarId;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

	@Column(name="via_id")
	private int viaId;

    public GnUbicacion() {
    }

	public int getUbicacionId() {
		return this.ubicacionId;
	}

	public void setUbicacionId(int ubicacionId) {
		this.ubicacionId = ubicacionId;
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

	public int getLadoCuadra() {
		return this.ladoCuadra;
	}

	public void setLadoCuadra(int ladoCuadra) {
		this.ladoCuadra = ladoCuadra;
	}

	public int getNumeroCuadra() {
		return this.numeroCuadra;
	}

	public void setNumeroCuadra(int numeroCuadra) {
		this.numeroCuadra = numeroCuadra;
	}

	public int getNumeroManzana() {
		return this.numeroManzana;
	}

	public void setNumeroManzana(int numeroManzana) {
		this.numeroManzana = numeroManzana;
	}

	public int getSectorLugarId() {
		return this.sectorLugarId;
	}

	public void setSectorLugarId(int sectorLugarId) {
		this.sectorLugarId = sectorLugarId;
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

	public int getViaId() {
		return this.viaId;
	}

	public void setViaId(int viaId) {
		this.viaId = viaId;
	}

}