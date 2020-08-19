package com.sat.sisat.usuario.dto;

import java.io.Serializable;

public class SubMenuDTO implements Serializable{
	
	private static final long serialVersionUID = 4636686895544895555L;

	private int menuId;
	
	private int subMenuId;

	private String submenu;
	
	private String url;
	
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

	public String getSubmenu() {
		return submenu;
	}

	public void setSubmenu(String submenu) {
		this.submenu = submenu;
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
