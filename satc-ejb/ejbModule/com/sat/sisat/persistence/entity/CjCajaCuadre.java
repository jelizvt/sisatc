/**
 * @author Liliana Aliaga
 * @version 1.0 
 * @since 21/02/2012
 * Entidad Registro de CajaCuadre
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
 * The persistent class for the cj_caja_cuadre database table.
 * 
 */
@Entity
@Table(name="cj_caja_cuadre")
public class CjCajaCuadre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cuadre_id")
	private int cuadreId;

	@Column(name="apertura_id")
	private int aperturaId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="forma_pago_id")
	private int formaPagoId;

	@Column(name="monto_cuadre")
	private BigDecimal montoCuadre;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public CjCajaCuadre() {
    }

	public int getCuadreId() {
		return this.cuadreId;
	}

	public void setCuadreId(int cuadreId) {
		this.cuadreId = cuadreId;
	}

	public int getAperturaId() {
		return this.aperturaId;
	}

	public void setAperturaId(int aperturaId) {
		this.aperturaId = aperturaId;
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

	public int getFormaPagoId() {
		return this.formaPagoId;
	}

	public void setFormaPagoId(int formaPagoId) {
		this.formaPagoId = formaPagoId;
	}

	public BigDecimal getMontoCuadre() {
		return this.montoCuadre;
	}

	public void setMontoCuadre(BigDecimal montoCuadre) {
		this.montoCuadre = montoCuadre;
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

}