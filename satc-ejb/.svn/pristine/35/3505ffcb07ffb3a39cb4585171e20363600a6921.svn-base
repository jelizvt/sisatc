package com.sat.sisat.determinacion.vehicular.calculo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ImpuestoVehicular implements Serializable{

	public ImpuestoVehicular() {
	}

	private static final long serialVersionUID = 1L;
	
	private BaseImponible baseImpCalculo = new BaseImponible();
	private BaseExonerada baseExonerCalculo = new BaseExonerada();

	private BigDecimal valorMEF;
	private int anioMenorAntig;
	private BigDecimal montoMenorAntig;

	private int anioFabric;
	private BigDecimal valorVenta;

	// private BigDecimal valorInafecFijo;
	private BigDecimal porcentajeExoner;

	private BigDecimal tasaImpuesto;

	private BigDecimal impuestoMinimo;
	private BigDecimal porcentajePropiedad;

	// Variables de retorno
	private BigDecimal baseImponible;


	private BigDecimal baseExonerada;
	private BigDecimal baseAfecta;
	private BigDecimal impuestoTotal;
	private BigDecimal impuestoParcial;
	private BigDecimal valoreRefencialMEF;
	
	


	//Inicio Ivo
	private BigDecimal ajuste;
	private BigDecimal valorAjuste;

	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}
	
	public void setValoreRefencialMEF(BigDecimal valoreRefencialMEF) {
		this.valoreRefencialMEF = valoreRefencialMEF;
	}
	public BigDecimal getTasaImpuesto() {
		return tasaImpuesto;
	}

	public void setTasaImpuesto(BigDecimal tasaImpuesto) {
		this.tasaImpuesto = tasaImpuesto;
	}

	public BigDecimal getAjuste() {
		return ajuste;
	}

	public void setAjuste(BigDecimal ajuste) {
		this.ajuste = ajuste;
	}

	public BigDecimal getValorAjuste() {
		return valorAjuste;
	}

	public void setValorAjuste(BigDecimal valorAjuste) {
		this.valorAjuste = valorAjuste;
	}
	
	//Fin Ivo
	public ImpuestoVehicular(BigDecimal valorMEF, int anioMenorAntig,
			BigDecimal montoMenorAntig, int anioFabric, BigDecimal valorVenta,
			BigDecimal porcentajeExoner, BigDecimal tasaImpuesto,
			BigDecimal impuestoMinimo, BigDecimal porcentajePropiedad) {
		super();
		this.valorMEF = valorMEF;
		this.anioMenorAntig = anioMenorAntig;
		this.montoMenorAntig = montoMenorAntig;
		this.anioFabric = anioFabric;
		this.valorVenta = valorVenta;
		// this.valorInafecFijo = valorInafecFijo;
		this.porcentajeExoner = porcentajeExoner;
		this.tasaImpuesto = tasaImpuesto;
		this.impuestoMinimo = impuestoMinimo;
		this.porcentajePropiedad = porcentajePropiedad;
		hacerCalculos();
	}

	private void hacerCalculos() {
		if (valorMEF == null) {
			if(montoMenorAntig == null){
				return;
			}
			baseImponible = baseImpCalculo.getBaseImponible(anioMenorAntig,	montoMenorAntig, anioFabric, valorVenta);
			valoreRefencialMEF = baseImpCalculo.getValorRefencialMEF();
		} else {
			baseImponible = baseImpCalculo.getBaseImponible(valorMEF,valorVenta);
			valoreRefencialMEF = valorMEF;
		}
		if (baseImponible != null) {
			if (porcentajeExoner == null
					|| porcentajeExoner.equals(new BigDecimal("0"))) {
				baseExonerada = new BigDecimal("0");
			} else if (porcentajeExoner.equals(new BigDecimal("100"))) {
				baseExonerada = baseImponible;
			} else {
				baseExonerada = baseExonerCalculo.getValorBaseExoner(
						baseImponible, porcentajeExoner);
			}

			baseAfecta = baseImponible.subtract(baseExonerada);
			impuestoTotal = baseAfecta.multiply(tasaImpuesto);

			// Considera impuesto mínimo
			if (impuestoMinimo != null) {
				if (impuestoTotal.doubleValue() < impuestoMinimo.doubleValue()) {
					impuestoTotal = impuestoMinimo;
				}
			}

			if (porcentajePropiedad.doubleValue() < 100) {
				impuestoParcial = impuestoTotal.multiply(porcentajePropiedad).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
			} else {
				impuestoParcial = impuestoTotal;
			}
		}
	}

	public BigDecimal getBaseImponible() {
		return baseImponible;
	}

	public BigDecimal getBaseExonerada() {
		return baseExonerada;
	}

	public BigDecimal getBaseAfecta() {
		return baseAfecta;
	}

	public BigDecimal getImpuestoTotal() {
		return impuestoTotal.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getImpuestoParcial() {
		return impuestoParcial.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getValoreRefencialMEF() {
		return valoreRefencialMEF;
	}
}