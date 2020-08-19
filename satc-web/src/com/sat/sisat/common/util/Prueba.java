package com.sat.sisat.common.util;

public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Paginator paginator=new Paginator(22657, 1000);
		int length=paginator.maxPages();
		for(int i=0;i<length;i++){
			Page page=paginator.get(i);
			System.out.print(i+" ");
			System.out.print(page.getFrom());
			System.out.print(" ");
			System.out.println(page.getTo());
		}
	}

}
