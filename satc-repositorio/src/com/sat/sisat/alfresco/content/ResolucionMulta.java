package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class ResolucionMulta extends Contenido {

	

	private String nroRM;
	private String codContribRM;
	private Date fechaRM;

	public ResolucionMulta() {
		super();
	}	
	
	public ResolucionMulta(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);		
	}

	public ResolucionMulta(String name, byte[] contentBytes, String id, String nroRM, String codContribRM, Date fechaRM) {
		super(name, contentBytes, id);		
		this.nroRM = nroRM;
		this.codContribRM = codContribRM;
		this.fechaRM = fechaRM;
	}


	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] { Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_NRO_RM, this.getNroRM()),
				Utils.createNamedValue(Constantes.PROP_COD_CONTRIB_RM, this.getCodContribRM()),
				Utils.createNamedValue(Constantes.PROP_FECHA_RM, Util.convertDateToStringISO8601(this.getFechaRM())),
				 };

		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Resoluciones/cm:ResMulta";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_RES_MULTA;
	}

	public String getNroRM() {
		return nroRM;
	}

	public void setNroRM(String nroRM) {
		this.nroRM = nroRM;
	}

	public String getCodContribRM() {
		return codContribRM;
	}

	public void setCodContribRM(String codContribRM) {
		this.codContribRM = codContribRM;
	}

	public Date getFechaRM() {
		return fechaRM;
	}

	public void setFechaRM(Date fechaRM) {
		this.fechaRM = fechaRM;
	}

}
