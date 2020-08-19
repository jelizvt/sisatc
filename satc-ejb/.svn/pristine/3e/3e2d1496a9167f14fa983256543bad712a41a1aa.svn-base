/**
 * @author Liliana Aliaga
 * @version 1.0 
 * @since 21/02/2012
 * Entidad Registro de Beneficio
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
 * The persistent class for the cj_beneficio database table.
 * 
 */
@Entity
@Table(name="cj_beneficio")
public class CjBeneficio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="beneficio_id")
	private int beneficioId;
	

	@Column(name="anno_beneficio")
	private int annoBeneficio;

	@Column(name="cuota_beneficio")
	private int cuotaBeneficio;

	private String estado;

	@Column(name="fecha_fin")
	private Timestamp fechaFin;

	@Column(name="fecha_inicio")
	private Timestamp fechaInicio;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="tipo_beneficio")
	private String tipoBeneficio;

	@Column(name="usuario_id")
	private int usuarioId;

	@Column(name="valor_beneficio")
	private BigDecimal valorBeneficio;

    public CjBeneficio() {
    }

	public int getBeneficioId() {
		return this.beneficioId;
	}

	public void setBeneficioId(int beneficioId) {
		this.beneficioId = beneficioId;
	}

	public int getAnnoBeneficio() {
		return this.annoBeneficio;
	}

	public void setAnnoBeneficio(int annoBeneficio) {
		this.annoBeneficio = annoBeneficio;
	}

	public int getCuotaBeneficio() {
		return this.cuotaBeneficio;
	}

	public void setCuotaBeneficio(int cuotaBeneficio) {
		this.cuotaBeneficio = cuotaBeneficio;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Timestamp getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTipoBeneficio() {
		return this.tipoBeneficio;
	}

	public void setTipoBeneficio(String tipoBeneficio) {
		this.tipoBeneficio = tipoBeneficio;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public BigDecimal getValorBeneficio() {
		return this.valorBeneficio;
	}

	public void setValorBeneficio(BigDecimal valorBeneficio) {
		this.valorBeneficio = valorBeneficio;
	}

}