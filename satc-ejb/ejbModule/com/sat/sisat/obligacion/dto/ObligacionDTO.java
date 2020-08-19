package com.sat.sisat.obligacion.dto;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.sat.sisat.common.util.FileUpload;

public class ObligacionDTO implements Serializable {	

	private static final long serialVersionUID = 371697746310236666L;
	
	private Integer unidadId;
	
	private Integer personaId;
	
	private String conceptoDescripcion;
	
	private String subConceptoDescripcion;	

	private int obligacionId;
	
	private Integer conceptoId;
	
	private Integer subConceptoId;	
	
	private Integer conceptoIdTributoReferencia;

	private int annoAfectacion;
	
	private BigDecimal monto;
	
	private String nroValor;
	
	private Date fechaEmision;
	
	private Date fechaInfraccion;
	
	private Date fechaVencimiento;
	
	private Date fechaLiquidacion;
	
	private String referenciaREC;
	
	private int idReferenciaREC;
	
	private BigDecimal baseImponible;
	
	private BigDecimal impuesto;
	
	private BigDecimal tasa;	
	
	private File archivo;
	
	private FileUpload fileUpload;
	
	private String codigoPlacaReferencia;
	
	private int djReferencia;
	
	private Date fechaAdquision;

	private String glosa;
	
	private Boolean presentoDocumentos;
	
	private String nroRequerimiento;
	
	private String nroActa;
	
	private String nroPapeleta;
	
	private Integer papeletaId;
	
	private Date fechaActa;
	
	private String responsable;
	/**
	 * 1 - Incripción caso por defecto
	 * 2 - Descargo
	 */
	private Integer contexto = 1;
	
	public int getObligacionId() {
		return obligacionId;
	}

	public void setObligacionId(int obligacionId) {
		this.obligacionId = obligacionId;
	}

	public Integer getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}

	public Integer getSubConceptoId() {
		return subConceptoId;
	}

	public void setSubConceptoId(Integer subConceptoId) {
		this.subConceptoId = subConceptoId;
	}

	public int getAnnoAfectacion() {
		return annoAfectacion;
	}

	public void setAnnoAfectacion(int annoAfectacion) {
		this.annoAfectacion = annoAfectacion;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getNroValor() {
		return nroValor;
	}

	public void setNroValor(String nroValor) {
		this.nroValor = nroValor;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaInfraccion() {
		return fechaInfraccion;
	}

	public void setFechaInfraccion(Date fechaInfraccion) {
		this.fechaInfraccion = fechaInfraccion;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public BigDecimal getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}

	public BigDecimal getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(BigDecimal impuesto) {
		this.impuesto = impuesto;
	}

//	public String getRutaArchivo() {
//		return rutaArchivo;
//	}
//
//	public void setRutaArchivo(String rutaArchivo) {
//		this.rutaArchivo = rutaArchivo;
//	}

	public File getArchivo() {
		return archivo;
	}

	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}

	public String getConceptoDescripcion() {
		return conceptoDescripcion;
	}

	public void setConceptoDescripcion(String conceptoDescripcion) {
		this.conceptoDescripcion = conceptoDescripcion;
	}

	public String getSubConceptoDescripcion() {
		return subConceptoDescripcion;
	}

	public void setSubConceptoDescripcion(String subConceptoDescripcion) {
		this.subConceptoDescripcion = subConceptoDescripcion;
	}

	public BigDecimal getTasa() {
		return tasa;
	}

	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;		
	}

	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public String getReferenciaREC() {
		return referenciaREC;
	}

	public void setReferenciaREC(String referenciaREC) {
		this.referenciaREC = referenciaREC;
	}

	public String getCodigoPlacaReferencia() {
		return codigoPlacaReferencia;
	}

	public void setCodigoPlacaReferencia(String codigoPlacaReferencia) {
		this.codigoPlacaReferencia = codigoPlacaReferencia;
	}

	public FileUpload getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(FileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}

	public int getIdReferenciaREC() {
		return idReferenciaREC;
	}

	public void setIdReferenciaREC(int idReferenciaREC) {
		this.idReferenciaREC = idReferenciaREC;
	}

	public int getDjReferencia() {
		return djReferencia;
	}

	public void setDjReferencia(int djReferencia) {
		this.djReferencia = djReferencia;
	}

	public Date getFechaAdquision() {
		return fechaAdquision;
	}

	public void setFechaAdquision(Date fechaAdquision) {
		this.fechaAdquision = fechaAdquision;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public Integer getConceptoIdTributoReferencia() {
		return conceptoIdTributoReferencia;
	}

	public void setConceptoIdTributoReferencia(Integer conceptoIdTributoReferencia) {
		this.conceptoIdTributoReferencia = conceptoIdTributoReferencia;
	}

	public Integer getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(Integer unidadId) {
		this.unidadId = unidadId;
	}

	public Boolean getPresentoDocumentos() {
		return presentoDocumentos;
	}

	public void setPresentoDocumentos(Boolean presentoDocumentos) {
		this.presentoDocumentos = presentoDocumentos;
	}

	public String getNroRequerimiento() {
		return nroRequerimiento;
	}

	public void setNroRequerimiento(String nroRequerimiento) {
		this.nroRequerimiento = nroRequerimiento;
	}

	public String getNroActa() {
		return nroActa;
	}

	public void setNroActa(String nroActa) {
		this.nroActa = nroActa;
	}

	public Integer getContexto() {
		return contexto;
	}

	public void setContexto(Integer contexto) {
		this.contexto = contexto;
	}

	public String getNroPapeleta() {
		return nroPapeleta;
	}

	public void setNroPapeleta(String nroPapeleta) {
		this.nroPapeleta = nroPapeleta;
	}

	public Integer getPapeletaId() {
		return papeletaId;
	}

	public void setPapeletaId(Integer papeletaId) {
		this.papeletaId = papeletaId;
	}

	public Date getFechaActa() {
		return fechaActa;
	}

	public void setFechaActa(Date fechaActa) {
		this.fechaActa = fechaActa;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getResponsable() {
		return responsable;
	}

	
	
}
