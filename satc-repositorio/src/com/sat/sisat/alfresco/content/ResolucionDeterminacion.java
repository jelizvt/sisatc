package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class ResolucionDeterminacion extends Contenido {

		
	private String nroRD;
	private String codContribRD;	
	private Date fechaRD;	

	

	public ResolucionDeterminacion(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);		
	}


	public ResolucionDeterminacion(String name, byte[] contentBytes, String id, String nroRD, String codContribRD,
			Date fechaRD) {
		super(name, contentBytes, id);		
		this.nroRD = nroRD;
		this.codContribRD = codContribRD;
		this.fechaRD = fechaRD;
	}
	
	public NamedValue[] getNamedValueAspectAll() {
		
		NamedValue[] titledProps = new NamedValue[] {Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_NRO_RD, this.getNroRD()),
				Utils.createNamedValue(Constantes.PROP_COD_CONTRIB_RD, this.getCodContribRD()),
				Utils.createNamedValue(Constantes.PROP_FECHA_RD, Util.convertDateToStringISO8601(this.getFechaRD())), 
				
		};
		
		return titledProps;
	}	
	
	public String getNombreRepositorio() {		
		
		return "/app:company_home/cm:SATCRepositorio/cm:Resoluciones/cm:ResDeterminacion";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_RES_DETERMINACION;
	}

	
	public String getNroRD() {
		return nroRD;
	}

	public void setNroRD(String nroRD) {
		this.nroRD = nroRD;
	}

	public String getCodContribRD() {
		return codContribRD;
	}

	public void setCodContribRD(String codContribRD) {
		this.codContribRD = codContribRD;
	}

	public Date getFechaRD() {
		return fechaRD;
	}

	public void setFechaRD(Date fechaRD) {
		this.fechaRD = fechaRD;
	}

	
}
