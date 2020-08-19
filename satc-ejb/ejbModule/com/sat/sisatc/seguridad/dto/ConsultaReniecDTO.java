package com.sat.sisatc.seguridad.dto;

import java.io.Serializable;
import java.sql.Date;

public class ConsultaReniecDTO implements Serializable{
	
	String  dniConsulta;
	String dniConsultado;	
	Date fechConsulta	;
	Integer usuarioID	;
	String terminal;
	
	public String getDniConsulta() {
		return dniConsulta;
	}
	public void setDniConsulta(String dniConsulta) {
		this.dniConsulta = dniConsulta;
	}
	public String getDniConsultado() {
		return dniConsultado;
	}
	public void setDniConsultado(String dniConsutlado) {
		this.dniConsultado = dniConsutlado;
	}
	public Date getFechConsulta() {
		return fechConsulta;
	}
	public void setFechConsulta(Date fechConsulta) {
		this.fechConsulta = fechConsulta;
	}
	public Integer getUsuarioID() {
		return usuarioID;
	}
	public void setUsuarioID(Integer usuarioID) {
		this.usuarioID = usuarioID;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	
	
	

}
