package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class ResolucionEjecucionCoactiva extends Contenido {

	

	private String nroREC;
	private String codContribREC;
	private Date fechaREC;


	public ResolucionEjecucionCoactiva(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);		
	}

	public ResolucionEjecucionCoactiva(String name, byte[] contentBytes, String id, String nroREC,
			String codContribREC, Date fechaREC) {
		super(name, contentBytes, id);		
		this.nroREC = nroREC;
		this.codContribREC = codContribREC;
		this.fechaREC = fechaREC;
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] { Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_NRO_RD, this.getNroREC()),
				Utils.createNamedValue(Constantes.PROP_COD_CONTRIB_RD, this.getCodContribREC()),
				Utils.createNamedValue(Constantes.PROP_FECHA_RD, Util.convertDateToStringISO8601(this.getFechaREC())),

		};

		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Resoluciones/cm:ResEjeCoactiva";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_RES_EJEC_COACTIVA;
	}

	public String getNroREC() {
		return nroREC;
	}

	public void setNroREC(String nroREC) {
		this.nroREC = nroREC;
	}

	public String getCodContribREC() {
		return codContribREC;
	}

	public void setCodContribREC(String codContribREC) {
		this.codContribREC = codContribREC;
	}

	public Date getFechaREC() {
		return fechaREC;
	}

	public void setFechaREC(Date fechaREC) {
		this.fechaREC = fechaREC;
	}

}
