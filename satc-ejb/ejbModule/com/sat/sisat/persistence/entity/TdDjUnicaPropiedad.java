package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "td_DJ_unica_propiedad")
public class TdDjUnicaPropiedad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -954665782952785208L;
	
	@Id
	@Column(name="unica_prop_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer unicaPropId;
	
	@Column(name="dj_id")
	private Integer djId;
	
	@Column(name="vivienda")
	private Boolean vivienda;
	
	@Column(name="negocio")
	private Boolean negocio;
	
	@Column(name="licencia_funcionamiento")
	private Boolean licenciaFuncionamiento;
	
	@Column(name="requisito_licencia_id")
	private Integer requisitoLicenciaId;
	
	@Column(name="contribuyente_id")
	private Integer contribuyenteId;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="estado")
	private Integer estado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;
	
	@Column(name="terminal")
	private String terminal;

	public Integer getUnicaPropId() {
		return unicaPropId;
	}

	public void setUnicaPropId(Integer unicaPropId) {
		this.unicaPropId = unicaPropId;
	}

	public Integer getDjId() {
		return djId;
	}

	public void setDjId(Integer djId) {
		this.djId = djId;
	}



	public Boolean getVivienda() {
		return vivienda;
	}

	public void setVivienda(Boolean vivienda) {
		this.vivienda = vivienda;
	}

	public Boolean getNegocio() {
		return negocio;
	}

	public void setNegocio(Boolean negocio) {
		this.negocio = negocio;
	}

	public Boolean getLicenciaFuncionamiento() {
		return licenciaFuncionamiento;
	}

	public void setLicenciaFuncionamiento(Boolean licenciaFuncionamiento) {
		this.licenciaFuncionamiento = licenciaFuncionamiento;
	}

	public Integer getRequisitoLicenciaId() {
		return requisitoLicenciaId;
	}

	public void setRequisitoLicenciaId(Integer requisitoLicenciaId) {
		this.requisitoLicenciaId = requisitoLicenciaId;
	}

	public Integer getContribuyenteId() {
		return contribuyenteId;
	}

	public void setContribuyenteId(Integer contribuyenteId) {
		this.contribuyenteId = contribuyenteId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	

}
