package com.sat.sisat.alfresco.content;

import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;

public class AnexoAlcabala extends Contenido {

	private String tipoDocumento;
	private String nroDocumento;

	public AnexoAlcabala() {
		super();
	}

	public AnexoAlcabala(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);
	}	

	public AnexoAlcabala(String name, byte[] contentBytes, String id, String tipoDocumento, String nroDocumento) {
		super(name, contentBytes, id);
		this.tipoDocumento = tipoDocumento;
		this.nroDocumento = nroDocumento;
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] { Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_ANEX_NRO_DOC, this.getNroDocumento()),
				Utils.createNamedValue(Constantes.PROP_ANEX_TIPO_DOC, this.getTipoDocumento()), };
		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Anexo/cm:AnexoAlcabala";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_ANEXO_ALCABALA;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

}
