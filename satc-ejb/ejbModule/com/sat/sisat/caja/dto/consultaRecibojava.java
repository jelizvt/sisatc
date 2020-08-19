package com.sat.sisat.caja.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class consultaRecibojava implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	
	private int 	recibo_id;
	private String 	tipo_recibo;
	private String 	flag_extorno;
	private String 	nro_recibo;
	private Date 	fecha_pago;	
	
	private String datos;

	private String tributo;
	private String cuotas;
	private int periodo;
	private int persona_id;
	private String nro_papeleta;	

	private BigDecimal monto_deuda;
	private BigDecimal monto_descuento;
	private BigDecimal monto_pagado;

	private BigDecimal total_deuda;
	private BigDecimal total_descuento;
	private BigDecimal total_pagado;
	
	
	
	public int getRecibo_id() {
		return recibo_id;
	}
	public void setRecibo_id(int recibo_id) {
		this.recibo_id = recibo_id;
	}
	public String getTipo_recibo() {
		return tipo_recibo;
	}
	public void setTipo_recibo(String tipo_recibo) {
		this.tipo_recibo = tipo_recibo;
	}
	public String getFlag_extorno() {
		return flag_extorno;
	}
	public void setFlag_extorno(String flag_extorno) {
		this.flag_extorno = flag_extorno;
	}
	public String getNro_recibo() {
		return nro_recibo;
	}
	public void setNro_recibo(String nro_recibo) {
		this.nro_recibo = nro_recibo;
	}
	public Date getFecha_pago() {
		return fecha_pago;
	}
	public void setFecha_pago(Date fecha_pago) {
		this.fecha_pago = fecha_pago;
	}
	public String getDatos() {
		return datos;
	}
	public void setDatos(String datos) {
		this.datos = datos;
	}
	public String getTributo() {
		return tributo;
	}
	public void setTributo(String tributo) {
		this.tributo = tributo;
	}
	public String getCuotas() {
		return cuotas;
	}
	public void setCuotas(String cuota) {
		this.cuotas = cuota;
	}
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public int getPersona_id() {
		return persona_id;
	}
	public void setPersona_id(int persona_id) {
		this.persona_id = persona_id;
	}
	public String getNro_papeleta() {
		return nro_papeleta;
	}
	public void setNro_papeleta(String nro_papeleta) {
		this.nro_papeleta = nro_papeleta;
	}
	public BigDecimal getMonto_deuda() {
		return monto_deuda;
	}
	public void setMonto_deuda(BigDecimal monto_deuda) {
		this.monto_deuda = monto_deuda;
	}
	public BigDecimal getMonto_descuento() {
		return monto_descuento;
	}
	public void setMonto_descuento(BigDecimal monto_descuento) {
		this.monto_descuento = monto_descuento;
	}
	public BigDecimal getMonto_pagado() {
		return monto_pagado;
	}
	public void setMonto_pagado(BigDecimal monto_pagado) {
		this.monto_pagado = monto_pagado;
	}
	public BigDecimal getTotal_deuda() {
		return total_deuda;
	}
	public void setTotal_deuda(BigDecimal total_deuda) {
		this.total_deuda = total_deuda;
	}
	public BigDecimal getTotal_descuento() {
		return total_descuento;
	}
	public void setTotal_descuento(BigDecimal total_descuento) {
		this.total_descuento = total_descuento;
	}
	public BigDecimal getTotal_pagado() {
		return total_pagado;
	}
	public void setTotal_pagado(BigDecimal total_pagado) {
		this.total_pagado = total_pagado;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
		
	
}
