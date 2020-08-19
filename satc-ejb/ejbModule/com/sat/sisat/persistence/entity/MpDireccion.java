package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the mp_direccion database table.
 * 
 */
@Entity
@Table(name="mp_direccion")
@NamedQuery(name="findMpDireccionByPersonaId", query="SELECT a FROM MpDireccion a WHERE a.personaId=:p_persona_id AND a.estado='1'")
public class MpDireccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="direccion_id")
	private Integer direccionId;

	@Column(name="direccion_completa")
	private String direccionCompleta;

	@Column(name="distrito_id")
	private Integer distritoId;

	@Column(name="dpto_id")
	private Integer dptoId;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String letra;

	@Column(name="letra_interior")
	private String letraInterior;

	private String letra2;
	
	@Transient
	private String sector;
	
	private String lugar;

	private String manzana;

	@Column(name="manzana_catastral")
	private String manzanaCatastral;

	@Column(name="nombre_edificiacion")
	private String nombreEdificiacion;

	@Column(name="nombre_ingreso")
	private String nombreIngreso;

	@Column(name="nombre_interior")
	private String nombreInterior;

	private String numero;

	@Column(name="numero_interior")
	private String numeroInterior;

	private String numero2;

	@Column(name="persona_id")
	private Integer personaId;

	private String piso;

	@Column(name="provincia_id")
	private Integer provinciaId;

	private String referencia;

	private String terminal;

	@Column(name="tipo_direccion")
	private String tipoDireccion;

	@Column(name="tipo_edificacion_id")
	private Integer tipoEdificacionId;

	@Column(name="tipo_ingreso_id")
	private Integer tipoIngresoId;

	@Column(name="tipo_interior_id")
	private Integer tipoInteriorId;

	transient
	private String nombreVia;
	
    transient
	private Integer tipoViaId;

	@Column(name="usuario_id")
	private Integer usuarioId;

	transient
	private Integer viaId;
	
	transient
	private String tipoVia;
	
	@Column(name="tipo_agrupamiento_id")
	private Integer tipoAgrupamientoId;
	
	@Column(name="ubicacion_id")
	private Integer ubicacionId;
	
    @Transient   
	private Ubigeo ubigeo;
    
    @Transient
	private GnVia via;
    
    @Transient
	private GnDenominacionUrbana denominacionUrbana;
	
    @Column(name="lote")
	private String lote;
    
	public MpDireccion() {
    }

	public Integer getDireccionId() {
		return this.direccionId;
	}

	public void setDireccionId(Integer direccionId) {
		this.direccionId = direccionId;
	}

	public String getDireccionCompleta() {
		return this.direccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}

	public Integer getDistritoId() {
		return this.distritoId;
	}

	public void setDistritoId(Integer distritoId) {
		this.distritoId = distritoId;
	}

	public Integer getDptoId() {
		return this.dptoId;
	}

	public void setDptoId(Integer dptoId) {
		this.dptoId = dptoId;
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

	public Integer getProvinciaId() {
		return this.provinciaId;
	}

	public void setProvinciaId(Integer provinciaId) {
		this.provinciaId = provinciaId;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
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

	public Integer getTipoViaId() {
		return this.tipoViaId;
	}

	public void setTipoViaId(Integer tipoViaId) {
		this.tipoViaId = tipoViaId;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
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

	public String getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}

	public Integer getTipoAgrupamientoId() {
		return tipoAgrupamientoId;
	}

	public void setTipoAgrupamientoId(Integer tipoAgrupamientoId) {
		this.tipoAgrupamientoId = tipoAgrupamientoId;
	}

	public Integer getUbicacionId() {
		return ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}
}