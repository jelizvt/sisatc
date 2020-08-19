package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pa_persona")
public class PaPersona implements Serializable {
	
	private static final long serialVersionUID = -2939835567551770086L;

	@Id
	@Column(name="persona_id")
	private Integer personaId;

	@Column(name="tipo_doc_identidad")
	private Integer tipoDocumentoId;
	
	@Column(name="nro_doc_identidad")
	private String nroDocIdentidad;
	
	@Column(name="primer_nombre")
	private String primerNombre;

	@Column(name="segundo_nombre")
	private String segundoNombre;
	
	@Column(name="ape_paterno")
	private String apePaterno;

	@Column(name="ape_materno")
	private String apeMaterno;
	
	@Column(name="razon_social")
	private String razonSocial;
	
	@Column(name="es_infractor")
	private String esInfractor;
	
	@Column(name="es_propietario")
	private String esPropietario;
	
	@Column(name="num_licencia")
	private String numLicencia;

	@Column(name="clase_licencia_id")
	private Integer claseLicenciaId;
	
	@Column(name="tipo_documento_id")
	private Integer tipoDocumentoIdentidad;

	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="terminal")
	private String terminalRegistro;

	
	public Integer getPersonaId() {
		return personaId;
	}


	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}


	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}


	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}


	public String getNroDocIdentidad() {
		return nroDocIdentidad;
	}


	public void setNroDocIdentidad(String nroDocIdentidad) {
		this.nroDocIdentidad = nroDocIdentidad;
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


	public String getEsInfractor() {
		return esInfractor;
	}


	public void setEsInfractor(String esInfractor) {
		this.esInfractor = esInfractor;
	}


	public String getEsPropietario() {
		return esPropietario;
	}


	public void setEsPropietario(String esPropietario) {
		this.esPropietario = esPropietario;
	}


	public int getUsuarioId() {
		return usuarioId;
	}


	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	public String getTerminalRegistro() {
		return terminalRegistro;
	}


	public void setTerminalRegistro(String terminalRegistro) {
		this.terminalRegistro = terminalRegistro;
	}


	public PaPersona() {
    	
    }


	public String getNumLicencia() {
		return numLicencia;
	}


	public void setNumLicencia(String numLicencia) {
		this.numLicencia = numLicencia;
	}


	public Integer getClaseLicenciaId() {
		return claseLicenciaId;
	}


	public void setClaseLicenciaId(Integer claseLicenciaId) {
		this.claseLicenciaId = claseLicenciaId;
	}


	public Integer getTipoDocumentoIdentidad() {
		return tipoDocumentoIdentidad;
	}


	public void setTipoDocumentoIdentidad(Integer tipoDocumentoIdentidad) {
		this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
	}
	
	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
}