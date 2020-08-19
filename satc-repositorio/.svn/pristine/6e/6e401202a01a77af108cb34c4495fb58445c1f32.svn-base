package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class ResolucionControlDeuda extends Contenido {

	
	private String nroRCD;
	private String codContribRCD;
	private Date fechaRCD;

	

	public ResolucionControlDeuda(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);		
	}

	public ResolucionControlDeuda(String name, byte[] contentBytes, String id, String nroRCD, String codContribRCD,
			Date fechaRCD) {
		super(name, contentBytes, id);		
		this.nroRCD = nroRCD;
		this.codContribRCD = codContribRCD;
		this.fechaRCD = fechaRCD;
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] { Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_NRO_RCD, this.getNroRCD()),
				Utils.createNamedValue(Constantes.PROP_COD_CONTRIB_RCD, this.getCodContribRCD()),
				Utils.createNamedValue(Constantes.PROP_FECHA_RCD, Util.convertDateToStringISO8601(this.getFechaRCD())), };

		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Resoluciones/cm:ResConDeuda";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_RES_CONTROL_DEUDA;
	}

	public String getNroRCD() {
		return nroRCD;
	}

	public void setNroRCD(String nroRCD) {
		this.nroRCD = nroRCD;
	}

	public String getCodContribRCD() {
		return codContribRCD;
	}

	public void setCodContribRCD(String codContribRCD) {
		this.codContribRCD = codContribRCD;
	}

	public Date getFechaRCD() {
		return fechaRCD;
	}

	public void setFechaRCD(Date fechaRCD) {
		this.fechaRCD = fechaRCD;
	}

}
