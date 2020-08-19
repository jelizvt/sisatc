package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class ResolucionDptoFiscalizacion extends Contenido {

	
	private String nroRDF;
	private String codContribRDF;
	private Date fechaRDF;

	

	public ResolucionDptoFiscalizacion(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);		
	}

	public ResolucionDptoFiscalizacion(String name, byte[] contentBytes, String id, String nroRDF,
			String codContribRDF, Date fechaRDF) {
		super(name, contentBytes, id);		
		this.nroRDF = nroRDF;
		this.codContribRDF = codContribRDF;
		this.fechaRDF = fechaRDF;
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] { Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_NRO_RDF, this.getNroRDF()),
				Utils.createNamedValue(Constantes.PROP_COD_CONTRIB_RDF, this.getCodContribRDF()),
				Utils.createNamedValue(Constantes.PROP_FECHA_RDF, Util.convertDateToStringISO8601(this.getFechaRDF())), };

		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Resoluciones/cm:ResDptFiscalizacion";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_RES_DPTO_FISCA;
	}

	public String getNroRDF() {
		return nroRDF;
	}

	public void setNroRDF(String nroRDF) {
		this.nroRDF = nroRDF;
	}

	public String getCodContribRDF() {
		return codContribRDF;
	}

	public void setCodContribRDF(String codContribRDF) {
		this.codContribRDF = codContribRDF;
	}

	public Date getFechaRDF() {
		return fechaRDF;
	}

	public void setFechaRDF(Date fechaRDF) {
		this.fechaRDF = fechaRDF;
	}

}
