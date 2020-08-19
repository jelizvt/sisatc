package com.sat.sisat.common.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


@Named
@SessionScoped
public class UserSession implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 435601397374471082L;

	private Integer usuarioId;
	
	private String usuario;
	
	private String terminal;
	
	//Usuario por defecto 1:servicios;127.0.0.1
	
	public Integer getUsuarioId() {
		return (usuarioId==null?1:usuarioId);
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getUsuario() {
		 return (usuario==null?"servicios":usuario);
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTerminal() {
		return (terminal==null?"127.0.0.1":terminal);
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	
	public String toString(){
		
		return "Datos/ usuarioId:".concat(getUsuarioId().toString()).concat(" /usuario: ").concat(getUsuario()).concat(" /terminal: ").concat(getTerminal());
	}
}
