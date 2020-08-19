package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "td_DJ_conyuge")
public class TdDjConyuge implements Serializable{

	private static final long serialVersionUID = -421942756235155659L;
	
	@Id
	@Column(name="conyuge_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer conyugeId;
	
	@Column(name="dj_id")
	private Integer djId;
	 
	@Column(name="relacionado_id")
	private Integer relacionadoId;
	
	@Column(name="nro_doc_ident")
	private String nroDocIdent;
	
	@Column(name="nombre_apellidos")
	private String nombreApellidos;
	
	@Column(name="porcentaje_part")
	private BigDecimal porcentajePart;
	
	@Column(name="fallecido")
	private Boolean fallecido;
 
	@Column(name="requisito_part_id")
	private Integer requisitoPartId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_partida_defuncion")
	private Date fechaPartidaDefuncion;
	
	@Column(name="requisito_suc_id")
	private Integer requisitoSucId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_sucesion_intestada")
	private Date fechaSucesionIntestada;
	
	@Column(name="cuota_ideal")
	private BigDecimal cuotaIdeal;
	
	@Column(name="contribuyente_id")
	private Integer contribuyenteId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_presentacion")
	private Date fechaPresentacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="estado")
	private Integer estado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;
	
	@Column(name="terminal")
	private String terminal;
	
	
	
	
	



	public Integer getConyugeId() {
		return conyugeId;
	}

	public void setConyugeId(Integer conyugeId) {
		this.conyugeId = conyugeId;
	}

	public Integer getDjId() {
		return djId;
	}

	public void setDjId(Integer djId) {
		this.djId = djId;
	}

	public Integer getRelacionadoId() {
		return relacionadoId;
	}

	public void setRelacionadoId(Integer relacionadoId) {
		this.relacionadoId = relacionadoId;
	}

	public String getNroDocIdent() {
		return nroDocIdent;
	}

	public void setNroDocIdent(String nroDocIdent) {
		this.nroDocIdent = nroDocIdent;
	}

	public String getNombreApellidos() {
		return nombreApellidos;
	}

	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}

	public BigDecimal getPorcentajePart() {
		return porcentajePart;
	}

	public void setPorcentajePart(BigDecimal porcentajePart) {
		this.porcentajePart = porcentajePart;
	}

	public Boolean getFallecido() {
		return fallecido;
	}

	public void setFallecido(Boolean fallecido) {
		this.fallecido = fallecido;
	}

	public Integer getRequisitoPartId() {
		return requisitoPartId;
	}

	public void setRequisitoPartId(Integer requisitoPartId) {
		this.requisitoPartId = requisitoPartId;
	}

	public Date getFechaPartidaDefuncion() {
		return fechaPartidaDefuncion;
	}

	public void setFechaPartidaDefuncion(Date fechaPartidaDefuncion) {
		this.fechaPartidaDefuncion = fechaPartidaDefuncion;
	}

	public Integer getRequisitoSucId() {
		return requisitoSucId;
	}

	public void setRequisitoSucId(Integer requisitoSucId) {
		this.requisitoSucId = requisitoSucId;
	}

	public Date getFechaSucesionIntestada() {
		return fechaSucesionIntestada;
	}

	public void setFechaSucesionIntestada(Date fechaSucesionIntestada) {
		this.fechaSucesionIntestada = fechaSucesionIntestada;
	}

	public BigDecimal getCuotaIdeal() {
		return cuotaIdeal;
	}

	public void setCuotaIdeal(BigDecimal cuotaIdeal) {
		this.cuotaIdeal = cuotaIdeal;
	}

	public Integer getContribuyenteId() {
		return contribuyenteId;
	}

	public void setContribuyenteId(Integer contribuyenteId) {
		this.contribuyenteId = contribuyenteId;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
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
