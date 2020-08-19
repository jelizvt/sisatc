package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the ra_direccion_alcabala database table.
 * 
 */
@Entity
@Table(name="ra_direccion_alcabala")
@NamedQuery(name="findRaDireccionById", query="SELECT a FROM RaDireccionAlcabala a WHERE a.djalcabalaId=:p_djalcabala and a.direccionAlcabalaId=:p_direccion")
public class RaDireccionAlcabala implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="direccion_alcabala_id")
	private int direccionAlcabalaId;
	
	@Column(name="djalcabala_id")
	private int djalcabalaId;

	@Column(name="tipo_direccion")
	private String tipoDireccion;

	@Column(name="dpto_id")
	private int dptoId;

	@Column(name="provincia_id")
	private int provinciaId;

	@Column(name="distrito_id")
	private int distritoId;
	
	@Column(name="tipo_via_id")
	private int tipoViaId;
	
	@Column(name="via_id")
	private int viaId;

	@Column(name="desc_via")
	private String descVia;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	public int getDireccionAlcabalaId() {
		return this.direccionAlcabalaId;
	}

	public void setDireccionAlcabalaId(int direccionAlcabalaId) {
		this.direccionAlcabalaId = direccionAlcabalaId;
	}

	@Column(name="direccion_completa")
	private String direccionCompleta;

	@Column(name="domicilio_rustico")
	private String domicilioRustico;

	@Column(name="estado_id")
	private String estadoId;

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

	@Column(name="nombre_edificacion")
	private String nombreEdificacion;

	@Column(name="nombre_ingreso")
	private String nombreIngreso;

	@Column(name="nombre_interior")
	private String nombreInterior;

	@Column(name="nombre_predio")
	private String nombrePredio;

	private String numero;

	@Column(name="numero_interior")
	private String numeroInterior;

	private String numero2;

	private String piso;

	@Column(name="predio_colindante_este")
	private String predioColindanteEste;

	@Column(name="predio_colindante_norte")
	private String predioColindanteNorte;

	@Column(name="predio_colindante_oeste")
	private String predioColindanteOeste;

	@Column(name="predio_colindante_sur")
	private String predioColindanteSur;

	@Column(name="propietario_predio_este")
	private String propietarioPredioEste;

	@Column(name="propietario_predio_norte")
	private String propietarioPredioNorte;

	@Column(name="propietario_predio_oeste")
	private String propietarioPredioOeste;

	@Column(name="propietario_predio_sur")
	private String propietarioPredioSur;

	private String referencia;

	@Column(name="sector_id")
	private int sectorId;

	private String terminal;

	@Column(name="tipo_agrupamiento_id")
	private int tipoAgrupamientoId;

	@Column(name="tipo_edificacion_id")
	private int tipoEdificacionId;

	@Column(name="tipo_ingreso_id")
	private int tipoIngresoId;

	@Column(name="tipo_interior_id")
	private int tipoInteriorId;

	@Column(name="usuario_id")
	private int usuarioId;


  public RaDireccionAlcabala() {
  }

	public String getDescVia() {
		return this.descVia;
	}

	public void setDescVia(String descVia) {
		this.descVia = descVia;
	}

	public String getDireccionCompleta() {
		return this.direccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}

	public int getDistritoId() {
		return this.distritoId;
	}

	public void setDistritoId(int distritoId) {
		this.distritoId = distritoId;
	}

	public int getDjalcabalaId() {
		return this.djalcabalaId;
	}

	public void setDjalcabalaId(int djalcabalaId) {
		this.djalcabalaId = djalcabalaId;
	}

	public String getDomicilioRustico() {
		return this.domicilioRustico;
	}

	public void setDomicilioRustico(String domicilioRustico) {
		this.domicilioRustico = domicilioRustico;
	}

	public int getDptoId() {
		return this.dptoId;
	}

	public void setDptoId(int dptoId) {
		this.dptoId = dptoId;
	}

	public String getEstadoId() {
		return this.estadoId;
	}

	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
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

	public String getNombreAgrupamiento() {
		return this.nombreAgrupamiento;
	}

	public void setNombreAgrupamiento(String nombreAgrupamiento) {
		this.nombreAgrupamiento = nombreAgrupamiento;
	}

	public String getNombreEdificacion() {
		return this.nombreEdificacion;
	}

	public void setNombreEdificacion(String nombreEdificacion) {
		this.nombreEdificacion = nombreEdificacion;
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

	public String getNombrePredio() {
		return this.nombrePredio;
	}

	public void setNombrePredio(String nombrePredio) {
		this.nombrePredio = nombrePredio;
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

	public String getPropietarioPredioEste() {
		return this.propietarioPredioEste;
	}

	public void setPropietarioPredioEste(String propietarioPredioEste) {
		this.propietarioPredioEste = propietarioPredioEste;
	}

	public String getPropietarioPredioNorte() {
		return this.propietarioPredioNorte;
	}

	public void setPropietarioPredioNorte(String propietarioPredioNorte) {
		this.propietarioPredioNorte = propietarioPredioNorte;
	}

	public String getPropietarioPredioOeste() {
		return this.propietarioPredioOeste;
	}

	public void setPropietarioPredioOeste(String propietarioPredioOeste) {
		this.propietarioPredioOeste = propietarioPredioOeste;
	}

	public String getPropietarioPredioSur() {
		return this.propietarioPredioSur;
	}

	public void setPropietarioPredioSur(String propietarioPredioSur) {
		this.propietarioPredioSur = propietarioPredioSur;
	}

	public int getProvinciaId() {
		return this.provinciaId;
	}

	public void setProvinciaId(int provinciaId) {
		this.provinciaId = provinciaId;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public int getSectorId() {
		return this.sectorId;
	}

	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getTipoAgrupamientoId() {
		return this.tipoAgrupamientoId;
	}

	public void setTipoAgrupamientoId(int tipoAgrupamientoId) {
		this.tipoAgrupamientoId = tipoAgrupamientoId;
	}

	public String getTipoDireccion() {
		return this.tipoDireccion;
	}

	public void setTipoDireccion(String tipoDireccion) {
		this.tipoDireccion = tipoDireccion;
	}

	public int getTipoEdificacionId() {
		return this.tipoEdificacionId;
	}

	public void setTipoEdificacionId(int tipoEdificacionId) {
		this.tipoEdificacionId = tipoEdificacionId;
	}

	public int getTipoIngresoId() {
		return this.tipoIngresoId;
	}

	public void setTipoIngresoId(int tipoIngresoId) {
		this.tipoIngresoId = tipoIngresoId;
	}

	public int getTipoInteriorId() {
		return this.tipoInteriorId;
	}

	public void setTipoInteriorId(int tipoInteriorId) {
		this.tipoInteriorId = tipoInteriorId;
	}


	public int getTipoViaId() {
		return this.tipoViaId;
	}

	public void setTipoViaId(int tipoViaId) {
		this.tipoViaId = tipoViaId;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getViaId() {
		return this.viaId;
	}

	public void setViaId(int viaId) {
		this.viaId = viaId;
	}

}