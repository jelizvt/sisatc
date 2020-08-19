package com.sat.sisat.alfresco.content;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;

public class ResolucionSancion extends Contenido {

	

	private String nroResolucion;
	private String nroPIT;
	private String nroPlaca;
	private String tipoInfraccion;
	private String dni;

	public ResolucionSancion(String name, byte[] contentBytes, String id, String nroResolucion, String nroPIT,
			String nroPlaca, String tipoInfraccion, String dni) {
		super(name, contentBytes, id);		
		this.nroResolucion = nroResolucion;
		this.nroPIT = nroPIT;
		this.nroPlaca = nroPlaca;
		this.tipoInfraccion = tipoInfraccion;
		this.dni = dni;
	}

	public ResolucionSancion(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);		
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] { Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_NRO_RES, this.getNroResolucion()),
				Utils.createNamedValue(Constantes.PROP_NRO_PIT, this.getNroPIT()),
				Utils.createNamedValue(Constantes.PROP_NRO_PLACA, this.getNroPlaca()),
				Utils.createNamedValue(Constantes.PROP_TIPO_INFRACCION, this.getTipoInfraccion()),
				Utils.createNamedValue(Constantes.PROP_DNI, this.getDni()), };

		return titledProps;
	}

	public String getNroResolucion() {
		return nroResolucion;
	}

	public void setNroResolucion(String nroResolucion) {
		this.nroResolucion = nroResolucion;

	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Resoluciones/cm:ResSancion";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_RES_SANCION;
	}
	
	public String getNroPIT() {
		return nroPIT;
	}

	public void setNroPIT(String nroPIT) {
		this.nroPIT = nroPIT;
	}

	public String getNroPlaca() {
		return nroPlaca;
	}

	public void setNroPlaca(String nroPlaca) {
		this.nroPlaca = nroPlaca;
	}

	public String getTipoInfraccion() {
		return tipoInfraccion;
	}

	public void setTipoInfraccion(String tipoInfraccion) {
		this.tipoInfraccion = tipoInfraccion;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

}
