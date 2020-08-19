package com.sat.sisat.tramitedocumentario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DeclaracionJuradaAdultDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8534828267535272561L;

	
	private Integer djId;
	private Integer procedimientoId;
	private Integer tipoTramiteId;
	private Integer docuTramiteId;
    private String nroExpedienteGenerico;
    private String nroExpediente;
    private Integer contribuyenteId;
    private String nroDocContribuyente;
    private String nombreApellidosContribuyente;
    private String domicilioFiscalContribuyente;
    private Integer unidadId;
    private String observacion; 
    private String nroResolucion;
    private Date fechaPresentacion;
    private Date fechaActualizacion; 
    private Integer nroFolios;
    private String referencia;
    private Integer estadoExpediente;
    private Integer usuarioId;
    private Integer estado;
    private Date fechaRegistro;
    private String terminal;
    private Integer situacionExpediente;
    
    private Integer conyugeId;
	private Integer relacionadoId; 
	private String nroDocIdentConyuge;
	private String nombreApellidosConyuge;
	private BigDecimal porcentajePart;
	private Boolean fallecido;
	private Integer requisitoPartId;
	private Date fechaPartidaDefuncion;
	private Integer requisitoSucId;
	private Date fechaSucesionIntestada;
	private BigDecimal cuotaIdeal;
	
	private BigDecimal porcBenef;
	private String tipoBien;
	private int inicioAnnoBenef;

//    private Integer contribuyenteId;
//    private Date fechaPresentacion;
//    private Date fechaActualizacion;
//    private Integer usuarioId;
    private Integer estadoConyuge;
//    private Date fechaRegistro;
//    Private String terminal;
	
    private Integer unicaPropId;
  //  private Integer dj_id;
    private Boolean vivienda;
    private Boolean negocio;
    private Boolean licenciaFuncionamiento;
    private Integer requisitoLicenciaId;
 //   private Integer contribuyenteId;
 //   private Integer usuarioId;
 //   private Integer estado;
  //  private Date fechaRegistro;
  //  private String terminal;
    private Integer resolutorId;
    
    private String nroExpedienteAnterior;
    
    private String nombreContribuyente;
    
    private String procedimientoAsString;
    
    private String estadoAsString;

    private String situacionAsString;

    
    private String tipoTramite;
    
    private String referenciaOrDocumentoTramiteAsString;
    private String area;
    
    private String usuarioAsString;
    
    
    
	public BigDecimal getPorcBenef() {
		return porcBenef;
	}
	public void setPorcBenef(BigDecimal porcBenef) {
		this.porcBenef = porcBenef;
	}
	public String getTipoBien() {
		return tipoBien;
	}
	public void setTipoBien(String tipoBien) {
		this.tipoBien = tipoBien;
	}
	public int getInicioAnnoBenef() {
		return inicioAnnoBenef;
	}
	public void setInicioAnnoBenef(int inicioAnnoBenef) {
		this.inicioAnnoBenef = inicioAnnoBenef;
	}
	public String getEstadoAsString() {
		return estadoAsString;
	}
	public void setEstadoAsString(String estadoAsString) {
		this.estadoAsString = estadoAsString;
	}
	public String getSituacionAsString() {
		return situacionAsString;
	}
	public void setSituacionAsString(String situacionAsString) {
		this.situacionAsString = situacionAsString;
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
	public String getNroExpediente() {
		return nroExpediente;
	}
	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
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
	public Integer getEstadoExpediente() {
		return estadoExpediente;
	}
	public void setEstadoExpediente(Integer estadoExpediente) {
		this.estadoExpediente = estadoExpediente;
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
	public Integer getConyugeId() {
		return conyugeId;
	}
	public void setConyugeId(Integer conyugeId) {
		this.conyugeId = conyugeId;
	}
	public Integer getRelacionadoId() {
		return relacionadoId;
	}
	public void setRelacionadoId(Integer relacionadoId) {
		this.relacionadoId = relacionadoId;
	}

	public BigDecimal getPorcentajePart() {
		return porcentajePart;
	}
	public void setPorcentajePart(BigDecimal porcentajePart) {
		this.porcentajePart = porcentajePart;
	}

	public Integer getRequisitoPartId() {
		return requisitoPartId;
	}
	public void setRequisitoPartId(Integer requisitoPartId) {
		this.requisitoPartId = requisitoPartId;
	}
	public Date getFechaPartidaDefuncion() {
		return fechaPartidaDefuncion;
	}
	public void setFechaPartidaDefuncion(Date fechaPartidaDefuncion) {
		this.fechaPartidaDefuncion = fechaPartidaDefuncion;
	}
	public Integer getRequisitoSucId() {
		return requisitoSucId;
	}
	public void setRequisitoSucId(Integer requisitoSucId) {
		this.requisitoSucId = requisitoSucId;
	}
	public Date getFechaSucesionIntestada() {
		return fechaSucesionIntestada;
	}
	public void setFechaSucesionIntestada(Date fechaSucesionIntestada) {
		this.fechaSucesionIntestada = fechaSucesionIntestada;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	public Integer getEstadoConyuge() {
		return estadoConyuge;
	}
	public void setEstadoConyuge(Integer estadoConyuge) {
		this.estadoConyuge = estadoConyuge;
	}
	
	public Integer getUnicaPropId() {
		return unicaPropId;
	}
	public void setUnicaPropId(Integer unicaPropId) {
		this.unicaPropId = unicaPropId;
	}
	public String getNroDocIdentConyuge() {
		return nroDocIdentConyuge;
	}
	public void setNroDocIdentConyuge(String nroDocIdentConyuge) {
		this.nroDocIdentConyuge = nroDocIdentConyuge;
	}
	public String getNombreApellidosConyuge() {
		return nombreApellidosConyuge;
	}
	public void setNombreApellidosConyuge(String nombreApellidosConyuge) {
		this.nombreApellidosConyuge = nombreApellidosConyuge;
	}
	public Boolean getVivienda() {
		return vivienda;
	}
	public void setVivienda(Boolean vivienda) {
		this.vivienda = vivienda;
	}
	
	public BigDecimal getCuotaIdeal() {
		return cuotaIdeal;
	}
	public void setCuotaIdeal(BigDecimal cuotaIdeal) {
		this.cuotaIdeal = cuotaIdeal;
	}
	public Boolean getFallecido() {
		return fallecido;
	}
	public void setFallecido(Boolean fallecido) {
		this.fallecido = fallecido;
	}
	public Boolean getNegocio() {
		return negocio;
	}
	public void setNegocio(Boolean negocio) {
		this.negocio = negocio;
	}
	public Boolean getLicenciaFuncionamiento() {
		return licenciaFuncionamiento;
	}
	public void setLicenciaFuncionamiento(Boolean licenciaFuncionamiento) {
		this.licenciaFuncionamiento = licenciaFuncionamiento;
	}
	public Integer getRequisitoLicenciaId() {
		return requisitoLicenciaId;
	}
	public void setRequisitoLicenciaId(Integer requisitoLicenciaId) {
		this.requisitoLicenciaId = requisitoLicenciaId;
	}
	public Integer getResolutorId() {
		return resolutorId;
	}
	public void setResolutorId(Integer resolutorId) {
		this.resolutorId = resolutorId;
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
	public String getProcedimientoAsString() {
		return procedimientoAsString;
	}
	public void setProcedimientoAsString(String procedimientoAsString) {
		this.procedimientoAsString = procedimientoAsString;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getReferenciaOrDocumentoTramiteAsString() {
		return referenciaOrDocumentoTramiteAsString;
	}
	public void setReferenciaOrDocumentoTramiteAsString(String referenciaOrDocumentoTramiteAsString) {
		this.referenciaOrDocumentoTramiteAsString = referenciaOrDocumentoTramiteAsString;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getUsuarioAsString() {
		return usuarioAsString;
	}
	public void setUsuarioAsString(String usuarioAsString) {
		this.usuarioAsString = usuarioAsString;
	}
    
    
    
}
