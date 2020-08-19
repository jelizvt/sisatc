package com.sat.sisat.common.util;

import java.io.InputStream;
import java.util.Properties;

public final class SATParameterFactory {

	private static String DBNOMBRE_PARAM = "db.nombre";

	private static String DBSCHEME_PARAM = "db.scheme";

	private static String DBPERSONA_NOMBRE_PARAM = "dbPersona.nombre";

	private static String DBPERSONA_SCHEME_PARAM = "dbPersona.scheme";

	private static String DBDW_NOMBRE_PARAM = "dbDW.nombre";

	private static String DBDW_SCHEME_PARAM = "dbDW.scheme";

	private static volatile boolean loadedProperties = false;

	private static final String PROPERTY_FILE_NAME = "parameter.properties";

	private static String dbNombre = "satcdb";

	private static String dbScheme = "dbo";

	private static String dbPersonaNombre = "persona";

	private static String dbPersonaScheme = "dbo";

	private static String dbDWNombre = "satcdbdm";

	private static String dbDWScheme = "dbo";

	private static synchronized void loadProperties(String propertyFileName) {
		if (loadedProperties) {
			return;
		}
		Properties props = new Properties();
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);
		if (is != null) {

			try {
				props.load(is);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (props.getProperty(DBNOMBRE_PARAM) != null) {
			dbNombre = props.getProperty(DBNOMBRE_PARAM);
		}
		if (props.getProperty(DBSCHEME_PARAM) != null) {
			dbScheme = props.getProperty(DBSCHEME_PARAM);
		}
		if (props.getProperty(DBPERSONA_NOMBRE_PARAM) != null) {
			dbPersonaNombre = props.getProperty(DBPERSONA_NOMBRE_PARAM);
		}
		if (props.getProperty(DBPERSONA_SCHEME_PARAM) != null) {
			dbPersonaScheme = props.getProperty(DBPERSONA_SCHEME_PARAM);
		}
		if (props.getProperty(DBDW_NOMBRE_PARAM) != null) {
			dbDWNombre = props.getProperty(DBDW_NOMBRE_PARAM);
		}
		if (props.getProperty(DBDW_SCHEME_PARAM) != null) {
			dbDWScheme = props.getProperty(DBDW_SCHEME_PARAM);
		}

		loadedProperties = true;
	}

	public static String getDbNombre() {
		if (!loadedProperties) {
			loadProperties(PROPERTY_FILE_NAME);
		}
		return dbNombre;
	}

	public static String getDbScheme() {
		if (!loadedProperties) {
			loadProperties(PROPERTY_FILE_NAME);
		}
		return dbScheme;
	}

	public static String getDBNameScheme() {

		return getDbNombre().concat(".").concat(getDbScheme());
	}

	public static String getDbPersonaNombre() {
		if (!loadedProperties) {
			loadProperties(PROPERTY_FILE_NAME);
		}
		return dbPersonaNombre;
	}

	public static String getDbPersonaScheme() {
		if (!loadedProperties) {
			loadProperties(PROPERTY_FILE_NAME);
		}
		return dbPersonaScheme;
	}

	public static String getDBPersonaNameScheme() {

		return getDbPersonaNombre().concat(".").concat(getDbPersonaScheme());
	}

	public static String getDbDWNombre() {
		if (!loadedProperties) {
			loadProperties(PROPERTY_FILE_NAME);
		}
		return dbDWNombre;
	}

	public static String getDbDWScheme() {
		return dbDWScheme;
	}

	public static String getDBDWNameScheme() {
		if (!loadedProperties) {
			loadProperties(PROPERTY_FILE_NAME);
		}
		return getDbDWNombre().concat(".").concat(getDbDWScheme());
	}

}
