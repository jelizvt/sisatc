package com.sat.sisat.determinacion.vehicular.calculo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

public class BaseExonerada implements Serializable {

	/**
	 * Valida si el rango de inafectación contiene el primero de enero del año
	 * afecto.
	 * 
	 * @param anioAfec
	 *            Año afecto
	 * @param fechaIni
	 *            Fecha inicial de inafectación
	 * @param fechaFin
	 *            Fecha final de inafectación
	 * @return
	 */
	public static boolean isAfecto(int anioAfec, Date fechaIni, Date fechaFin) {
		Calendar calAfec = Calendar.getInstance();
		calAfec.set(anioAfec, 0, 1, 0, 0, 0);

		Calendar calIni = Calendar.getInstance();
		calIni.setTime(fechaIni);
		calIni.set(calIni.get(Calendar.YEAR), calIni.get(Calendar.MONTH),
				calIni.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

		Calendar calFin = Calendar.getInstance();
		calFin.setTime(fechaFin);
		calFin.set(calFin.get(Calendar.YEAR), calFin.get(Calendar.MONTH),
				calFin.get(Calendar.DAY_OF_MONTH), 23, 59, 59);

		long timeAfec = calAfec.getTimeInMillis();
		long timeIni = calIni.getTimeInMillis();
		long timeFin = calFin.getTimeInMillis();

		if (timeAfec >= timeIni && timeAfec <= timeFin) {
			return true;
		}

		return false;
	}

	/**
	 * Obitene el valor exonerado de acuerdo a la base imponible y el porcentaje
	 * de exoneración
	 * 
	 * @param baseImponible
	 *            Base imponible
	 * @param porcentajeExoner
	 *            Porcentaje de exoneración
	 * @return Base exonerada
	 */
	public BigDecimal getValorBaseExoner(BigDecimal baseImponible,
			BigDecimal porcentajeExoner) {

		BigDecimal porcentaje = porcentajeExoner.divide(new BigDecimal("100"),
				2, RoundingMode.HALF_UP);

		return baseImponible.multiply(porcentaje);
	}

	// public BigDecimal getValorInafecFijo(BigDecimal impuesto,
	// BigDecimal montoInfec) {
	// return impuesto.subtract(montoInfec);
	// }
}
