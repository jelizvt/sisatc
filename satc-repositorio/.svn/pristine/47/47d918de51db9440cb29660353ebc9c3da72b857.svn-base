package com.sat.sisat.alfresco.util;

import java.io.InputStream;
import java.util.Properties;

public final class AlfrescoRepoFactory {
	
	
	private static final String URL_REPOSITORIO = "repository.location";
	
	private static final String USER_PARAM = "repository.user";
	private static final String PASSWORD_PARAM = "repository.password";
	
	
	private static volatile boolean loadedProperties = false;		
	
	private static final String PROPERTY_FILE_NAME = "alfresco/webserviceclient.properties";
	
	private static String user="admin";
	
	private static String password="admin";
	
	private static String url = "http://localhost:8080/alfresco/api";
	
	
	
	private static synchronized void loadProperties(String propertyFileName)
    {
        if (loadedProperties)
        {
            return;
        }
        Properties props = new Properties();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);
        if (is != null)
        {
            // Load from the file
            try
            {
                props.load(is);            
            }
            catch (Exception e)
            {
                // Do nothing, just use the default endpoint
                //logger.debug("Unable to load webservice client properties from " + propertyFileName + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        // Add defaults for any properties not set
        
        if (props.getProperty(USER_PARAM) != null)
        {
        	user = props.getProperty(USER_PARAM);
        }
        if (props.getProperty(PASSWORD_PARAM) != null)
        {
            password = props.getProperty(PASSWORD_PARAM);
        }if(props.getProperty(URL_REPOSITORIO) != null){
        	url = props.getProperty(URL_REPOSITORIO);
        }
        
        loadedProperties = true;
    }

	public static String getUser() {
		if(!loadedProperties) {
			loadProperties(PROPERTY_FILE_NAME);
		}
		return user;
	}
	
	public static String getPassword() {
		if(!loadedProperties) {
			loadProperties(PROPERTY_FILE_NAME);
		}
		return password;
	}

	public static String getUrl() {
		return url;
	}
}
