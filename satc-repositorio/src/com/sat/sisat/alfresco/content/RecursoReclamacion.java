package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class RecursoReclamacion extends Contenido {

	

	private String tipoRecla;
	private String codContribRecursoRecla;
	private Date fechaRecursoRecla;
	private String nroRecursoRecla;

	

	public RecursoReclamacion(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);		
	}

	public RecursoReclamacion(String name, byte[] contentBytes, String id, String tipoRecla,
			String codContribRecursoRecla, Date fechaRecursoRecla, String nroRecursoRecla) {
		super(name, contentBytes, id);		
		this.tipoRecla = tipoRecla;
		this.codContribRecursoRecla = codContribRecursoRecla;
		this.fechaRecursoRecla = fechaRecursoRecla;
		this.nroRecursoRecla = nroRecursoRecla;
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] {
				Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_TIPO_RECLA, this.getTipoRecla()),
				Utils.createNamedValue(Constantes.PROP_COD_CONTRIB_RECURSO_RECLA, this.getCodContribRecursoRecla()),
				Utils.createNamedValue(Constantes.PROP_FECHA_RECURSO_RECLA,
						Util.convertDateToStringISO8601(this.getFechaRecursoRecla())),
				Utils.createNamedValue(Constantes.PROP_NRO_RECURSO_RECLA, this.getNroRecursoRecla()), };

		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Recursos/cm:RcsReclamacion";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_RESCURSO_RECLA;
	}

	public String getTipoRecla() {
		return tipoRecla;
	}

	public void setTipoRecla(String tipoRecla) {
		this.tipoRecla = tipoRecla;
	}

	public String getCodContribRecursoRecla() {
		return codContribRecursoRecla;
	}

	public void setCodContribRecursoRecla(String codContribRecursoRecla) {
		this.codContribRecursoRecla = codContribRecursoRecla;
	}

	public Date getFechaRecursoRecla() {
		return fechaRecursoRecla;
	}

	public void setFechaRecursoRecla(Date fechaRecursoRecla) {
		this.fechaRecursoRecla = fechaRecursoRecla;
	}

	public String getNroRecursoRecla() {
		return nroRecursoRecla;
	}

	public void setNroRecursoRecla(String nroRecursoRecla) {
		this.nroRecursoRecla = nroRecursoRecla;
	}

}
