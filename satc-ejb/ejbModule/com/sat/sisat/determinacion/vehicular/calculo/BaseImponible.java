package com.sat.sisat.determinacion.vehicular.calculo;

import java.io.Serializable;
import java.math.BigDecimal;

public class BaseImponible implements Serializable {

	private BigDecimal valorRefencialMEF = BigDecimal.ZERO;

	/**
	 * Obtiene la base imponible comparando directamente el valor del mef con el
	 * valor de venta
	 * 
	 * @param valorMEF
	 *            Valor publicado por el MEF para el año afectado
	 * @param valorVenta
	 *            Valor de venta del vehículo
	 * @return Base imponible
	 */
	public BigDecimal getBaseImponible(BigDecimal valorMEF,
			BigDecimal valorVenta) {

		valorRefencialMEF = valorMEF;
		if (valorVenta.doubleValue() > valorMEF.doubleValue()) {
			return valorVenta;
		}

		return valorMEF;
	}

	/**
	 * Obtiene la base imponible haciendo cálculos de ajuste para el valor del
	 * mef y luego comparando con el valor de venta
	 * 
	 * @param anioMenorAntig
	 *            Año de menor antigüedad en la tabla del MEF
	 * @param montoMenorAntig
	 *            Monto para el año de menor antigüedad en la tabla del MEF
	 * @param anioFabric
	 *            Año de fabricación del vehículo
	 * @param valorVenta
	 *            Valor de venta del vahículo
	 * @return Base imponible
	 */
	public BigDecimal getBaseImponible(int anioMenorAntig,
			BigDecimal montoMenorAntig, int anioFabric, BigDecimal valorVenta) {

		BigDecimal valorMEF = (new TablaReferencialMEF()).getValorAjusteMEF(
				anioMenorAntig, montoMenorAntig, anioFabric);

		if (valorMEF == null) {
			return null;
		}

		valorRefencialMEF = valorMEF;
		if (valorVenta.doubleValue() > valorMEF.doubleValue()) {
			return valorVenta;
		}

		return valorMEF;
	}

	public BigDecimal getValorRefencialMEF() {
		return valorRefencialMEF;
	}
}