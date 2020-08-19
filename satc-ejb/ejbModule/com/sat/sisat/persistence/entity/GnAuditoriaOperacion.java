package com.sat.sisat.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="gn_auditoria_operacion")
public class GnAuditoriaOperacion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(name="auditoria_id")	
	private int auditoriaId;
	
	@Column(name="tabla_id")
	private Integer tablaId;

	@Column(name="tabla_nombre")
	private String tablaNombre;
	
	@Column(name="persona_id")
	private Integer personaId;
	
	@Column(name="contribuyente")
	private String contribuyente;
	
	@Column(name="tipo_doc_identidad")
	private Integer tipoDocumentoId;
	
	@Column(name="nro_doc_identidad")
	private String nroDocIdentidad;
	
	@Column(name="numero_operacion")
	private String codigoOperacion;
	
	@Column(name="predio_id")
	private Integer predioId;
	
	@Column(name="placa")
	private String placa;
	
	@Column(name="motivo_descargo_id")
	private Integer motivoDescargoId;
	
	@Column(name="motivo_declaracion_id")
	private Integer motivoDeclaracionId;
	
	@Column(name="estado_operacion")
	private String estado;
	
	@Column(name="anio_operacion")
	private Integer anio;
	
	@Column(name="monto_operacion")
	private Double monto;
	
	@Column(name="tipo_operacion")
	private Integer tipoOperacion;
	
	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="terminal")
	private String terminalRegistro;

	@Column(name="fecha_registro")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fechaRegistro;
	
	
	public GnAuditoriaOperacion() {
    	
    }


	public int getAuditoriaId() {
		return auditoriaId;
	}


	public void setAuditoriaId(int auditoriaId) {
		this.auditoriaId = auditoriaId;
	}


	public Integer getTablaId() {
		return tablaId;
	}


	public void setTablaId(Integer tablaId) {
		this.tablaId = tablaId;
	}


	public String getTablaNombre() {
		return tablaNombre;
	}


	public void setTablaNombre(String tablaNombre) {
		this.tablaNombre = tablaNombre;
	}


	public Integer getPersonaId() {
		return personaId;
	}


	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}


	public String getContribuyente() {
		return contribuyente;
	}


	public void setContribuyente(String contribuyente) {
		this.contribuyente = contribuyente;
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


	public String getCodigoOperacion() {
		return codigoOperacion;
	}


	public void setCodigoOperacion(String codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
	}


	public Integer getPredioId() {
		return predioId;
	}


	public void setPredioId(Integer predioId) {
		this.predioId = predioId;
	}


	public String getPlaca() {
		return placa;
	}


	public void setPlaca(String placa) {
		this.placa = placa;
	}


	public Integer getMotivoDescargoId() {
		return motivoDescargoId;
	}


	public void setMotivoDescargoId(Integer motivoDescargoId) {
		this.motivoDescargoId = motivoDescargoId;
	}


	public Integer getMotivoDeclaracionId() {
		return motivoDeclaracionId;
	}


	public void setMotivoDeclaracionId(Integer motivoDeclaracionId) {
		this.motivoDeclaracionId = motivoDeclaracionId;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public Integer getAnio() {
		return anio;
	}


	public void setAnio(Integer anio) {
		this.anio = anio;
	}


	public Double getMonto() {
		return monto;
	}


	public void setMonto(Double monto) {
		this.monto = monto;
	}


	public Integer getTipoOperacion() {
		return tipoOperacion;
	}


	public void setTipoOperacion(Integer tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}


	public Integer getUsuarioId() {
		return usuarioId;
	}


	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}


	public String getTerminalRegistro() {
		return terminalRegistro;
	}


	public void setTerminalRegistro(String terminalRegistro) {
		this.terminalRegistro = terminalRegistro;
	}


	public java.util.Date getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(java.util.Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	
}
