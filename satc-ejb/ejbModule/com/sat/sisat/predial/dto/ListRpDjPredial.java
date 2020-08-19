package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sat.sisat.common.util.Constante;


public class ListRpDjPredial implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6713983183231873647L;
	
	private String anioDj;
	private int djId;
	private String motivoDeclaracion;
	private int motivoDeclaracionId;

	private String condicionPropiedad;
	private String tipoAdquisicion;
	private Timestamp  fechaDeclaracion;
	private String fechaAdquisicion;
	private BigDecimal porcPropiedad;
	
	private BigDecimal areaTerreno;
	private BigDecimal areaTerrenoHas;
	
	private String ubicacionPredio;
	private String direccionCompleta;
	private String usuario;
	private String fiscalizado;
	private String fiscaAceptada;
	private String fiscaCerrada;
	private int predioId;
	private Boolean flagDjAnno;
	private String codigoPredio;
	//Evita la generacion de declaraciones juradas en estado pendiente
	private Boolean poseePendiente;
	private Boolean poseePendienteFisca;
	
	private String tipoPredio;
	
	//para descargo
	private String esDescargoFlag;
	private Timestamp fechaDescargo;
	private int montivoDescargoId;
	private String motivoDescargo;
	private String glosa;
	private int notariaId;
	private String nombreNotaria;

	private boolean fusionMigracion;
	
	
	public Boolean esPendiente(){
		return estado.equals("2")?Boolean.TRUE:Boolean.FALSE;
	}
	public Boolean esEliminado(){
		return estado.equals("9")?Boolean.TRUE:Boolean.FALSE;
	}
	public Boolean esDescargo(){
		return motivoDeclaracionId == 4 ? Boolean.TRUE:Boolean.FALSE;
	}
	public Boolean esFusionMigracion(){
		return ( montivoDescargoId == Constante.MOTIVO_DESCARGO_FUSION_MIGRACION  && motivoDeclaracionId == Constante.MOTIVO_DECLARACION_MIGRACION)? Boolean.TRUE:Boolean.FALSE;
	}
	public String getStyleSelected() {
        if(esPendiente())
        	return "yellow";
        else if(esEliminado())
        	return "#F6CECE";
        else if(esDescargo())
        	return "#E6E6E6";
        else if(esFusionMigracion())
        	return "#D8D8D8";
        else if(getFlagDjAnno())
        	return "#99FF99";        
        else
        	return "white";
    }
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	private String estado;
	
	public String getCodigoPredio() {
		return codigoPredio;
	}
	public void setCodigoPredio(String codigoPredio) {
		this.codigoPredio = codigoPredio;
	}
	public Boolean getFlagDjAnno() {
		return flagDjAnno;
	}
	public void setFlagDjAnno(Boolean flagDjAnno) {
		this.flagDjAnno = flagDjAnno;
	}
	public String getAnioDj() {
		return anioDj;
	}
	public void setAnioDj(String anioDj) {
		this.anioDj = anioDj;
	}
	public int getDjId() {
		return djId;
	}
	public void setDjId(int djId) {
		this.djId = djId;
	}
	public String getMotivoDeclaracion() {
		return motivoDeclaracion;
	}
	public void setMotivoDeclaracion(String motivoDeclaracion) {
		this.motivoDeclaracion = motivoDeclaracion;
	}
	public String getCondicionPropiedad() {
		return condicionPropiedad;
	}
	public void setCondicionPropiedad(String condicionPropiedad) {
		this.condicionPropiedad = condicionPropiedad;
	}
	public String getTipoAdquisicion() {
		return tipoAdquisicion;
	}
	public void setTipoAdquisicion(String tipoAdquisicion) {
		this.tipoAdquisicion = tipoAdquisicion;
	}
	public Timestamp getFechaDeclaracion() {
		return fechaDeclaracion;
	}
	public void setFechaDeclaracion(Timestamp fechaDeclaracion) {
		this.fechaDeclaracion = fechaDeclaracion;
	}
	public String getFechaAdquisicion() {
		return fechaAdquisicion;
	}
	public void setFechaAdquisicion(String fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}
	public BigDecimal getAreaTerreno() {
		return areaTerreno;
	}
	public void setAreaTerreno(BigDecimal areaTerreno) {
		this.areaTerreno = areaTerreno;
	}
	public String getUbicacionPredio() {
		return ubicacionPredio;
	}
	public void setUbicacionPredio(String ubicacionPredio) {
		this.ubicacionPredio = ubicacionPredio;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getFiscalizado() {
		return fiscalizado;
	}
	public void setFiscalizado(String fiscalizado) {
		this.fiscalizado = fiscalizado;
	}
	public int getPredioId() {
		return predioId;
	}
	public void setPredioId(int predioId) {
		this.predioId = predioId;
	}
	public BigDecimal getPorcPropiedad() {
		return porcPropiedad;
	}
	public void setPorcPropiedad(BigDecimal porcPropiedad) {
		this.porcPropiedad = porcPropiedad;
	}	
	public int getMotivoDeclaracionId() {
		return motivoDeclaracionId;
	}
	public void setMotivoDeclaracionId(int motivoDeclaracionId) {
		this.motivoDeclaracionId = motivoDeclaracionId;
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
	
	public Boolean getPoseePendiente() {
		return poseePendiente;
	}

	public void setPoseePendiente(Boolean poseePendiente) {
		this.poseePendiente = poseePendiente;
	}
	
	public Boolean getPoseePendienteFisca() {
		return poseePendienteFisca;
	}

	public void setPoseePendienteFisca(Boolean poseePendienteFisca) {
		this.poseePendienteFisca = poseePendienteFisca;
	}
	
	public String getTipoPredio() {
		return tipoPredio;
	}
	public void setTipoPredio(String tipoPredio) {
		this.tipoPredio = tipoPredio;
	}
	
	public BigDecimal getAreaTerrenoHas() {
		return areaTerrenoHas;
	}
	public void setAreaTerrenoHas(BigDecimal areaTerrenoHas) {
		this.areaTerrenoHas = areaTerrenoHas;
	}
	public String getNombreNotaria() {
		return nombreNotaria;
	}
	public void setNombreNotaria(String nombreNotaria) {
		this.nombreNotaria = nombreNotaria;
	}
	public String getMotivoDescargo() {
		return motivoDescargo;
	}
	public void setMotivoDescargo(String motivoDescargo) {
		this.motivoDescargo = motivoDescargo;
	}
	public String getEsDescargoFlag() {
		return esDescargoFlag;
	}
	public void setEsDescargoFlag(String esDescargoFLag) {
		this.esDescargoFlag = esDescargoFLag;
	}
	public String getDireccionCompleta() {
		return direccionCompleta;
	}
	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}
	
	public Timestamp getFechaDescargo() {
		return fechaDescargo;
	}
	public void setFechaDescargo(Timestamp fechaDescargo) {
		this.fechaDescargo = fechaDescargo;
	}
	public int getMontivoDescargoId() {
		return montivoDescargoId;
	}
	public void setMontivoDescargoId(int montivoDescargoId) {
		this.montivoDescargoId = montivoDescargoId;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	public int getNotariaId() {
		return notariaId;
	}
	public void setNotariaId(int notariaId) {
		this.notariaId = notariaId;
	}
	public boolean isFusionMigracion() {
		fusionMigracion = esFusionMigracion();
		return fusionMigracion;
	}
	public void setFusionMigracion(boolean fusionMigracion) {
		this.fusionMigracion = fusionMigracion;
	}

}
