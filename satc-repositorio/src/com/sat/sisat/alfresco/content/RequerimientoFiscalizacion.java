package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class RequerimientoFiscalizacion extends Contenido {

	
	private String nroRFisca;
	private String codContribRFisca;
	private Date fechaRFisca;
	

	public RequerimientoFiscalizacion(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);		
	}

	public RequerimientoFiscalizacion(String name, byte[] contentBytes, String id, String nroRFisca,
			String codContribRFisca, Date fechaRFisca) {
		super(name, contentBytes, id);		
		this.nroRFisca = nroRFisca;
		this.codContribRFisca = codContribRFisca;
		this.fechaRFisca = fechaRFisca;
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] {
				Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_NRO_RFISCA, this.getNroRFisca()),
				Utils.createNamedValue(Constantes.PROP_COD_CONTRIB_RFISCA, this.getCodContribRFisca()),
				Utils.createNamedValue(Constantes.PROP_FECHA_RFISCA,
						Util.convertDateToStringISO8601(this.getFechaRFisca())), };

		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Requerimientos/cm:ReqFiscalizacion";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_REQ_FISCA;
	}
	
	public String getNroRFisca() {
		return nroRFisca;
	}

	public void setNroRFisca(String nroRFisca) {
		this.nroRFisca = nroRFisca;
	}

	public String getCodContribRFisca() {
		return codContribRFisca;
	}

	public void setCodContribRFisca(String codContribRFisca) {
		this.codContribRFisca = codContribRFisca;
	}

	public Date getFechaRFisca() {
		return fechaRFisca;
	}

	public void setFechaRFisca(Date fechaRFisca) {
		this.fechaRFisca = fechaRFisca;
	}

}
