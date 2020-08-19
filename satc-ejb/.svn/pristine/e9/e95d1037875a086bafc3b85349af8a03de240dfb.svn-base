package com.sat.sisat.tramitedocumentario.dto;

import java.io.Serializable;
import java.util.Date;

public class BusquedaExpedienteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2491604487331789438L;
	
	private Integer unidadId;

	private Integer procedimientoId;
	
	private Integer tipoTramiteId;
	
	private Integer documentoTramiteId;
	
	private String nroExpediente;
	
	private String nroExpedienteAnterior;
	
	private Date fechaRecepcion;
	
	private Integer estadoExpediente;
	
	private Integer situacionExpediente; //para nuevo campo situacion de expediente
	
	private Integer usuarioExp; //para nuevo campo usuario de expediente
	
	private String nombreContribuyente;
	
	public Integer getDocumentoTramiteId() {
		return documentoTramiteId;
	}

	public void setDocumentoTramiteId(Integer documentoTramiteId) {
		this.documentoTramiteId = documentoTramiteId;
	}

	public String getNroExpediente() {
		return nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) {
		if(nroExpediente != null && !nroExpediente.equals("00000000")){
			this.nroExpediente = nroExpediente;
		}
	}

	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	
	public Integer getProcedimientoId() {
		return procedimientoId;
	}

	public void setProcedimientoId(Integer procedimientoId) {
		this.procedimientoId = procedimientoId;
	}

	public Integer getTipoTramiteId() {
		return tipoTramiteId;
	}

	public void setTipoTramiteId(Integer tipoTramiteId) {
		this.tipoTramiteId = tipoTramiteId;
	}

	public Integer getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(Integer unidadId) {
		this.unidadId = unidadId;
	}

	public Integer getEstadoExpediente() {
		return estadoExpediente;
	}

	public void setEstadoExpediente(Integer estadoExpediente) {
		this.estadoExpediente = estadoExpediente;
	}
	
	//se agrega situacion de expediente
	public Integer getSituacionExpediente() {
		return situacionExpediente;
	}

	public void setSituacionExpediente(Integer situacionExpediente) {
		this.situacionExpediente = situacionExpediente;
	}
	//se agrega usuario en expediente
	public Integer getUsuarioExp() {
		return usuarioExp;
	}

	public void setUsuarioExp(Integer usuarioExp) {
		this.usuarioExp = usuarioExp;
	}
	
	public String getNroExpedienteAnterior() {
		return nroExpedienteAnterior;
	}

	public void setNroExpedienteAnterior(String nroExpedienteAnterior) {
		this.nroExpedienteAnterior = nroExpedienteAnterior;
	}
	
	public String getNombreContribuyente() {
		return nombreContribuyente;
	}

	public void setNombreContribuyente(String nombreContribuyente) {
		this.nombreContribuyente = nombreContribuyente;
	}	

}
