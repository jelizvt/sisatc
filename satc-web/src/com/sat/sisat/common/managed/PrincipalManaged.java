package com.sat.sisat.common.managed;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.SATParameterFactory;

@ManagedBean
@ViewScoped
public class PrincipalManaged extends BaseManaged {

	private static final long serialVersionUID = -50738742644178197L;

	private String nombreBD;
	
	public PrincipalManaged() {
	}

	@PostConstruct
	public void inicializar(){
		nombreBD = SATParameterFactory.getDbNombre();
	}
	
	public void tributosAction(ActionEvent ev){
		getSessionManaged().setModuloFisca(false);
	}
	
	public void fiscalizacionAction(ActionEvent ev){
		getSessionManaged().setModuloFisca(true);
	}

	public String getNombreBD() {
		return nombreBD;
	}

	public void setNombreBD(String nombreBD) {
		this.nombreBD = nombreBD;
	}
}
