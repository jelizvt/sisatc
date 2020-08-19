package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the cc_rec database table.
 * 
 */
@Entity
@Table(name="cc_rec")
public class CcRec implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="rec_id")
	private int recId;

	@Column(name="anno_rec")
	private int annoRec;

	@Column(name="deuda_total")
	private BigDecimal deuaTotal;

	private String estado;

	@Column(name="fecha_emision")
	private Timestamp fechaEmision;

	@Column(name="fecha_rec")
	private Timestamp fechaRec;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="lote_id")
	private int loteId;

	@Column(name="motivo_cancelacion")
	private String motivoCancelacion;

	@Column(name="nro_notificacion")
	private String nroNotificacion;

	@Column(name="nro_rec")
	private String nroRec;

	@Column(name="persona_id")
	private int personaId;

	private String terminal;
	
	@Transient
	private String text;
	
	@Transient
	private String type;
	
	@Transient
	private Boolean seleccionado;

    public CcRec() {
    }

	public int getRecId() {
		return this.recId;
	}

	public void setRecId(int recId) {
		this.recId = recId;
	}

	public int getAnnoRec() {
		return this.annoRec;
	}

	public void setAnnoRec(int annoRec) {
		this.annoRec = annoRec;
	}

	public BigDecimal getDeuaTotal() {
		return this.deuaTotal;
	}

	public void setDeuaTotal(BigDecimal deuaTotal) {
		this.deuaTotal = deuaTotal;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaEmision() {
		return this.fechaEmision;
	}

	public void setFechaEmision(Timestamp fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Timestamp getFechaRec() {
		return this.fechaRec;
	}

	public void setFechaRec(Timestamp fechaRec) {
		this.fechaRec = fechaRec;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getLoteId() {
		return this.loteId;
	}

	public void setLoteId(int loteId) {
		this.loteId = loteId;
	}

	public String getMotivoCancelacion() {
		return this.motivoCancelacion;
	}

	public void setMotivoCancelacion(String motivoCancelacion) {
		this.motivoCancelacion = motivoCancelacion;
	}

	public String getNroNotificacion() {
		return this.nroNotificacion;
	}

	public void setNroNotificacion(String nroNotificacion) {
		this.nroNotificacion = nroNotificacion;
	}

	public String getNroRec() {
		return this.nroRec;
	}

	public void setNroRec(String nroRec) {
		this.nroRec = nroRec;
	}

	public int getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

}