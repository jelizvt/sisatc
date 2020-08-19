package com.sat.sisat.administracion.parameter;

import java.util.ResourceBundle;

/**
 * @author Miguel Macias	
 * @version 0.1
 * @since 31/07/2012
 * La clase ParameterLoader.java ha sido creada con el fin de exponer los parametros ingresados desde un archivo
*/
public class ParameterLoader {
	public static final String MESSAGE_PATH = "parameter";
	public static String getParameter(String key) {
		if (key == null) {
			return null;
		}
		try {
			ResourceBundle messages = ResourceBundle.getBundle(MESSAGE_PATH);
			return messages.getString(key);
		} catch (Exception e) {
			return key;
		}
	}
}
