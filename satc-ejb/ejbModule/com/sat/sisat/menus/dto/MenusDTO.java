package com.sat.sisat.menus.dto;
//-=CRAMIREZ=-
import java.io.Serializable;

public class MenusDTO implements Serializable{
	private static final long serialVersionUID = 4636686895544895555L;

	private int menuId;
	
	private int subMenuId;

	private String menu;

	private String submenu;
	
	private String tipo;
	
	private String url;

	private String nivelAcceso;
	
	private Integer estado;
	
	private Integer perfilSubmenuId;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getPerfilSubmenuId() {
		return perfilSubmenuId;
	}

	public void setPerfilSubmenuId(Integer perfilSubmenuId) {
		this.perfilSubmenuId = perfilSubmenuId;
	}
	
	
	
}
