/**
 * @author Liliana Aliaga
 * @version 1.0 
 * @since 21/02/2012
 * Entidad Registro de Partida 
 */
package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * The persistent class for the cj_partida database table.
 * 
 */
@Entity
@Table(name="cj_partida")
public class CjPartida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="partida_id")
	private int partidaId;

	@Column(name="cj__partida_id")
	private int cjPartidaId;

	private String descripcion;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private int nivel;

	@Column(name="nro_partida")
	private String nroPartida;

	private String terminal;

	@Column(name="total_insoluto")
	private BigDecimal totalInsoluto;

	@Column(name="total_interes")
	private BigDecimal totalInteres;

	@Column(name="total_reajuste")
	private BigDecimal totalReajuste;

	@Column(name="usuario_id")
	private int usuarioId;

    public CjPartida() {
    }

	public int getPartidaId() {
		return this.partidaId;
	}

	public void setPartidaId(int partidaId) {
		this.partidaId = partidaId;
	}

	public int getCjPartidaId() {
		return this.cjPartidaId;
	}

	public void setCjPartidaId(int cjPartidaId) {
		this.cjPartidaId = cjPartidaId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public int getNivel() {
		return this.nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public String getNroPartida() {
		return this.nroPartida;
	}

	public void setNroPartida(String nroPartida) {
		this.nroPartida = nroPartida;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public BigDecimal getTotalInsoluto() {
		return this.totalInsoluto;
	}

	public void setTotalInsoluto(BigDecimal totalInsoluto) {
		this.totalInsoluto = totalInsoluto;
	}

	public BigDecimal getTotalInteres() {
		return this.totalInteres;
	}

	public void setTotalInteres(BigDecimal totalInteres) {
		this.totalInteres = totalInteres;
	}

	public BigDecimal getTotalReajuste() {
		return this.totalReajuste;
	}

	public void setTotalReajuste(BigDecimal totalReajuste) {
		this.totalReajuste = totalReajuste;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}
