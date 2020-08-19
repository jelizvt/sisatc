package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CjPapeletaDTO implements Serializable{

	private static final long serialVersionUID = 496680749969137239L;

	private int papeletaId;
	private String  nroPapeleta;
	private String placa;
	private Date fechaInfraccion;
	private String infractor;
	private Integer deudaId;
	private String descInfraccion;
	private String nroResolucion;
	private Date fechaNotificacion;
	private BigDecimal montoSinDescuento;
	private BigDecimal montoConDescuento;
	
	public CjPapeletaDTO(int papeletaId,
			String nroPapeleta,
			String placa,
			Date fechaInfraccion,
			String infractor,
			Integer deudaId) {
		super();
		this.papeletaId = papeletaId;
		this.nroPapeleta = nroPapeleta;
		this.placa = placa;
		this.fechaInfraccion = fechaInfraccion;
		this.infractor = infractor;
		this.deudaId = deudaId;
	}

	public CjPapeletaDTO(){
	}

	public CjPapeletaDTO(int papeletaId, String nroPapeleta, String placa,
			Date fechaInfraccion, String infractor) {
		super();
		this.papeletaId = papeletaId;
		this.nroPapeleta = nroPapeleta;
		this.placa = placa;
		this.fechaInfraccion = fechaInfraccion;
		this.infractor = infractor;
	}

	public int getPapeletaId() {
		return papeletaId;
	}

	public void setPapeletaId(int papeletaId) {
		this.papeletaId = papeletaId;
	}

	public String getNroPapeleta() {
		return nroPapeleta;
	}

	public void setNroPapeleta(String nroPapeleta) {
		this.nroPapeleta = nroPapeleta;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Date getFechaInfraccion() {
		return fechaInfraccion;
	}

	public void setFechaInfraccion(Date fechaInfraccion) {
		this.fechaInfraccion = fechaInfraccion;
	}

	public String getInfractor() {
		return infractor;
	}

	public void setInfractor(String infractor) {
		this.infractor = infractor;
	}

	public Integer getDeudaId() {
		return deudaId;
	}

	public void setDeudaId(Integer deudaId) {
		this.deudaId = deudaId;
	}

	public String getDescInfraccion() {
		return descInfraccion;
	}

	public void setDescInfraccion(String descInfraccion) {
		this.descInfraccion = descInfraccion;
	}

	public String getNroResolucion() {
		return nroResolucion;
	}

	public void setNroResolucion(String nroResolucion) {
		this.nroResolucion = nroResolucion;
	}

	public BigDecimal getMontoSinDescuento() {
		return montoSinDescuento;
	}

	public void setMontoSinDescuento(BigDecimal montoSinDescuento) {
		this.montoSinDescuento = montoSinDescuento;
	}

	public BigDecimal getMontoConDescuento() {
		return montoConDescuento;
	}

	public void setMontoConDescuento(BigDecimal montoConDescuento) {
		this.montoConDescuento = montoConDescuento;
	}

	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
}
