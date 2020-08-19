package com.sat.sisat.predial.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sat.sisat.common.util.Constante;

public class FindRpDjPredial implements Serializable {
	
	private int personaId;
	private int predioId;
	private int djId;
	private String codigoAnterior;
	private Timestamp  fechaDeclaracion;
	private String fechaAdquisicion;
	private BigDecimal porcPropiedad;
	
	private String tipoPredio;
	private String tipoPredioDesc;
	
	private String codigoPredio;
	private String direccionCompleta;
	private String sector;
	private int sectorId;
	private String manzana;
	private String cuadra;
	private String lado;
	
	private String viadescripcion;
	private String sectordescripcion;
	private String denurbanadescripcion;
	
	private String anioDj;
	private int tipoDocIdentidad;
	private String descripcionDocIdentidad;
	private int tipoPersonaId;
	private String descripcionTipoPersona;
	private int distritoId;
	
	private String descripcionDistrito;
	private String numeroDocIdentidad;
	private String apellidosNombres;
	private String razonSocial;
	private String estado;
	
	private String motivoDeclaracion;
	private String motivoDeclaracionId;
	private Integer motivoDescargoId;
	
	private String fiscalizado;
	private String tipoVia;
	
	//inicio ivo
	private String apePaterno;
	private String apeMaterno;
	private int tipoViaId;
	private String telefonoPersona;
	private int subtipoPersonaId;
	private int viaId;
	//fin ivo
	private Double areaTerreno;
	private Double areaTerrenoHas;
	
	private String condicionPropiedad;
	
	private Integer ubicacionId; 
	
	private String nombreEdificiacion;
	
	private boolean selected;
	
	public Boolean esPendiente(){
		return estado.equals("2")?Boolean.TRUE:Boolean.FALSE;
	}
	public Boolean esDescargo(){
		return motivoDeclaracionId.trim().equals("4") ? Boolean.TRUE:Boolean.FALSE;
	}
	public Boolean esFusionMigracion(){
		return ( motivoDescargoId.compareTo(new Integer(Constante.MOTIVO_DESCARGO_FUSION_MIGRACION)) == 0  && motivoDeclaracionId.trim().equals(Constante.MOTIVO_DECLARACION_MIGRACION.toString()))? Boolean.TRUE:Boolean.FALSE;
	}
	
	public String getStyleSelected() {
        if(esPendiente())
        	return "yellow";
        else if(esDescargo())
        	return "#E6E6E6";
        else if(esFusionMigracion())
        	return "#D8D8D8";
        else if(!esPendiente())
        	return "#99FF99";
        else if (ubicacionId==0)
        	return 	"#F6E3CE";
        else
        	return "white";
    }
	
	public String getAnioDj() {
		return anioDj;
	}
	public void setAnioDj(String anioDj) {
		this.anioDj = anioDj;
	}
	public int getTipoDocIdentidad() {
		return tipoDocIdentidad;
	}
	public void setTipoDocIdentidad(int tipoDocIdentidad) {
		this.tipoDocIdentidad = tipoDocIdentidad;
	}
	public String getDescripcionDocIdentidad() {
		return descripcionDocIdentidad;
	}
	public void setDescripcionDocIdentidad(String descripcionDocIdentidad) {
		this.descripcionDocIdentidad = descripcionDocIdentidad;
	}
	public int getTipoPersonaId() {
		return tipoPersonaId;
	}
	public void setTipoPersonaId(int tipoPersonaId) {
		this.tipoPersonaId = tipoPersonaId;
	}
	public String getDescripcionTipoPersona() {
		return descripcionTipoPersona;
	}
	public void setDescripcionTipoPersona(String descripcionTipoPersona) {
		this.descripcionTipoPersona = descripcionTipoPersona;
	}
	public int getDistritoId() {
		return distritoId;
	}
	public void setDistritoId(int distritoId) {
		this.distritoId = distritoId;
	}
	public String getDescripcionDistrito() {
		return descripcionDistrito;
	}
	public void setDescripcionDistrito(String descripcionDistrito) {
		this.descripcionDistrito = descripcionDistrito;
	}
	public String getNumeroDocIdentidad() {
		return numeroDocIdentidad;
	}
	public void setNumeroDocIdentidad(String numeroDocIdentidad) {
		this.numeroDocIdentidad = numeroDocIdentidad;
	}
	public String getApellidosNombres() {
		return apellidosNombres;
	}
	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getViadescripcion() {
		return viadescripcion;
	}
	public void setViadescripcion(String viadescripcion) {
		this.viadescripcion = viadescripcion;
	}
	public String getSectordescripcion() {
		return sectordescripcion;
	}
	public void setSectordescripcion(String sectordescripcion) {
		this.sectordescripcion = sectordescripcion;
	}
	public String getDenurbanadescripcion() {
		return denurbanadescripcion;
	}
	public void setDenurbanadescripcion(String denurbanadescripcion) {
		this.denurbanadescripcion = denurbanadescripcion;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	
	public int getPredioId() {
		return predioId;
	}
	public void setPredioId(int predioId) {
		this.predioId = predioId;
	}
	public int getDjId() {
		return djId;
	}
	public void setDjId(int djId) {
		this.djId = djId;
	}
	public String getCodigoAnterior() {
		return codigoAnterior;
	}
	public void setCodigoAnterior(String codigoAnterior) {
		this.codigoAnterior = codigoAnterior;
	}
	public Timestamp getFechaDeclaracion() {
		return fechaDeclaracion;
	}
	public void setFechaDeclaracion(Timestamp fechaDeclaracion) {
		this.fechaDeclaracion = fechaDeclaracion;
	}
	public String getTipoPredio() {
		return tipoPredio;
	}
	public void setTipoPredio(String tipoPredio) {
		this.tipoPredio = tipoPredio;
	}
	public String getCodigoPredio() {
		return codigoPredio;
	}
	public void setCodigoPredio(String codigoPredio) {
		this.codigoPredio = codigoPredio;
	}
	public String getDireccionCompleta() {
		return direccionCompleta;
	}
	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}
	public int getSectorId() {
		return sectorId;
	}
	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}
	public String getManzana() {
		return manzana;
	}
	public void setManzana(String manzana) {
		this.manzana = manzana;
	}
	public String getFechaAdquisicion() {
		return fechaAdquisicion;
	}
	public void setFechaAdquisicion(String fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}	
	public int getPersonaId() {
		return personaId;
	}
	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}
	public String getTipoPredioDesc() {
		return tipoPredioDesc;
	}
	public void setTipoPredioDesc(String tipoPredioDesc) {
		this.tipoPredioDesc = tipoPredioDesc;
	}
	public BigDecimal getPorcPropiedad() {
		return porcPropiedad;
	}
	public void setPorcPropiedad(BigDecimal porcPropiedad) {
		this.porcPropiedad = porcPropiedad;
	}
	public String getMotivoDeclaracion() {
		return motivoDeclaracion;
	}
	public void setMotivoDeclaracion(String motivoDeclaracion) {
		this.motivoDeclaracion = motivoDeclaracion;
	}
	public String getMotivoDeclaracionId() {
		return motivoDeclaracionId;
	}

	public void setMotivoDeclaracionId(String motivoDeclaracionId) {
		this.motivoDeclaracionId = motivoDeclaracionId;
	}

	public String getFiscalizado() {
		return fiscalizado;
	}

	public void setFiscalizado(String fiscalizado) {
		this.fiscalizado = fiscalizado;
	}

	public String getApePaterno() {
		return apePaterno;
	}

	public void setApePaterno(String apePaterno) {
		this.apePaterno = apePaterno;
	}

	public String getApeMaterno() {
		return apeMaterno;
	}

	public void setApeMaterno(String apeMaterno) {
		this.apeMaterno = apeMaterno;
	}

	public int getTipoViaId() {
		return tipoViaId;
	}

	public void setTipoViaId(int tipoViaId) {
		this.tipoViaId = tipoViaId;
	}

	public String getTelefonoPersona() {
		return telefonoPersona;
	}

	public void setTelefonoPersona(String telefonoPersona) {
		this.telefonoPersona = telefonoPersona;
	}

	public int getSubtipoPersonaId() {
		return subtipoPersonaId;
	}

	public void setSubtipoPersonaId(int subtipoPersonaId) {
		this.subtipoPersonaId = subtipoPersonaId;
	}
	
	public String getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}
	
	public String getCuadra() {
		return cuadra;
	}

	public void setCuadra(String cuadra) {
		this.cuadra = cuadra;
	}

	public String getLado() {
		return lado;
	}

	public void setLado(String lado) {
		this.lado = lado;
	}

	public int getViaId() {
		return viaId;
	}

	public void setViaId(int viaId) {
		this.viaId = viaId;
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
	
	public String getCondicionPropiedad() {
		return condicionPropiedad;
	}

	public void setCondicionPropiedad(String condicionPropiedad) {
		this.condicionPropiedad = condicionPropiedad;
	}
	
	public Integer getUbicacionId() {
		return ubicacionId;
	}
	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}
	public Integer getMotivoDescargoId() {
		return motivoDescargoId;
	}
	public void setMotivoDescargoId(Integer motivoDescargoId) {
		this.motivoDescargoId = motivoDescargoId;
	}
	public String getNombreEdificiacion() {
		return nombreEdificiacion;
	}
	public void setNombreEdificiacion(String nombreEdificiacion) {
		this.nombreEdificiacion = nombreEdificiacion;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
