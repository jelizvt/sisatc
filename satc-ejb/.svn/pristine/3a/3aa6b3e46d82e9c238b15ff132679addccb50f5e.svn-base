package com.sat.sisat.expediente.business;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.sat.sisat.expediente.dao.ExpedienteBusinessDao;
import com.sat.sisat.expediente.dto.FindExpedienteByPersona;
import com.sat.sisat.fiscalizacion.dao.FiscalizacionBusinessDao;
import com.sat.sisat.fiscalizacion.dto.FindInpscDocTipo;
import com.sat.sisat.predial.business.BaseBusiness;

/**
 * Session Bean implementation class ExpedienteBo
 */
@Stateless
public class ExpedienteBo extends BaseBusiness implements ExpedienteBoRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExpedienteBusinessDao service;
	
	public ExpedienteBusinessDao getService() {
		return this.service;
	}

	@PostConstruct
	public void initialize() {
		this.service = new ExpedienteBusinessDao();
		setDataManager(this.service);
	}	

	public List<FindExpedienteByPersona> getAllExpedientesByPersona(Integer personaId) throws Exception {
		return getService().getAllExpedientesByPersona(personaId);
	}	

}
