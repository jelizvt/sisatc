package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PagoTupaReferenciaDTO implements Serializable {

	private static final long serialVersionUID = 5573380850666318350L;

	private Integer personaId  = null;
	
	private String apellidosNombres;
	
	private String placa;
	
	private String dniRuc;

	private String obs;
	
	private String fuente; 
	
	private BigDecimal total = new BigDecimal("0.00");
	private BigDecimal descuento = new BigDecimal("0.00");
	
	public PagoTupaReferenciaDTO() {
		super();
	}
	
	public String getPlaca() {
		return placa;
	}

	public String getDniRuc() {
		return dniRuc;
	}	

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public void setDniRuc(String dniRuc) {
		this.dniRuc = dniRuc;
	}
	
	public String toString(){
		
		
		StringBuilder sb = new StringBuilder();
		if(dniRuc != null && !dniRuc.isEmpty()){
			sb.append("Dni/Ruc: ").append(this.dniRuc).append(" ");
		}		
		if(apellidosNombres != null && !apellidosNombres.isEmpty()){
			sb.append("Nombres: ").append(this.apellidosNombres).append(" ");
		}		
		if(placa != null && !placa.isEmpty()){
			sb.append("Placa: ").append(this.placa).append(" ");
		}		
		if(obs != null && !obs.isEmpty()){
			sb.append("Obs: ").append(obs);
		}			
		
		return sb.toString().trim();
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getApellidosNombres() {
		return apellidosNombres;
	}

	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}
	
	
}
