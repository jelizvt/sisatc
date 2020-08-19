/**
 * @author Liliana Aliaga
 * @version 1.0 
 * @since 21/02/2012
 * Entidad Registro de Pagos 
 */
package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;




/**
 * The persistent class for the cj_forma_pago database table.
 * 
 */
@Entity
@Table(name="cj_forma_pago")
public class CjFormaPago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="forma_pago_id")
	private int formaPagoId;

	private String descripcion;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="nombre_banco")
	private String nombreBanco;

	private String terminal;

    public CjFormaPago() {
    }

	public int getFormaPagoId() {
		return this.formaPagoId;
	}

	public void setFormaPagoId(int formaPagoId) {
		this.formaPagoId = formaPagoId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionCorta() {
		return this.descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
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

	public String getNombreBanco() {
		return this.nombreBanco;
	}

	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

}