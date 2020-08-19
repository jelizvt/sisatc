package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "td_DJ")
public class TdDJ implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2784980741531399221L;
	
	@Id
	@Column(name= "dj_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer djId;
	
	@Column (name= "procedimiento_id")
	private Integer procedimientoId;
	
	@Column (name= "tipo_tramite_id")
	private Integer tipoTramiteId;
	
	@Column (name= "docu_tramite_id")
    private Integer docuTramiteId;
    
    @Column (name =	"nro_expediente_generico")
    private String nroExpedienteGenerico;
    
    @Column (name = "nro_expediente")
    private String nroExpediente;
    
    @Column (name = "contribuyente_id")
    private Integer contribuyenteId;
    
    @Column (name = "nro_doc_contribuyente")
    private String nroDocContribuyente;
    
    @Column (name = "nombre_apellidos_contribuyente")
    private String nombreApellidosContribuyente;
    
    @Column (name = "domicilio_fiscal_contribuyente")
    private String domicilioFiscalContribuyente;
    
    @Column (name = "unidad_id")
    private Integer unidadId;
    
    @Column (name = "observacion")
    private String observacion;
    
    @Column (name = "nro_resolucion")
    private String nroResolucion;
    
	@Temporal(TemporalType.TIMESTAMP)
    @Column (name = "fecha_presentacion")
    private Date fechaPresentacion;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column (name = "fecha_actualizacion")
    private Date fechaActualizacion;
	
	@Column (name = "nro_folios")
    private Integer nroFolios;
	
	@Column (name = "referencia")
    private String referencia;
	
	@Column (name = "estado_expediente")
    private Integer estadoExpediente;
	
	@Column (name = "usuario_id")
    private Integer usuarioId;
	
	@Column (name = "estado")
    private Integer estado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro")
    private Date fechaRegistro;
	
	@Column(name = "terminal")
    private String terminal;
	
	@Column(name = "situacion_expediente")
    private Integer situacionExpediente;
	
	@Column(name = "representante_id", nullable = true)
	private Integer representanteId;
	
	@Column(name="archivo_nombre", nullable=false)
	private String archivoNombre;
	
	@Column(name="archivo_ubicacion", nullable=true)
	private String archivoUbicacion;
	
	@Column(name = "resolutor_id", nullable = true)
	private Integer resolutorId;
	
	@Column(name="porcentaje_beneficio")
	private BigDecimal porcBeneficio;
	
	@Column(name="inicio_anno_beneficio")
	private Integer iniAnnoBenef;
	
	@Column(name="tipo_bien")
	private String tipoBien;
	
	
	
	
	public BigDecimal getPorcBeneficio() {
		return porcBeneficio;
	}

	public void setPorcBeneficio(BigDecimal porcBeneficio) {
		this.porcBeneficio = porcBeneficio;
	}

	public Integer getIniAnnoBenef() {
		return iniAnnoBenef;
	}

	public void setIniAnnoBenef(Integer iniAnnoBenef) {
		this.iniAnnoBenef = iniAnnoBenef;
	}

	public String getTipoBien() {
		return tipoBien;
	}

	public void setTipoBien(String tipoBien) {
		this.tipoBien = tipoBien;
	}

	public Integer getResolutorId() {
		return resolutorId;
	}

	public void setResolutorId(Integer resolutorId) {
		this.resolutorId = resolutorId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getRepresentanteId() {
		return representanteId;
	}

	public void setRepresentanteId(Integer representanteId) {
		this.representanteId = representanteId;
	}


	
	public Integer getEstadoExpediente() {
		return estadoExpediente;
	}

	public void setEstadoExpediente(Integer estadoExpediente) {
		this.estadoExpediente = estadoExpediente;
	}

	public Integer getDjId() {
		return djId;
	}

	public void setDjId(Integer djId) {
		this.djId = djId;
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

	public Integer getDocuTramiteId() {
		return docuTramiteId;
	}

	public void setDocuTramiteId(Integer docuTramiteId) {
		this.docuTramiteId = docuTramiteId;
	}

	public String getNroExpedienteGenerico() {
		return nroExpedienteGenerico;
	}

	public void setNroExpedienteGenerico(String nroExpedienteGenerico) {
		this.nroExpedienteGenerico = nroExpedienteGenerico;
	}


	
	public Integer getContribuyenteId() {
		return contribuyenteId;
	}

	public void setContribuyenteId(Integer contribuyenteId) {
		this.contribuyenteId = contribuyenteId;
	}

	public String getNroDocContribuyente() {
		return nroDocContribuyente;
	}

	public void setNroDocContribuyente(String nroDocContribuyente) {
		this.nroDocContribuyente = nroDocContribuyente;
	}

	public String getNombreApellidosContribuyente() {
		return nombreApellidosContribuyente;
	}

	public void setNombreApellidosContribuyente(String nombreApellidosContribuyente) {
		this.nombreApellidosContribuyente = nombreApellidosContribuyente;
	}

	public String getDomicilioFiscalContribuyente() {
		return domicilioFiscalContribuyente;
	}

	public void setDomicilioFiscalContribuyente(String domicilioFiscalContribuyente) {
		this.domicilioFiscalContribuyente = domicilioFiscalContribuyente;
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

	public String getNroResolucion() {
		return nroResolucion;
	}

	public void setNroResolucion(String nroResolucion) {
		this.nroResolucion = nroResolucion;
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

	public Integer getNroFolios() {
		return nroFolios;
	}

	public void setNroFolios(Integer nroFolios) {
		this.nroFolios = nroFolios;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}



	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
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

	public Integer getSituacionExpediente() {
		return situacionExpediente;
	}

	public void setSituacionExpediente(Integer situacionExpediente) {
		this.situacionExpediente = situacionExpediente;
	}

	public String getNroExpediente() {
		return nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
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
