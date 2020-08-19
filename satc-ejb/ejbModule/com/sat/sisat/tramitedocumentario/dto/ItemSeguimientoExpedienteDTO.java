package com.sat.sisat.tramitedocumentario.dto;

import java.io.Serializable;
import java.util.Date;

public class ItemSeguimientoExpedienteDTO implements Serializable {

	private static final long serialVersionUID = -2801779678270670519L;
	
	private Integer expedienteId;
	private String expediente;
	private String nroExpediente;
	private Date fechaRegistro;
	private String procedimiento;
	private String tramite;
	private String remitente;
	private String docRemite;
	private String firmante;
	private String docFirma;
	private String direccion;
	private String cargo;
	private Date fechaPresentacion;
	private String nroExpedienteAnterior;
	private String documento;
	private String asunto;
	private Date fechaActualizacion;
	private Integer nroFolios;
	private Integer nroExpFedat;
	private String usuario;
	private String usuarioDes;
	private String nombreArchivo;
	private String ubicacionArchivo;
	
	public Integer getExpedienteId(){
		return expedienteId;
	}
	
	public void setExpedienteId(Integer expedienteId){
		this.expedienteId = expedienteId;
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
	
	public Date getFechaRegistro(){
		return fechaRegistro;
	}
	
	public void setFechaRegistro(Date fechaRegistro){
		this.fechaRegistro = fechaRegistro;
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
	
	public String getDireccion(){
		return direccion;
	}
	
	public void setDireccion(String direccion){
		this.direccion = direccion;
	}
	
	public String getCargo(){
		return cargo;
	}
	
	public void setCargo(String cargo){
		this.cargo = cargo;
	}
	
	public Date getFechaPresentacion(){
		return fechaPresentacion;
	}
	
	public void setFechaPresentacion(Date fechaPresentacion){
		this.fechaPresentacion = fechaPresentacion;
	}
	
	public String getNroExpedienteAnterior(){
		return nroExpedienteAnterior;
	}
	
	public void setNroExpedienteAnterior(String nroExpedienteAnterior){
		this.nroExpedienteAnterior = nroExpedienteAnterior;
	}
	
	public String getDocumento(){
		return documento;
	}
	
	public void setDocumento(String documento){
		this.documento = documento;
	}
	
	public String getAsunto(){
		return asunto;
	}
	
	public void setAsunto(String asunto){
		this.asunto = asunto;
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
	
	public Integer getNroExpFedat(){
		return nroExpFedat;
	}
	
	public void setNroExpFedat(Integer nroExpFedat){
		this.nroExpFedat = nroExpFedat;
	}
	
	public String getUsuario(){
		return usuario;
	}
	
	public void setUsuario(String usuario){
		this.usuario = usuario;
	}
	
	public String getUsuarioDes(){
		return usuarioDes;
	}
	
	public void setUsuarioDes(String usuarioDes){
		this.usuarioDes = usuarioDes;
	}
	
	public String getNombreArchivo(){
		return nombreArchivo;
	}
	
	public void setNombreArchivo(String nombreArchivo){
		this.nombreArchivo = nombreArchivo;
	}
	
	public String getUbicacionArchivo(){
		return ubicacionArchivo;
	}
	
	public void setUbicacionArchivo(String ubicacionArchivo){
		this.ubicacionArchivo = ubicacionArchivo;
	}
		
}