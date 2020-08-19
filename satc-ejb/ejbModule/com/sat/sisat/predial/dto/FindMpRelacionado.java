package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.sat.sisat.persistence.entity.MpRelacionado;

public class FindMpRelacionado  implements Serializable {
	
	private int personaId;
	private int relacionadoId;
	private String relacionadoDescripcion;
	private String apeMaterno;
	private String apePaterno;
	private String email;
	private String estado;
	private String facebook;
	private Timestamp fechaRegistro;
	private String nroDocuIdentidad3;
	private String primerNombre;
	private String segundoNombre;
	private String telefono;
	private String terminal;
	private int tipoRelacionId;
	private String twitter;
	private int usuarioId;
	private int mpTipoDocIdentidadId;
	private String mpTipoDocIdentidadDescripcion;
	private String apellidosNombres;
	private MpRelacionado mpRelacionado;

    public FindMpRelacionado() {
    }

	public int getPersonaId() {
		return personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public int getRelacionadoId() {
		return relacionadoId;
	}

	public void setRelacionadoId(int relacionadoId) {
		this.relacionadoId = relacionadoId;
	}

	public String getRelacionadoDescripcion() {
		return relacionadoDescripcion;
	}

	public void setRelacionadoDescripcion(String relacionadoDescripcion) {
		this.relacionadoDescripcion = relacionadoDescripcion;
	}

	public String getApeMaterno() {
		return apeMaterno;
	}

	public void setApeMaterno(String apeMaterno) {
		this.apeMaterno = apeMaterno;
	}

	public String getApePaterno() {
		return apePaterno;
	}

	public void setApePaterno(String apePaterno) {
		this.apePaterno = apePaterno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNroDocuIdentidad3() {
		return nroDocuIdentidad3;
	}

	public void setNroDocuIdentidad3(String nroDocuIdentidad3) {
		this.nroDocuIdentidad3 = nroDocuIdentidad3;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getTipoRelacionId() {
		return tipoRelacionId;
	}

	public void setTipoRelacionId(int tipoRelacionId) {
		this.tipoRelacionId = tipoRelacionId;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getMpTipoDocIdentidadId() {
		return mpTipoDocIdentidadId;
	}

	public void setMpTipoDocIdentidadId(int mpTipoDocIdentidadId) {
		this.mpTipoDocIdentidadId = mpTipoDocIdentidadId;
	}

	public String getMpTipoDocIdentidadDescripcion() {
		return mpTipoDocIdentidadDescripcion;
	}

	public void setMpTipoDocIdentidadDescripcion(
			String mpTipoDocIdentidadDescripcion) {
		this.mpTipoDocIdentidadDescripcion = mpTipoDocIdentidadDescripcion;
	}

	public String getApellidosNombres() {
		return apellidosNombres;
	}

	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}

	public MpRelacionado getMpRelacionado() {
		return mpRelacionado;
	}

	public void setMpRelacionado(MpRelacionado mpRelacionado) {
		this.mpRelacionado = mpRelacionado;
	}
}
