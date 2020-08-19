package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class ResolucionCobranzaCoactiva extends Contenido {

	
	private String nroRCC;
	private String codContribRCC;
	private Date fechaRCC;

	

	public ResolucionCobranzaCoactiva(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);		
	}

	public ResolucionCobranzaCoactiva(String name, byte[] contentBytes, String id, String nroRCC, String codContribRCC,
			Date fechaRCC) {
		super(name, contentBytes, id);		
		this.nroRCC = nroRCC;
		this.codContribRCC = codContribRCC;
		this.fechaRCC = fechaRCC;
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] { Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_NRO_RCC, this.getNroRCC()),
				Utils.createNamedValue(Constantes.PROP_COD_CONTRIB_RCC, this.getCodContribRCC()),
				Utils.createNamedValue(Constantes.PROP_FECHA_RCC, Util.convertDateToStringISO8601(this.getFechaRCC())), };

		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Resoluciones/cm:ResCobCoactiva";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_RES_COBRA_COAC;
	}

	public String getNroRCC() {
		return nroRCC;
	}

	public void setNroRCC(String nroRCC) {
		this.nroRCC = nroRCC;
	}

	public String getCodContribRCC() {
		return codContribRCC;
	}

	public void setCodContribRCC(String codContribRCC) {
		this.codContribRCC = codContribRCC;
	}

	public Date getFechaRCC() {
		return fechaRCC;
	}

	public void setFechaRCC(Date fechaRCC) {
		this.fechaRCC = fechaRCC;
	}

}
