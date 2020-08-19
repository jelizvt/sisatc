package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class ResolucionFraccionamiento extends Contenido {

	private String nroRFracc;
	private String codContribRFracc;
	private Date fechaRFracc;

	

	public ResolucionFraccionamiento(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);		
	}

	public ResolucionFraccionamiento(String name, byte[] contentBytes, String id, String nroRFracc,
			String codContribRFracc, Date fechaRFracc) {
		super(name, contentBytes, id);		
		this.nroRFracc = nroRFracc;
		this.codContribRFracc = codContribRFracc;
		this.fechaRFracc = fechaRFracc;
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] {
				Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_NRO_RFRACC, this.getNroRFracc()),
				Utils.createNamedValue(Constantes.PROP_COD_CONTRIB_RFRACC, this.getCodContribRFracc()),
				Utils.createNamedValue(Constantes.PROP_FECHA_RFRACC,
						Util.convertDateToStringISO8601(this.getFechaRFracc())), };

		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Resoluciones/cm:ResFraccionamiento";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_RES_FRACCIONAMIENTO;
	}

	public String getNroRFracc() {
		return nroRFracc;
	}

	public void setNroRFracc(String nroRFracc) {
		this.nroRFracc = nroRFracc;
	}

	public String getCodContribRFracc() {
		return codContribRFracc;
	}

	public void setCodContribRFracc(String codContribRFracc) {
		this.codContribRFracc = codContribRFracc;
	}

	public Date getFechaRFracc() {
		return fechaRFracc;
	}

	public void setFechaRFracc(Date fechaRFracc) {
		this.fechaRFracc = fechaRFracc;
	}

}
