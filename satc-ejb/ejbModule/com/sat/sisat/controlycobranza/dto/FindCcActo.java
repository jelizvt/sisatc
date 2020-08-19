package com.sat.sisat.controlycobranza.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class FindCcActo  implements Serializable {
	
	private Integer actoId;
	
	private Integer loteId;
	
	private Integer reclamoId;
	
	private String nroActo;
	
	private Integer annoActo;
	
	private Integer annoDeuda;
	
	private Integer personaId;

	private String nroCargoNotificacion;
	
	private String estado;

	private Timestamp fechaNotificacion;
	
	private Timestamp fechaReclamo;

	private Timestamp fechaEmision;

	private Timestamp fechaLote;

	private Timestamp fechaCancelacion;

	private Timestamp fechaRegistro;

	private BigDecimal montoDeuda;
	
	private String tipoDocumento;
	
	private String tipoActo;
	
	private BigDecimal deudaTotalRecActo;
	
	private String apellidosNombres;
	
	private Long contenId;
	
	private String subConcepto;
	
	private Integer notificado;
	
	private String concepto;
	
	private String cuotas;
	
	private String nroRec;
	
	private Integer recId;
	
	private String tipoActoRec;
	
	private String tipoNotificacion;
	
	private String registrador;
	
	public String getTipoNotificacion() {
		return tipoNotificacion;
	}

	public void setTipoNotificacion(String tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}

	//reclamo
	private String nroDocumentoReclamo;
	private Integer estadoReclamoId;
	
	public FindCcActo(){
		
	}

	public String getNroDocumentoReclamo() {
		return nroDocumentoReclamo;
	}

	public void setNroDocumentoReclamo(String nroDocumentoReclamo) {
		this.nroDocumentoReclamo = nroDocumentoReclamo;
	}

	public Integer getEstadoReclamoId() {
		return estadoReclamoId;
	}

	public void setEstadoReclamoId(Integer estadoReclamoId) {
		this.estadoReclamoId = estadoReclamoId;
	}

	public Integer getLoteId() {
		return loteId;
	}

	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}

	public String getNroActo() {
		return nroActo;
	}

	public void setNroActo(String nroActo) {
		this.nroActo = nroActo;
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

	public BigDecimal getMontoDeuda() {
		return montoDeuda;
	}

	public void setMontoDeuda(BigDecimal montoDeuda) {
		this.montoDeuda = montoDeuda;
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

	public String getSubConcepto() {
		return subConcepto;
	}

	public void setSubConcepto(String subConcepto) {
		this.subConcepto = subConcepto;
	}

	public Integer getAnnoActo() {
		return annoActo;
	}

	public void setAnnoActo(Integer annoActo) {
		this.annoActo = annoActo;
	}

	public Integer getActoId() {
		return actoId;
	}

	public void setActoId(Integer actoId) {
		this.actoId = actoId;
	}

	public Integer getNotificado() {
		return notificado;
	}

	public void setNotificado(Integer notificado) {
		this.notificado = notificado;
	}

	public String getCuotas() {
		return cuotas;
	}

	public void setCuotas(String cuotas) {
		this.cuotas = cuotas;
	}

	public BigDecimal getDeudaTotalRecActo() {
		return deudaTotalRecActo;
	}

	public void setDeudaTotalRecActo(BigDecimal deudaTotalRecActo) {
		this.deudaTotalRecActo = deudaTotalRecActo;
	}

	public String getNroRec() {
		return nroRec;
	}

	public void setNroRec(String nroRec) {
		this.nroRec = nroRec;
	}

	public Integer getRecId() {
		return recId;
	}

	public void setRecId(Integer recId) {
		this.recId = recId;
	}

	public String getTipoActoRec() {
		return tipoActoRec;
	}

	public void setTipoActoRec(String tipoActoRec) {
		this.tipoActoRec = tipoActoRec;
	}

	public String getApellidosNombres() {
		return apellidosNombres;
	}

	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Long getContenId() {
		return contenId;
	}

	public void setContenId(Long contenId) {
		this.contenId = contenId;
	}
	
	public Timestamp getFechaReclamo() {
		return fechaReclamo;
	}

	public void setFechaReclamo(Timestamp fechaReclamo) {
		this.fechaReclamo = fechaReclamo;
	}
	
	public Integer getReclamoId() {
		return reclamoId;
	}

	public void setReclamoId(Integer reclamoId) {
		this.reclamoId = reclamoId;
	}

	public String getRegistrador() {
		return registrador;
	}

	public void setRegistrador(String registrador) {
		this.registrador = registrador;
	}

	public Integer getAnnoDeuda() {
		return annoDeuda;
	}

	public void setAnnoDeuda(Integer annoDeuda) {
		this.annoDeuda = annoDeuda;
	}
	
	
}
