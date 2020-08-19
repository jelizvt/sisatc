package com.sat.sisat.common.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class FacesUtil {
	public static Object getSessionMapValue(FacesContext context, String key) {
        return context.getExternalContext().getSessionMap().get(key);
    }
    public static void setSessionMapValue(FacesContext context, String key, Object value) {
        context.getExternalContext().getSessionMap().put(key, value);
    }
    public static Object removeSessionMapValue(FacesContext context, String key) {
        return context.getExternalContext().getSessionMap().remove(key);
    }
    
    public static String getRequestParameter(String name) {
        return (String) FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap().get(name);
    }
    
    public static void closeSession(){
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Object session = externalContext.getSession(false);
		HttpSession httpSession = (HttpSession) session;
		httpSession.invalidate();
	}
    public static void closeSession(String namebean){
    	FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Object session = externalContext.getSession(false);
		HttpSession httpSession = (HttpSession) session;
		httpSession.removeAttribute(namebean);
	}
}
