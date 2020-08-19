package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class PapeletaInfraccion extends Contenido {

	
	private String nroPITInfra;
	private String nroPlacaInfra;
	private String tipoInfraccionInfra;
	private String dniInfra;
	private Date fechaInfra;



	public PapeletaInfraccion(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);		
	}

	public PapeletaInfraccion(String name, byte[] contentBytes, String id, String nroPITInfra, String nroPlacaInfra,
			String tipoInfraccionInfra, String dniInfra, Date fechaInfra) {
		super(name, contentBytes, id);		
		this.nroPITInfra = nroPITInfra;
		this.nroPlacaInfra = nroPlacaInfra;
		this.tipoInfraccionInfra = tipoInfraccionInfra;
		this.dniInfra = dniInfra;
		this.fechaInfra = fechaInfra;
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] { Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_NRO_PIT_INFRA, this.getNroPITInfra()),
				Utils.createNamedValue(Constantes.PROP_NRO_PLACA_INFRA, this.getNroPlacaInfra()),
				Utils.createNamedValue(Constantes.PROP_TIPO_INFRACCION_INFRA, this.getTipoInfraccionInfra()),
				Utils.createNamedValue(Constantes.PROP_DNI_INFRA, this.getDniInfra()),
				Utils.createNamedValue(Constantes.PROP_FECHA_INFRA, Util.convertDateToStringISO8601(this.getFechaInfra())),};

		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Papeletas/cm:PapInfTransito";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_PAPEL_INFRACC_TRANSITO;
	}

	
	public String getNroPITInfra() {
		return nroPITInfra;
	}

	public void setNroPITInfra(String nroPITInfra) {
		this.nroPITInfra = nroPITInfra;
	}

	public String getNroPlacaInfra() {
		return nroPlacaInfra;
	}

	public void setNroPlacaInfra(String nroPlacaInfra) {
		this.nroPlacaInfra = nroPlacaInfra;
	}

	public String getTipoInfraccionInfra() {
		return tipoInfraccionInfra;
	}

	public void setTipoInfraccionInfra(String tipoInfraccionInfra) {
		this.tipoInfraccionInfra = tipoInfraccionInfra;
	}

	public String getDniInfra() {
		return dniInfra;
	}

	public void setDniInfra(String dniInfra) {
		this.dniInfra = dniInfra;
	}

	public Date getFechaInfra() {
		return fechaInfra;
	}

	public void setFechaInfra(Date fechaInfra) {
		this.fechaInfra = fechaInfra;
	}

}
