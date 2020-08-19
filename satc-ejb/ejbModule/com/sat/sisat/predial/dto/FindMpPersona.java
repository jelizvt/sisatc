package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.sat.sisat.persistence.entity.MpPersona;

public class FindMpPersona  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4263443752932524827L;
	private Integer personaId;
	private Integer tipoPersonaId;
	private Integer subtipoPersonaId;
	private Integer tipoDocIdentidadId;
	private String tipoPersona;
	private String tipoDocumentoIdentidad;
	private String nroDocuIdentidad;
	private String apePaterno;
	private String apeMaterno;
	private String primerNombre;
	private String segundoNombre;
	private String razonSocial;
	private String apellidosNombres;
	private Integer predios;
	private Integer vehiculos;
	private String domicilioPersona;
	private String nroDocIdentidadAdi;
	private String telefono;
	private String fax;
	private String email;
	private String twitter;
	private String facebook;
	private String flagNotificaEmail;
	private Timestamp fechaDeclaracion;
	private Integer nroDj;
	private Timestamp fechaBaja;
	private String nroPartidaDefuncion;
	private Timestamp fechaSituacionEmpresarial;
	private Timestamp fechaFinSituacionEmpresarial;
	private String tipoDocSustSituacionEmpresarial;
	private String nroDocSustSituacionEmpresarial;
	private Timestamp fechaEmisionSituacionEmpresarial;
	private Integer situacionEmpresarialId;
	private Integer tipodocumentoId;
	private String tipodocumento;
	private String subtipopersona;
	private String tipoCondicionEspecial;
	private Integer tipoCondicionEspecialId;
	private String situacionEmpresarial;
	private MpPersona mpPersona;
	private Integer gncondicionEspecialId;
	private Timestamp fechaInscripcion;
	private String tipoDocuAdicional;
	private String nroDocuAdicional;
	private String notificaEmail;
	private String nroDocumentoCondContri;
	private Timestamp fechaRegistro;
	private Timestamp fechaRegistroCondContri;
	private Timestamp fechaInicioCondContri;
	private Timestamp fechafinCondContri;
	private Timestamp fechaDocumentoCondContri;
	private String nroDjFormato;
	private String estado;
	private String estadoDescripcion;
	private String partidaDefuncion;
	private Timestamp fechaDefuncion;
	private Double porcentaje;
	private int ubicacionId;
	private String placa;
	private String flagEstatal;
	private String glosa;
	private boolean flagUbicableControl;
	
	//Agrega el buscador de personas relacionados
	private String relacionado;
	
	public String getRelacionado() {
		return relacionado;
	}
	public void setRelacionado(String relacionado) {
		this.relacionado = relacionado;
	}
	private boolean estadoRegPersona;
	public String getApellidosNombres() {
		return apellidosNombres;
	}
	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}
	public Integer getPersonaId() {
		return personaId;
	}
	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	public Integer getTipoPersonaId() {
		return tipoPersonaId;
	}
	public void setTipoPersonaId(Integer tipoPersonaId) {
		this.tipoPersonaId = tipoPersonaId;
	}
	public Integer getTipoDocIdentidadId() {
		return tipoDocIdentidadId;
	}
	public void setTipoDocIdentidadId(Integer tipoDocIdentidadId) {
		this.tipoDocIdentidadId = tipoDocIdentidadId;
	}
	public String getNroDocuIdentidad() {
		return nroDocuIdentidad;
	}
	public void setNroDocuIdentidad(String nroDocuIdentidad) {
		this.nroDocuIdentidad = nroDocuIdentidad;
	}
	public String getApePaterno() {
		return apePaterno;
	}
	public void setApePaterno(String apePaterno) {
		this.apePaterno = apePaterno;
	}
	public String getApeMaterno() {
		return apeMaterno;
	}
	public void setApeMaterno(String apeMaterno) {
		this.apeMaterno = apeMaterno;
	}
	public String getPrimerNombre() {
		return primerNombre;
	}
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	public String getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public String getTipoDocumentoIdentidad() {
		return tipoDocumentoIdentidad;
	}
	public void setTipoDocumentoIdentidad(String tipoDocumentoIdentidad) {
		this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
	}
	public Integer getPredios() {
		return predios;
	}
	public void setPredios(Integer predios) {
		this.predios = predios;
	}
	public Integer getVehiculos() {
		return vehiculos;
	}
	public void setVehiculos(Integer vehiculos) {
		this.vehiculos = vehiculos;
	}
	public String getDomicilioPersona() {
		return domicilioPersona;
	}
	public void setDomicilioPersona(String domicilioPersona) {
		this.domicilioPersona = domicilioPersona;
	}
	public MpPersona getMpPersona() {
		return mpPersona;
	}
	public void setMpPersona(MpPersona mpPersona) {
		this.mpPersona = mpPersona;
	}
	public String getNroDocIdentidadAdi() {
		return nroDocIdentidadAdi;
	}
	public void setNroDocIdentidadAdi(String nroDocIdentidadAdi) {
		this.nroDocIdentidadAdi = nroDocIdentidadAdi;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public String getFlagNotificaEmail() {
		return flagNotificaEmail;
	}
	public void setFlagNotificaEmail(String flagNotificaEmail) {
		this.flagNotificaEmail = flagNotificaEmail;
	}
	public Timestamp getFechaDeclaracion() {
		return fechaDeclaracion;
	}
	public void setFechaDeclaracion(Timestamp fechaDeclaracion) {
		this.fechaDeclaracion = fechaDeclaracion;
	}
	public Integer getNroDj() {
		return nroDj;
	}
	public void setNroDj(Integer nroDj) {
		this.nroDj = nroDj;
	}
	public Timestamp getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Timestamp fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getNroPartidaDefuncion() {
		return nroPartidaDefuncion;
	}
	public void setNroPartidaDefuncion(String nroPartidaDefuncion) {
		this.nroPartidaDefuncion = nroPartidaDefuncion;
	}
	public Timestamp getFechaSituacionEmpresarial() {
		return fechaSituacionEmpresarial;
	}
	public void setFechaSituacionEmpresarial(Timestamp fechaSituacionEmpresarial) {
		this.fechaSituacionEmpresarial = fechaSituacionEmpresarial;
	}
	public Timestamp getFechaFinSituacionEmpresarial() {
		return fechaFinSituacionEmpresarial;
	}
	public void setFechaFinSituacionEmpresarial(
			Timestamp fechaFinSituacionEmpresarial) {
		this.fechaFinSituacionEmpresarial = fechaFinSituacionEmpresarial;
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
	public String getTipodocumento() {
		return tipodocumento;
	}
	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}
	public String getSubtipopersona() {
		return subtipopersona;
	}
	public void setSubtipopersona(String subtipopersona) {
		this.subtipopersona = subtipopersona;
	}
	public String getTipoCondicionEspecial() {
		return tipoCondicionEspecial;
	}
	public void setTipoCondicionEspecial(String tipoCondicionEspecial) {
		this.tipoCondicionEspecial = tipoCondicionEspecial;
	}
	public String getSituacionEmpresarial() {
		return situacionEmpresarial;
	}
	public void setSituacionEmpresarial(String situacionEmpresarial) {
		this.situacionEmpresarial = situacionEmpresarial;
	}
	public Integer getGncondicionEspecialId() {
		return gncondicionEspecialId;
	}
	public void setGncondicionEspecialId(Integer gncondicionEspecialId) {
		this.gncondicionEspecialId = gncondicionEspecialId;
	}
	public Integer getTipodocumentoId() {
		return tipodocumentoId;
	}
	public void setTipodocumentoId(Integer tipodocumentoId) {
		this.tipodocumentoId = tipodocumentoId;
	}
	public Timestamp getFechaInscripcion() {
		return fechaInscripcion;
	}
	public void setFechaInscripcion(Timestamp fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}
	public String getTipoDocuAdicional() {
		return tipoDocuAdicional;
	}
	public void setTipoDocuAdicional(String tipoDocuAdicional) {
		this.tipoDocuAdicional = tipoDocuAdicional;
	}
	public String getNroDocuAdicional() {
		return nroDocuAdicional;
	}
	public void setNroDocuAdicional(String nroDocuAdicional) {
		this.nroDocuAdicional = nroDocuAdicional;
	}
	public String getNotificaEmail() {
		return notificaEmail;
	}
	public void setNotificaEmail(String notificaEmail) {
		this.notificaEmail = notificaEmail;
	}
	public String getNroDocumentoCondContri() {
		return nroDocumentoCondContri;
	}
	public void setNroDocumentoCondContri(String nroDocumentoCondContri) {
		this.nroDocumentoCondContri = nroDocumentoCondContri;
	}
	public Timestamp getFechaRegistroCondContri() {
		return fechaRegistroCondContri;
	}
	public void setFechaRegistroCondContri(Timestamp fechaRegistroCondContri) {
		this.fechaRegistroCondContri = fechaRegistroCondContri;
	}
	public Timestamp getFechaInicioCondContri() {
		return fechaInicioCondContri;
	}
	public void setFechaInicioCondContri(Timestamp fechaInicioCondContri) {
		this.fechaInicioCondContri = fechaInicioCondContri;
	}
	public Timestamp getFechafinCondContri() {
		return fechafinCondContri;
	}
	public void setFechafinCondContri(Timestamp fechafinCondContri) {
		this.fechafinCondContri = fechafinCondContri;
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
	public String getEstadoDescripcion() {
		return estadoDescripcion;
	}
	public void setEstadoDescripcion(String estadoDescripcion) {
		this.estadoDescripcion = estadoDescripcion;
	}
	public Integer getSubtipoPersonaId() {
		return subtipoPersonaId;
	}
	public void setSubtipoPersonaId(Integer subtipoPersonaId) {
		this.subtipoPersonaId = subtipoPersonaId;
	}
	public String getPartidaDefuncion() {
		return partidaDefuncion;
	}
	public void setPartidaDefuncion(String partidaDefuncion) {
		this.partidaDefuncion = partidaDefuncion;
	}
	public Timestamp getFechaDefuncion() {
		return fechaDefuncion;
	}
	public void setFechaDefuncion(Timestamp fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}
	public Integer getTipoCondicionEspecialId() {
		return tipoCondicionEspecialId;
	}
	public void setTipoCondicionEspecialId(Integer tipoCondicionEspecialId) {
		this.tipoCondicionEspecialId = tipoCondicionEspecialId;
	}
	public Timestamp getFechaDocumentoCondContri() {
		return fechaDocumentoCondContri;
	}
	public void setFechaDocumentoCondContri(Timestamp fechaDocumentoCondContri) {
		this.fechaDocumentoCondContri = fechaDocumentoCondContri;
	}
	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public boolean isEstadoRegPersona() {
		return estadoRegPersona;
	}
	public void setEstadoRegPersona(boolean estadoRegPersona) {
		this.estadoRegPersona = estadoRegPersona;
	}
	public Double getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}
	public int getUbicacionId() {
		return ubicacionId;
	}
	public void setUbicacionId(int ubicacionId) {
		this.ubicacionId = ubicacionId;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
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
	public boolean isFlagUbicableControl() {
		return flagUbicableControl;
	}
	public void setFlagUbicableControl(boolean flagUbicableControl) {
		this.flagUbicableControl = flagUbicableControl;
	}
	
}
