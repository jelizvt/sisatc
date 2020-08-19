package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the mp_relacionado database table.
 * 
 */
@Entity
@Table(name="mp_relacionado")
@NamedQuery(name="getAllMpRelacionadoPersona", query="SELECT a FROM MpRelacionado a WHERE a.id.personaId=:p_persona_id AND a.estado='1'")
public class MpRelacionado implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MpRelacionadoPK id;
	
//	@Id
//	@Column(name="relacionado_id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer relacionadoId;
//	
	@Column(name="ape_materno")
	private String apeMaterno;
	
	@Column(name="ape_paterno")
	private String apePaterno;
	
	private String email;

	private String estado;
	
	private String facebook;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
	@Column(name="nro_docu_identidad")
	private String nroDocuIdentidad;
	
	@Column(name="primer_nombre")
	private String primerNombre;
	
	@Column(name="segundo_nombre")
	private String segundoNombre;
	
	private String telefono;

	private String terminal;

	@Column(name="tipo_relacion_id")
	private Integer tipoRelacionId;

	private String twitter;

	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="tipo_doc_identidad_id")
	private Integer mpTipoDocIdentidadId;
	
	@Column(name="apellidos_nombres")
	private String apellidosNombres;
	
	@Column(name="porc_participacion")
	private BigDecimal porcParticipacion;
	

    public MpRelacionado() {
    }

	public MpRelacionadoPK getId() {
		return this.id;
	}

	public void setId(MpRelacionadoPK id) {
		this.id = id;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFacebook() {
		return this.facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNroDocuIdentidad() {
		return this.nroDocuIdentidad;
	}

	public void setNroDocuIdentidad(String nroDocuIdentidad) {
		this.nroDocuIdentidad = nroDocuIdentidad;
	}

	public String getPrimerNombre() {
		return this.primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
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

	public int getTipoRelacionId() {
		return this.tipoRelacionId;
	}

	public void setTipoRelacionId(int tipoRelacionId) {
		this.tipoRelacionId = tipoRelacionId;
	}

	public String getTwitter() {
		return this.twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getMpTipoDocIdentidadId() {
		return mpTipoDocIdentidadId;
	}

	public void setMpTipoDocIdentidadId(Integer mpTipoDocIdentidadId) {
		this.mpTipoDocIdentidadId = mpTipoDocIdentidadId;
	}

	public String getApellidosNombres() {
		return apellidosNombres;
	}

	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}

	public BigDecimal getPorcParticipacion() {
		return porcParticipacion;
	}

	public void setPorcParticipacion(BigDecimal porcParticipacion) {
		this.porcParticipacion = porcParticipacion;
	}

//	public Integer getRelacionadoId() {
//		return relacionadoId;
//	}
//
//	public void setRelacionadoId(Integer relacionadoId) {
//		this.relacionadoId = relacionadoId;
//	}

	public void setTipoRelacionId(Integer tipoRelacionId) {
		this.tipoRelacionId = tipoRelacionId;
	}

	
	
}