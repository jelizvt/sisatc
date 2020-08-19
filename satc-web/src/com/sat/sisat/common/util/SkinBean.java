package com.sat.sisat.common.util;

//import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
//import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;

@SessionScoped
@ManagedBean
public class SkinBean {
	// @ManagedProperty(value = "blueSky")
	private String skin = "blueSky";

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}
}
