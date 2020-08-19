package com.sat.sisat.controlycobranza.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class FindCcRec implements Serializable {
	
	private String nroDocumento;	
	private String descripcionPersona;	
	private Integer actoId;	
	private Integer loteId;	
	private Integer recId;	
	private Integer tipoActoRec;	
	private String tipoActoRecDescrip;	
	private String nroRec;	
	private Integer annoRec;
	private Integer personaId;
	private String nroCargoNotificacion;	
	private String estado;
	private Timestamp fechaNotificacion;
	private Timestamp fechaEmision;
	private Timestamp fechaLote;
	private Timestamp fechaCancelacion;
	private Timestamp fechaRegistro;
	private BigDecimal deudaTotal;	
	private Integer tipoActoId;	
	private String direccion;	
	private String nroExpediente;
	
	private Integer tipoRecId;
	private Integer tipoDocumentoId;
	private Integer expedienteId;
	private String apellidosNombresPersona;
	private String direccionpersona;
	private String fechaEmisionFormato;
	
	
	public Integer getActoId() {
		return actoId;
	}

	public void setActoId(Integer actoId) {
		this.actoId = actoId;
	}

	public Integer getLoteId() {
		return loteId;
	}

	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}

	public Integer getRecId() {
		return recId;
	}

	public void setRecId(Integer recId) {
		this.recId = recId;
	}

	public Integer getTipoActoRec() {
		return tipoActoRec;
	}

	public void setTipoActoRec(Integer tipoActoRec) {
		this.tipoActoRec = tipoActoRec;
	}

	public String getTipoActoRecDescrip() {
		return tipoActoRecDescrip;
	}

	public void setTipoActoRecDescrip(String tipoActoRecDescrip) {
		this.tipoActoRecDescrip = tipoActoRecDescrip;
	}

	public String getNroRec() {
		return nroRec;
	}

	public void setNroRec(String nroRec) {
		this.nroRec = nroRec;
	}

	public Integer getAnnoRec() {
		return annoRec;
	}

	public void setAnnoRec(Integer annoRec) {
		this.annoRec = annoRec;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public String getNroCargoNotificacion() {
		return nroCargoNotificacion;
	}

	public void setNroCargoNotificacion(String nroCargoNotificacion) {
		this.nroCargoNotificacion = nroCargoNotificacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Timestamp fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Timestamp getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Timestamp fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Timestamp getFechaLote() {
		return fechaLote;
	}

	public void setFechaLote(Timestamp fechaLote) {
		this.fechaLote = fechaLote;
	}

	public Timestamp getFechaCancelacion() {
		return fechaCancelacion;
	}

	public void setFechaCancelacion(Timestamp fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public BigDecimal getDeudaTotal() {
		return deudaTotal;
	}

	public void setDeudaTotal(BigDecimal deudaTotal) {
		this.deudaTotal = deudaTotal;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTipoActo() {
		return tipoActo;
	}

	public void setTipoActo(String tipoActo) {
		this.tipoActo = tipoActo;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getDescripcionPersona() {
		return descripcionPersona;
	}

	public void setDescripcionPersona(String descripcionPersona) {
		this.descripcionPersona = descripcionPersona;
	}

	public Integer getTipoActoId() {
		return tipoActoId;
	}

	public void setTipoActoId(Integer tipoActoId) {
		this.tipoActoId = tipoActoId;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNroExpediente() {
		return nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}


	public String getDireccionpersona() {
		return direccionpersona;
	}

	public void setDireccionpersona(String direccionpersona) {
		this.direccionpersona = direccionpersona;
	}

	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}

	public void setTipoDocumentoId(Integer tipodocumentoId) {
		this.tipoDocumentoId = tipodocumentoId;
	}

	public Integer getExpedienteId() {
		return expedienteId;
	}

	public void setExpedienteId(Integer expedienteId) {
		this.expedienteId = expedienteId;
	}

	public String getApellidosNombresPersona() {
		return apellidosNombresPersona;
	}

	public void setApellidosNombresPersona(String appellidosNombresPersona) {
		this.apellidosNombresPersona = appellidosNombresPersona;
	}

	public Integer getTipoRecId() {
		return tipoRecId;
	}

	public void setTipoRecId(Integer tipoRecId) {
		this.tipoRecId = tipoRecId;
	}

	public String getFechaEmisionFormato() {
		return fechaEmisionFormato;
	}

	public void setFechaEmisionFormato(String fechaEmisionFormato) {
		this.fechaEmisionFormato = fechaEmisionFormato;
	}

	private String tipoDocumento;
	
	private String tipoActo;
}
