package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class ResolucionReclamaciones extends Contenido {



	private String nroRecla;
	private String codContribRecla;
	private Date fechaRecla;

	public ResolucionReclamaciones(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);		
	}

	public ResolucionReclamaciones(String name, byte[] contentBytes, String id, String nroRecla,
			String codContribRecla, Date fechaRecla) {
		super(name, contentBytes, id);		
		this.nroRecla = nroRecla;
		this.codContribRecla = codContribRecla;
		this.fechaRecla = fechaRecla;
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] { Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_NRO_RECLA, this.getNroRecla()),
				Utils.createNamedValue(Constantes.PROP_COD_CONTRIB_RECLA, this.getCodContribRecla()),
				Utils.createNamedValue(Constantes.PROP_FECHA_RECLA, Util.convertDateToStringISO8601(this.getFechaRecla())),
		};

		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Resoluciones/cm:ResReclamaciones";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_RES_RECLAMACIONES;
	}

	public String getNroRecla() {
		return nroRecla;
	}

	public void setNroRecla(String nroRecla) {
		this.nroRecla = nroRecla;
	}

	public String getCodContribRecla() {
		return codContribRecla;
	}

	public void setCodContribRecla(String codContribRecla) {
		this.codContribRecla = codContribRecla;
	}

	public Date getFechaRecla() {
		return fechaRecla;
	}

	public void setFechaRecla(Date fechaRecla) {
		this.fechaRecla = fechaRecla;
	}

}
