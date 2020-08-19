package com.sat.sisat.common.dto;

import java.io.Serializable;

public class BaseDTO implements Serializable{

	private static final long serialVersionUID = -2837443580817953035L;

	private boolean selected;
	
	public BaseDTO(){
	}

	public BaseDTO(boolean selected) {
		super();
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
