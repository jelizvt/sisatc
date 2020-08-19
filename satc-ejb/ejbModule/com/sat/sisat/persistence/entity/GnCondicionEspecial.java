package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the gn_condicion_especial database table.
 * 
 */
@Entity
@Table(name="gn_condicion_especial")
//Periodo de la condicion especial es al anio siguiente de la fecha de inicio de la condicion especial
//corrige la inafectacion de los jubilados
@NamedQuery(name="getGnCondicionEspecialByPersonaId", query="SELECT m FROM GnCondicionEspecial m WHERE m.id.personaId=:p_persona_id AND m.estado='1' AND year(CONVERT(date,fecha_inicio,103))<=:p_periodo AND year(CONVERT(date,fecha_fin,103))>=:p_periodo AND porcentaje>0 ")
//@NamedQuery(name="getGnCondicionEspecialByPersonaId", query="SELECT m FROM GnCondicionEspecial m WHERE m.id.personaId=:p_persona_id AND m.estado='1' AND year(CONVERT(date,fecha_fin,103))>=:p_periodo ")/** * Línea de cod. comentada el 16/06/2016,no diferencia fechas, caso del contribuyente con código:7292. */
//NamedQuery(name="getGnCondicionEspecialByPersonaId", query="SELECT m FROM GnCondicionEspecial m WHERE m.id.personaId=:p_persona_id AND m.estado='1' AND CONVERT(date,fecha_fin,103)>=:p_periodo ")
public class GnCondicionEspecial implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GnCondicionEspecialPK id;

	private String estado;

	@Column(name="fecha_fin")
	private Timestamp fechaFin;

	@Column(name="fecha_inicio")
	private Timestamp fechaInicio;
	
	@Column(name="fecha_documento")
	private Timestamp fechaDocumento;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="nro_documento")
	private String nroDocumento;

	private String terminal;

	@Column(name="tipo_cond_especial_id")
	private Integer tipoCondEspecialId;

	@Column(name="tipo_documento")
	private Integer tipoDocumento;

	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="porcentaje")
	private Double porcentaje;
	
	@Column(name="requerimiento_ce_id")
	private Integer requerimientoId;
	
	@Column(name="estado_resolucion_id")
	private Integer estadoResolucionId;

	@Column(name="glosa")
	private String glosa;
	
    public GnCondicionEspecial() {
    }

	public GnCondicionEspecialPK getId() {
		return this.id;
	}

	public void setId(GnCondicionEspecialPK id) {
		this.id = id;
	}
	
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Timestamp getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNroDocumento() {
		return this.nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getTipoCondEspecialId() {
		return this.tipoCondEspecialId;
	}

	public void setTipoCondEspecialId(Integer tipoCondEspecialId) {
		this.tipoCondEspecialId = tipoCondEspecialId;
	}

	public Integer getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Timestamp getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(Timestamp fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public Integer getRequerimientoId() {
		return requerimientoId;
	}

	public void setRequerimientoId(Integer requerimientoId) {
		this.requerimientoId = requerimientoId;
	}

	public Integer getEstadoResolucionId() {
		return estadoResolucionId;
	}

	public void setEstadoResolucionId(Integer estadoResolucionId) {
		this.estadoResolucionId = estadoResolucionId;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

}