package com.sat.sisat.papeleta.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.sat.sisat.common.util.Constante;

/**
 * @author Arturo
 *
 */
public class GridDetalleLote implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1501531605841526745L;
	private Integer cargaLotesId;
	private Integer cargaDetalleLotesId;
	private String nombresApellidosInfractor ;
	private Timestamp fechaInfraccion;
	private String placa;
	private String nombresApellidosPolicia;
	private String numOficio;
	private String numPapeleta;
	private String apellidoPaternoInfractor;
	private String apellidoMaternoInfractor;
	private String primerNombreInfractor;
	private String segundoNombreInfractor;
	private String infraccion;
	private Integer papeletaId;
	private String estado;
	private String ley;
	private Integer leyId;
	private Integer infraccionId;
	private String descripcion;
	//para la verificacion mediante el proceso de digitacion
	private HistoricoPapeletaDTO historico;
	
	public HistoricoPapeletaDTO getHistorico() {
		return historico;
	}
	public void setHistorico(HistoricoPapeletaDTO historico) {
		this.historico = historico;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getNombresApellidosInfractor() {
		return nombresApellidosInfractor;
	}
	public void setNombresApellidosInfractor(String nombresApellidosInfractor) {
		this.nombresApellidosInfractor = nombresApellidosInfractor;
	}
	public Timestamp getFechaInfraccion() {
		return fechaInfraccion;
	}
	public void setFechaInfraccion(Timestamp fechaInfraccion) {
		this.fechaInfraccion = fechaInfraccion;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getNombresApellidosPolicia() {
		return nombresApellidosPolicia;
	}
	public void setNombresApellidosPolicia(String nombresApellidosPolicia) {
		this.nombresApellidosPolicia = nombresApellidosPolicia;
	}
	public String getNumOficio() {
		return numOficio;
	}
	public void setNumOficio(String numOficio) {
		this.numOficio = numOficio;
	}
	
	public String getNumPapeleta() {
		return numPapeleta;
	}
	public void setNumPapeleta(String numPapeleta) {
		this.numPapeleta = numPapeleta;
	}
	public String getApellidoPaternoInfractor() {
		return apellidoPaternoInfractor;
	}
	public void setApellidoPaternoInfractor(String apellidoPaternoInfractor) {
		this.apellidoPaternoInfractor = apellidoPaternoInfractor;
	}
	public String getApellidoMaternoInfractor() {
		return apellidoMaternoInfractor;
	}
	public void setApellidoMaternoInfractor(String apellidoMaternoInfractor) {
		this.apellidoMaternoInfractor = apellidoMaternoInfractor;
	}
	public String getPrimerNombreInfractor() {
		return primerNombreInfractor;
	}
	public void setPrimerNombreInfractor(String primerNombreInfractor) {
		this.primerNombreInfractor = primerNombreInfractor;
	}
	public String getSegundoNombreInfractor() {
		return segundoNombreInfractor;
	}
	public void setSegundoNombreInfractor(String segundoNombreInfractor) {
		this.segundoNombreInfractor = segundoNombreInfractor;
	}
	public String getInfraccion() {
		return infraccion.trim();
	}
	public void setInfraccion(String infraccion) {
		this.infraccion = infraccion.trim();
	}
	
	public Integer getPapeletaId() {
		return papeletaId;
	}
	public void setPapeletaId(Integer papeletaId) {
		this.papeletaId = papeletaId;
	}
	public String getEstado() {
		if(estado.equals(Constante.ESTADO_PAPELETA_PENDIENTE)){
			return "Pendiente";
		}else if(estado.equals(Constante.ESTADO_PAPELETA_REGISTRADO)){
			return "Registrado";
		}else if(estado.equals(Constante.ESTADO_PAPELETA_NO_COINCIDE)){
			return "No Coincide";
		}else if(estado.equals(Constante.ESTADO_PAPELETA_DEFINITIVO)){
			return "Definitivo";
		}else if(estado.equals(Constante.ESTADO_PAPELETA_DEFINITIVO_CANCELADO)){
			return "Definitivo Cancelado";
		}
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getEstadoIntValue() {		
		return estado;
	}
	
	public String getLey() {
		return ley;
	}
	public void setLey(String ley) {
		this.ley = ley;
	}
	public Integer getLeyId() {
		return leyId;
	}
	public void setLeyId(Integer leyId) {
		this.leyId = leyId;
	}
	public Integer getInfraccionId() {
		return infraccionId;
	}
	public void setInfraccionId(Integer infraccionId) {
		this.infraccionId = infraccionId;
	}
	public Integer getCargaLotesId() {
		return cargaLotesId;
	}
	public void setCargaLotesId(Integer cargaLotesId) {
		this.cargaLotesId = cargaLotesId;
	}
	public Integer getCargaDetalleLotesId() {
		return cargaDetalleLotesId;
	}
	public void setCargaDetalleLotesId(Integer cargaDetalleLotesId) {
		this.cargaDetalleLotesId = cargaDetalleLotesId;
	}	
	
	
}
