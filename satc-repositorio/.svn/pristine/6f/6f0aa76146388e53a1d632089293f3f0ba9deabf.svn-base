package com.sat.sisat.alfresco.content;

import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.Utils;

import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;

public class Contenido {	
	
	private String name;	
	
	private String id;

	private ContentFormat contentFormat = new ContentFormat(Constantes.CONTENT_MIMETYPE_OCTET_STREAM, Constantes.ENCODING_UTF8);;

	private String nombreRepositorio = "/app:company_home/cm:SATCRepositorio";
	
	private String tipoContenido = Constants.TYPE_CONTENT;
	
	private String propContenido = Constants.PROP_CONTENT;

	private byte[] contentBytes;
	
	private String pathFTP;
	
	public Contenido(String name, byte[] contentBytes, String id) {
		super();
		
		this.name = name;
		this.contentBytes = contentBytes;
		this.id = id;
		Util.setAutoContentFormat(this);
		
	}		
	
	public Contenido(String name, ContentFormat contentFormat, String nombreRepositorio,
			byte[] contentBytes) {
		super();
		
		this.name = name;	
		this.contentFormat = contentFormat;
		this.nombreRepositorio = nombreRepositorio;
		this.contentBytes = contentBytes;
	}

	public Contenido() {
		super();
	}

	public NamedValue[] getNamedValueAspectAll() {
		
		NamedValue[] titledProps = new NamedValue[0];        
		
		return titledProps;
	}
	
	public NamedValue[] getNamedValueContent() {
		
		NamedValue[] contentProps = new NamedValue[1];
		
		contentProps[0] = Utils.createNamedValue(Constants.PROP_NAME, this.getName());
		
		return contentProps;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNombreRepositorio() {
		return nombreRepositorio;
	}

	public void setNombreRepositorio(String nombreRepositorio) {
		this.nombreRepositorio = nombreRepositorio;
	}
	
	public String getTipoContenido() {
		return tipoContenido;
	}

	public void setTipoContenido(String tipoContenido) {
		this.tipoContenido = tipoContenido;
	}

	public ContentFormat getContentFormat() {
		return contentFormat;
	}

	public void setContentFormat(ContentFormat contentFormat) {
		this.contentFormat = contentFormat;
	}

	public String getPropContenido() {
		return propContenido;
	}

	public void setPropContenido(String propContenido) {
		this.propContenido = propContenido;
	}

	public byte[] getContentBytes() {
		return contentBytes;
	}

	public void setContentBytes(byte[] contentBytes) {
		this.contentBytes = contentBytes;
	}
	
	/**
	 * Metodo usado para cambiar el tipo de archivo en base al mimetype, por defecto el tipo usado es PDF, pero 
	 * se puede guardar imagenes como en jpg, png u cualquier otro tipo de archivo siempre y cuando se ingrese un
	 * mimetype valido.
	 * @param mimetype Por ejm. application/pdf, image/jpeg
	 */
	public void changeContentFormat(String mimetype){
		
		if (mimetype != null && !mimetype.isEmpty()) {
			contentFormat = new ContentFormat(mimetype, Constantes.ENCODING_UTF8);			
		}
	}
	
	/**
	 * Metodo usado para cambiar el tipo de archivo en base al mimetype y a la codificacion usada, por defecto el tipo 
	 * usado es PDF y la codificacion es UTF-8, pero se puede guardar imagenes como en jpg, png u cualquier otro tipo 
	 * de archivo siempre y cuando se ingrese un mimetype y una codificacion valida.
	 * @param mimetype Por ejm. application/pdf, image/jpeg
	 * @param encoding Por ejm. UTF-8
	 */
	public void changeContentFormat(String mimetype, String encoding) {
	
		if (mimetype != null && !mimetype.isEmpty() && encoding != null && !encoding.isEmpty()) {
			contentFormat = new ContentFormat(mimetype, encoding);
	
		}		
	}

	public String getPathFTP() {
		return pathFTP;
	}

	public void setPathFTP(String pathFTP) {
		this.pathFTP = pathFTP;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
