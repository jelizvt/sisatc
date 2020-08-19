package com.sat.sisat.common.vo;

public class Mensaje {
	private String mensaje;
	private Integer tipoMensaje;
	private Boolean rendered;
	
	public Mensaje(){
		this.mensaje="";
		this.tipoMensaje=0;
		this.rendered=Boolean.FALSE;
	}
	
	public Mensaje(String msg,Integer tipo,Boolean rendered){
		this.mensaje=msg;
		this.tipoMensaje=tipo;
		this.rendered=rendered;
	}
	
	public Boolean getRendered() {
		return rendered;
	}
	public void setRendered(Boolean rendered) {
		this.rendered = rendered;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Integer getTipoMensaje() {
		return tipoMensaje;
	}
	public void setTipoMensaje(Integer tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}
	
	
}
