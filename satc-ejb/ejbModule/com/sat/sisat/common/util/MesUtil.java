package com.sat.sisat.common.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

//import com.satc.sisat.persistence.entity.RjMes;

public class MesUtil {
	
	 private static MesUtil instance=new MesUtil();
	 //private BusinessDao dao=new BusinessDao();
	 private HashMap<String, String> mapRjMes=new HashMap<String, String>();
	 
	 
	 public MesUtil(){
		 /*try{
			 List<RjMes> lstRjMes=dao.getAllRjMes();
			 Iterator<RjMes> itm = lstRjMes.iterator();
			 while (itm.hasNext()){
		     	RjMes obj = itm.next();  
		     	mapRjMes.put(obj.getMesId(),obj.getDescripcion().trim());
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }*/
	 }
	   
	 public String getCurrentDateWord(){
			String fechaLetras; 
			HashMap<Integer, String> mapIntRjMes=new HashMap<Integer, String>();
			mapIntRjMes.put(1, "Enero");
			 mapIntRjMes.put(2, "Febrero");
			 mapIntRjMes.put(3, "Marzo");
			 mapIntRjMes.put(4, "Abril");
			 mapIntRjMes.put(5, "Mayo");
			 mapIntRjMes.put(6, "Junio");
			 mapIntRjMes.put(7, "Julio");
			 mapIntRjMes.put(8, "Agosto");
			 mapIntRjMes.put(9, "Setiembre");
			 mapIntRjMes.put(10, "Octubre");
			 mapIntRjMes.put(11, "Noviembre");
			 mapIntRjMes.put(12, "Diciembre");
			 
			Calendar calendar = Calendar.getInstance();
			int dia=calendar.get(Calendar.DAY_OF_MONTH);
			int mes=calendar.get(Calendar.MONTH)+1;
			int anio=calendar.get(Calendar.YEAR);
			fechaLetras=dia+" de "+mapIntRjMes.get(mes)+" del "+anio;
			return fechaLetras;
	 }
	 
	 public static MesUtil getInstance() {
	        return instance;
	 }
	 public String getDescricion(String codigo){
		 return mapRjMes.get(codigo);
	 }
     
}
