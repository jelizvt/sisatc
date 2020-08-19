package com.sat.sisat.cuponera.business;

import javax.ejb.Local;

@Local
public interface CuponeraBoLocal {
	public int registraPersonaCuponera(Integer personaId) throws Exception;
}
