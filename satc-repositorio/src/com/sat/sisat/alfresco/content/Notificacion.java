package com.sat.sisat.alfresco.content;

import java.util.Date;

import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class Notificacion extends Contenido {

	private String personaId;

	private String tipoActo;

	private Date fechaNotificacion;

	private String notificadorId;

	private String nroDocumento;

	public Notificacion(String name, byte[] contentBytes, String id) {
		super(name, contentBytes, id);
	}

	public Notificacion(String name,
			byte[] contentBytes,
			String id,
			String personaId,
			String tipoActo,
			Date fechaNotificacion,
			String notificadorId,
			String nroDocumento) {
		super(name, contentBytes, id);
		this.personaId = personaId;
		this.tipoActo = tipoActo;
		this.fechaNotificacion = fechaNotificacion;
		this.notificadorId = notificadorId;
		this.nroDocumento = nroDocumento;
	}

	public NamedValue[] getNamedValueAspectAll() {

		NamedValue[] titledProps = new NamedValue[] {
				Utils.createNamedValue(Constantes.PROP_ID, this.getId()),
				Utils.createNamedValue(Constantes.PROP_PERSONA_ID, this.getPersonaId()),
				Utils.createNamedValue(Constantes.PROP_TIPO_ACTO, this.getTipoActo()),
				Utils.createNamedValue(Constantes.PROP_FECHA_NOTIFICACION, Util.convertDateToStringISO8601(this.getFechaNotificacion())),
				Utils.createNamedValue(Constantes.PROP_NOTIFICADOR_ID, this.getNotificadorId()),
				Utils.createNamedValue(Constantes.PROP_NRO_DOCUMENTO, this.getNroDocumento()),				
		};

		return titledProps;
	}

	public String getNombreRepositorio() {

		return "/app:company_home/cm:SATCRepositorio/cm:Notificaciones";
	}

	public String getTipoContenido() {
		return Constantes.TYPE_NOTIFICACION;
	}

	public String getPersonaId() {
		return personaId;
	}

	public void setPersonaId(String personaId) {
		this.personaId = personaId;
	}

	public String getTipoActo() {
		return tipoActo;
	}

	public void setTipoActo(String tipoActo) {
		this.tipoActo = tipoActo;
	}

	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public String getNotificadorId() {
		return notificadorId;
	}

	public void setNotificadorId(String notificadorId) {
		this.notificadorId = notificadorId;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
}
