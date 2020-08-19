package com.sat.sisat.util.exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;

public class ExceptionHandler extends ExceptionHandlerWrapper {
	 
	 private final javax.faces.context.ExceptionHandler wrapped;
	 
	 public ExceptionHandler(final javax.faces.context.ExceptionHandler wrapped) {
		 this.wrapped = wrapped;
	 }
	 
	 @Override
	 public javax.faces.context.ExceptionHandler getWrapped() {
		 return this.wrapped;
	 }
	 
	 @Override
	 public void handle() throws FacesException {
	  for (final Iterator<ExceptionQueuedEvent> it = getUnhandledExceptionQueuedEvents().iterator(); it.hasNext();) {
	   Throwable t = it.next().getContext().getException();
	   while ((t instanceof FacesException || t instanceof ELException)
	     && t.getCause() != null) {
	    t = t.getCause();
	   }
	   if (t instanceof FileNotFoundException || t instanceof Exception 
	     || t instanceof ViewExpiredException) {
	    final FacesContext facesContext = FacesContext.getCurrentInstance();
	    final ExternalContext externalContext = facesContext.getExternalContext();
	    final Map<String, Object> requestMap = externalContext.getRequestMap();
	    try {
	     //LOG.info("{}: {}", t.getClass().getSimpleName(), t.getMessage());
	     String message;
	     if (t instanceof ViewExpiredException) {
	      final String viewId = ((ViewExpiredException) t).getViewId();
	      message = "View is expired. <a href='/ifos"   +viewId   +"'>Back</a>";
	     } else {
	      message = t.getMessage(); // beware, don't leak internal info!
	     }
//	     System.out.println(message);
	     requestMap.put("errorMsg", message);
	     try {
	      externalContext.dispatch("/pages/error/error.jsp");
	     } catch (final IOException e) {
	    	 e.printStackTrace();
	     }
	     facesContext.responseComplete();
	    } finally {
	     it.remove();
	    }
	   }
	  }
	  getWrapped().handle();
	 }
	 
	}