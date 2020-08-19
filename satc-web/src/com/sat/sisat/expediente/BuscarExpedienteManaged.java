package com.sat.sisat.expediente;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.expediente.business.ExpedienteBoRemote;
import com.sat.sisat.expediente.dto.FindExpedienteByPersona;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorial;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;

@ManagedBean
@ViewScoped
public class BuscarExpedienteManaged extends BaseManaged {


	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;

	@EJB
	ExpedienteBoRemote expedienteBo;
	
	private List<FindExpedienteByPersona> lstExpedientes = new ArrayList<FindExpedienteByPersona>();
	
	private Integer personaId;

	public BuscarExpedienteManaged() {
		
	}
	
	@PostConstruct
	public void init() {
						
	}	
	public void buscar(){
		try {				
			
			lstExpedientes = expedienteBo.getAllExpedientesByPersona(personaId);					
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public List<FindExpedienteByPersona> getLstExpedientes() {
		return lstExpedientes;
	}

	public void setLstExpedientes(List<FindExpedienteByPersona> lstExpedientes) {
		this.lstExpedientes = lstExpedientes;
	}
	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}	
	
	

}
