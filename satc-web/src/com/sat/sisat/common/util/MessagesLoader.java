package com.sat.sisat.common.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class MessagesLoader {

	public static final String MESSAGE_PATH = "com.sat.sisat.messages.message";
	private static HashMap<String,Object> messageBundles = new HashMap<String,Object>();

	/**
	 * Obtiene una cadena(Mensaje), segun la llave dada, desde el archivo de
	 * mensajes
	 * 
	 * @param key
	 *            Llave para buscar la cadena(Mensaje)
	 * @return Cadena correspondiente a la llave ingresada. Si la llave no encuentra
	 *         el mensaje devuelve la misma llave.
	 */
	public static String getMessage(String key) {
		if (key == null) {
			return null;
		}
		try {
			Locale locale = FacesContext.getCurrentInstance().getViewRoot()
					.getLocale();
			if (locale == null) {
				locale = Locale.ENGLISH;
			}
			ResourceBundle messages = (ResourceBundle) messageBundles
					.get(locale.toString());
			if (messages == null) {
				messages = ResourceBundle.getBundle(MESSAGE_PATH, locale);
				messageBundles.put(locale.toString(), messages);
			}
			return messages.getString(key);
		} catch (Exception e) {
			return key;
		}
	}
}
