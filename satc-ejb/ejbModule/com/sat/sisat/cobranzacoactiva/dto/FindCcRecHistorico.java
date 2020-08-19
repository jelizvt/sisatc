package com.sat.sisat.cobranzacoactiva.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class FindCcRecHistorico implements Serializable {

	private Integer deudaExigibleId;
	private Integer recId;
	private Integer tipoRecId;
	private String descripcionCortaRec;
	private String descripcionRec;
	private Integer loteId;
	private Integer loteRecId;
	private Timestamp fechaRegistro;
	private Timestamp fechaEmision;
	private Timestamp fechaNotificacion;
	private Date fechaCancelacion;
	private String nroExpediente;
	private BigDecimal montoDeuda;
	private Integer usuarioId;
	private String nombreUsuario;
	private Integer motivoNotificacionId;
	private Integer notificadorId;
	private Integer anioDeuda;
	private Integer subConceptoId;
	private Integer personaId;
	private Integer anioRec;	
	private String usuarioNotificacion;
	
	public Integer getDeudaExigibleId() {
		return deudaExigibleId;
	}
	public void setDeudaExigibleId(Integer deudaExigibleId) {
		this.deudaExigibleId = deudaExigibleId;
	}
	public Integer getRecId() {
		return recId;
	}
	public void setRecId(Integer recId) {
		this.recId = recId;
	}
	public String getDescripcionCortaRec() {
		return descripcionCortaRec;
	}
	public void setDescripcionCortaRec(String descripcionCortaRec) {
		this.descripcionCortaRec = descripcionCortaRec;
	}
	public String getDescripcionRec() {
		return descripcionRec;
	}
	public void setDescripcionRec(String descripcionRec) {
		this.descripcionRec = descripcionRec;
	}
	public Integer getLoteId() {
		return loteId;
	}
	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}
	public String getNroExpediente() {
		return nroExpediente;
	}
	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}
	public BigDecimal getMontoDeuda() {
		return montoDeuda;
	}
	public void setMontoDeuda(BigDecimal montoDeuda) {
		this.montoDeuda = montoDeuda;
	}
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public void setTipoRecId(Integer tipoRecId) {
		this.tipoRecId = tipoRecId;
	}
	public Integer getMotivoNotificacionId() {
		return motivoNotificacionId;
	}
	public void setMotivoNotificacionId(Integer motivoNotificacionId) {
		this.motivoNotificacionId = motivoNotificacionId;
	}
	public Integer getNotificadorId() {
		return notificadorId;
	}
	public void setNotificadorId(Integer notificadorId) {
		this.notificadorId = notificadorId;
	}
	public Integer getPersonaId() {
		return personaId;
	}
	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	public Integer getTipoRecId() {
		return tipoRecId;
	}

//	public Integer getAnioDeuda() {
//		return anioDeuda;
//	}
//	public void setAnioDeuda(Integer anioDeuda) {
//		this.anioDeuda = anioDeuda;
//	}
	public Integer getSubConceptoId() {
		return subConceptoId;
	}
	public void setSubConceptoId(Integer subConceptoId) {
		this.subConceptoId = subConceptoId;
	}
	public Integer getLoteRecId() {
		return loteRecId;
	}
	public void setLoteRecId(Integer loteRecId) {
		this.loteRecId = loteRecId;
	}
	
	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}
	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}
	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Timestamp getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Timestamp fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public Timestamp getFechaNotificacion() {
		return fechaNotificacion;
	}
	public void setFechaNotificacion(Timestamp fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
	public Integer getAnioRec() {
		return anioRec;
	}
	public void setAnioRec(Integer anioRec) {
		this.anioRec = anioRec;
	}
	public Integer getAnioDeuda() {
		return anioDeuda;
	}
	public void setAnioDeuda(Integer anioDeuda) {
		this.anioDeuda = anioDeuda;
	}
	public String getUsuarioNotificacion() {
		return usuarioNotificacion;
	}
	public void setUsuarioNotificacion(String usuarioNotificacion) {
		this.usuarioNotificacion = usuarioNotificacion;
	}
	
}
