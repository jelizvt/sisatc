package com.sat.sisat.fiscalizacion.dto;

import java.io.Serializable;
import java.util.Date;

public class FindInspeccionHistorial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	//private Integer anioInspeccion;
	//private Integer djId;
	//private Integer predioId;
	//private Date fechaInspeccion;

	private Integer  inspeccionId;
	private Integer  tipoDocumentoId;
	private String   tipoDocumentoNombre;
	private String nroRequerimiento;
	private Integer personaId;
	private String apellidosNombres;
	private String personaDomicilioFiscal;//
	private String personaDni;//
	private Integer  ubicacionId;
	private Integer  sectorId;//
	private Integer  viaId;//
	private String sectorNombre;//
	private String viaNombre;//
	private String manzanaNombre;//
	private Integer  programaId;
	private String programaNombre;
	private Integer estado; 
	private String estadoDescripcion;//
	private Date fechaEmision;
	private Date fechaNotificacion;
	private Integer usuarioId;
	private String nombreUsuario;
	private Integer inspectorId;//
	private String inspectorNombre;//
	private String inspectorDni;//
	private Integer resultadoId;//
	private String resultadoNombre;//
	private String resultadoNumero;//
	private Date fechaResultado;//
	private Integer esquelaId;//
	private String esquelaNombre;//
	private String esquelaNumero;//
	private Date fechaEsquela;//
	private Integer arId;//
	private String arNombre;//
	private String arNumero;//
	private Date fechaAr;//
	private String djNumero;//
	private Date djFecha;//
	private String condicion;//
	private String observaciones;//
	private Date fechaRegistro;
	private Date fechaInspeccion;
	private Integer inspectorIdAr;//
	private String anioInspeccion;
	private Integer docAsocId;//
	private String docAsocNombre;//
	private String docAsocNumero;//
	
	private Integer arFipId;//
	private String arFipNumero;//
	private Date fechaArFip;//
	
	private Integer  distritoId;//
	private Integer omisoId;
	private String   distritoNombre;
	private String placa;
	private String placa_anterior;
	
	/**
	 * Registro de Ubicación Física de Requerimiento:
	 */
	private Integer paquete;
	private Integer annioPaquete;
	private Integer expediente;

	

	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}
	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}
	public String getTipoDocumentoNombre() {
		return tipoDocumentoNombre;
	}
	public void setTipoDocumentoNombre(String tipoDocumentoNombre) {
		this.tipoDocumentoNombre = tipoDocumentoNombre;
	}
	public Integer getPersonaId() {
		return personaId;
	}
	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	public String getApellidosNombres() {
		return apellidosNombres;
	}
	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getInspeccionId() {
		return inspeccionId;
	}
	public void setInspeccionId(Integer inspeccionId) {
		this.inspeccionId = inspeccionId;
	}
	public Integer getProgramaId() {
		return programaId;
	}
	public void setProgramaId(Integer programaId) {
		this.programaId = programaId;
	}
	public String getProgramaNombre() {
		return programaNombre;
	}
	public void setProgramaNombre(String programaNombre) {
		this.programaNombre = programaNombre;
	}
	public String getNroRequerimiento() {
		return nroRequerimiento;
	}
	public void setNroRequerimiento(String nroRequerimiento) {
		this.nroRequerimiento = nroRequerimiento;
	}
	
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}
	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getPersonaDomicilioFiscal() {
		return personaDomicilioFiscal;
	}
	public void setPersonaDomicilioFiscal(String personaDomicilioFiscal) {
		this.personaDomicilioFiscal = personaDomicilioFiscal;
	}
	public String getPersonaDni() {
		return personaDni;
	}
	public void setPersonaDni(String personaDni) {
		this.personaDni = personaDni;
	}
	public Integer getUbicacionId() {
		return ubicacionId;
	}
	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}
	public Integer getSectorId() {
		return sectorId;
	}
	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}
	public Integer getViaId() {
		return viaId;
	}
	public void setViaId(Integer viaId) {
		this.viaId = viaId;
	}
	public String getSectorNombre() {
		return sectorNombre;
	}
	public void setSectorNombre(String sectorNombre) {
		this.sectorNombre = sectorNombre;
	}
	public String getViaNombre() {
		return viaNombre;
	}
	public void setViaNombre(String viaNombre) {
		this.viaNombre = viaNombre;
	}
	public String getManzanaNombre() {
		return manzanaNombre;
	}
	public void setManzanaNombre(String manzanaNombre) {
		this.manzanaNombre = manzanaNombre;
	}
	public String getEstadoDescripcion() {
		return estadoDescripcion;
	}
	public void setEstadoDescripcion(String estadoDescripcion) {
		this.estadoDescripcion = estadoDescripcion;
	}
	public Integer getInspectorId() {
		return inspectorId;
	}
	public void setInspectorId(Integer inspectorId) {
		this.inspectorId = inspectorId;
	}
	public String getInspectorNombre() {
		return inspectorNombre;
	}
	public void setInspectorNombre(String inspectorNombre) {
		this.inspectorNombre = inspectorNombre;
	}
	public String getInspectorDni() {
		return inspectorDni;
	}
	public void setInspectorDni(String inspectorDni) {
		this.inspectorDni = inspectorDni;
	}
	public Integer getResultadoId() {
		return resultadoId;
	}
	public void setResultadoId(Integer resultadoId) {
		this.resultadoId = resultadoId;
	}
	public String getResultadoNombre() {
		return resultadoNombre;
	}
	public void setResultadoNombre(String resultadoNombre) {
		this.resultadoNombre = resultadoNombre;
	}
	public String getResultadoNumero() {
		return resultadoNumero;
	}
	public void setResultadoNumero(String resultadoNumero) {
		this.resultadoNumero = resultadoNumero;
	}
	public Date getFechaResultado() {
		return fechaResultado;
	}
	public void setFechaResultado(Date fechaResultado) {
		this.fechaResultado = fechaResultado;
	}
	public Integer getEsquelaId() {
		return esquelaId;
	}
	public void setEsquelaId(Integer esquelaId) {
		this.esquelaId = esquelaId;
	}
	public String getEsquelaNombre() {
		return esquelaNombre;
	}
	public void setEsquelaNombre(String esquelaNombre) {
		this.esquelaNombre = esquelaNombre;
	}
	public String getEsquelaNumero() {
		return esquelaNumero;
	}
	public void setEsquelaNumero(String esquelaNumero) {
		this.esquelaNumero = esquelaNumero;
	}
	public Date getFechaEsquela() {
		return fechaEsquela;
	}
	public void setFechaEsquela(Date fechaEsquela) {
		this.fechaEsquela = fechaEsquela;
	}
	public Integer getArId() {
		return arId;
	}
	public void setArId(Integer arId) {
		this.arId = arId;
	}
	public String getArNombre() {
		return arNombre;
	}
	public void setArNombre(String arNombre) {
		this.arNombre = arNombre;
	}
	public String getArNumero() {
		return arNumero;
	}
	public void setArNumero(String arNumero) {
		this.arNumero = arNumero;
	}
	public Date getFechaAr() {
		return fechaAr;
	}
	public void setFechaAr(Date fechaAr) {
		this.fechaAr = fechaAr;
	}
	public String getDjNumero() {
		return djNumero;
	}
	public void setDjNumero(String djNumero) {
		this.djNumero = djNumero;
	}
	public Date getDjFecha() {
		return djFecha;
	}
	public void setDjFecha(Date djFecha) {
		this.djFecha = djFecha;
	}
	public String getCondicion() {
		return condicion;
	}
	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Date getFechaInspeccion() {
		return fechaInspeccion;
	}
	public void setFechaInspeccion(Date fechaInspeccion) {
		this.fechaInspeccion = fechaInspeccion;
	}
	public Integer getInspectorIdAr() {
		return inspectorIdAr;
	}
	public void setInspectorIdAr(Integer inspectorIdAr) {
		this.inspectorIdAr = inspectorIdAr;
	}
	public String getAnioInspeccion() {
		return anioInspeccion;
	}
	public void setAnioInspeccion(String anioInspeccion) {
		this.anioInspeccion = anioInspeccion;
	}
	public Integer getDocAsocId() {
		return docAsocId;
	}
	public void setDocAsocId(Integer docAsocId) {
		this.docAsocId = docAsocId;
	}
	public String getDocAsocNombre() {
		return docAsocNombre;
	}
	public void setDocAsocNombre(String docAsocNombre) {
		this.docAsocNombre = docAsocNombre;
	}
	public String getDocAsocNumero() {
		return docAsocNumero;
	}
	public void setDocAsocNumero(String docAsocNumero) {
		this.docAsocNumero = docAsocNumero;
	}
	public Integer getArFipId() {
		return arFipId;
	}
	public void setArFipId(Integer arFipId) {
		this.arFipId = arFipId;
	}
	public String getArFipNumero() {
		return arFipNumero;
	}
	public void setArFipNumero(String arFipNumero) {
		this.arFipNumero = arFipNumero;
	}
	public Date getFechaArFip() {
		return fechaArFip;
	}
	public void setFechaArFip(Date fechaArFip) {
		this.fechaArFip = fechaArFip;
	}
	public Integer getDistritoId() {
		return distritoId;
	}
	public void setDistritoId(Integer distritoId) {
		this.distritoId = distritoId;
	}
	public String getDistritoNombre() {
		return distritoNombre;
	}
	public void setDistritoNombre(String distritoNombre) {
		this.distritoNombre = distritoNombre;
	}
	public Integer getOmisoId() {
		return omisoId;
	}
	public void setOmisoId(Integer omisoId) {
		this.omisoId = omisoId;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getPlaca_anterior() {
		return placa_anterior;
	}
	public void setPlaca_anterior(String placa_anterior) {
		this.placa_anterior = placa_anterior;
	}
		
	public Integer getPaquete() {
		return paquete;
	}
	public void setPaquete(Integer paquete) {
		this.paquete = paquete;
	}
	public Integer getAnnioPaquete() {
		return annioPaquete;
	}
	public void setAnnioPaquete(Integer annioPaquete) {
		this.annioPaquete = annioPaquete;
	}
		
	public Integer getExpediente() {
		return expediente;
	}
	public void setExpediente(Integer expediente) {
		this.expediente = expediente;
	}

}
