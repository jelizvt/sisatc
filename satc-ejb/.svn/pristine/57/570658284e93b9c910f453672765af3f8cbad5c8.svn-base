package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the mp_persona_domicilio database table.
 * 
 */
@Entity
@Table(name="mp_persona_domicilio")
public class MpPersonaDomicilio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="domicilio_persona_id")
	private Integer domicilio_persona_id;

	@Column(name="direccion_id")
	private Integer direccionId;

	@Column(name="domicilio_completo")
	private String domicilioCompleto;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="flag_fiscal")
	private String flagFiscal;

	@Column(name="flag_fiscal_notificado")
	private String flagFiscalNotificado;

	@Column(name="flag_procesal")
	private String flagProcesal;

	@Column(name="flag_real")
	private String flagReal;

	@Column(name="persona_id")
	private Integer personaId;

	private String terminal;

	@Column(name="terminal_actualizacion")
	private String terminalActualizacion;

	@Column(name="tipo_predio")
	private String tipoPredio;

	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="usuario_id_actualizacion")
	private Integer usuarioIdActualizacion;
	
	@Column(name="tipo_domicilio")
	private String tipoDomicilio;
	
	@Column(name="estado")
	private String estado;
    
	@Transient
	private Boolean domiProcesal;
	@Transient
	private Boolean domiReal;
	
	public MpPersonaDomicilio() {
    }

	public Integer getDomicilio_persona_id() {
		return this.domicilio_persona_id;
	}

	public void setDomicilio_persona_id(Integer domicilio_persona_id) {
		this.domicilio_persona_id = domicilio_persona_id;
	}

	public Integer getDireccionId() {
		return this.direccionId;
	}

	public void setDireccionId(Integer direccionId) {
		this.direccionId = direccionId;
	}

	public String getDomicilioCompleto() {
		return this.domicilioCompleto;
	}

	public void setDomicilioCompleto(String domicilioCompleto) {
		this.domicilioCompleto = domicilioCompleto;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFlagFiscal() {
		return this.flagFiscal;
	}

	public void setFlagFiscal(String flagFiscal) {
		this.flagFiscal = flagFiscal;
	}

	public String getFlagFiscalNotificado() {
		return this.flagFiscalNotificado;
	}

	public void setFlagFiscalNotificado(String flagFiscalNotificado) {
		this.flagFiscalNotificado = flagFiscalNotificado;
	}

	public String getFlagProcesal() {
		return this.flagProcesal;
	}

	public void setFlagProcesal(String flagProcesal) {
		this.flagProcesal = flagProcesal;
	}

	public String getFlagReal() {
		return this.flagReal;
	}

	public void setFlagReal(String flagReal) {
		this.flagReal = flagReal;
	}

	public int getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTerminalActualizacion() {
		return this.terminalActualizacion;
	}

	public void setTerminalActualizacion(String terminalActualizacion) {
		this.terminalActualizacion = terminalActualizacion;
	}

	public String getTipoPredio() {
		return this.tipoPredio;
	}

	public void setTipoPredio(String tipoPredio) {
		this.tipoPredio = tipoPredio;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getUsuarioIdActualizacion() {
		return this.usuarioIdActualizacion;
	}

	public void setUsuarioIdActualizacion(Integer usuarioIdActualizacion) {
		this.usuarioIdActualizacion = usuarioIdActualizacion;
	}

	public String getTipoDomicilio() {
		return tipoDomicilio;
	}

	public void setTipoDomicilio(String tipoDomicilio) {
		this.tipoDomicilio = tipoDomicilio;
	}

	public Boolean getDomiProcesal() {
		return domiProcesal;
	}

	public void setDomiProcesal(Boolean domiProcesal) {
		this.domiProcesal = domiProcesal;
	}

	public Boolean getDomiReal() {
		return domiReal;
	}

	public void setDomiReal(Boolean domiReal) {
		this.domiReal = domiReal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}