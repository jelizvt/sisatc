package com.sat.sisat.persistence.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import javax.persistence.Table;

import java.sql.Timestamp;


/**
 * The persistent class for the ra_transferente_alcabala database table.
 * 
 */
@Entity
@Table(name="ra_transferente_alcabala")
//@NamedQuery(name="getRATransferenteAlcabalaByDJAlcabajaId", query="SELECT m FROM RaTransferenteAlcabala m WHERE m.djalcabalaId=:p_djalcabala")
public class RaTransferenteAlcabala implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="djalcabala_id")
	private int djalcabalaId;

	@Column(name="transferente_id")
	private int transferenteId;

	@Column(name="tipo_persona_id")
	private int tipoPersonaId;
	
	@Column(name="subtipo_persona_id")
	private int subtipoPersonaId;
	
	@Column(name="PERSONA_ID")
	private int personaId;
	
	@Column(name="tipo_doc_identidad_id")
	private int tipoDocIdentidadId;
	
	@Column(name="nro_docu_identidad")
	private String nroDocuIdentidad;
	
	@Column(name="ape_paterno")
	private String apePaterno;

	@Column(name="ape_materno")
	private String apeMaterno;
	
	@Column(name="nombres")
	private String nombres;
	
	@Column(name="razon_social")
	private String razonSocial;
	
	@Column(name="nombre_completo")
	private String nombreCompleto;
	
	@Column(name="direccion_completa")
	private String direccionCompleta;
	
	@Column(name="telefono")
	private String telefono;
	
	@Column(name="referencia")
	private String referencia;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="fecha_const_nacimiento")
	private Timestamp fechaConsNacimiento;
		
	@Column(name="fecha_defuncion")
	private Timestamp fechaDefuncion;
	
	@Column(name="nro_partida_defuncion")
	private String nroPartidaDefuncion;
	
	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
	@Column(name="terminal")
	private String terminal;
	


    public RaTransferenteAlcabala() {
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


	public String getDireccionCompleta() {
		return this.direccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}


	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	public String getNombreCompleto() {
		return this.nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}


	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}


	public String getNroDocuIdentidad() {
		return this.nroDocuIdentidad;
	}

	public void setNroDocuIdentidad(String nroDocuIdentidad) {
		this.nroDocuIdentidad = nroDocuIdentidad;
	}


	public int getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}


	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}


	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}


	public int getSubtipoPersonaId() {
		return this.subtipoPersonaId;
	}

	public void setSubtipoPersonaId(int subtipoPersonaId) {
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


	public int getTipoDocIdentidadId() {
		return this.tipoDocIdentidadId;
	}

	public void setTipoDocIdentidadId(int tipoDocIdentidadId) {
		this.tipoDocIdentidadId = tipoDocIdentidadId;
	}


	public int getTipoPersonaId() {
		return this.tipoPersonaId;
	}

	public void setTipoPersonaId(int tipoPersonaId) {
		this.tipoPersonaId = tipoPersonaId;
	}


	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}


	public int getDjalcabalaId() {
		return djalcabalaId;
	}


	public void setDjalcabalaId(int djalcabalaId) {
		this.djalcabalaId = djalcabalaId;
	}

	public int getTransferenteId() {
		return transferenteId;
	}


	public void setTransferenteId(int transferenteId) {
		this.transferenteId = transferenteId;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public Timestamp getFechaDefuncion() {
		return fechaDefuncion;
	}


	public void setFechaDefuncion(Timestamp fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}


	public Timestamp getFechaConsNacimiento() {
		return fechaConsNacimiento;
	}


	public void setFechaConsNacimiento(Timestamp fechaConsNacimiento) {
		this.fechaConsNacimiento = fechaConsNacimiento;
	}


	public String getNroPartidaDefuncion() {
		return nroPartidaDefuncion;
	}


	public void setNroPartidaDefuncion(String nroPartidaDefuncion) {
		this.nroPartidaDefuncion = nroPartidaDefuncion;
	}


	
}