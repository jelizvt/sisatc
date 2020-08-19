package com.sat.sisat.papeletas;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import com.sat.sisat.papeletas.dto.ResolucionDTO;

public class TaskManager implements Runnable {
	private ArrayList<ResolucionDTO> lResolucion=new ArrayList<ResolucionDTO>();
	
	private String templatePath;
	private String resolucionRootPath;
	private String papeletaRootPath;
	private String logosatcPath;
	//--
	private String nomApeResponsableSat;
	private String cargoResponsableSat;
	
	public TaskManager(ArrayList<ResolucionDTO> list){
		lResolucion.addAll(list);
		init();
	}
	
	public void init(){
		this.templatePath="/com/sat/sisat/papeletas/template.pdf";
		this.logosatcPath="/com/sat/sisat/papeletas/logosatc.jpg";
		//--
		this.resolucionRootPath=ParameterLoader.getParameter("resolucion_root_path");
		this.papeletaRootPath=ParameterLoader.getParameter("papeleta_root_path");
		this.nomApeResponsableSat=ParameterLoader.getParameter("nom_ape_responsable_sat");
		this.cargoResponsableSat=ParameterLoader.getParameter("cargo_responsable_sat");
	}
	
	public void run(){
		try{
			Iterator<ResolucionDTO> it=lResolucion.iterator();
			URL logosatc_img =  this.getClass().getResource(this.logosatcPath);
			URL template_pdf =  this.getClass().getResource(this.templatePath);
			
			while(it.hasNext()){
				ResolucionDTO obj=it.next();
				obj.setNomApeResponsableSat(this.nomApeResponsableSat);
				obj.setCargoResponsableSat(this.cargoResponsableSat);
				Generator.generate(obj, template_pdf, this.resolucionRootPath, this.papeletaRootPath, logosatc_img);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
