package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the mp_direccion database table.
 * 
 */
@Entity
@Table(name="pa_direccion")
@NamedQuery(name="getPaDireccionByPersonaId", query="SELECT a FROM PaDireccion a WHERE a.personaId=:p_persona_id AND a.estado='1' ")
public class PaDireccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="direccion_id")
	private Integer direccionId;

	@Column(name="tipo_via_id")
	private Integer tipoViaId;
	
	@Column(name="via_id")
	private Integer viaId;

	@Column(name="lugar_id")
	private Integer lugarId;
	
	@Column(name="sector_id")
	private Integer sectorId;
	
	@Column(name="direccion_completa")
	private String direccionCompleta;
	
	@Column(name="numero")
	private String numero;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
	@Column(name="terminal")
	private String terminal;

	@Column(name="persona_id")
	private Integer personaId;
	
	@Column(name="papeleta_id")
	private Integer papeletaId;
	
	@Column(name="es_infractor")
	private String esInfractor;
	
	public String getEsInfractor() {
		return esInfractor;
	}

	public void setEsInfractor(String esInfractor) {
		this.esInfractor = esInfractor;
	}

	public Integer getPapeletaId() {
		return papeletaId;
	}

	public void setPapeletaId(Integer papeletaId) {
		this.papeletaId = papeletaId;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public Integer getDireccionId() {
		return direccionId;
	}

	public void setDireccionId(Integer direccionId) {
		this.direccionId = direccionId;
	}

	public Integer getLugarId() {
		return lugarId;
	}

	public void setLugarId(Integer lugarId) {
		this.lugarId = lugarId;
	}

	public Integer getSectorId() {
		return sectorId;
	}

	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}

	public String getDireccionCompleta() {
		return direccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getViaId() {
		return viaId;
	}

	public void setViaId(Integer viaId) {
		this.viaId = viaId;
	}

	public Integer getTipoViaId() {
		return tipoViaId;
	}

	public void setTipoViaId(Integer tipoViaId) {
		this.tipoViaId = tipoViaId;
	}
}