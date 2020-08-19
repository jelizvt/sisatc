package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class OrdenPago extends Contenido {

	
	private String nroOP;
	private String codContribOP;
	private Date fechaOP;

	public OrdenPago(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);		
	}

	public OrdenPago(String name, byte[] contentBytes, String id, String nroOP, String codContribOP, Date fechaOP) {
		super(name, contentBytes, id);		
		this.nroOP = nroOP;
		this.codContribOP = codContribOP;
		this.fechaOP = fechaOP;
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] { Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_NRO_OP, this.getNroOP()),
				Utils.createNamedValue(Constantes.PROP_COD_CONTRIB_OP, this.getCodContribOP()),
				Utils.createNamedValue(Constantes.PROP_FECHA_OP, Util.convertDateToStringISO8601(this.getFechaOP())),};

		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Ordenes/cm:OrdPago";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_ORDEN_PAGO;
	}

	public String getNroOP() {
		return nroOP;
	}

	public void setNroOP(String nroOP) {
		this.nroOP = nroOP;
	}

	public String getCodContribOP() {
		return codContribOP;
	}

	public void setCodContribOP(String codContribOP) {
		this.codContribOP = codContribOP;
	}

	public Date getFechaOP() {
		return fechaOP;
	}

	public void setFechaOP(Date fechaOP) {
		this.fechaOP = fechaOP;
	}
	
}
