package com.sat.sisat.persona.managed;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.persona.dto.ValidaUbicacionDTO;

@ManagedBean
@ViewScoped
public class ValidaUbicacionManaged extends BaseManaged{
	
	private Integer persona_id;
	private Integer persona_select_id;
	private Integer estado_select = 0;
	private Integer flagUbicableControl;
	private String glosa = "";
	private List<ValidaUbicacionDTO> FindPersonasValidaUbicacion;
	
	@EJB
	PersonaBoRemote personaBo;
	
	
	@PostConstruct
	public void init(){
		this.buscar();
	}
	
	public void buscar() {
		try {
			if(persona_id == null) {
				persona_id = 0;
			}
			FindPersonasValidaUbicacion = personaBo.getValidaPersonaUbicacion(persona_id);
			if(persona_id == 0) {
				persona_id = null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void selectValidaUbicacionVal(Integer persona_id, Integer flag ){
		persona_select_id = persona_id;
		estado_select = 3;
		flagUbicableControl = flag;
	}
	public void selectValidaUbicacionAnul(Integer persona_id, Integer flag ){
		persona_select_id = persona_id;
		estado_select = 4;
		flagUbicableControl = flag;
	}
	
	public void changeEstado() {
		try {
			if (persona_select_id == null || persona_select_id == 0 
					|| estado_select == null || estado_select == 0	
					|| flagUbicableControl == null ) {
					 addErrorMessage("Ocurrio un error, actualice su navegador.");
					return;
				}
			
			if (glosa.equals("") && glosa != null && estado_select == 4) {
				 addErrorMessage("Por favor, ingrese observaciones.");
				return;
			}
			
			personaBo.changeFlagUbicableVerifica(persona_select_id,estado_select, glosa, flagUbicableControl);
			this.buscar();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public Integer getPersona_id() {
		return persona_id;
	}
	public void setPersona_id(Integer persona_id) {
		this.persona_id = persona_id;
	}

	public List<ValidaUbicacionDTO> getFindPersonasValidaUbicacion() {
		return FindPersonasValidaUbicacion;
	}

	public void setFindPersonasValidaUbicacion(List<ValidaUbicacionDTO> findPersonasValidaUbicacion) {
		FindPersonasValidaUbicacion = findPersonasValidaUbicacion;
	}

	public Integer getPersona_select_id() {
		return persona_select_id;
	}

	public void setPersona_select_id(Integer persona_select_id) {
		this.persona_select_id = persona_select_id;
	}

	public Integer getEstado_select() {
		return estado_select;
	}

	public void setEstado_select(Integer estado_select) {
		this.estado_select = estado_select;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	
	
	
}
