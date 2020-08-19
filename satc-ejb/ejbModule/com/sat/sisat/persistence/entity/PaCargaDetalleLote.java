package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the pa_carga_detalle_lotes database table.
 * 
 */
@Entity
@Table(name="pa_carga_detalle_lotes")
@NamedQuery(name="getAllPaCargaDetalleLoteByCargaLoteId", query="SELECT m FROM PaCargaDetalleLote m WHERE m.cargaLotesId=:p_carga_lote_id ORDER BY m.corrOficio ASC")
public class PaCargaDetalleLote implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="carga_detalle_lotes_id")
	private Integer cargaDetalleLotesId;

	@Column(name="carga_lotes_id")
	private Integer cargaLotesId;

	@Column(name="cip_policia")
	private String cipPolicia;

	@Column(name="cod_infraccion")
	private String codInfraccion;

	private String conductor;

	@Column(name="corr_oficio")
	private Integer corrOficio;

	@Column(name="error_code")
	private String errorCode;

	@Column(name="error_message")
	private String errorMessage;

	private String estado;

	@Column(name="fec_papeleta")
	private String fecPapeleta;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="nom_policia")
	private String nomPolicia;

	@Column(name="num_licencia")
	private String numLicencia;

	@Column(name="num_papeleta")
	private String numPapeleta;

	private String origen;

	@Column(name="papeleta_id")
	private Integer papeletaId;

	private String placa;

	@Column(name="terminal")
	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="proceso_carga_id")
	private Integer procesoCargaId;

	public Integer getCargaDetalleLotesId() {
		return cargaDetalleLotesId;
	}

	public void setCargaDetalleLotesId(Integer cargaDetalleLotesId) {
		this.cargaDetalleLotesId = cargaDetalleLotesId;
	}

	public Integer getCargaLotesId() {
		return cargaLotesId;
	}

	public void setCargaLotesId(Integer cargaLotesId) {
		this.cargaLotesId = cargaLotesId;
	}

	public String getCipPolicia() {
		return cipPolicia;
	}

	public void setCipPolicia(String cipPolicia) {
		this.cipPolicia = cipPolicia;
	}

	public String getCodInfraccion() {
		return codInfraccion;
	}

	public void setCodInfraccion(String codInfraccion) {
		this.codInfraccion = codInfraccion;
	}

	public String getConductor() {
		return conductor;
	}

	public void setConductor(String conductor) {
		this.conductor = conductor;
	}

	public Integer getCorrOficio() {
		return corrOficio;
	}

	public void setCorrOficio(Integer corrOficio) {
		this.corrOficio = corrOficio;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFecPapeleta() {
		return fecPapeleta;
	}

	public void setFecPapeleta(String fecPapeleta) {
		this.fecPapeleta = fecPapeleta;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNomPolicia() {
		return nomPolicia;
	}

	public void setNomPolicia(String nomPolicia) {
		this.nomPolicia = nomPolicia;
	}

	public String getNumLicencia() {
		return numLicencia;
	}

	public void setNumLicencia(String numLicencia) {
		this.numLicencia = numLicencia;
	}

	public String getNumPapeleta() {
		return numPapeleta;
	}

	public void setNumPapeleta(String numPapeleta) {
		this.numPapeleta = numPapeleta;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public Integer getPapeletaId() {
		return papeletaId;
	}

	public void setPapeletaId(Integer papeletaId) {
		this.papeletaId = papeletaId;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal= terminal;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getProcesoCargaId() {
		return procesoCargaId;
	}

	public void setProcesoCargaId(Integer procesoCargaId) {
		this.procesoCargaId = procesoCargaId;
	}
	
    

}