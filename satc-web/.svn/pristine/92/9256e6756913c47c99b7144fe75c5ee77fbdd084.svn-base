package com.sat.sisat.administracion.managed;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.sat.sisat.administracion.dto.CampoDTO;



@FacesConverter(value = "campoConverter")
public class CampoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		
		if (arg0 == null){throw new NullPointerException("context");}
		if (arg1 == null){throw new NullPointerException("component");}
		
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Application app = fc.getApplication();
		ValueExpression ve = app.getExpressionFactory().createValueExpression(
				fc.getELContext(), String.format("#{%s}", "administrarTablasManaged"),
				Object.class);
		
		AdministrarTablasManaged administrarTablasManaged =(AdministrarTablasManaged)ve.getValue(fc.getELContext());
		
		CampoDTO obj = administrarTablasManaged.getMapColumna().get(arg2);
		
		if(obj == null){
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Valor desconocido", "El campo no se encuentra en el map" );
					throw new ConverterException( message );
		}
		
		return obj;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg0 == null){throw new NullPointerException("context");}
		if (arg1 == null){throw new NullPointerException("component");}
		
		return ((CampoDTO)arg2).getAlias();
	}

}
