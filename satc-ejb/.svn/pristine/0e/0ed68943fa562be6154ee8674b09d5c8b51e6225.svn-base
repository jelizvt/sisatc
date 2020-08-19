package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="cd_descargo")
public class CdDescargo implements Serializable{

	private static final long serialVersionUID = 5128025605130859402L;

	@Id
	@Column(name="descargo_id")
	private int descargoId;
	
	@Column(name="tipo_descargo")
	private int tipoDescargo;
	
	@Column(name="tipo_documento_id")
	private Integer tipoDocumentoId;
	
	@Column(name="nro_documento", length=100)
	private String nroDocumento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_documento")
	private Date fechaDocumento;
	
	@Column(name="observacion")
	private String observacion;
	
	@Column(name="deuda_id")
	private int deudaId;
	
	@Column(name="total_descargado")
	private BigDecimal totalDescargado;
	
	@Column(name="interes")
	private BigDecimal interes;
	
	@Column(name="reajuste")
	private BigDecimal reajuste;
		
	@Column(name="total_deuda")
	private BigDecimal totalDeuda;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro_deuda")
	private Date fechaRegistroDeuda;
		
	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="estado" , length=1)
	private String estado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;
		
	@Column(name="terminal", length=20)
	private String terminal;	
	
	private int personaId;

	
	public int getDescargoId() {
		return descargoId;
	}

	public void setDescargoId(int descargoId) {
		this.descargoId = descargoId;
	}

	public int getDeudaId() {
		return deudaId;
	}

	public void setDeudaId(int deudaId) {
		this.deudaId = deudaId;
	}

	public int getTipoDescargo() {
		return tipoDescargo;
	}

	public void setTipoDescargo(int tipoDescargo) {
		this.tipoDescargo = tipoDescargo;
	}

	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}

	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public Date getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
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

	public BigDecimal getTotalDescargado() {
		return totalDescargado;
	}

	public void setTotalDescargado(BigDecimal totalDescargado) {
		this.totalDescargado = totalDescargado;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public BigDecimal getReajuste() {
		return reajuste;
	}

	public void setReajuste(BigDecimal reajuste) {
		this.reajuste = reajuste;
	}

	public BigDecimal getTotalDeuda() {
		return totalDeuda;
	}

	public void setTotalDeuda(BigDecimal totalDeuda) {
		this.totalDeuda = totalDeuda;
	}

	public Date getFechaRegistroDeuda() {
		return fechaRegistroDeuda;
	}

	public void setFechaRegistroDeuda(Date fechaRegistroDeuda) {
		this.fechaRegistroDeuda = fechaRegistroDeuda;
	}

	public int getPersonaId() {
		return personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}
	
	
	
}
