package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * The persistent class for the cc_acto database table.
 * 
 */
@Entity
@Table(name="cc_acto")
public class CcActo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CcActoPK id;

	@Column(name="anno_acto")
	private Integer annoActo;

	private String comentario;

	@Column(name="concepto_id")
	private Integer conceptoId;

	private String estado;

	@Column(name="fecha_cancelacion")
	private Timestamp fechaCancelacion;

	@Column(name="fecha_emision")
	private Timestamp fechaEmision;

	@Column(name="fecha_notificacion")
	private Timestamp fechaNotificacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="flag_cancelacion")
	private String flagCancelacion;
	
	@Column(name="flag_origen")
	private String flagOrigen;

	@Column(name="flag_rec")
	private String flagRec;

	@Column(name="flag_rec_junta")
	private String flagRecJunta;

	@Column(name="flag_res")
	private String flagRes;

	@Column(name="flag_td")
	private String flagTd;

	@Column(name="monto_deuda")
	private BigDecimal montoDeuda;

	@Column(name="nro_acto")
	private String nroActo;

	@Column(name="nro_cargo_notificacion")
	private String nroCargoNotificacion;

	@Column(name="nro_expediente")
	private String nroExpediente;

	@Column(name="nro_resolucion")
	private String nroResolucion;

	@Column(name="persona_id")
	private Integer personaId;

	@Column(name="sector_id")
	private Integer sectorId;

	@Column(name="subconcepto_id")
	private Integer subconceptoId;

	private String terminal;

	@Column(name="tipo_acto_id")
	private Integer tipoActoId;

	@Column(name="tipo_persona_id")
	private Integer tipoPersonaId;

	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="acto_persona_id")
	private Integer actoPersonaId;

	@Column(name="tipo_documento")
	private Integer tipoDocumento;
	
	@Column(name="determinacion_id")
	private Integer determinacionId;
	
    public CcActo() {
    }

	public CcActoPK getId() {
		return this.id;
	}

	public void setId(CcActoPK id) {
		this.id = id;
	}
	
	public Integer getAnnoActo() {
		return this.annoActo;
	}

	public void setAnnoActo(Integer annoActo) {
		this.annoActo = annoActo;
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer getConceptoId() {
		return this.conceptoId;
	}

	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaCancelacion() {
		return this.fechaCancelacion;
	}

	public void setFechaCancelacion(Timestamp fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	public Timestamp getFechaEmision() {
		return this.fechaEmision;
	}

	public void setFechaEmision(Timestamp fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Timestamp getFechaNotificacion() {
		return this.fechaNotificacion;
	}

	public void setFechaNotificacion(Timestamp fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFlagCancelacion() {
		return this.flagCancelacion;
	}

	public void setFlagCancelacion(String flagCancelacion) {
		this.flagCancelacion = flagCancelacion;
	}

	public String getFlagRec() {
		return this.flagRec;
	}

	public void setFlagRec(String flagRec) {
		this.flagRec = flagRec;
	}

	public String getFlagRecJunta() {
		return this.flagRecJunta;
	}

	public void setFlagRecJunta(String flagRecJunta) {
		this.flagRecJunta = flagRecJunta;
	}

	public String getFlagRes() {
		return this.flagRes;
	}

	public void setFlagRes(String flagRes) {
		this.flagRes = flagRes;
	}

	public String getFlagTd() {
		return this.flagTd;
	}

	public void setFlagTd(String flagTd) {
		this.flagTd = flagTd;
	}

	public BigDecimal getMontoDeuda() {
		return this.montoDeuda;
	}

	public void setMontoDeuda(BigDecimal montoDeuda) {
		this.montoDeuda = montoDeuda;
	}

	public String getNroActo() {
		return this.nroActo;
	}

	public void setNroActo(String nroActo) {
		this.nroActo = nroActo;
	}

	public String getNroCargoNotificacion() {
		return this.nroCargoNotificacion;
	}

	public void setNroCargoNotificacion(String nroCargoNotificacion) {
		this.nroCargoNotificacion = nroCargoNotificacion;
	}

	public String getNroExpediente() {
		return this.nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}

	public String getNroResolucion() {
		return this.nroResolucion;
	}

	public void setNroResolucion(String nroResolucion) {
		this.nroResolucion = nroResolucion;
	}

	public Integer getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public Integer getSectorId() {
		return this.sectorId;
	}

	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}

	public Integer getSubconceptoId() {
		return this.subconceptoId;
	}

	public void setSubconceptoId(Integer subconceptoId) {
		this.subconceptoId = subconceptoId;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getTipoActoId() {
		return this.tipoActoId;
	}

	public void setTipoActoId(Integer tipoActoId) {
		this.tipoActoId = tipoActoId;
	}

	public Integer getTipoPersonaId() {
		return this.tipoPersonaId;
	}

	public void setTipoPersonaId(Integer tipoPersonaId) {
		this.tipoPersonaId = tipoPersonaId;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getFlagOrigen() {
		return flagOrigen;
	}

	public void setFlagOrigen(String flagOrigen) {
		this.flagOrigen = flagOrigen;
	}

	public Integer getActoPersonaId() {
		return actoPersonaId;
	}

	public void setActoPersonaId(Integer actoPersonaId) {
		this.actoPersonaId = actoPersonaId;
	}

	public Integer getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Integer getDeterminacionId() {
		return determinacionId;
	}

	public void setDeterminacionId(Integer determinacionId) {
		this.determinacionId = determinacionId;
	}

}