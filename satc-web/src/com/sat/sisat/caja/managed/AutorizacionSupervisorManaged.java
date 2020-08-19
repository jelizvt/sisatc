package com.sat.sisat.caja.managed;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.caja.business.CajaBoRemote;

@ManagedBean
@ViewScoped
public class AutorizacionSupervisorManaged {
	
	@EJB
	CajaBoRemote cajaBo;
	
	private String username;
	private String password;
	
	public AutorizacionSupervisorManaged(){
	}
	
	@PostConstruct
	public void init(){   
	}
	
	public String Autorizacion() {

	if(username.equals("usuario") && password.equals(""))
		return "ok";
	else if (username.equals(""))
		return "";
	else {
		username ="Reintentar";
		return "MalUsuario";
	}
}
/*
  public String validarUsuario() {
            if (username.equals("usuario") &&  password.equals("sesamo"))
                  return "ok";
            else if (username.equals("test"))
                  return "test";
            else {
                  username = "Reintentar";
                  return "badUsername";
            }
      }
 */
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

}

