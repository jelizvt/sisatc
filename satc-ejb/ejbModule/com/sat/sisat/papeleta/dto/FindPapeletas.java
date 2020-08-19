package com.sat.sisat.papeleta.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.sat.sisat.common.dto.BaseDTO;
import com.sat.sisat.common.util.Constante;

public class FindPapeletas extends BaseDTO implements Serializable {
	
	private String numPapeleta;
	private Timestamp fechaInfraccion;
	private String infractor;
	private String propietario;
	private String placa;
	private String infraccion;
	private int infraccionId;
	private String infraccionDescAbreviada;
	private int papeletaId;
	private String origen;
	private String numOficio;
	private String estadoDesc;
	private Double montoMulta;
	private String estadoPapeleta;
	//--
	private Integer tipoDocumentoId;
	private String numeroDocumento;
	private String numeroLicencia;
	private String descTipoDocumento;
	//--
	private Integer infractorId;
	private String estado;
	
	private String estadoPapeletaString;
	private String placaAnterior;
	private String glosa;
	private String registrador;
	
	private String usuarioActualiza;

	//fecha de notificaci�n de la resoluci�n
	private Timestamp fechaNotificacion;
	private Timestamp fechaEmision;
	
	//para nuevos criterios de busqueda
	private String infractorCbf;
	private String placaCbf;
	
	public Timestamp getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Timestamp fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public Timestamp getFechaNotificacion() {
		return fechaNotificacion;
	}
	public void setFechaNotificacion(Timestamp fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
	public String getUsuarioActualiza() {
		return usuarioActualiza;
	}
	public void setUsuarioActualiza(String usuarioActualiza) {
		this.usuarioActualiza = usuarioActualiza;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Integer getInfractorId() {
		return infractorId;
	}
	public void setInfractorId(Integer infractorId) {
		this.infractorId = infractorId;
	}
	public String getDescTipoDocumento() {
		return descTipoDocumento;
	}
	public void setDescTipoDocumento(String descTipoDocumento) {
		this.descTipoDocumento = descTipoDocumento;
	}
	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}
	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getNumeroLicencia() {
		return numeroLicencia;
	}
	public void setNumeroLicencia(String numeroLicencia) {
		this.numeroLicencia = numeroLicencia;
	}
	public String getEstadoPapeleta() {
		if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_PENDIENTE)){
			return "verificacion";
		}else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_REGISTRADO)){
			return "Registrado";
		}else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_NO_COINCIDE)){
			return "No Coincide";
		}else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_DEFINITIVO)){
			return "Pendiente";
		}
		//agregado
		else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_CANCELADO)){
			return "Cancelado";
		}
		else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_DESCARGADO)){
			return "Descargado";
		}
		else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_ANULADO)){
			return "Anulado";
		}
		else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_COMPENSADO)){
			return "Compensado";
		}
		else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_PRESCRITO)){
			return "Prescrito";
		}
		return estadoPapeleta;
	}
	
	public void setEstadoPapeleta(String estadoPapeleta) {
		this.estadoPapeleta = estadoPapeleta;
	}
	public Double getMontoMulta() {
		return montoMulta;
	}
	public void setMontoMulta(Double montoMulta) {
		this.montoMulta = montoMulta;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getNumPapeleta() {
		return numPapeleta;
	}
	public void setNumPapeleta(String numPapeleta) {
		this.numPapeleta = numPapeleta;
	}
	public Timestamp getFechaInfraccion() {
		return fechaInfraccion;
	}
	public void setFechaInfraccion(Timestamp fechaInfraccion) {
		this.fechaInfraccion = fechaInfraccion;
	}
	public String getInfractor() {
		return infractor;
	}
	public void setInfractor(String infractor) {
		this.infractor = infractor;
	}
	public String getPropietario() {
		return propietario;
	}
	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getInfraccion() {
		return infraccion;
	}
	public void setInfraccion(String infraccion) {
		this.infraccion = infraccion;
	}
	public int getInfraccionId() {
		return infraccionId;
	}
	public void setInfraccionId(int infraccionId) {
		this.infraccionId = infraccionId;
	}
	public String getInfraccionDescAbreviada() {
		return infraccionDescAbreviada;
	}
	public void setInfraccionDescAbreviada(String infraccionDescAbreviada) {
		this.infraccionDescAbreviada = infraccionDescAbreviada;
	}
	public int getPapeletaId() {
		return papeletaId;
	}
	public void setPapeletaId(int papeletaId) {
		this.papeletaId = papeletaId;
	}
	public String getNumOficio() {
		return numOficio;
	}
	public void setNumOficio(String numOficio) {
		this.numOficio = numOficio;
	}
	public String getEstadoDesc() {
		return estadoDesc;
	}
	public void setEstadoDesc(String estadoDesc) {
		this.estadoDesc = estadoDesc;
	}
	public String getEstadoPapeletaString() {
		if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_PENDIENTE)){
			return "verificacion";
		}else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_REGISTRADO)){
			return "Registrado";
		}else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_NO_COINCIDE)){
			return "No Coincide";
		}else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_DEFINITIVO)){
			return "Pendiente";
		}
		//agregado
		else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_CANCELADO)){
			return "Cancelado";
		}
		else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_DESCARGADO)){
			return "Descargado";
		}
		else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_ANULADO)){
			return "Anulado";
		}
		else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_COMPENSADO)){
			return "Compensado";
		}
		else if(estadoPapeleta.equals(Constante.ESTADO_PAPELETA_PRESCRITO)){
			return "Prescrito";
		}else{
			estadoPapeletaString = "";
		}
		return estadoPapeletaString;
	}
	public void setEstadoPapeletaString(String estadoPapeletaString) {
		this.estadoPapeletaString = estadoPapeletaString;
	}
	public String getPlacaAnterior() {
		return placaAnterior;
	}
	public void setPlacaAnterior(String placaAnterior) {
		this.placaAnterior = placaAnterior;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	public String getRegistrador() {
		return registrador;
	}
	public void setRegistrador(String registrador) {
		this.registrador = registrador;
	}
	//para nuevos criterios de busqueda
	public String getInfractorCbf() {
		return infractorCbf;
	}
	public void setInfractorCbf(String infractorCbf) {
		this.infractorCbf = infractorCbf;
	}
	public String getPlacaCbf() {
		return placaCbf;
	}
	public void setPlacaCbf(String placaCbf) {
		this.placaCbf = placaCbf;
	}
}
