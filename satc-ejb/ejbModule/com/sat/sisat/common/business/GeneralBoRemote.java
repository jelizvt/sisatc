package com.sat.sisat.common.business;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.Remote;

import com.sat.sisat.persistence.entity.GnTipoCambio;

@Remote
public interface GeneralBoRemote {

	public abstract GnTipoCambio obtenerTipoCambioDia(int tipoMonedaId);

	public abstract GnTipoCambio obtenerTipoCambio(int tipoCambioId);

	public abstract int ObtenerCorrelativoTabla(String tabla, int cantidad)
			throws Exception;

	public abstract String obtenerTerminalCliente() throws Exception;

	public abstract String ObtenerCorrelativoDocumento(String tabla,
			String columna) throws Exception;

	public abstract BigDecimal getUitAnio(int anio);
	
	/*
	 * funcion que trae el interes simple de un capital
	 * Autor: IVO TANTAMANGO
	 */
	public BigDecimal obtenerInteresSimple(BigDecimal capital,Date fechaInteres, Date fechaVencimiento, Date fechaConsulta) throws Exception;
}
