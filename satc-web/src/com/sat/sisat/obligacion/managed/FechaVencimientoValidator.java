package com.sat.sisat.obligacion.managed;

import java.util.Date;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;



@FacesValidator(value = "fechaVencimientoValidator")
public class FechaVencimientoValidator implements Validator {	

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Date date = (Date) value;
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Application app = fc.getApplication();
		ValueExpression ve = app.getExpressionFactory().createValueExpression(
				fc.getELContext(), String.format("#{%s}", "obligacionManaged"),
				Object.class);
		
		ObligacionManaged obligacionManaged =(ObligacionManaged)ve.getValue(fc.getELContext());
		
		Date dateEmision = obligacionManaged.getObligacionDTO().getFechaEmision();
		
		
		if(date.compareTo(dateEmision) <= 0){
			FacesMessage message = new FacesMessage();
			message.setDetail("La fecha de vencimiento tiene que ser mayor a la fecha de emisión");
			message.setSummary("La fecha de vencimiento tiene que ser mayor a la fecha de emisión");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}		
	}
}
		


