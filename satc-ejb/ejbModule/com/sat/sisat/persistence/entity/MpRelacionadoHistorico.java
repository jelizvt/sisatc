package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the mp_relacionado_historico database table.
 * 
 */
@Entity
@Table(name="mp_relacionado_historico")
public class MpRelacionadoHistorico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="historico_rel_id")
	private Integer historicoRelId;

	private String anexo;

	@Column(name="ape_materno")
	private String apeMaterno;

	@Column(name="ape_paterno")
	private String apePaterno;

	@Column(name="dj_contribuyente")
	private int djContribuyente;

	private String email;

	private int estado;

	private String fax;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="fecha_fin")
	private Timestamp fechaFin;

	@Column(name="fecha_fin_vigencia")
	private Timestamp fechaFinVigencia;

	@Column(name="fecha_ini_vigencia")
	private Timestamp fechaIniVigencia;

	@Column(name="fecha_inicio")
	private Timestamp fechaInicio;

	@Column(name="fecha_operacion")
	private Timestamp fechaOperacion;

	@Column(name="nro_doc_identidad")
	private String nroDocIdentidad;

	@Column(name="persona_id")
	private int personaId;

	@Column(name="primer_nombre")
	private String primerNombre;

	private int relacionado;

	@Column(name="relacionado_id")
	private int relacionadoId;

	@Column(name="segundo_nombre")
	private String segundoNombre;

	private String telefono;

	private String terminal;

	@Column(name="tipo_docu_identidad")
	private int tipoDocuIdentidad;

	@Column(name="tipo_operacion_id")
	private int tipoOperacionId;

	@Column(name="tipo_relacion")
	private int tipoRelacion;

	@Column(name="usuario_id")
	private int usuarioId;

	@Column(name="usuario_operacion")
	private int usuarioOperacion;

    public MpRelacionadoHistorico() {
    }

	public int getHistoricoRelId() {
		return this.historicoRelId;
	}

	public void setHistoricoRelId(int historicoRelId) {
		this.historicoRelId = historicoRelId;
	}

	public String getAnexo() {
		return this.anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public String getApeMaterno() {
		return this.apeMaterno;
	}

	public void setApeMaterno(String apeMaterno) {
		this.apeMaterno = apeMaterno;
	}

	public String getApePaterno() {
		return this.apePaterno;
	}

	public void setApePaterno(String apePaterno) {
		this.apePaterno = apePaterno;
	}

	public int getDjContribuyente() {
		return this.djContribuyente;
	}

	public void setDjContribuyente(int djContribuyente) {
		this.djContribuyente = djContribuyente;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEstado() {
		return this.estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Timestamp getFechaFinVigencia() {
		return this.fechaFinVigencia;
	}

	public void setFechaFinVigencia(Timestamp fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public Timestamp getFechaIniVigencia() {
		return this.fechaIniVigencia;
	}

	public void setFechaIniVigencia(Timestamp fechaIniVigencia) {
		this.fechaIniVigencia = fechaIniVigencia;
	}

	public Timestamp getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaOperacion() {
		return this.fechaOperacion;
	}

	public void setFechaOperacion(Timestamp fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public String getNroDocIdentidad() {
		return this.nroDocIdentidad;
	}

	public void setNroDocIdentidad(String nroDocIdentidad) {
		this.nroDocIdentidad = nroDocIdentidad;
	}

	public int getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public String getPrimerNombre() {
		return this.primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public int getRelacionado() {
		return this.relacionado;
	}

	public void setRelacionado(int relacionado) {
		this.relacionado = relacionado;
	}

	public int getRelacionadoId() {
		return this.relacionadoId;
	}

	public void setRelacionadoId(int relacionadoId) {
		this.relacionadoId = relacionadoId;
	}

	public String getSegundoNombre() {
		return this.segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getTipoDocuIdentidad() {
		return this.tipoDocuIdentidad;
	}

	public void setTipoDocuIdentidad(int tipoDocuIdentidad) {
		this.tipoDocuIdentidad = tipoDocuIdentidad;
	}

	public int getTipoOperacionId() {
		return this.tipoOperacionId;
	}

	public void setTipoOperacionId(int tipoOperacionId) {
		this.tipoOperacionId = tipoOperacionId;
	}

	public int getTipoRelacion() {
		return this.tipoRelacion;
	}

	public void setTipoRelacion(int tipoRelacion) {
		this.tipoRelacion = tipoRelacion;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getUsuarioOperacion() {
		return this.usuarioOperacion;
	}

	public void setUsuarioOperacion(int usuarioOperacion) {
		this.usuarioOperacion = usuarioOperacion;
	}

}