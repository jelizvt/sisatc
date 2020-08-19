package com.sat.sisat.cobranzacoactiva.dto;

import java.io.Serializable;
import java.util.Date;
public class FindCcRecTipo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer tipoRecId;
	private String descripcionTipoRec;
	private String descripcionCortaTipoRec;
	private Integer subconceptoId;
	private Integer nroRecsGeneradas;
	private Integer loteId;
	private Date fechaEmisionRec;
	private Date fechaNotificacionRec;
	private Integer conceptoId;
	private String conceptoDescripcion;
	private Integer usuarioId;
	private Integer documentoPdf;
	
	public Integer getDocumentoPdf() {
		return documentoPdf;
	}
	public void setDocumentoPdf(Integer documentoPdf) {
		this.documentoPdf = documentoPdf;
	}
	public Integer getSubconceptoId() {
		return subconceptoId;
	}
	public void setSubconceptoId(Integer subconceptoId) {
		this.subconceptoId = subconceptoId;
	}
	public String getDescripcionTipoRec() {
		return descripcionTipoRec;
	}
	public void setDescripcionTipoRec(String descripcionTipoRec) {
		this.descripcionTipoRec = descripcionTipoRec;
	}
		
	public Integer getTipoRecId() {
		return tipoRecId;
	}
	public void setTipoRecId(Integer tipoRecId) {
		this.tipoRecId = tipoRecId;
	}
	
	public String getDescripcionCortaTipoRec() {
		return descripcionCortaTipoRec;
	}
	public void setDescripcionCortaTipoRec(String descripcionCortaTipoRec) {
		this.descripcionCortaTipoRec = descripcionCortaTipoRec;
	}
	public Integer getNroRecsGeneradas() {
		return nroRecsGeneradas;
	}
	public void setNroRecsGeneradas(Integer nroRecsGeneradas) {
		this.nroRecsGeneradas = nroRecsGeneradas;
	}
	public Integer getLoteId() {
		return loteId;
	}
	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}
	public Date getFechaEmisionRec() {
		return fechaEmisionRec;
	}
	public void setFechaEmisionRec(Date fechaEmisionRec) {
		this.fechaEmisionRec = fechaEmisionRec;
	}
	public Date getFechaNotificacionRec() {
		return fechaNotificacionRec;
	}
	public void setFechaNotificacionRec(Date fechaNotificacionRec) {
		this.fechaNotificacionRec = fechaNotificacionRec;
	}
	public Integer getConceptoId() {
		return conceptoId;
	}
	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}
	public String getConceptoDescripcion() {
		return conceptoDescripcion;
	}
	public void setConceptoDescripcion(String conceptoDescripcion) {
		this.conceptoDescripcion = conceptoDescripcion;
	}
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	
}
