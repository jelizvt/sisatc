package com.sat.sisat.common.util;


public class Paginator {
	private int rowsPerPage=0;
	private int totalRows=0;
	
	public Paginator(int totalRows,int rowsPerPage){
		this.rowsPerPage=rowsPerPage;
		this.totalRows=totalRows;
	}
	
	private int size(){
		return this.totalRows;
	}
	
	public int maxPages(){
		 return size() % this.rowsPerPage == 0?size() / this.rowsPerPage:(size()/ this.rowsPerPage) + 1;
	}
	
	public Page get(int index){
		int from = Math.max(0,index*this.rowsPerPage)+1;
		int to = Math.min(size(),(index+1)*this.rowsPerPage);
		return new Page(index,from,to);
	}
}
