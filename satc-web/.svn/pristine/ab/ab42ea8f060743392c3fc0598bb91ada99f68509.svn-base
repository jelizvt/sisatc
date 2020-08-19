package com.sat.sisat.common.util;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class WebMessages implements Serializable {
	private static final long serialVersionUID = 16051001L;
    
	public static void messageFatal(Exception e) {
		StringWriter sw = new StringWriter(); 
        PrintWriter pw = new PrintWriter(sw); 
        e.printStackTrace(pw);
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage();
        message.setDetail(e.getMessage());
        message.setSummary(sw.getBuffer().toString().substring(0, sw.getBuffer().toString().length()>1000?1000:sw.getBuffer().toString().length()));
        message.setSeverity(message.SEVERITY_FATAL);
        context.addMessage("msg", message);
	}
	
    public static void messageError(String pMessage) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage();
            message.setDetail(pMessage);
            message.setSummary(pMessage);
            message.setSeverity(message.SEVERITY_ERROR);
            context.addMessage("msg", message);
            
    }
    
    public static void messageInfo(String pMessage) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage();
            message.setDetail(pMessage);
            message.setSummary(pMessage);
            message.setSeverity(message.SEVERITY_INFO);
            context.addMessage("msg", message);
    }
}
