package com.sat.sisat.common.util;

import java.io.Serializable;

public class Page implements Serializable {
	private int currentPage;
	private int from;
	private int to;
	
	public Page(int currentPage,int from,int to){
		this.currentPage=currentPage;
		this.from=from;
		this.to=to;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	
	
	
}
