package com.sat.sisat.cuponera.business;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import com.sat.sisat.cuponera.dao.CuponeraDao;
import com.sat.sisat.predial.business.BaseBusiness;

@Stateless
public class CuponeraBo extends BaseBusiness implements CuponeraBoRemote {

	private CuponeraDao service;

	public CuponeraDao getService() {
		return this.service;
	}

	@PostConstruct
	public void initialize() {
		this.service = new CuponeraDao();
		setDataManager(this.service);
	}

	public int registraPersonaCuponera(Integer personaId) {
		return service.registraPersonaCuponera(personaId);
	}
}
