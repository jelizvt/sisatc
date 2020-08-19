package com.sat.sisat.common.business;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.persistence.entity.GnTipoCambio;
import com.sat.sisat.predial.business.BaseBusiness;

@Stateless
public class GeneralBo extends BaseBusiness implements GeneralBoRemote {

	private GeneralDao service;

	public GeneralBo() {
	}

	public GeneralDao getService() {
		return this.service;
	}

	@PostConstruct
	public void initialize() {
		this.service = new GeneralDao();
		setDataManager(this.service);	
	}

	public GnTipoCambio obtenerTipoCambioDia(int tipoMonedaId) {
		return getService().obtenerTipoCambioDia(tipoMonedaId);
	}

	/**
	 * Obtiene el tipo de cambio segun su clarve primaria
	 */
	public GnTipoCambio obtenerTipoCambio(int tipoCambioId) {
		return getService().obtenerTipoCambio(tipoCambioId);
	}

	public int ObtenerCorrelativoTabla(String tabla, int cantidad)
			throws Exception {
		return getService().ObtenerCorrelativoTabla(tabla, cantidad);
	}

	public String obtenerTerminalCliente() throws Exception {
		return getService().obtenerTerminalCliente();
	}

	public String ObtenerCorrelativoDocumento(String tabla, String columna)
			throws Exception {
		return getService().ObtenerCorrelativoDocumento(tabla, columna);
	}

	public BigDecimal getUitAnio(int anio) {
		return getService().getUitAnio(anio);
	}

	/*
	 * funcion que trae el interes simple de un capital
	 * Autor: IVO TANTAMANGO
	 */
	public BigDecimal obtenerInteresSimple(BigDecimal capital,
			Date fechaInteres, Date fechaVencimiento, Date fechaConsulta)
			throws Exception {
		// TODO Auto-generated method stub
		return getService().obtenerInteresSimple(capital, fechaInteres, fechaVencimiento, fechaConsulta);
	}
}
