package com.sat.sisat.fiscalizacion.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.sat.sisat.common.util.Constante;

public class FindInspeccionDj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer predioId;
	private Integer djId;
	private Integer personaId;
	private Integer annoDj;
	private Date fechaDeclaracion;
	private Date fechaAdquisicion;
	private String descripcionTipoPredio;
	private String predioIdAntiguo;
	private BigDecimal porcPropiedad;
	private Double areaTerreno;
	private Double areaTerrenoHas;
	private Integer estadoFiscalizado;
	private Integer estado;
	private Integer flagDjAnno;
	private Date fechaRegistro;
	private Integer reqInspeccion;
	private String nroreqInspeccion;
	private Integer ubicacionId;
	private Integer tipoViaId;
	private Integer viaId;
	private String via;
	private String tipoVia;
	private Integer sectorId;
	private String sector;
	private String manzana;
	private String cuadra;
	private String ladoCuadra;
	private String lugar;

	private String descripcionCondicionPredio;
	private String descripcionCortaTipoPredio;

	private String direccionPredio;
	private String nroResultadoInspeccion;
	
	private boolean selected;
	
	public Boolean esPendiente(){
		return estado.equals("2")?Boolean.TRUE:Boolean.FALSE;
	}
	
	public String getColorByEstado() {
		
        if(esPendiente())
        	return "#2AFFAA";//"yellow";
        else if(!esPendiente())
        	return "#99FF99";//"#D4FFAA";
        else
        	return "white";
    }
	
	
	public Integer getPredioId() {
		return predioId;
	}
	public void setPredioId(Integer predioId) {
		this.predioId = predioId;
	}
	public Integer getDjId() {
		return djId;
	}
	public void setDjId(Integer djId) {
		this.djId = djId;
	}
	public Integer getPersonaId() {
		return personaId;
	}
	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	public Integer getAnnoDj() {
		return annoDj;
	}
	public void setAnnoDj(Integer annoDj) {
		this.annoDj = annoDj;
	}
	public String getDescripcionTipoPredio() {
		return descripcionTipoPredio;
	}
	public void setDescripcionTipoPredio(String descripcionTipoPredio) {
		this.descripcionTipoPredio = descripcionTipoPredio;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public Integer getFlagDjAnno() {
		return flagDjAnno;
	}
	public void setFlagDjAnno(Integer flagDjAnno) {
		this.flagDjAnno = flagDjAnno;
	}
	public Integer getEstadoFiscalizado() {
		return estadoFiscalizado;
	}
	public void setEstadoFiscalizado(Integer estadoFiscalizado) {
		this.estadoFiscalizado = estadoFiscalizado;
	}
	public String getPredioIdAntiguo() {
		return predioIdAntiguo;
	}
	public void setPredioIdAntiguo(String predioIdAntiguo) {
		this.predioIdAntiguo = predioIdAntiguo;
	}
	public String getDescripcionCondicionPredio() {
		return descripcionCondicionPredio;
	}
	public void setDescripcionCondicionPredio(String descripcionCondicionPredio) {
		this.descripcionCondicionPredio = descripcionCondicionPredio;
	}
	public String getDescripcionCortaTipoPredio() {
		return descripcionCortaTipoPredio;
	}
	public void setDescripcionCortaTipoPredio(String descripcionCortaTipoPredio) {
		this.descripcionCortaTipoPredio = descripcionCortaTipoPredio;
	}
	public BigDecimal getPorcPropiedad() {
		return porcPropiedad;
	}
	public void setPorcPropiedad(BigDecimal porcPropiedad) {
		this.porcPropiedad = porcPropiedad;
	}
	public Double getAreaTerreno() {
		return areaTerreno;
	}
	public void setAreaTerreno(Double areaTerreno) {
		this.areaTerreno = areaTerreno;
	}
	public Double getAreaTerrenoHas() {
		return areaTerrenoHas;
	}
	public void setAreaTerrenoHas(Double areaTerrenoHas) {
		this.areaTerrenoHas = areaTerrenoHas;
	}
	public Date getFechaDeclaracion() {
		return fechaDeclaracion;
	}
	public void setFechaDeclaracion(Date fechaDeclaracion) {
		this.fechaDeclaracion = fechaDeclaracion;
	}
	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}
	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Integer getReqInspeccion() {
		return reqInspeccion;
	}
	public void setReqInspeccion(Integer reqInspeccion) {
		this.reqInspeccion = reqInspeccion;
	}
	public String getNroreqInspeccion() {
		return nroreqInspeccion;
	}
	public void setNroreqInspeccion(String nroreqInspeccion) {
		this.nroreqInspeccion = nroreqInspeccion;
	}

	public String getDireccionPredio() {
		return direccionPredio;
	}

	public void setDireccionPredio(String direccionPredio) {
		this.direccionPredio = direccionPredio;
	}

	public Integer getUbicacionId() {
		return ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public Integer getTipoViaId() {
		return tipoViaId;
	}

	public void setTipoViaId(Integer tipoViaId) {
		this.tipoViaId = tipoViaId;
	}

	public Integer getViaId() {
		return viaId;
	}

	public void setViaId(Integer viaId) {
		this.viaId = viaId;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}

	public Integer getSectorId() {
		return sectorId;
	}

	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getManzana() {
		return manzana;
	}

	public void setManzana(String manzana) {
		this.manzana = manzana;
	}

	
	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getCuadra() {
		return cuadra;
	}

	public void setCuadra(String cuadra) {
		this.cuadra = cuadra;
	}

	public String getLadoCuadra() {
		return ladoCuadra;
	}

	public void setLadoCuadra(String ladoCuadra) {
		this.ladoCuadra = ladoCuadra;
	}

	public String getNroResultadoInspeccion() {
		return nroResultadoInspeccion;
	}

	public void setNroResultadoInspeccion(String nroResultadoInspeccion) {
		this.nroResultadoInspeccion = nroResultadoInspeccion;
	}
	


}
