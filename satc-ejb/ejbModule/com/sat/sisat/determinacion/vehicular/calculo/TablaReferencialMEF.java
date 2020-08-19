package com.sat.sisat.determinacion.vehicular.calculo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TablaReferencialMEF {

	public BigDecimal getValorAjusteMEF(int anioMenorAntig,
			BigDecimal montoMenorAntig, int anioFabric) {

		BigDecimal factor = getFactor(anioMenorAntig, anioFabric);
		if (factor == null) {
			return null;
		}

		return montoMenorAntig.multiply(factor);
	}

	public BigDecimal getFactor(int anioMenorAntig, int anioFabric) {

		BigDecimal diferen = new BigDecimal(anioMenorAntig)
				.subtract(new BigDecimal(anioFabric));

		if (diferen.doubleValue() < 0) {
			// TODO : Refactoring
//			System.out.println("Anio fabricación < anio de menor antiguedad");
			return null;
		}

		int maxAniosAtigConsiderar = 10;
		BigDecimal div = diferen.divide(new BigDecimal(maxAniosAtigConsiderar),
				4, RoundingMode.HALF_UP);

		BigDecimal factor = new BigDecimal(1).subtract(div);

		// Factor no puede ser menor que 0.1
		if (factor.doubleValue() < 0.1) {
			return new BigDecimal("0.1");
		}

		return factor;
	}
}
