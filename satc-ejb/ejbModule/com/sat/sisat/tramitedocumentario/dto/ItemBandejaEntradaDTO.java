package com.sat.sisat.tramitedocumentario.dto;

import java.io.Serializable;
import java.util.Date;

import com.sat.sisat.common.util.Constante;

public class ItemBandejaEntradaDTO implements Serializable {

	private static final long serialVersionUID = -2801779678270670512L;

	private Integer expedienteId;
	private String nroExpedienteGenerico;
	private String codExpediente;
	private String nroExpedienteAnterior;

	private String lote;// quitando la variable lote
	private Date fechaRecepcion;
	private String alegato;
	private String area;
	private String tipoTramite;
	private Integer estado;
	private Integer estadoExpediente;
	private Integer situacionExpediente;//se agrego situacion
	private Date fechaAsignacion;
	private String nombreContribuyente;

	private Integer procedimientoId;

	private String procedimientoAsString;

	private Integer usuarioId;

	private String usuarioAsString;
	
	private String usuarioRegistrador;
	
	private String referenciaOrDocumentoTramiteAsString;
	
	private Boolean select;
	
	private Integer flagDescargo;

	public Integer getProcedimientoId() {
		return procedimientoId;
	}

	public String getProcedimientoAsString() {
		return procedimientoAsString;
	}

	public void setProcedimientoId(Integer procedimientoId) {
		this.procedimientoId = procedimientoId;
	}

	public void setProcedimientoAsString(String procedimientoAsString) {
		this.procedimientoAsString = procedimientoAsString;
	}

	public String getCodExpediente() {
		return codExpediente;
	}

	public void setCodExpediente(String codExpediente) {
		this.codExpediente = codExpediente;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public String getAlegato() {
		return alegato;
	}

	public void setAlegato(String alegato) {
		this.alegato = alegato;
	}

	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public Integer getExpedienteId() {
		return expedienteId;
	}

	public void setExpedienteId(Integer expedienteId) {
		this.expedienteId = expedienteId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public Integer getEstadoExpediente() {
		return estadoExpediente;
	}

	public void setEstadoExpediente(Integer estadoExpediente) {
		this.estadoExpediente = estadoExpediente;
	}

	public String getEstadoExpedienteAsString() {

		String estadoExpediente;

		switch (this.estadoExpediente) {
		case Constante.ESTADO_EXPEDIENTE_RECEPCIONADO:
			estadoExpediente = "Recepcionado";
			break;
		case Constante.ESTADO_EXPEDIENTE_PEND_REQUISITOS:
			estadoExpediente = "Pend. Requisitos";
			break;
		case Constante.ESTADO_EXPEDIENTE_EN_TRAMITE:
			estadoExpediente = "En Tramite";
			break;
		case Constante.ESTADO_EXPEDIENTE_RESUELTO:
			estadoExpediente = "Resuelto";
			break;
		case Constante.ESTADO_EXPEDIENTE_PEND_APROBACION:
			estadoExpediente = "Pend. Aprobación";
			break;
		case Constante.ESTADO_EXPEDIENTE_ASIGNADO:
			estadoExpediente = "Asignado";
			break;
		case Constante.ESTADO_EXPEDIENTE_ATENDIDO:
			estadoExpediente = "Atendido";
			break;
		case Constante.ESTADO_EXPEDIENTE_ANULADO:
			estadoExpediente = "Anulado";			
			break;
		case Constante.ESTADO_EXPEDIENTE_ARCHIVADO:
			estadoExpediente = "Archivado";
			break;
		case Constante.ESTADO_EXPEDIENTE_OBSERVADO:
			estadoExpediente = "Observado";
			break;
		case Constante.ESTADO_EXPEDIENTE_COMPLETO:
			estadoExpediente = "Completo";
			break;
		case Constante.ESTADO_EXPEDIENTE_INCOMPLETO:
			estadoExpediente = "Incompleto";
			break;
		default:
			estadoExpediente = "No Encontrado";
			break;
		}

		return estadoExpediente;
	}
	
	//para situacion de expediente
	public Integer getSituacionExpediente() {
		return situacionExpediente;
	}

	public void setSituacionExpediente(Integer situacionExpediente) {
		this.situacionExpediente = situacionExpediente;
	}

	public String getSituacionExpedienteAsString() {

		String situacionExpediente;

		switch (this.situacionExpediente) {
		case Constante.SITUACION_EXPEDIENTE_REGISTRADO:
			situacionExpediente = "Registrado";
			break;
		case Constante.SITUACION_EXPEDIENTE_DIGITALIZADO:
			situacionExpediente = "Digitalizado";
			break;
		case Constante.SITUACION_EXPEDIENTE_DERIVADO:
			situacionExpediente = "Derivado";
			break;
		case Constante.SITUACION_EXPEDIENTE_ASIGNADO:
			situacionExpediente = "Asignado";
			break;
		case Constante.SITUACION_EXPEDIENTE_RECEPCIONADO:
			situacionExpediente = "Recepcionado";
			break;
		case Constante.SITUACION_EXPEDIENTE_DEVUELTO:
			situacionExpediente = "Devuelto";
			break;
		case Constante.SITUACION_EXPEDIENTE_RESUELTO:
			situacionExpediente = "Resuelto";
			break;
		case Constante.SITUACION_EXPEDIENTE_ANULADO:
			situacionExpediente = "Anulado";
			break;
		case Constante.SITUACION_EXPEDIENTE_ARCHIVADO:
			situacionExpediente = "Archivado";
			break;
		default:
			situacionExpediente = "No Encontrado";
			break;
		}

		return situacionExpediente;
	}
	
	public String getUsuarioAsString() {
		return usuarioAsString;
	}

	public void setUsuarioAsString(String usuarioAsString) {
		this.usuarioAsString = usuarioAsString;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getReferenciaOrDocumentoTramiteAsString() {
		return referenciaOrDocumentoTramiteAsString;
	}

	public void setReferenciaOrDocumentoTramiteAsString(String referenciaOrDocumentoTramiteAsString) {
		this.referenciaOrDocumentoTramiteAsString = referenciaOrDocumentoTramiteAsString;
	}

	/**
	 * @return the nroExpedienteGenerico
	 */
	public String getNroExpedienteGenerico() {
		return nroExpedienteGenerico;
	}

	/**
	 * @param nroExpedienteGenerico
	 *            the nroExpedienteGenerico to set
	 */
	public void setNroExpedienteGenerico(String nroExpedienteGenerico) {
		this.nroExpedienteGenerico = nroExpedienteGenerico;
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
	
	public String getUsuarioRegistrador() {
		return usuarioRegistrador;
	}

	public void setUsuarioRegistrador(String usuarioRegistrador) {
		this.usuarioRegistrador = usuarioRegistrador;
	}

	public Boolean getSelect() {
		return select;
	}

	public void setSelect(Boolean select) {
		this.select = select;
	}

	public Integer getFlagDescargo() {
		return flagDescargo;
	}

	public void setFlagDescargo(Integer flagDescargo) {
		this.flagDescargo = flagDescargo;
	}
	
}
