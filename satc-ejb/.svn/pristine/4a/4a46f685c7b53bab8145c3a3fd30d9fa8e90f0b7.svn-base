package com.sat.sisat.imputacion.business;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.sat.sisat.exception.SisatException;
import com.sat.sisat.imputacion.dao.ImputacionDao;
import com.sat.sisat.predial.business.BaseBusiness;

@Stateless
public class InputacionBo extends BaseBusiness implements InputacionBoRemote {

	private ImputacionDao inputacionDao;

	public InputacionBo() {
	}

	@PostConstruct
	public void initialize() {
		this.inputacionDao = new ImputacionDao();
    	setDataManager(this.inputacionDao);
	}

	public void inputarDeudaSinValores(int deterPreviaId, int deterId,
			int usuarioId, String terminal) throws SisatException {
		try {
			inputacionDao.inputarDeudaSinValores(deterPreviaId, deterId,
					usuarioId, terminal);
		} catch (SisatException ex) {
			throw ex;
		} catch (Exception ex) {
			String msg = "No se ha podido inputar deudas anteriores";
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
	}
}
