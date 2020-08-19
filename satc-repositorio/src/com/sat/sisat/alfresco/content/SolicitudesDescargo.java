package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class SolicitudesDescargo extends Contenido {

	

	private String nroRegMP;
	private String codContribSD;
	private Date fechaSD;
	private String nroPITSD;

	

	public SolicitudesDescargo(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);
		
	}

	public SolicitudesDescargo(String name, byte[] contentBytes, String id, String nroRegMP, String codContribSD,
			Date fechaSD, String nroPITSD) {
		super(name, contentBytes, id);		
		this.nroRegMP = nroRegMP;
		this.codContribSD = codContribSD;
		this.fechaSD = fechaSD;
		this.nroPITSD = nroPITSD;
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] { Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_NRO_REG_MP, this.getNroRegMP()),
				Utils.createNamedValue(Constantes.PROP_COD_CONTRIB_SD, this.getCodContribSD()),
				Utils.createNamedValue(Constantes.PROP_FECHA_SD, Util.convertDateToStringISO8601(this.getFechaSD())),
				Utils.createNamedValue(Constantes.PROP_NRO_PIT_SD, this.getNroPITSD()), };

		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Varios/cm:SolDescargo";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_SOLIC_DESCARGO;
	}

	public String getNroRegMP() {
		return nroRegMP;
	}

	public void setNroRegMP(String nroRegMP) {
		this.nroRegMP = nroRegMP;
	}

	public String getCodContribSD() {
		return codContribSD;
	}

	public void setCodContribSD(String codContribSD) {
		this.codContribSD = codContribSD;
	}

	public Date getFechaSD() {
		return fechaSD;
	}

	public void setFechaSD(Date fechaSD) {
		this.fechaSD = fechaSD;
	}

	public String getNroPITSD() {
		return nroPITSD;
	}

	public void setNroPITSD(String nroPITSD) {
		this.nroPITSD = nroPITSD;
	}

}
