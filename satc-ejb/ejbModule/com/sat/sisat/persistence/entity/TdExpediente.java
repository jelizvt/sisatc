package com.sat.sisat.persistence.entity;

// Generated 20/11/2012 10:29:09 AM by Hibernate Tools 4.0.0

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;

/**
 * TdExpediente generated by hbm2java
 */
@Entity
@Table(name = "td_expediente")
public class TdExpediente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4193320460217404125L;

	@Id
	@Column(name = "expediente_id", nullable = false)
	private Integer expedienteId;	

	@Column(name="procedimiento_id")
	private Integer procedimientoId;
	
	@Column(name = "tipo_tramite_id", nullable = false)
	private Integer tipoTramiteId;

	@Column(name = "docu_tramite_id")
	private Integer docuTramiteId;

	@Column(name="nro_expediente_generico")
	private String nroExpedienteGenerico;	
	
	@Column(name = "nro_expediente")
	private String nroExpediente;
	
	@Column(name = "nro_expediente_anterior")
	private String nroExpedienteAnterior;

	@Column(name = "contribuyente_id")
	private Integer contribuyenteId;
	
	@Column(name = "representante_id", nullable = true)
	private Integer representanteId;	

	@Column(name = "unidad_id", nullable = false)
	private Integer unidadId;

	@Column(name = "observacion")
	private String observacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_presentacion", nullable = false)
	private Date fechaPresentacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_actualizacion", nullable = false)
	private Date fechaActualizacion;

	@Transient
	private Integer tipoResultado;

	@Column(name = "nro_folios", nullable = false)
	@Min(value= 0)
	private Integer nroFolios;

	@Column(name = "estado_expediente", nullable = false)
	@DefaultValue(value = "11")
	private Integer estadoExpediente;
	
	@Column(name="referencia", nullable=true)
	private String referencia;

	@Column(name = "usuario_id")
	@NotNull
	private Integer usuarioId;
	
	@Column(name = "estado", nullable = false)
	@NotNull
	private String estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro")
	@NotNull
	private Date fechaRegistro;

	@Column(name = "terminal", nullable = false)
	@NotNull
	private String terminal;
	
	@Column(name = "nro_exp_fedat", nullable = false)
	private Integer nroExpFedat;

	//nuevos campos
	@Column(name = "situacion_expediente", nullable = false)
	@DefaultValue(value = "9")
	private Integer situacionExpediente;
	
	@Column(name="asunto", nullable=true)
	private String asunto;
	
	@Column(name = "report", nullable = true)
	@DefaultValue(value = "1")
	private Integer report;
	
	@Column(name="archivo_nombre", nullable=false)
	private String archivoNombre;
	
	@Column(name="archivo_ubicacion", nullable=true)
	private String archivoUbicacion;
	
	
	public Integer getExpedienteId() {
		return expedienteId;
	}

	public void setExpedienteId(Integer expedienteId) {
		this.expedienteId = expedienteId;
	}

	public Integer getTipoTramiteId() {
		return tipoTramiteId;
	}

	public void setTipoTramiteId(Integer tipoTramiteId) {
		this.tipoTramiteId = tipoTramiteId;
	}

	public Integer getDocuTramiteId() {
		return docuTramiteId;
	}

	public void setDocuTramiteId(Integer docuTramiteId) {
		this.docuTramiteId = docuTramiteId;
	}

	public String getNroExpediente() {
		return nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}

	public Integer getRepresentanteId() {
		return representanteId;
	}

	public void setRepresentanteId(Integer representanteId) {
		this.representanteId = representanteId;
	}

	public Integer getContribuyenteId() {
		return contribuyenteId;
	}

	public void setContribuyenteId(Integer contribuyenteId) {
		this.contribuyenteId = contribuyenteId;
	}

	public Integer getProcedimientoId() {
		return procedimientoId;
	}

	public void setProcedimientoId(Integer procedimientoId) {
		this.procedimientoId = procedimientoId;
	}

	public Integer getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(Integer unidadId) {
		this.unidadId = unidadId;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Integer getTipoResultado() {
		return tipoResultado;
	}

	public void setTipoResultado(Integer tipoResultado) {
		this.tipoResultado = tipoResultado;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public Integer getNroFolios() {
		return nroFolios;
	}

	public void setNroFolios(Integer nroFolios) {
		this.nroFolios = nroFolios;
	}

	public Integer getEstadoExpediente() {
		return estadoExpediente;
	}

	public void setEstadoExpediente(Integer estadoExpediente) {
		this.estadoExpediente = estadoExpediente;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getNroExpedienteGenerico() {
		return nroExpedienteGenerico;
	}

	public void setNroExpedienteGenerico(String nroExpedienteGenerico) {
		this.nroExpedienteGenerico = nroExpedienteGenerico;
	}

	public String getNroExpedienteAnterior() {
		return nroExpedienteAnterior;
	}

	public void setNroExpedienteAnterior(String nroExpedienteAnterior) {
		this.nroExpedienteAnterior = nroExpedienteAnterior;
	}

	public Integer getNroExpFedat() {
		return nroExpFedat;
	}

	public void setNroExpFedat(Integer nroExpFedat) {
		this.nroExpFedat = nroExpFedat;
	}
	
	public Integer getSituacionExpediente() {
		return situacionExpediente;
	}

	public void setSituacionExpediente(Integer situacionExpediente) {
		this.situacionExpediente = situacionExpediente;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	
	public Integer getReport(){
		return report;
	}
	
	public void setReport(Integer report){
		this.report = report;
	}
	
	public String getArchivoNombre() {
		return archivoNombre;
	}

	public void setArchivoNombre(String archivoNombre) {
		this.archivoNombre = archivoNombre;
	}
	
	public String getArchivoUbicacion() {
		return archivoUbicacion;
	}

	public void setArchivoUbicacion(String archivoUbicacion) {
		this.archivoUbicacion = archivoUbicacion;
	}
}
