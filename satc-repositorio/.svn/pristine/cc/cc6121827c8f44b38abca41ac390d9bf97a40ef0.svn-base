package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class RecursoApelacion extends Contenido {

	
	private String nroApela;
	private String codContribRecursoApela;
	private Date fechaRecursoApela;
	private String nroRecursoApela;

	
	
	public RecursoApelacion(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);		
	}

	public RecursoApelacion(String name, byte[] contentBytes, String id, String nroApela,
			String codContribRecursoApela, Date fechaRecursoApela, String nroRecursoApela) {
		super(name, contentBytes, id);		
		this.nroApela = nroApela;
		this.codContribRecursoApela = codContribRecursoApela;
		this.fechaRecursoApela = fechaRecursoApela;
		this.nroRecursoApela = nroRecursoApela;
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] {
				Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_NRO_APELA, this.getNroApela()),
				Utils.createNamedValue(Constantes.PROP_COD_CONTRIB_RECURSO_APELA, this.getCodContribRecursoApela()),
				Utils.createNamedValue(Constantes.PROP_FECHA_RECURSO_APELA,
						Util.convertDateToStringISO8601(this.getFechaRecursoApela())),
				Utils.createNamedValue(Constantes.PROP_NRO_RECURSO_APELA, this.getNroRecursoApela()), };

		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Recursos/cm:RcsApelacion";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_RESCURSO_APELA;
	}

	public String getNroApela() {
		return nroApela;
	}

	public void setNroApela(String nroApela) {
		this.nroApela = nroApela;
	}

	public String getCodContribRecursoApela() {
		return codContribRecursoApela;
	}

	public void setCodContribRecursoApela(String codContribRecursoApela) {
		this.codContribRecursoApela = codContribRecursoApela;
	}

	public Date getFechaRecursoApela() {
		return fechaRecursoApela;
	}

	public void setFechaRecursoApela(Date fechaRecursoApela) {
		this.fechaRecursoApela = fechaRecursoApela;
	}

	public String getNroRecursoApela() {
		return nroRecursoApela;
	}

	public void setNroRecursoApela(String nroRecursoApela) {
		this.nroRecursoApela = nroRecursoApela;
	}
}
