/**
 * @author Liliana Aliaga
 * @version 1.0 
 * @since 21/02/2012
 * Entidad Registro Pago
 */

package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * The persistent class for the cj_pago database table.
 * 
 */
@Entity
@Table(name="cj_pago")
public class CjPago implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="pago_id")
	private int pagoId;
	
	@Column(name="recibo_id")
	private int reciboId;
	
	@Column(name="persona_id")
	private Integer personaId;

	@Column(name="deuda_id")
	private int deudaId;
	
	@Column(name = "tipo_uso_id", nullable = true)
	private Integer tipoUsoId;
	
	@Column(name="tipo_operacion")
	private String tipoOperacion;
	
	@Column(name="insoluto")
	private BigDecimal insoluto;
	
	@Column(name="reajuste")
	private BigDecimal reajuste;
	
	@Column(name="derecho_emision")
	private BigDecimal derechoEmision;
	
	@Column(name="interes_capitalizado")
	private BigDecimal interesCapitalizado;
	
	@Column(name="interes_mensual")
	private BigDecimal interesMensual;
	
	@Column(name="interes")
	private BigDecimal interes;
	
	@Column(name="monto_beneficio")
	private BigDecimal montoBeneficio;
	
	@Column(name="monto_pago")
	private BigDecimal montoPago;
	
	@Column(name="fecha_pago")
	private Timestamp fechaPago;
	
	@Column(name="flag_extorno")
	private String flagExtorno;
	
	@Column(name="fecha_extorno")
	private Timestamp fechaExtorno;
	
	@Column(name="referencia")
	private String referencia;
	
	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="estado")
	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
	@Column(name="terminal")
	private String terminal;
	
	@Column(name="apertura_id")
	private int aperturaId;
	
	@Column(name="fecha_upd")
	private Timestamp fechaUpd;
	
	@Column(name="terminal_upd")
	private String terminalUpd;
	
	@Column(name="usuario_upd_id")
	private Integer usuarioUpdId;

    public CjPago() {
    }

	public int getPagoId() {
		return pagoId;
	}

	public void setPagoId(int pagoId) {
		this.pagoId = pagoId;
	}

	public int getReciboId() {
		return reciboId;
	}

	public void setReciboId(int reciboId) {
		this.reciboId = reciboId;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public int getDeudaId() {
		return deudaId;
	}

	public void setDeudaId(int deudaId) {
		this.deudaId = deudaId;
	}

	public Integer getTipoUsoId() {
		return tipoUsoId;
	}

	public void setTipoUsoId(Integer tipoUsoId) {
		this.tipoUsoId = tipoUsoId;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public BigDecimal getInsoluto() {
		return insoluto;
	}

	public void setInsoluto(BigDecimal insoluto) {
		this.insoluto = insoluto;
	}

	public BigDecimal getReajuste() {
		return reajuste;
	}

	public void setReajuste(BigDecimal reajuste) {
		this.reajuste = reajuste;
	}

	public BigDecimal getDerechoEmision() {
		return derechoEmision;
	}

	public void setDerechoEmision(BigDecimal derechoEmision) {
		this.derechoEmision = derechoEmision;
	}

	public BigDecimal getInteresCapitalizado() {
		return interesCapitalizado;
	}

	public void setInteresCapitalizado(BigDecimal interesCapitalizado) {
		this.interesCapitalizado = interesCapitalizado;
	}

	public BigDecimal getInteresMensual() {
		return interesMensual;
	}

	public void setInteresMensual(BigDecimal interesMensual) {
		this.interesMensual = interesMensual;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public BigDecimal getMontoBeneficio() {
		return montoBeneficio;
	}

	public void setMontoBeneficio(BigDecimal montoBeneficio) {
		this.montoBeneficio = montoBeneficio;
	}

	public BigDecimal getMontoPago() {
		return montoPago;
	}

	public void setMontoPago(BigDecimal montoPago) {
		this.montoPago = montoPago;
	}

	public Timestamp getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Timestamp fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getFlagExtorno() {
		return flagExtorno;
	}

	public void setFlagExtorno(String flagExtorno) {
		this.flagExtorno = flagExtorno;
	}

	public Timestamp getFechaExtorno() {
		return fechaExtorno;
	}

	public void setFechaExtorno(Timestamp fechaExtorno) {
		this.fechaExtorno = fechaExtorno;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
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

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getAperturaId() {
		return aperturaId;
	}

	public void setAperturaId(int aperturaId) {
		this.aperturaId = aperturaId;
	}

	public Timestamp getFechaUpd() {
		return fechaUpd;
	}

	public void setFechaUpd(Timestamp fechaUpd) {
		this.fechaUpd = fechaUpd;
	}

	public String getTerminalUpd() {
		return terminalUpd;
	}

	public void setTerminalUpd(String terminalUpd) {
		this.terminalUpd = terminalUpd;
	}

	public Integer getUsuarioUpdId() {
		return usuarioUpdId;
	}

	public void setUsuarioUpdId(Integer usuarioUpdId) {
		this.usuarioUpdId = usuarioUpdId;
	}
}