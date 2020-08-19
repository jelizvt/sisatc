package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sat.sisat.persistence.entity.PaPapeleta;

public class CjPapeletaEntity extends PaPapeleta implements Serializable {
	
	private String infractorDes;
	private String nro_papeleta;
	private BigDecimal monto7;
	private BigDecimal monto7a15;
	private BigDecimal montoMayora15;
	private String CategoriaDes;
	private String marcaDes;
	private String modeloDes;
	private String observacion;
	private String placa;
	private boolean select;     
	
	public BigDecimal getMontoMayora15() {
		return montoMayora15;
	}
	public void setMontoMayora15(BigDecimal montoMayora15) {
		this.montoMayora15 = montoMayora15;
	}
	public BigDecimal getMonto7a15() {
		return monto7a15;
	}
	public void setMonto7a15(BigDecimal monto7a15) {
		this.monto7a15 = monto7a15;
	}
	public BigDecimal getMonto7() {
		return monto7;
	}
	public void setMonto7(BigDecimal monto7) {
		this.monto7 = monto7;
	}
	public String getNro_papeleta() {
		return nro_papeleta;
	}
	public void setNro_papeleta(String nro_papeleta) {
		this.nro_papeleta = nro_papeleta;
	}
	public String getInfractorDes() {
		return infractorDes;
	}
	public void setInfractorDes(String infractorDes) {
		this.infractorDes = infractorDes;
	}
	public String getCategoriaDes() {
		return CategoriaDes;
	}
	public void setCategoriaDes(String categoriaDes) {
		CategoriaDes = categoriaDes;
	}
	public String getModeloDes() {
		return modeloDes;
	}
	public void setModeloDes(String modeloDes) {
		this.modeloDes = modeloDes;
	}
	public String getMarcaDes() {
		return marcaDes;
	}
	public void setMarcaDes(String marcaDes) {
		this.marcaDes = marcaDes;
	}
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	

}
