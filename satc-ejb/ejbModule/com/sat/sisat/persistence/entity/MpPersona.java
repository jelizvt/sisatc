package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the mp_persona database table.
 * 
 */
@Entity
@Table(name="mp_persona")
//@NamedQuery(name="getAllPersonaId", query="SELECT a.personaId FROM MpPersona a WHERE a.estado='1' AND a. AND a. ORDER BY a.personaId ASC")
@NamedQuery(name="getAllPersonaId", query="select distinct r.personaId  from RpDjpredial r where r.annoDj=2013 and r.estado='1' and r.flagDjAnno='1'")
public class MpPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="persona_id")
	private Integer personaId;

	@Column(name="ape_materno")
	private String apeMaterno;

	@Column(name="ape_paterno")
	private String apePaterno;

	@Column(name="apellidos_nombres")
	private String apellidosNombres;

	//private String clave;

	private String email;

	private String facebook;

	private String fax;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="fecha_baja")
	private Timestamp fechaBaja;

	@Column(name="fecha_declaracion")
	private Timestamp fechaDeclaracion;

	@Column(name="fecha_defuncion")
	private Timestamp fechaDefuncion;

	@Column(name="fecha_fin_situacion_empresarial")
	private Timestamp fechaFinSituacionEmpresarial;

	@Column(name="fecha_inscripcion")
	private Timestamp fechaInscripcion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="fecha_situacion_empre")
	private Timestamp fechaSituacionEmpre;

	@Column(name="flag_notifica_email")
	private String flagNotificaEmail;

	//private String login;

	@Column(name="nro_dj")
	private Integer nroDj;

	@Column(name="nro_docu_identidad")
	private String nroDocuIdentidad;

	@Column(name="nro_docu_identidad_adi")
	private String nroDocuIdentidadAdi;

	@Column(name="nro_licencia")
	private String nroLicencia;

	@Column(name="nro_partida_defuncion")
	private String nroPartidaDefuncion;

	@Column(name="primer_nombre")
	private String primerNombre;

	@Column(name="razon_social")
	private String razonSocial;

	@Column(name="segundo_nombre")
	private String segundoNombre;

	@Column(name="subtipo_persona_id")
	private Integer subtipoPersonaId;

	private String telefono;

	private String terminal;

	@Column(name="tipo_doc_identidad_id")
	private Integer tipoDocIdentidadId;
	
	@Column(name="situacion_empresarial_id")
	private Integer situacionEmpresarialId;

	@Column(name="tipo_persona_id")
	private Integer tipoPersonaId;

	private String twitter;

	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="tipo_doc_sust_situacion_empresarial")
	private String tipoDocSustSituacionEmpresarial;
	
	@Column(name="nro_doc_sust_situacion_empresarial")
	private String nroDocSustSituacionEmpresarial;
	
	@Column(name="fecha_emision_situacion_empresarial")
	private Timestamp fechaEmisionSituacionEmpresarial;
	
	@Column(name="nro_dj_formato")
	private String nroDjFormato;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="flag_estatal")
	private String flagEstatal;
	
	@Column(name="glosa")
	private String glosa;
	
	@Column(name="cod_ant_satcaj")
	private String codAntSatcaj;
	
	//@Column(name="is_ubicable_control", nullable = false) 
	@Column(name="is_ubicable_control")
	private boolean isUbicableControl = false;
	
	
	transient
	private Boolean notificaEmail;

	public MpPersona() {
    }
   
    public MpPersona(Integer personaId){
    	this.personaId=personaId;
    }
    
	
    public Integer getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
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

	public String getApellidosNombres() {
		return this.apellidosNombres;
	}

	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}

	/*
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}*/

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebook() {
		return this.facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
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

	public Timestamp getFechaBaja() {
		return this.fechaBaja;
	}

	public void setFechaBaja(Timestamp fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Timestamp getFechaDeclaracion() {
		return this.fechaDeclaracion;
	}

	public void setFechaDeclaracion(Timestamp fechaDeclaracion) {
		this.fechaDeclaracion = fechaDeclaracion;
	}

	public Timestamp getFechaDefuncion() {
		return this.fechaDefuncion;
	}

	public void setFechaDefuncion(Timestamp fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}

	public Timestamp getFechaFinSituacionEmpresarial() {
		return this.fechaFinSituacionEmpresarial;
	}

	public void setFechaFinSituacionEmpresarial(Timestamp fechaFinSituacionEmpresarial) {
		this.fechaFinSituacionEmpresarial = fechaFinSituacionEmpresarial;
	}

	public Timestamp getFechaInscripcion() {
		return this.fechaInscripcion;
	}

	public void setFechaInscripcion(Timestamp fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Timestamp getFechaSituacionEmpre() {
		return this.fechaSituacionEmpre;
	}

	public void setFechaSituacionEmpre(Timestamp fechaSituacionEmpre) {
		this.fechaSituacionEmpre = fechaSituacionEmpre;
	}

	public String getFlagNotificaEmail() {
		return this.flagNotificaEmail;
	}

	public void setFlagNotificaEmail(String flagNotificaEmail) {
		this.flagNotificaEmail = flagNotificaEmail;
	}

	/*
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}*/

	public Integer getNroDj() {
		return this.nroDj;
	}

	public void setNroDj(Integer nroDj) {
		this.nroDj = nroDj;
	}

	public String getNroDocuIdentidad() {
		return this.nroDocuIdentidad;
	}

	public void setNroDocuIdentidad(String nroDocuIdentidad) {
		this.nroDocuIdentidad = nroDocuIdentidad;
	}

	public String getNroDocuIdentidadAdi() {
		return this.nroDocuIdentidadAdi;
	}

	public void setNroDocuIdentidadAdi(String nroDocuIdentidadAdi) {
		this.nroDocuIdentidadAdi = nroDocuIdentidadAdi;
	}

	public String getNroLicencia() {
		return this.nroLicencia;
	}

	public void setNroLicencia(String nroLicencia) {
		this.nroLicencia = nroLicencia;
	}

	public String getNroPartidaDefuncion() {
		return this.nroPartidaDefuncion;
	}

	public void setNroPartidaDefuncion(String nroPartidaDefuncion) {
		this.nroPartidaDefuncion = nroPartidaDefuncion;
	}

	public String getPrimerNombre() {
		return this.primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getSegundoNombre() {
		return this.segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public Integer getSubtipoPersonaId() {
		return this.subtipoPersonaId;
	}

	public void setSubtipoPersonaId(Integer subtipoPersonaId) {
		this.subtipoPersonaId = subtipoPersonaId;
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

	public Integer getTipoDocIdentidadId() {
		return this.tipoDocIdentidadId;
	}

	public void setTipoDocIdentidadId(Integer tipoDocIdentidadId) {
		this.tipoDocIdentidadId = tipoDocIdentidadId;
	}

	public Integer getTipoPersonaId() {
		return this.tipoPersonaId;
	}

	public void setTipoPersonaId(Integer tipoPersonaId) {
		this.tipoPersonaId = tipoPersonaId;
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

	public Boolean isNotificaEmail() {
		return notificaEmail;
	}

	public void setNotificaEmail(Boolean notificaEmail) {
		this.notificaEmail = notificaEmail;
	}

	public String getTipoDocSustSituacionEmpresarial() {
		return tipoDocSustSituacionEmpresarial;
	}

	public void setTipoDocSustSituacionEmpresarial(
			String tipoDocSustSituacionEmpresarial) {
		this.tipoDocSustSituacionEmpresarial = tipoDocSustSituacionEmpresarial;
	}

	public String getNroDocSustSituacionEmpresarial() {
		return nroDocSustSituacionEmpresarial;
	}

	public void setNroDocSustSituacionEmpresarial(
			String nroDocSustSituacionEmpresarial) {
		this.nroDocSustSituacionEmpresarial = nroDocSustSituacionEmpresarial;
	}

	public Timestamp getFechaEmisionSituacionEmpresarial() {
		return fechaEmisionSituacionEmpresarial;
	}

	public void setFechaEmisionSituacionEmpresarial(
			Timestamp fechaEmisionSituacionEmpresarial) {
		this.fechaEmisionSituacionEmpresarial = fechaEmisionSituacionEmpresarial;
	}

	public Integer getSituacionEmpresarialId() {
		return situacionEmpresarialId;
	}

	public void setSituacionEmpresarialId(Integer situacionEmpresarialId) {
		this.situacionEmpresarialId = situacionEmpresarialId;
	}

	public String getNroDjFormato() {
		return nroDjFormato;
	}

	public void setNroDjFormato(String nroDjFormato) {
		this.nroDjFormato = nroDjFormato;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Boolean getNotificaEmail() {
			return notificaEmail;
	}

	public String getFlagEstatal() {
		return flagEstatal;
	}

	public void setFlagEstatal(String flagEstatal) {
		this.flagEstatal = flagEstatal;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public String getCodAntSatcaj() {
		return codAntSatcaj;
	}

	public void setCodAntSatcaj(String codAntSatcaj) {
		this.codAntSatcaj = codAntSatcaj;
	}

	public boolean isUbicableControl() {
		return isUbicableControl;
	}

	public void setUbicableControl(boolean isUbicableControl) {
		
			this.isUbicableControl = isUbicableControl;

	}

	

	

	
	
}