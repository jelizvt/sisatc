package com.sat.sisat.usuario.managed;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.usuario.business.UsuarioBoRemote;

@ManagedBean
@ViewScoped
public class CambioClaveManaged extends BaseManaged {

	private static final long serialVersionUID = -1286197024506630776L;

	@EJB
	private UsuarioBoRemote userBo;

	private String claveActual;
	private String claveNueva;
	private String claveNuevaRepeat;
	private boolean saveCorrecto;
	private String saveMessage;

	public CambioClaveManaged() {
	}

	@PostConstruct
	public void inicialize() {

	}

	public String saveNewPass() {
		if(claveActual==null || claveActual.isEmpty() || claveNueva == null || claveNueva.isEmpty() || claveNuevaRepeat == null || claveNuevaRepeat.isEmpty()){
			saveMessage = "No se permiten datos vación, Por favor verifique";
			saveCorrecto = false;
			return null;
		}
		
		if(!claveNueva.equals(claveNuevaRepeat)){
			saveMessage = "Las nuevas claves no concuerdan";
			saveCorrecto = false;
			return null;
		}
		
		if(claveActual.equals(claveNueva)){
			saveMessage = "La nueva clave no puede ser igual a la anterior.";
			saveCorrecto = false;
			return null;
		}
		
		try{
			
			/** guardando el nuevo passwords */
			userBo.changePassword(getSessionManaged().getUsuarioLogIn().getUsuarioId(), claveActual, claveNueva);
			saveMessage = "Su clave ha sido cambiada con éxito. El sistema se reiniciar�.";
			saveCorrecto = true;
						
			Thread.sleep(2000);
			this.getServletRequest().getSession().invalidate();
			getExternalContext().redirect("inicio.jsf");
			
		}catch(SisatException ex){
			saveMessage = ex.getMessage();
			saveCorrecto = false;
		}catch(Exception ex){
			String msg = "No ha sido posible cambiar la clave";
			System.out.println(msg + ex);
		}
		return null;
	}
	
	public String clean(){
		setClaveActual("");
		setClaveNueva("");
		setClaveNuevaRepeat("");
		setSaveMessage("");
		return null;
	}

	public UsuarioBoRemote getUserBo() {
		return userBo;
	}

	public void setUserBo(UsuarioBoRemote userBo) {
		this.userBo = userBo;
	}

	public String getClaveActual() {
		return claveActual;
	}

	public void setClaveActual(String claveActual) {
		this.claveActual = claveActual;
	}

	public String getClaveNueva() {
		return claveNueva;
	}

	public void setClaveNueva(String claveNueva) {
		this.claveNueva = claveNueva;
	}

	public String getClaveNuevaRepeat() {
		return claveNuevaRepeat;
	}

	public void setClaveNuevaRepeat(String claveNuevaRepeat) {
		this.claveNuevaRepeat = claveNuevaRepeat;
	}

	public boolean isSaveCorrecto() {
		return saveCorrecto;
	}

	public void setSaveCorrecto(boolean saveCorrecto) {
		this.saveCorrecto = saveCorrecto;
	}

	public String getSaveMessage() {
		return saveMessage;
	}

	public void setSaveMessage(String saveMessage) {
		this.saveMessage = saveMessage;
	}
}
