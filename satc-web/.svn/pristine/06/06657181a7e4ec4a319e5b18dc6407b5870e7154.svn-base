package com.sat.sisat.obligacion.managed;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;



@FacesValidator(value = "nroValorValidator")
public class NroValorValidator implements Validator {

	private static final String VALOR_REGEX = "^([0-9][0-9][0-9])(-([0-9][0-9][0-9]-[0-9]{8})){1}$";

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String nrovalor = (String) value;
		Pattern mask = null;
		mask = Pattern.compile(VALOR_REGEX);
		Matcher matcher = mask.matcher(nrovalor);
		if (!matcher.matches()) {
			FacesMessage message = new FacesMessage();
			message.setDetail("El Nro Valor ingresado es incorrecto");
			message.setSummary("El Nro Valor ingresado es incorrecto");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
}
		


