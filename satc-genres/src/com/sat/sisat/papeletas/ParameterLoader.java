package com.sat.sisat.papeletas;

import java.util.ResourceBundle;

public class ParameterLoader {
	
	public static final String MESSAGE_PATH = "com.sat.sisat.papeletas.parameter";
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
