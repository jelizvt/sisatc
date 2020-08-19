package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the rp_djdireccion database table.
 * 
 */
@Entity
@Table(name="rp_djdireccion")
@NamedQuery(name="getAllRpDjdireccionByDjId", query="SELECT a FROM RpDjdireccion a WHERE a.estado='1' AND  a.djId=:p_dj_id ")
public class RpDjdireccion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient
	private Ubigeo ubigeo;
	
	@Transient
	private GnVia via;
	
	@Transient
	private GnDenominacionUrbana denominacionUrbana;

	@Id
	@Column(name="direccion_id")
	private Integer direccionId;

	@Column(name="deno_id")
	private Integer denoId;

	@Column(name="direccion_completa")
	private String direccionCompleta;

	@Column(name="dj_id")
	private Integer djId;

	@Column(name="domicilio_fiscal")
	private String domicilioFiscal;

	@Column(name="domicilio_rustico")
	private String domicilioRustico;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String letra;

	@Column(name="letra_interior")
	private String letraInterior;

	private String letra2;

	private String lote;

	private String lugar;

	private String manzana;

	@Column(name="manzana_catastral")
	private String manzanaCatastral;

	@Column(name="nombre_agrupamiento")
	private String nombreAgrupamiento;

	@Column(name="nombre_edificiacion")
	private String nombreEdificiacion;

	@Column(name="nombre_ingreso")
	private String nombreIngreso;

	@Column(name="nombre_interior")
	private String nombreInterior;

	@Column(name="nombre_via")
	private String nombreVia;

	private String numero;

	@Column(name="numero_interior")
	private String numeroInterior;

	private String numero2;

	@Column(name="persona_id")
	private Integer personaId;

	private String piso;

	@Column(name="predio_colindante_este")
	private String predioColindanteEste;

	@Column(name="predio_colindante_norte")
	private String predioColindanteNorte;

	@Column(name="predio_colindante_oeste")
	private String predioColindanteOeste;

	@Column(name="predio_colindante_sur")
	private String predioColindanteSur;

	@Column(name="propietario_colindante_este")
	private String propietarioColindanteEste;

	@Column(name="propietario_colindante_norte")
	private String propietarioColindanteNorte;

	@Column(name="propietario_colindante_oeste")
	private String propietarioColindanteOeste;

	@Column(name="propietario_colindante_sur")
	private String propietarioColindanteSur;

	private String referencia;

	@Column(name="sector_catastral")
	private String sectorCatastral;

	private String terminal;

	@Column(name="tipo_agrupamiento_id")
	private Integer tipoAgrupamientoId;

	@Column(name="tipo_direccion")
	private String tipoDireccion;

	@Column(name="tipo_edificacion_id")
	private Integer tipoEdificacionId;

	@Column(name="tipo_ingreso_id")
	private Integer tipoIngresoId;

	@Column(name="tipo_interior_id")
	private Integer tipoInteriorId;

	@Column(name="ubicacion_id")
	private Integer ubicacionId;

	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="valor_arancel")
	private BigDecimal valorArancel;

	@Column(name="via_id")
	private Integer viaId;

    public RpDjdireccion() {
    }

	public Integer getDireccionId() {
		return this.direccionId;
	}

	public void setDireccionId(Integer direccionId) {
		this.direccionId = direccionId;
	}

	public Integer getDenoId() {
		return this.denoId;
	}

	public void setDenoId(Integer denoId) {
		this.denoId = denoId;
	}

	public String getDireccionCompleta() {
		return this.direccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}

	public Integer getDjId() {
		return this.djId;
	}

	public void setDjId(Integer djId) {
		this.djId = djId;
	}

	public String getDomicilioFiscal() {
		return this.domicilioFiscal;
	}

	public void setDomicilioFiscal(String domicilioFiscal) {
		this.domicilioFiscal = domicilioFiscal;
	}

	public String getDomicilioRustico() {
		return this.domicilioRustico;
	}

	public void setDomicilioRustico(String domicilioRustico) {
		this.domicilioRustico = domicilioRustico;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getLetra() {
		return this.letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public String getLetraInterior() {
		return this.letraInterior;
	}

	public void setLetraInterior(String letraInterior) {
		this.letraInterior = letraInterior;
	}

	public String getLetra2() {
		return this.letra2;
	}

	public void setLetra2(String letra2) {
		this.letra2 = letra2;
	}

	public String getLote() {
		return this.lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getLugar() {
		return this.lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getManzana() {
		return this.manzana;
	}

	public void setManzana(String manzana) {
		this.manzana = manzana;
	}

	public String getManzanaCatastral() {
		return this.manzanaCatastral;
	}

	public void setManzanaCatastral(String manzanaCatastral) {
		this.manzanaCatastral = manzanaCatastral;
	}

	public String getNombreEdificiacion() {
		return this.nombreEdificiacion;
	}

	public void setNombreEdificiacion(String nombreEdificiacion) {
		this.nombreEdificiacion = nombreEdificiacion;
	}

	public String getNombreIngreso() {
		return this.nombreIngreso;
	}

	public void setNombreIngreso(String nombreIngreso) {
		this.nombreIngreso = nombreIngreso;
	}

	public String getNombreInterior() {
		return this.nombreInterior;
	}

	public void setNombreInterior(String nombreInterior) {
		this.nombreInterior = nombreInterior;
	}

	public String getNombreVia() {
		return this.nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public String getNombreAgrupamiento() {
		return this.nombreAgrupamiento;
	}

	public void setNombreAgrupamiento(String nombreAgrupamiento) {
		this.nombreAgrupamiento = nombreAgrupamiento;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumeroInterior() {
		return this.numeroInterior;
	}

	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}

	public String getNumero2() {
		return this.numero2;
	}

	public void setNumero2(String numero2) {
		this.numero2 = numero2;
	}

	public Integer getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public String getPiso() {
		return this.piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getPredioColindanteEste() {
		return this.predioColindanteEste;
	}

	public void setPredioColindanteEste(String predioColindanteEste) {
		this.predioColindanteEste = predioColindanteEste;
	}

	public String getPredioColindanteNorte() {
		return this.predioColindanteNorte;
	}

	public void setPredioColindanteNorte(String predioColindanteNorte) {
		this.predioColindanteNorte = predioColindanteNorte;
	}

	public String getPredioColindanteOeste() {
		return this.predioColindanteOeste;
	}

	public void setPredioColindanteOeste(String predioColindanteOeste) {
		this.predioColindanteOeste = predioColindanteOeste;
	}

	public String getPredioColindanteSur() {
		return this.predioColindanteSur;
	}

	public void setPredioColindanteSur(String predioColindanteSur) {
		this.predioColindanteSur = predioColindanteSur;
	}

	public String getPropietarioColindanteEste() {
		return this.propietarioColindanteEste;
	}

	public void setPropietarioColindanteEste(String propietarioColindanteEste) {
		this.propietarioColindanteEste = propietarioColindanteEste;
	}

	public String getPropietarioColindanteNorte() {
		return this.propietarioColindanteNorte;
	}

	public void setPropietarioColindanteNorte(String propietarioColindanteNorte) {
		this.propietarioColindanteNorte = propietarioColindanteNorte;
	}

	public String getPropietarioColindanteOeste() {
		return this.propietarioColindanteOeste;
	}

	public void setPropietarioColindanteOeste(String propietarioColindanteOeste) {
		this.propietarioColindanteOeste = propietarioColindanteOeste;
	}

	public String getPropietarioColindanteSur() {
		return this.propietarioColindanteSur;
	}

	public void setPropietarioColindanteSur(String propietarioColindanteSur) {
		this.propietarioColindanteSur = propietarioColindanteSur;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getSectorCatastral() {
		return this.sectorCatastral;
	}

	public void setSectorCatastral(String sectorCatastral) {
		this.sectorCatastral = sectorCatastral;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getTipoAgrupamientoId() {
		return this.tipoAgrupamientoId;
	}

	public void setTipoAgrupamientoId(Integer tipoAgrupamientoId) {
		this.tipoAgrupamientoId = tipoAgrupamientoId;
	}

	public String getTipoDireccion() {
		return this.tipoDireccion;
	}

	public void setTipoDireccion(String tipoDireccion) {
		this.tipoDireccion = tipoDireccion;
	}

	public Integer getTipoEdificacionId() {
		return this.tipoEdificacionId;
	}

	public void setTipoEdificacionId(Integer tipoEdificacionId) {
		this.tipoEdificacionId = tipoEdificacionId;
	}

	public Integer getTipoIngresoId() {
		return this.tipoIngresoId;
	}

	public void setTipoIngresoId(Integer tipoIngresoId) {
		this.tipoIngresoId = tipoIngresoId;
	}

	public Integer getTipoInteriorId() {
		return this.tipoInteriorId;
	}

	public void setTipoInteriorId(Integer tipoInteriorId) {
		this.tipoInteriorId = tipoInteriorId;
	}

	public Integer getUbicacionId() {
		return this.ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public BigDecimal getValorArancel() {
		return this.valorArancel;
	}

	public void setValorArancel(BigDecimal valorArancel) {
		this.valorArancel = valorArancel;
	}

	public Integer getViaId() {
		return this.viaId;
	}

	public void setViaId(Integer viaId) {
		this.viaId = viaId;
	}
	
	public Ubigeo getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(Ubigeo ubigeo) {
		this.ubigeo = ubigeo;
	}

	public GnVia getVia() {
		return via;
	}

	public void setVia(GnVia via) {
		this.via = via;
	}

	public GnDenominacionUrbana getDenominacionUrbana() {
		return denominacionUrbana;
	}

	public void setDenominacionUrbana(GnDenominacionUrbana denominacionUrbana) {
		this.denominacionUrbana = denominacionUrbana;
	}
}