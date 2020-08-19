package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class DocumentoInscripcionDescargo extends Contenido {

	

	private String tipoDoc;
	private String codContribInscripDesc;
	private Date fechaInscripDesc;
	private String nroDj;
	private String tipo;

	

	public DocumentoInscripcionDescargo(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);
		
	}

	public DocumentoInscripcionDescargo(String name, byte[] contentBytes, String id, String tipoDoc,
			String codContribInscripDesc, Date fechaInscripDesc, String nroDj, String tipo) {
		super(name, contentBytes, id);		
		this.tipoDoc = tipoDoc;
		this.codContribInscripDesc = codContribInscripDesc;
		this.fechaInscripDesc = fechaInscripDesc;
		this.nroDj = nroDj;
		this.tipo = tipo;
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] {
				Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_TIPO_DOC, this.getTipoDoc()),
				Utils.createNamedValue(Constantes.PROP_COD_CONTRIB_INSCRIP_DESC, this.getCodContribInscripDesc()),
				Utils.createNamedValue(Constantes.PROP_FECHA_INSCRIP_DESC,
						Util.convertDateToStringISO8601(this.getFechaInscripDesc())),
				Utils.createNamedValue(Constantes.PROP_NRO_DJ, this.getNroDj()),
				Utils.createNamedValue(Constantes.PROP_TIPO, this.getTipo()), };

		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Varios/cm:DocInscDesc";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_DOC_INSCRIP_DESC;
	}

	
	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getCodContribInscripDesc() {
		return codContribInscripDesc;
	}

	public void setCodContribInscripDesc(String codContribInscripDesc) {
		this.codContribInscripDesc = codContribInscripDesc;
	}

	public Date getFechaInscripDesc() {
		return fechaInscripDesc;
	}

	public void setFechaInscripDesc(Date fechaInscripDesc) {
		this.fechaInscripDesc = fechaInscripDesc;
	}

	public String getNroDj() {
		return nroDj;
	}

	public void setNroDj(String nroDj) {
		this.nroDj = nroDj;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
