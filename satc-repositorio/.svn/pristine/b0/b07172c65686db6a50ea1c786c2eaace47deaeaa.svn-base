package com.sat.sisat.alfresco.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.alfresco.webservice.types.ContentFormat;

import com.sat.sisat.alfresco.content.Contenido;

public class Util {

	private static String formatDate = "yyyy-MM-dd'T'00:00:00.000Z";
	private static String formatDateTime = "yyyy-MM-dd'T'HH:mm:ss.mmmZ";

	private static HashMap<String, String> mimeTypes;
	
	public Util() {
		
		
		super();
		

		
	}

	public static String convertDateToStringISO8601(Date fecha) {

		if(fecha == null) {
			return null;
		}
		
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(Util.formatDate, Locale.US);
		String str = sdf.format(fecha);

		/**
		 * Adecuando el formato fecha al formato de la ISO 8601. Complete date plus hours, minutes,
		 * seconds and a decimal fraction of a second YYYY-MM-DDThh:mm:ss.sTZD (eg
		 * 1997-07-16T19:20:30.45+01:00) *
		 * 
		 * Java no logra colocar los dos puntos al final, entonces forzamos el reemplazo para que
		 * alfresco pueda interpretar esa fecha y hora.
		 */
		int length = str.length();
		str = str.substring(0, length - 2).concat(":").concat(str.substring(length - 2));

		return str;
	}

	public static String convertDateTimeToStringISO8601(Date fecha) {

		if(fecha == null) {
			return null;
		}
		
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(Util.formatDateTime, Locale.US);
		String str = sdf.format(fecha);

		/**
		 * Adecuando el formato fecha al formato de la ISO 8601. Complete date plus hours, minutes,
		 * seconds and a decimal fraction of a second YYYY-MM-DDThh:mm:ss.sTZD (eg
		 * 1997-07-16T19:20:30.45+01:00) *
		 * 
		 * Java no logra colocar los dos puntos al final, entonces forzamos el reemplazo para que
		 * alfresco pueda interpretar esa fecha y hora.
		 */
		int length = str.length();
		str = str.substring(0, length - 2).concat(":").concat(str.substring(length - 2));

		return str;
	}

	public static Date convertStringISO8601ToDate(String fecha) {

		/**
		 * Removiendo el caracter ':' debido a que el formato de la ISO 8601 en la parte de GMT
		 * contiene 2 puntos y java no reconoce ese caracter en el parseo de la fecha.
		 */
		int index = fecha.lastIndexOf(":");

		fecha = fecha.substring(0, index).concat(fecha.substring(index + 1));

		Date date = Calendar.getInstance().getTime();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(Util.formatDate, Locale.US);

		try {
			date = sdf.parse(fecha);
		} catch (ParseException e) {
			// TODO Agregar el lanzador de la excepcion
			e.printStackTrace();
		}

		return date;
	}

	public static Date convertStringISO8601ToDateTime(String fecha) {

		/**
		 * Removiendo el caracter ':' debido a que el formato de la ISO 8601 en la parte de GMT
		 * contiene 2 puntos y java no reconoce ese caracter en el parseo de la fecha.
		 */
		int index = fecha.lastIndexOf(":");

		fecha = fecha.substring(0, index).concat(fecha.substring(index + 1));

		Date date = Calendar.getInstance().getTime();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(Util.formatDateTime, Locale.US);

		try {
			date = sdf.parse(fecha);
		} catch (ParseException e) {			
			e.printStackTrace();
		}

		return date;
	}
	
	public static byte[] getBytesFromFile(File f) throws IOException {

		FileInputStream fin = null;

		FileChannel ch = null;
		byte[] bytes = null;
		try {
			fin = new FileInputStream(f);

			ch = fin.getChannel();
			int size = (int) ch.size();
			MappedByteBuffer buf = ch.map(MapMode.READ_ONLY, 0, size);
			bytes = new byte[size];
			buf.get(bytes);

			
		} catch (IOException e) {

			throw e;
		} finally {
			try {
				if (fin != null) {
					fin.close();
				}
				if (ch != null) {
					ch.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}
	
	/**
	 * Metodo usado para cambiar el tipo de archivo en base al mimetype, por defecto el tipo usado es PDF, pero 
	 * se puede guardar imagenes como en jpg, png u cualquier otro tipo de archivo siempre y cuando se ingrese un
	 * mimetype valido.
	 * @param mimetype Por ejm. application/pdf, image/jpeg
	 */
	public static void changeContentFormat(Contenido contenido, String mimetype) {

		ContentFormat contentFormat = null;
		if (mimetype != null && !mimetype.isEmpty()) {
			contentFormat = new ContentFormat(mimetype, Constantes.ENCODING_UTF8);
			contenido.setContentFormat(contentFormat);
		}		
	}

	/**
	 * Metodo usado para cambiar el tipo de archivo en base al mimetype y a la codificacion usada, por defecto el tipo 
	 * usado es PDF y la codificacion es UTF-8, pero se puede guardar imagenes como en jpg, png u cualquier otro tipo 
	 * de archivo siempre y cuando se ingrese un mimetype y una codificacion valida.
	 * @param mimetype Por ejm. application/pdf, image/jpeg
	 * @param encoding Por ejm. UTF-8
	 */
	public static void changeContentFormat(Contenido contenido, String mimetype, String encoding) {

		ContentFormat contentFormat = null;
		if (mimetype != null && !mimetype.isEmpty() && encoding != null && !encoding.isEmpty()) {
			contentFormat = new ContentFormat(mimetype, encoding);
			contenido.setContentFormat(contentFormat);
		}		
	}
	
	
	/**
	 * Metodo que setea un correcto mimetype para la un contenido, si este no
	 * encuentra el mimetype correcto, permanecera el que viene creado por
	 * defecto que es <code>application/octet-stream</code>
	 * 
	 * @param contenido
	 *            Variable Contenido de la cual obtendremos el name para poder
	 *            buscar el mimetype, siempre viene del formato
	 *            <nombre>.<extension>
	 */
	public static void setAutoContentFormat(Contenido contenido) {

		if (mimeTypes == null) {
			mimeTypes = new HashMap<String, String>();

			mimeTypes.put("pdf", "application/pdf");
			mimeTypes.put("txt", "text/plain");
			mimeTypes.put("rtf", "text/rtf");

			mimeTypes.put("gif", "image/gif");
			mimeTypes.put("jpeg", "image/jpeg");
			mimeTypes.put("jpg", "image/jpeg");
			mimeTypes.put("jpe", "image/jpeg");
			mimeTypes.put("png", "image/png");
			mimeTypes.put("tiff", "image/tiff");
			mimeTypes.put("tif", "image/tiff");
			mimeTypes.put("bmp", "image/bmp");

			mimeTypes.put("doc", "application/msword");
			mimeTypes.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			mimeTypes.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			mimeTypes.put("xls", "application/vnd.ms-excel");

			mimeTypes.put("zip", "application/zip");
			mimeTypes.put("rar", "application/octet-stream");

		}

		String name = contenido.getName();

		int ipost = name.lastIndexOf(".");

		if (ipost == -1) {
			/**
			 * Se identifica que el archivo no posee extension, el mimetype
			 * quedara por defecto
			 */
			return;
		}

		String ext = name.substring(ipost + 1);

		String mime = mimeTypes.get(ext);
		/**
		 * Se cambia el mimetype por defecto si se encuentra un mimetype
		 * asociado a la extension del contenido ingresado
		 */
		if (mime != null) {
			contenido.setContentFormat(new ContentFormat(mime, Constantes.ENCODING_UTF8));
		}
	}
	
	
}