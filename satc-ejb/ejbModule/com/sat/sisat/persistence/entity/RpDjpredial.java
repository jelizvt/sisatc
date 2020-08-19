package com.sat.sisat.persistence.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.sat.sisat.persistence.GeneralEntity;
import com.sat.sisat.persistence.GeneralEntityListener;


/**
 * The persistent class for the rp_djpredial database table.
 * 
 */
@Entity
@EntityListeners(GeneralEntityListener.class)
@Table(name="rp_djpredial")
@NamedQueries({
	@NamedQuery(name="getAllRpDJpredialByAnnoDj", query="SELECT a FROM RpDjpredial a WHERE a.estado='1' AND  a.flagDjAnno=:p_flag_dj_anno AND year(CONVERT(date,a.fechaAdquisicion,103))<=:p_periodo AND a.annoDj=:p_periodo AND a.djId >=:p_djid_inicio AND a.djId<:p_djid_fin "),
	//@NamedQuery(name="getAllRpDJpredialByPersonaId", query="SELECT a FROM RpDjpredial a WHERE a.estado='1' AND  a.personaId=:p_persona_id AND a.flagDjAnno=:p_flag_dj_anno AND year(CONVERT(date,a.fechaAdquisicion,103))<=:p_periodo AND a.annoDj=:p_periodo AND a.motivoDeclaracionId!=4 ORDER BY a.djId DESC"),
	@NamedQuery(name="getAllRpDJpredialByPersonaId", query="SELECT a FROM RpDjpredial a WHERE a.estado='1' AND  a.personaId=:p_persona_id AND a.flagDjAnno=:p_flag_dj_anno AND year(CONVERT(date,a.fechaAdquisicion,103))<=:p_periodo AND a.annoDj=:p_periodo AND a.motivoDeclaracionId!=4 and (a.motivoDescargoId!=98 or a.motivoDescargoId is null) ORDER BY a.djId DESC"),
	@NamedQuery(name="getAllRpDJpredialArbByPersonaId", query="SELECT a FROM RpDjpredial a WHERE a.estado='1' AND  a.personaId=:p_persona_id AND a.flagDjAnno=:p_flag_dj_anno AND a.annoDj=:p_periodo and a.tipoPredio='U' ORDER BY a.djId DESC"),
	@NamedQuery(name="getAllRpDjpredialByAnnoDjPersonaId", query="SELECT a FROM RpDjpredial a WHERE a.personaId=:p_persona_id AND a.estado='1' AND a.predioId=:p_predio_id AND a.flagDjAnno='1' AND a.annoDj>=:p_anno_dj ORDER BY a.annoDj ASC"),
})

public class RpDjpredial implements GeneralEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="dj_id")
	private Integer djId;

	@Column(name="altitud_id")
	private Integer altitudId;

	@Column(name="anno_dj")
	private Integer annoDj;

	@Column(name="area_terreno")
	private BigDecimal areaTerreno;

	@Column(name="area_terreno_has")
	private BigDecimal areaTerrenoHas;
	
	@Column(name="area_terreno_comun")
	private BigDecimal areaTerrenoComun;

	@Column(name="codigo_anterior")
	private String codigoAnterior;

	@Column(name="codigo_predio")
	private String codigoPredio;

	@Column(name="estado")
	private String estado;

	@Column(name="fecha_adquisicion")
	private Date fechaAdquisicion;

	@Column(name="fecha_declaracion")
	private Timestamp fechaDeclaracion;

	@Column(name="fecha_descargo")
	private Timestamp fechaDescargo;

	@Column(name="flag_dj_anno")
	private String flagDjAnno;

	@Column(name="frente")
	private BigDecimal frente;

	@Column(name="glosa")
	private String glosa;

	@Column(name="id_anterior")
	private String idAnterior;

	@Column(name="motivo_descargo_id")
	private Integer motivoDescargoId;

	@Column(name="nombre_predio")
	private String nombrePredio;

	@Column(name="nro_modificacon")
	private Integer nroModificacon;

	@Column(name="persona_id")
	private Integer personaId;

	@Column(name="porc_propiedad")
	private BigDecimal porcPropiedad;

	@Column(name="predio_id")
	private Integer predioId;

	@Column(name="tipo_predio")
	private String tipoPredio;

	@Column(name="ubicacion_predio_id")
	private Integer ubicacionPredioId;

	@Column(name="desc_domicilio")
	private String descDomicilio;
	
	@Column(name="condicion_propiedad_id")
	private Integer condicionPropiedadId;
	
	@Column(name="tipo_adquisicion_id")
	private Integer tipoAdquisicionId;
	
	@Column(name="cond_espe_predio_id")
	private Integer condEspePredioId;
	
	@Column(name="tipo_tierra_id")
	private Integer tipoTierraId;
	
	@Column(name="categoria_rustico_id")
	private Integer categoriaRusticoId;
	
	@Column(name="es_habitable")
	private String esHabitable;
	
	@Column(name="motivo_declaracion_id")
	private Integer motivoDeclaracionId;
	
	@Column(name="notaria_id")
	private Integer notariaId;
	
	@Column(name="fiscalizado")
	private String fiscalizado; 
	
	@Column(name="fisca_aceptada")
	private String fiscaAceptada; 
	
	@Column(name="fisca_cerrada")
	private String fiscaCerrada; 
	
	@Column(name="tipo_uso_rustico_id")
	private Integer tipoUsoRusticoId;
	
	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="terminal")
	private String terminal;

	@Column(name="terminal_registro")
	private String terminalRegistro;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="es_descargo")
	private String esDescargo;
	
	@Column(name="area_terreno_comun_has")
	private BigDecimal areaTerrenoComunHas;
	
	@Column(name="secano")
	private String secano;
	
	@Column(name="frente_ok")
	private String frenteOk;
	
	@Column(name="frente_area_comun")
	private BigDecimal frenteAreaComun;
	
	@Column(name="distancia_a_predio")
	private BigDecimal distanciaAPredio;
	
	@Column(name="porcentaje_participacion")
	private BigDecimal porcentajeParticipacion;
	
	@Column(name="frente_via")
	private String frenteVia;
	
	public BigDecimal getAreaTerrenoComunHas() {
		return areaTerrenoComunHas;
	}

	public void setAreaTerrenoComunHas(BigDecimal areaTerrenoComunHas) {
		this.areaTerrenoComunHas = areaTerrenoComunHas;
	}
	
	public String getEsDescargo() {
		return esDescargo;
	}

	public void setEsDescargo(String esDescargo) {
		this.esDescargo = esDescargo;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	
	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTerminalRegistro() {
		return this.terminalRegistro;
	}

	public void setTerminalRegistro(String terminalRegistro) {
		this.terminalRegistro = terminalRegistro;
	}
	
	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	public Integer getTipoUsoRusticoId() {
		return tipoUsoRusticoId;
	}

	public void setTipoUsoRusticoId(Integer tipoUsoRusticoId) {
		this.tipoUsoRusticoId = tipoUsoRusticoId;
	}

	public String getFiscaAceptada() {
		return fiscaAceptada;
	}

	public void setFiscaAceptada(String fiscaAceptada) {
		this.fiscaAceptada = fiscaAceptada;
	}

	public String getFiscaCerrada() {
		return fiscaCerrada;
	}

	public void setFiscaCerrada(String fiscaCerrada) {
		this.fiscaCerrada = fiscaCerrada;
	}

	public String getFiscalizado() {
		return fiscalizado;
	}

	public void setFiscalizado(String fiscalizado) {
		this.fiscalizado = fiscalizado;
	}

	public Integer getMotivoDeclaracionId() {
		return motivoDeclaracionId;
	}

	public void setMotivoDeclaracionId(Integer motivoDeclaracionId) {
		this.motivoDeclaracionId = motivoDeclaracionId;
	}

	public String getEsHabitable() {
		return esHabitable;
	}

	public void setEsHabitable(String esHabitable) {
		this.esHabitable = esHabitable;
	}

	public Integer getCondicionPropiedadId() {
		return condicionPropiedadId;
	}

	public void setCondicionPropiedadId(Integer condicionPropiedadId) {
		this.condicionPropiedadId = condicionPropiedadId;
	}

	public Integer getTipoAdquisicionId() {
		return tipoAdquisicionId;
	}

	public void setTipoAdquisicionId(Integer tipoAdquisicionId) {
		this.tipoAdquisicionId = tipoAdquisicionId;
	}

	public Integer getCondEspePredioId() {
		return condEspePredioId;
	}

	public void setCondEspePredioId(Integer condEspePredioId) {
		this.condEspePredioId = condEspePredioId;
	}

	public Integer getTipoTierraId() {
		return tipoTierraId;
	}

	public void setTipoTierraId(Integer tipoTierraId) {
		this.tipoTierraId = tipoTierraId;
	}

	public Integer getCategoriaRusticoId() {
		return categoriaRusticoId;
	}

	public void setCategoriaRusticoId(Integer categoriaRusticoId) {
		this.categoriaRusticoId = categoriaRusticoId;
	}

	public RpDjpredial() {
    }

	public Integer getDjId() {
		return this.djId;
	}

	public void setDjId(Integer djId) {
		this.djId = djId;
	}

	public Integer getAltitudId() {
		return this.altitudId;
	}

	public void setAltitudId(Integer altitudId) {
		this.altitudId = altitudId;
	}

	public Integer getAnnoDj() {
		return this.annoDj;
	}

	public void setAnnoDj(Integer annoDj) {
		this.annoDj = annoDj;
	}

	public BigDecimal getAreaTerreno() {
		return this.areaTerreno;
	}

	public void setAreaTerreno(BigDecimal areaTerreno) {
		this.areaTerreno = areaTerreno;
	}

	public BigDecimal getAreaTerrenoComun() {
		return this.areaTerrenoComun;
	}

	public void setAreaTerrenoComun(BigDecimal areaTerrenoComun) {
		this.areaTerrenoComun = areaTerrenoComun;
	}

	public String getCodigoAnterior() {
		return this.codigoAnterior;
	}

	public void setCodigoAnterior(String codigoAnterior) {
		this.codigoAnterior = codigoAnterior;
	}

	public String getCodigoPredio() {
		return this.codigoPredio;
	}

	public void setCodigoPredio(String codigoPredio) {
		this.codigoPredio = codigoPredio;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	

	public Date getFechaAdquisicion() {
		return this.fechaAdquisicion;
	}

	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	public Timestamp getFechaDeclaracion() {
		return this.fechaDeclaracion;
	}

	public void setFechaDeclaracion(Timestamp fechaDeclaracion) {
		this.fechaDeclaracion = fechaDeclaracion;
	}

	public Timestamp getFechaDescargo() {
		return this.fechaDescargo;
	}

	public void setFechaDescargo(Timestamp fechaDescargo) {
		this.fechaDescargo = fechaDescargo;
	}

	public String getFlagDjAnno() {
		return this.flagDjAnno;
	}

	public void setFlagDjAnno(String flagDjAnno) {
		this.flagDjAnno = flagDjAnno;
	}

	public BigDecimal getFrente() {
		return this.frente;
	}

	public void setFrente(BigDecimal frente) {
		this.frente = frente;
	}

	public String getGlosa() {
		return this.glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public String getIdAnterior() {
		return this.idAnterior;
	}

	public void setIdAnterior(String idAnterior) {
		this.idAnterior = idAnterior;
	}

	public Integer getMotivoDescargoId() {
		return this.motivoDescargoId;
	}

	public void setMotivoDescargoId(Integer motivoDescargoId) {
		this.motivoDescargoId = motivoDescargoId;
	}

	public String getNombrePredio() {
		return this.nombrePredio;
	}

	public void setNombrePredio(String nombrePredio) {
		this.nombrePredio = nombrePredio;
	}

	public Integer getNroModificacon() {
		return this.nroModificacon;
	}

	public void setNroModificacon(Integer nroModificacon) {
		this.nroModificacon = nroModificacon;
	}

	public Integer getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public BigDecimal getPorcPropiedad() {
		return this.porcPropiedad;
	}

	public void setPorcPropiedad(BigDecimal porcPropiedad) {
		this.porcPropiedad = porcPropiedad;
	}

	public Integer getPredioId() {
		return this.predioId;
	}

	public void setPredioId(Integer predioId) {
		this.predioId = predioId;
	}

	

	public String getTipoPredio() {
		return this.tipoPredio;
	}

	public void setTipoPredio(String tipoPredio) {
		this.tipoPredio = tipoPredio;
	}

	public Integer getUbicacionPredioId() {
		return this.ubicacionPredioId;
	}

	public void setUbicacionPredioId(Integer ubicacionPredioId) {
		this.ubicacionPredioId = ubicacionPredioId;
	}

	public String getDescDomicilio() {
		return descDomicilio;
	}

	public void setDescDomicilio(String descDomicilio) {
		this.descDomicilio = descDomicilio;
	}
	public Integer getNotariaId() {
		return notariaId;
	}

	public void setNotariaId(Integer notariaId) {
		this.notariaId = notariaId;
	}

	public BigDecimal getArancelDireccion() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public BigDecimal getAreaTerrenoHas() {
		return areaTerrenoHas;
	}

	public void setAreaTerrenoHas(BigDecimal areaTerrenoHas) {
		this.areaTerrenoHas = areaTerrenoHas;
	}

	public String getSecano() {
		return secano;
	}

	public void setSecano(String secano) {
		this.secano = secano;
	}

	public String getFrenteOk() {
		return frenteOk;
	}

	public void setFrenteOk(String frenteOk) {
		this.frenteOk = frenteOk;
	}

	public BigDecimal getFrenteAreaComun() {
		return frenteAreaComun;
	}

	public void setFrenteAreaComun(BigDecimal frenteAreaComun) {
		this.frenteAreaComun = frenteAreaComun;
	}

	public BigDecimal getDistanciaAPredio() {
		return distanciaAPredio;
	}

	public void setDistanciaAPredio(BigDecimal distanciaAPredio) {
		this.distanciaAPredio = distanciaAPredio;
	}

	public BigDecimal getPorcentajeParticipacion() {
		return porcentajeParticipacion;
	}

	public void setPorcentajeParticipacion(BigDecimal porcentajeParticipacion) {
		this.porcentajeParticipacion = porcentajeParticipacion;
	}

	public String getFrenteVia() {
		return frenteVia;
	}

	public void setFrenteVia(String frenteVia) {
		this.frenteVia = frenteVia;
	}
}