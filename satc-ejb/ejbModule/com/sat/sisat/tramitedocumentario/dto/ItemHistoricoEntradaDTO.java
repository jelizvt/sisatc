package com.sat.sisat.tramitedocumentario.dto;

import java.io.Serializable;
import java.util.Date;

public class ItemHistoricoEntradaDTO implements Serializable {

	private static final long serialVersionUID = -2801779678270670510L;
	
	private Integer expedienteTransaccionId;
	private Date fechaMovimiento;
	private Integer expedienteId;
	private String procedimiento;
	private String tramite;
	private String expediente;
	private String nroExpediente;
	private String nroExpedienteAnterior;
	private String remitente;
	private String docRemite;
	private String firmante;
	private String docFirma;
	private String areaDescripcion;
	private String descCorta;
	private String observacion;
	private Date fechaRegistro;
	private Date fechaPresentacion;
	private Date fechaActualizacion;
	private Integer nroFolios;
	private String referencia;
	private Integer estado;
	private String estadoExpediente;
	private String situacionExpediente;
	private Integer nroFedat;
	private String asunto;
	private String usuario;
	private String usuarioCompleto;
	
	
	public Integer getExpedienteTransaccionId(){
		return expedienteTransaccionId;
	}
	
	public void setExpedienteTransaccionId(Integer expedienteTransaccionId){
		this.expedienteTransaccionId = expedienteTransaccionId;
	}
	
	public Date getFechaMovimiento(){
		return fechaMovimiento;
	}
	
	public void setFechaMovimiento(Date fechaMovimiento){
		this.fechaMovimiento = fechaMovimiento;
	}
	
	public Integer getExpedienteId(){
		return expedienteId;
	}
	
	public void setExpedienteId(Integer expedienteId){
		this.expedienteId = expedienteId;
	}
	
	public String getProcedimiento(){
		return procedimiento;
	}
	
	public void setProcedimiento(String procedimiento){
		this.procedimiento = procedimiento;
	}
	
	public String getTramite(){
		return tramite;
	}
	
	public void setTramite(String tramite){
		this.tramite = tramite;
	}
	
	public String getExpediente(){
		return expediente;
	}
	
	public void setExpediente(String expediente){
		this.expediente = expediente;
	}
	
	public String getNroExpediente(){
		return nroExpediente;
	}
	
	public void setNroExpediente(String nroExpediente){
		this.nroExpediente = nroExpediente;
	}
	
	public String getNroExpedienteAnterior(){
		return nroExpedienteAnterior;
	}
	
	public void setNroExpedienteAnterior(String nroExpedienteAnterior){
		this.nroExpedienteAnterior = nroExpedienteAnterior;
	}
	
	public String getRemitente(){
		return remitente;
	}
	
	public void setRemitente(String remitente){
		this.remitente = remitente;
	}
	
	public String getDocRemite(){
		return docRemite;
	}
	
	public void setDocRemite(String docRemite){
		this.docRemite = docRemite;
	}
	
	public String getFirmante(){
		return firmante;
	}
	
	public void setFirmante(String firmante){
		this.firmante = firmante;
	}
	
	public String getDocFirma(){
		return docFirma;
	}
	
	public void setDocFirma(String docFirma){
		this.docFirma = docFirma;
	}

	public String getAreaDescripcion(){
		return areaDescripcion;
	}
	
	public void setAreaDescripcion(String areaDescripcion){
		this.areaDescripcion = areaDescripcion;
	}

	public String getDescCorta(){
		return descCorta;
	}
	
	public void setDescCorta(String descCorta){
		this.descCorta = descCorta;
	}

	public String getObservacion(){
		return observacion;
	}
	
	public void setObservacion(String observacion){
		this.observacion = observacion;
	}
		
	public Date getFechaRegistro(){
		return fechaRegistro;
	}
	
	public void setFechaRegistro(Date fechaRegistro){
		this.fechaRegistro = fechaRegistro;
	}
	
	public Date getFechaPresentacion(){
		return fechaPresentacion;
	}
	
	public void setFechaPresentacion(Date fechaPresentacion){
		this.fechaPresentacion = fechaPresentacion;
	}
	
	public Date getFechaActualizacion(){
		return fechaActualizacion;
	}
	
	public void setFechaActualizacion(Date fechaActualizacion){
		this.fechaActualizacion = fechaActualizacion;
	}
	
	public Integer getNroFolios(){
		return nroFolios;
	}
	
	public void setNroFolios(Integer nroFolios){
		this.nroFolios = nroFolios;
	}
	
	public String getReferencia(){
		return referencia;
	}
	
	public void setReferencia(String referencia){
		this.referencia = referencia;
	}
	
	public Integer getEstado(){
		return estado;
	}
	
	public void setEstado(Integer estado){
		this.estado = estado;
	}
	
	public String getEstadoExpediente(){
		return estadoExpediente;
	}
	
	public void setEstadoExpediente(String estadoExpediente){
		this.estadoExpediente = estadoExpediente;
	}
	
	public String getSituacionExpediente(){
		return situacionExpediente;
	}
	
	public void setSituacionExpediente(String situacionExpediente){
		this.situacionExpediente = situacionExpediente;
	}
	
	public Integer getNroFedat(){
		return nroFedat;
	}
	
	public void setNroFedat(Integer nroFedat){
		this.nroFedat = nroFedat;
	}
	
	public String getAsunto(){
		return asunto;
	}
	
	public void setAsunto(String asunto){
		this.asunto = asunto;
	}
	
	public String getUsuario(){
		return usuario;
	}
	
	public void setUsuario(String usuario){
		this.usuario = usuario;
	}

	public String getUsuarioCompleto(){
		return usuarioCompleto;
	}
	
	public void setUsuarioCompleto(String usuarioCompleto){
		this.usuarioCompleto = usuarioCompleto;
	}
}
