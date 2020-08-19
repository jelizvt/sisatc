package com.sat.sisat.persistence.entity;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name="rp_fiscalizacion_inspeccion")
public class RpFiscalizacionInspeccion implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(name="inspeccion_id")	
	private int inspeccionId;
	
	@Column(name="tipo_documento_id")
	private Integer tipoDocumentoId;

	@Column(name="dj_id")
	private Integer djId;

	@Column(name="persona_id")
	private Integer personaId;

	@Column(name="predio_id")
	private Integer predioId;
	
	@Column(name="programa_id")
	private Integer programaId;

	@Column(name="inspector_id")
	private Integer inspectorId;

	@Column(name="nro_requerimiento")
	private String nroRequerimiento;
	
//	@Column(name="nroCarta")
//	private String nro_carta;
	
	@Column(name="fecha_emision")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fechaEmision;
		
	@Column(name="fecha_notificacion")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fechaNotificacion;
		
	@Column(name="fecha_emision_resultado")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fechaEmisionResultado;
		
	@Column(name="fecha_notificacion_resultado")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fechaNotificacionResultado;
	
	@Column(name="glosa")
	private String glosa;

	@Column(name="estado")
	private String  estado;
	
	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="fecha_registro")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fechaRegistro;
	
	@Column(name="terminal")
	private String terminal;	
	
	@Column(name="fecha_actualizacion")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fechaActualizacion;

	public int getInspeccionId() {
		return inspeccionId;
	}

	public void setInspeccionId(int inspeccionId) {
		this.inspeccionId = inspeccionId;
	}

	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}

	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}

	public Integer getDjId() {
		return djId;
	}

	public void setDjId(Integer djId) {
		this.djId = djId;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public Integer getPredioId() {
		return predioId;
	}

	public void setPredioId(Integer predioId) {
		this.predioId = predioId;
	}

	public Integer getProgramaId() {
		return programaId;
	}

	public void setProgramaId(Integer programaId) {
		this.programaId = programaId;
	}

	public Integer getInspectorId() {
		return inspectorId;
	}

	public void setInspectorId(Integer inspectorId) {
		this.inspectorId = inspectorId;
	}

	public String getNroRequerimiento() {
		return nroRequerimiento;
	}

	public void setNroRequerimiento(String nroRequerimiento) {
		this.nroRequerimiento = nroRequerimiento;
	}

//	public String getNro_carta() {
//		return nro_carta;
//	}
//
//	public void setNro_carta(String nro_carta) {
//		this.nro_carta = nro_carta;
//	}

	public java.util.Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(java.util.Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public java.util.Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(java.util.Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public java.util.Date getFechaEmisionResultado() {
		return fechaEmisionResultado;
	}

	public void setFechaEmisionResultado(java.util.Date fechaEmisionResultado) {
		this.fechaEmisionResultado = fechaEmisionResultado;
	}

	public java.util.Date getFechaNotificacionResultado() {
		return fechaNotificacionResultado;
	}

	public void setFechaNotificacionResultado(
			java.util.Date fechaNotificacionResultado) {
		this.fechaNotificacionResultado = fechaNotificacionResultado;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public java.util.Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(java.util.Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public java.util.Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(java.util.Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	
    

	

	
}