package com.sat.sisat.common.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;



@FacesConverter(value = "uppeCaseConverter")
public class UpperCaseConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		
		if (arg0 == null){throw new NullPointerException("context");}
		if (arg1 == null){throw new NullPointerException("component");}		
		
		return arg2.toUpperCase();
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg0 == null){throw new NullPointerException("context");}
		if (arg1 == null){throw new NullPointerException("component");}
		
		return arg2.toString();
	}

}
