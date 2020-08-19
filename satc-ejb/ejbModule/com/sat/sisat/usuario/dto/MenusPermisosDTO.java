package com.sat.sisat.usuario.dto;
//-=CRAMIREZ=-
import java.io.Serializable;
import java.util.Date;

public class MenusPermisosDTO implements Serializable{
	private static final long serialVersionUID = 4636686895544895555L;

	private int menuId;
	
	private int subMenuId;
	
	private int nivelAccesoId;

	private String menu;

	private String submenu;
	
	private String url;

	private String nivelAcceso;
	
	private int tipoUrl;

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(int subMenuId) {
		this.subMenuId = subMenuId;
	}

	public int getNivelAccesoId() {
		return nivelAccesoId;
	}

	public void setNivelAccesoId(int nivelAccesoId) {
		this.nivelAccesoId = nivelAccesoId;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getSubmenu() {
		return submenu;
	}

	public void setSubmenu(String submenu) {
		this.submenu = submenu;
	}

	public String getNivelAcceso() {
		return nivelAcceso;
	}

	public void setNivelAcceso(String nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTipoUrl() {
		return tipoUrl;
	}

	public void setTipoUrl(int tipoUrl) {
		this.tipoUrl = tipoUrl;
	}

	
	
	
}
